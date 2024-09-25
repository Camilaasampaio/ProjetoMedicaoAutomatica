package br.com.fiap.ProjetoMedicaoAutomatica.model;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.EquipamentoMedicaoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_equipamento_medicao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EquipamentoMedicao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_EQUIPAMENTO_MEDICAO_SEQ")
    @SequenceGenerator(name = "T_EQUIPAMENTO_MEDICAO_SEQ", sequenceName = "T_EQUIPAMENTO_MEDICAO_SEQ", allocationSize = 1)
    @Column(name = "cd_equipamento")
    private Long cdEquipamento;
    @Column(name = "nm_equipamento")
    private String nmEquipamento;
    @Column(name = "ds_tipo")
    private String dsTipo;
    @Column(name = "nm_fabricante")
    private String nmFabricante;
    @Column(name = "dt_instalacao")
    private LocalDate dtInstalacao;

    public EquipamentoMedicao(EquipamentoMedicaoDTO equipamentoMedicaoDTO) {
        this.cdEquipamento = equipamentoMedicaoDTO.id();
        this.nmEquipamento = equipamentoMedicaoDTO.nome();
        this.dsTipo = equipamentoMedicaoDTO.tipo();
        this.nmFabricante = equipamentoMedicaoDTO.fabricante();
        this.dtInstalacao = equipamentoMedicaoDTO.dataInstalacao();
    }
}
