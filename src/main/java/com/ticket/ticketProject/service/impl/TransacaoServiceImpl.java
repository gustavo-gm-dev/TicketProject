package com.ticket.ticketProject.service.impl;

import com.ticket.ticketProject.model.*;
import com.ticket.ticketProject.model.dto.transacao.CadastrarTransacaoDTO;
import com.ticket.ticketProject.model.dto.transacao.TransacaoDTO;
import com.ticket.ticketProject.model.mapper.TransacaoMapper;
import com.ticket.ticketProject.repository.*;
import com.ticket.ticketProject.service.TransacaoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransacaoServiceImpl implements TransacaoService {

    @Autowired
    TransacaoRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteIngressoRepository clienteIngressoRepository;

    @Autowired
    VendedorIngressoRepository vendedorIngressoRepository;

    @Autowired
    IngressoRepository ingressoRepository;

    @Autowired
    TransacaoMapper mapper;

    @Override
    public List<TransacaoDTO> getAllTransacoes(Long idIngresso) {
        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(idIngresso);
        if(ingressoOptional.isEmpty()){
            throw new EntityNotFoundException("Ingresso não encontrado com o ID: " + idIngresso);
        }

        Ingresso ingresso = ingressoOptional.get();

        Optional<List<Transacao>> transacoesOptional = repository.findTransacaoByIngresso(ingresso);
        List<Transacao> transacoes = transacoesOptional.get();
        List<TransacaoDTO> transacoesDTOs = transacoes.stream()
                .map(mapper::transacaoToTransacaoDTO)
                .collect(Collectors.toList());
        return transacoesDTOs;
    }

    @Override
    public TransacaoDTO createTransacao(CadastrarTransacaoDTO dto) {
        Long idIngresso = dto.idIngresso();
        Long idCliente = dto.idCliente();
        //Busca Ingresso
        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(idIngresso);
        if(ingressoOptional.isEmpty()){
            throw new EntityNotFoundException("Ingresso não encontrado com o ID: " + idIngresso);
        }

        Ingresso ingresso = ingressoOptional.get();
        if (!ingresso.getEstado().equals(EstadoIngresso.DISPONIVEL)) {
            // Validar se o status está disponível
            throw new IllegalStateException("Ingresso não está disponível para transação.");
        }

        // Alterar status do ingresso
        ingresso.setEstado(EstadoIngresso.RESERVADO);
        ingresso = ingressoRepository.save(ingresso);

        //Busca Usuario Cliente
        Optional<Usuario> clienteOptional = usuarioRepository.findById(idCliente);
        if(clienteOptional.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + idCliente);
        }

        //Atrela Cliente com Ingresso
        Usuario cliente = clienteOptional.get();
        ClienteIngresso clienteIngresso = new ClienteIngresso(null, cliente, ingresso);
        clienteIngresso = clienteIngressoRepository.save(clienteIngresso);

        // Busca o vendedor do Ingresso
        VendedorIngresso vendedorIngresso = ingresso.getVendedorIngressos();
        if (vendedorIngresso == null) {
            throw new IllegalStateException("Vendedor não encontrado para o ingresso ID: " + idIngresso);
        }

        // Criar Transação
        Transacao transacao = new Transacao(
                null,
                LocalDate.now(),
                clienteIngresso,
                vendedorIngresso,
                ingressoOptional.get(),
                dto.valor(),
                EstadoTransacao.PENDENTE
        );

        transacao = repository.save(transacao);

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                transacao.getId(),
                transacao.getData(),
                transacao.getClienteIngresso().getId(),
                transacao.getVendedorIngresso().getId(),
                transacao.getIngresso().getId(),
                transacao.getValor(),
                transacao.getEstado()
        );

        return transacaoDTO;
    }

    @Override
    public TransacaoDTO confirmarPagamento(Long idIngresso) {

        //Busca Ingresso
        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(idIngresso);
        if(ingressoOptional.isEmpty()){
            throw new EntityNotFoundException("Ingresso não encontrado com o ID: " + idIngresso);
        }

        Ingresso ingresso = ingressoOptional.get();
        if (!ingresso.getEstado().equals(EstadoIngresso.RESERVADO)) {
            //Ingresso deve estar reservado
            throw new IllegalStateException("Ingresso não está RESERVADO para transacao.");
        }

        //Buscas os dados de transacao
        Optional<List<Transacao>> transacoesOptional = repository.findTransacaoByIngresso(ingresso);
        if(transacoesOptional.isEmpty()){
            throw new EntityNotFoundException("Transacao não encontrada com os dados informados");
        }

        //Altera o status para salvar uma nova transacao
        List<Transacao> transacoes = transacoesOptional.get();
        Optional<Transacao> transacaoMaisRecenteOptional = transacoes.stream()
                        .max(Comparator.comparing(Transacao::getData));
        if (transacaoMaisRecenteOptional.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma transação válida encontrada para o ingresso com ID: " + idIngresso);
        }
        Transacao transacaoMaisRecente = new Transacao(
                null,
                transacaoMaisRecenteOptional.get().getData(),
                transacaoMaisRecenteOptional.get().getClienteIngresso(),
                transacaoMaisRecenteOptional.get().getVendedorIngresso(),
                transacaoMaisRecenteOptional.get().getIngresso(),
                transacaoMaisRecenteOptional.get().getValor(),
                transacaoMaisRecenteOptional.get().getEstado()
        );

        // Atualizar estado da transação
        transacaoMaisRecente.setEstado(EstadoTransacao.CONFIRMADA);
        transacaoMaisRecente = repository.save(transacaoMaisRecente);

        // Altera o status do ingresso para vendido
        ingresso.setEstado(EstadoIngresso.VENDIDO);
        ingresso = ingressoRepository.save(ingresso);

        // Atualiza o ingresso na transação confirmada
        transacaoMaisRecente.setIngresso(ingresso);

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                transacaoMaisRecente.getId(),
                transacaoMaisRecente.getData(),
                transacaoMaisRecente.getClienteIngresso().getId(),
                transacaoMaisRecente.getVendedorIngresso().getId(),
                transacaoMaisRecente.getIngresso().getId(),
                transacaoMaisRecente.getValor(),
                transacaoMaisRecente.getEstado()
        );

        return transacaoDTO;
    }

    @Override
    public TransacaoDTO confirmarRecebimento(Long idIngresso) {
        //Busca Ingresso
        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(idIngresso);
        if(ingressoOptional.isEmpty()){
            throw new EntityNotFoundException("Ingresso não encontrado com o ID: " + idIngresso);
        }

        Ingresso ingresso = ingressoOptional.get();
        if (!ingresso.getEstado().equals(EstadoIngresso.VENDIDO)) {
            //Ingresso deve estar reservado
            throw new IllegalStateException("Ingresso não está VENDIDO para transacao.");
        }

        //Buscas os dados de transacao
        Optional<List<Transacao>> transacoesOptional = repository.findTransacaoByIngresso(ingresso);
        if(transacoesOptional.isEmpty()){
            throw new EntityNotFoundException("Transacao não encontrada com os dados informados");
        }

        //Altera o status para salvar uma nova transacao
        List<Transacao> transacoes = transacoesOptional.get();
        Optional<Transacao> transacaoMaisRecenteOptional = transacoes.stream()
                .max(Comparator.comparing(Transacao::getData));
        if (transacaoMaisRecenteOptional.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma transação válida encontrada para o ingresso com ID: " + idIngresso);
        }
        Transacao transacaoMaisRecente = new Transacao(
                null,
                transacaoMaisRecenteOptional.get().getData(),
                transacaoMaisRecenteOptional.get().getClienteIngresso(),
                transacaoMaisRecenteOptional.get().getVendedorIngresso(),
                transacaoMaisRecenteOptional.get().getIngresso(),
                transacaoMaisRecenteOptional.get().getValor(),
                transacaoMaisRecenteOptional.get().getEstado()
        );

        // Atualizar estado da transação
        transacaoMaisRecente.setEstado(EstadoTransacao.LIBERADA);
        transacaoMaisRecente.setId(null);
        transacaoMaisRecente = repository.save(transacaoMaisRecente);

        // Altera o status do ingresso para vendido
        ingresso.setEstado(EstadoIngresso.RECEBIDO);
        ingresso = ingressoRepository.save(ingresso);

        // Atualiza o ingresso na transação confirmada
        transacaoMaisRecente.setIngresso(ingresso);

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                transacaoMaisRecente.getId(),
                transacaoMaisRecente.getData(),
                transacaoMaisRecente.getClienteIngresso().getId(),
                transacaoMaisRecente.getVendedorIngresso().getId(),
                transacaoMaisRecente.getIngresso().getId(),
                transacaoMaisRecente.getValor(),
                transacaoMaisRecente.getEstado()
        );

        return transacaoDTO;
    }

}
