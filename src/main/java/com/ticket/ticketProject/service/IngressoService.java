package com.ticket.ticketProject.service;

import com.ticket.ticketProject.model.dto.ingresso.AtualizarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.CadastrarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.IngressoDTO;

import java.util.List;

public interface IngressoService {

    List<IngressoDTO> getAllIngressos();

    IngressoDTO getIngressoById(Long id);

    IngressoDTO cadastrarIngresso (Long idUsuario, CadastrarIngressoDTO dto);

    IngressoDTO atualizarIngresso (AtualizarIngressoDTO dto);
}
