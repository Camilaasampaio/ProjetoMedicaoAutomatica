package br.com.fiap.ProjetoMedicaoAutomatica.dto;

import br.com.fiap.ProjetoMedicaoAutomatica.model.EquipamentoMedicao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EquipamentoMedicaoDTO(
        Long id,
        @NotBlank(message = "O nome do equipamento de medição é obrigatório!")
        String nome,
        @NotBlank(message = "O tipo do equipamento de medição é obrigatório!")
        String tipo,
        @NotBlank(message = "O nome do fabricante é obrigatório!")
        String fabricante,
        @NotNull(message = "A data de instalação é obrigatória!")
        LocalDate dataInstalacao
) {
    public EquipamentoMedicaoDTO(EquipamentoMedicao equipamentoMedicao) {
        this(
                equipamentoMedicao.getCdEquipamento(),
                equipamentoMedicao.getNmEquipamento(),
                equipamentoMedicao.getDsTipo(),
                equipamentoMedicao.getNmFabricante(),
                equipamentoMedicao.getDtInstalacao()
        );
    }
}
