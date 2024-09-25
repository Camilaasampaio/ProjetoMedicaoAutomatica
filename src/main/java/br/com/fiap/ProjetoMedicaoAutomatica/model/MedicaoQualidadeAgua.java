package br.com.fiap.ProjetoMedicaoAutomatica.model;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.MedicaoQualidadeAguaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_qualidade_agua_tempo_real")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MedicaoQualidadeAgua {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_QUALIDADE_AGUA_SEQ")
    @SequenceGenerator(name = "T_QUALIDADE_AGUA_SEQ", sequenceName = "T_QUALIDADE_AGUA_SEQ", allocationSize = 1)
    @Column(name = "cd_qualidadeagua")
    private Long cdQualidadeAgua;
    @Column(name = "dt_tempo")
    private LocalDate dtTempo;
    @Column(name = "ds_cloro")
    private String dsCloro;
    @Column(name = "cd_local")
    private Long cdLocal;
    @Column(name = "cd_equipamento")
    private Long cdEquipamento;

    public MedicaoQualidadeAgua(MedicaoQualidadeAguaDTO medicaoQualidadeAguaDTO) {
        this.cdQualidadeAgua = medicaoQualidadeAguaDTO.id();
        this.dtTempo = medicaoQualidadeAguaDTO.data();
        this.dsCloro = medicaoQualidadeAguaDTO.descricaoCloro();
        this.cdLocal = medicaoQualidadeAguaDTO.idLocal();
        this.cdEquipamento = medicaoQualidadeAguaDTO.idEquipamento();
    }
}
