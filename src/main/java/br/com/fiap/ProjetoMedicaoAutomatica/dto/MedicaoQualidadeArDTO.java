package br.com.fiap.ProjetoMedicaoAutomatica.dto;

import br.com.fiap.ProjetoMedicaoAutomatica.model.MedicaoQualidadeAr;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MedicaoQualidadeArDTO(
        Long id,
        @NotNull(message = "A data de medição é obrigatória!")
        LocalDate data,
        @NotBlank(message = "O resultado da medição de SO2 é obrigatório!")
        String valorSo2,
        @NotBlank(message = "O resultado da medição de NO2 é obrigatório!")
        String valorNo2,
        @NotNull(message = "O código do local de medição é obrigatório!")
        Long idLocal,
        @NotNull(message = "O código do equipamento de medição é obrigatório!")
        Long idEquipamento

) {
    public MedicaoQualidadeArDTO(MedicaoQualidadeAr medicaoQualidadeAr) {
        this(
                medicaoQualidadeAr.getCdQualidade(),
                medicaoQualidadeAr.getDtTempo(),
                medicaoQualidadeAr.getVlSo2(),
                medicaoQualidadeAr.getVlNo2(),
                medicaoQualidadeAr.getCdLocal(),
                medicaoQualidadeAr.getCdEquipamento()
        );
    }
}
