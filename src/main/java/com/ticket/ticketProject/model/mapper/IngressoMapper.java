package com.ticket.ticketProject.model.mapper;

import com.ticket.ticketProject.model.Ingresso;
import com.ticket.ticketProject.model.dto.ingresso.AtualizarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.CadastrarIngressoDTO;
import com.ticket.ticketProject.model.dto.ingresso.IngressoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngressoMapper {

    Ingresso ingressoDTOToEntity(IngressoDTO dto);

    IngressoDTO ingressoToIngressoDTO(Ingresso ingresso);

    Ingresso cadastrarDTOToEntity(CadastrarIngressoDTO dto);

    Ingresso atualizarDTOToEntity(AtualizarIngressoDTO dto);

}
