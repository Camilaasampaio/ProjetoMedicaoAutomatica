package br.com.fiap.ProjetoMedicaoAutomatica.repository;

import br.com.fiap.ProjetoMedicaoAutomatica.model.EquipamentoMedicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoMedicaoRepository extends JpaRepository<EquipamentoMedicao, Long> {
}
