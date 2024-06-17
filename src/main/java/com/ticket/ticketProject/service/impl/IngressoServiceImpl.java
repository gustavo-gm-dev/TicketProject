package com.ticket.ticketProject.service.impl;

import com.ticket.ticketProject.model.EstadoIngresso;
import com.ticket.ticketProject.model.Ingresso;
import com.ticket.ticketProject.model.Usuario;
import com.ticket.ticketProject.model.VendedorIngresso;
import com.ticket.ticketProject.model.dto.ingresso.AtualizarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.CadastrarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.IngressoDTO;
import com.ticket.ticketProject.model.mapper.IngressoMapper;
import com.ticket.ticketProject.repository.IngressoRepository;
import com.ticket.ticketProject.repository.UsuarioRepository;
import com.ticket.ticketProject.repository.VendedorIngressoRepository;
import com.ticket.ticketProject.service.IngressoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngressoServiceImpl implements IngressoService {

    @Autowired
    private IngressoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VendedorIngressoRepository vendedorIngressoRepository;

    @Autowired
    private IngressoMapper mapper;

    @Override
    public List<IngressoDTO> getAllIngressos() {
        List<Ingresso> ingresso = repository.findAll();
        List<IngressoDTO> ingressoDTO = ingresso.stream()
                .filter(i -> i.getEstado() == EstadoIngresso.DISPONIVEL)
                .map(mapper::ingressoToIngressoDTO)
                .collect(Collectors.toList());
        return ingressoDTO;
    }

    @Override
    public IngressoDTO getIngressoById(Long id) {
        Optional<Ingresso> ingresso = repository.findById(id);
        return ingresso.map(mapper::ingressoToIngressoDTO).orElse(null);
    }

    @Override
    public IngressoDTO cadastrarIngresso(Long idUsuario, CadastrarIngressoDTO dto) {
        //Busca Usuario
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if(usuarioOptional.isPresent()){
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + idUsuario);
        }

        //Salva dados do Ingresso
        Ingresso ingresso = mapper.cadastrarDTOToEntity(dto);
        ingresso = repository.save(ingresso);

        //Atrela dados do Ingresso ao Vendedor
        Usuario usuario = usuarioOptional.get();
        VendedorIngresso vendedorIngresso = new VendedorIngresso(null, usuario, ingresso);
        vendedorIngressoRepository.save(vendedorIngresso);

        return mapper.ingressoToIngressoDTO(ingresso);
    }

    @Override
    public IngressoDTO atualizarIngresso(AtualizarIngressoDTO dto) {
        Optional<Ingresso> ingressoOptional = repository.findById(dto.id());
        if (ingressoOptional.isEmpty()) {
            throw new EntityNotFoundException("Ingresso não encontrado com ID: " + dto.id());
        }

        Ingresso ingresso = ingressoOptional.get();
        if (dto.evento() != null && !dto.evento().isBlank()) {
            ingresso.setEvento(dto.evento());
        }
        if (dto.data() != null) {
            ingresso.setData(dto.data());
        }
        if (dto.preco() != null) {
            ingresso.setPreco(dto.preco());
        }
        if (dto.local() != null && !dto.local().isBlank()) {
            ingresso.setLocal(dto.local());
        }
        if (dto.estado() != null) {
            ingresso.setEstado(dto.estado());
        }

        ingresso = repository.save(ingresso);
        return mapper.ingressoToIngressoDTO(ingresso);
    }

}
