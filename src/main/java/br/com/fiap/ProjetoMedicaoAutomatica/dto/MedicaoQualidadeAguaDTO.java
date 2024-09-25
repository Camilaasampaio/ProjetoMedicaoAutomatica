package br.com.fiap.ProjetoMedicaoAutomatica.dto;

import br.com.fiap.ProjetoMedicaoAutomatica.model.MedicaoQualidadeAgua;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MedicaoQualidadeAguaDTO(
        Long id,
        @NotNull(message = "A data de medição é obrigatória!")
        LocalDate data,
        @NotBlank(message = "O resultado da medição do cloro é obrigatório!")
        String descricaoCloro,
        @NotNull(message = "O código do local de medição é obrigatório!")
        Long idLocal,
        @NotNull(message = "O código do equipamento de medição é obrigatório!")
        Long idEquipamento

) {
    public MedicaoQualidadeAguaDTO(MedicaoQualidadeAgua medicaoQualidadeAgua) {
        this(
                medicaoQualidadeAgua.getCdQualidadeAgua(),
                medicaoQualidadeAgua.getDtTempo(),
                medicaoQualidadeAgua.getDsCloro(),
                medicaoQualidadeAgua.getCdLocal(),
                medicaoQualidadeAgua.getCdEquipamento()
        );
    }
}
