package br.com.fiap.ProjetoMedicaoAutomatica.model;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.LocalDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_local")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_LOCAL_SEQ")
    @SequenceGenerator(name = "T_LOCAL_SEQ", sequenceName = "T_LOCAL_SEQ", allocationSize = 1)
    @Column(name = "cd_local")
    private Long cdLocal;
    @Column(name = "nm_local")
    private String nmLocal;
    @Column(name = "nm_endereco")
    private String nmEndereco;
    @Column(name = "nm_cidade")
    private String nmCidade;

    public Local(LocalDTO localDTO) {
        this.cdLocal = localDTO.id();
        this.nmLocal = localDTO.nome();
        this.nmEndereco = localDTO.endereco();
        this.nmCidade = localDTO.cidade();
    }
}
