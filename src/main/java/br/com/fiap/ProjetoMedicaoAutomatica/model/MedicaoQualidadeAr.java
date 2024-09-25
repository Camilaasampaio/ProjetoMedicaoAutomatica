package br.com.fiap.ProjetoMedicaoAutomatica.model;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.MedicaoQualidadeArDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_qualidade_ar_tempo_real")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MedicaoQualidadeAr {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_QUALIDADE_AR_SEQ")
    @SequenceGenerator(name = "T_QUALIDADE_AR_SEQ", sequenceName = "T_QUALIDADE_AR_SEQ", allocationSize = 1)
    @Column(name = "cd_qualidade")
    private Long cdQualidade;
    @Column(name = "dt_tempo")
    private LocalDate dtTempo;
    @Column(name = "vl_so2")
    private String vlSo2;
    @Column(name = "vl_no2")
    private String vlNo2;
    @Column(name = "cd_local")
    private Long cdLocal;
    @Column(name = "cd_equipamento")
    private Long cdEquipamento;

    public MedicaoQualidadeAr(MedicaoQualidadeArDTO medicaoQualidadeArDTO) {
        this.cdQualidade = medicaoQualidadeArDTO.id();
        this.dtTempo = medicaoQualidadeArDTO.data();
        this.vlSo2 = medicaoQualidadeArDTO.valorSo2();
        this.vlNo2 = medicaoQualidadeArDTO.valorNo2();
        this.cdLocal = medicaoQualidadeArDTO.idLocal();
        this.cdEquipamento = medicaoQualidadeArDTO.idEquipamento();
    }
}
