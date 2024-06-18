package com.ticket.ticketProject.model.mapper;

import com.ticket.ticketProject.model.Usuario;
import com.ticket.ticketProject.model.dto.usuario.UsuarioDTO;
import com.ticket.ticketProject.model.dto.usuario.CadastrarUsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    Usuario cadastrarUsuarioDTOToEntity(CadastrarUsuarioDTO dto);

    UsuarioDTO usuarioToUsuarioDto(Usuario usuario);


}
