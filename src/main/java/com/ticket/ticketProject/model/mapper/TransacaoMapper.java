package com.ticket.ticketProject.model.mapper;

import com.ticket.ticketProject.model.Ingresso;
import com.ticket.ticketProject.model.Transacao;
import com.ticket.ticketProject.model.dto.ingresso.IngressoDTO;
import com.ticket.ticketProject.model.dto.transacao.CadastrarTransacaoDTO;
import com.ticket.ticketProject.model.dto.transacao.TransacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransacaoMapper {

    Transacao transacaoDTOToEntity (TransacaoDTO dto);

    TransacaoDTO transacaoToTransacaoDTO (Transacao transacao);

}
