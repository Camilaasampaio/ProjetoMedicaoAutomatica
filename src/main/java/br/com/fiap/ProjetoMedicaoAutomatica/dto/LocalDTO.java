package br.com.fiap.ProjetoMedicaoAutomatica.dto;

import br.com.fiap.ProjetoMedicaoAutomatica.model.Local;
import jakarta.validation.constraints.NotBlank;

public record LocalDTO(
        Long id,
        @NotBlank(message = "O nome do local é obrigatório!")
        String nome,
        @NotBlank(message = "O endereço do local é obrigatório!")
        String endereco,
        @NotBlank(message = "O nome da cidade é obrigatório!")
        String cidade
) {
    public LocalDTO(Local local) {
        this(
                local.getCdLocal(),
                local.getNmLocal(),
                local.getNmEndereco(),
                local.getNmCidade()
        );
    }
}
