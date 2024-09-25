package br.com.fiap.ProjetoMedicaoAutomatica.dto;

import br.com.fiap.ProjetoMedicaoAutomatica.model.Usuario;

public record UsuarioExibicaoDTO(
        Long usuarioId,
        String nome,
        String email
) {
    public UsuarioExibicaoDTO(Usuario usuario) {
        this(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
