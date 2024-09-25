package br.com.fiap.ProjetoMedicaoAutomatica.service;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.EquipamentoMedicaoDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.exceptions.EquipamentoMedicaoNaoEncontradoException;
import br.com.fiap.ProjetoMedicaoAutomatica.model.EquipamentoMedicao;
import br.com.fiap.ProjetoMedicaoAutomatica.repository.EquipamentoMedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipamentoMedicaoService {

    @Autowired
    private EquipamentoMedicaoRepository equipamentoRepository;

    public EquipamentoMedicaoDTO salvarEquipamento(EquipamentoMedicaoDTO equipamentoDTO) {
        EquipamentoMedicao equipamentoMedicao = new EquipamentoMedicao(equipamentoDTO);
        return new EquipamentoMedicaoDTO(equipamentoRepository.save(equipamentoMedicao));
    }

    public EquipamentoMedicaoDTO buscarPorId(Long id) {
        Optional<EquipamentoMedicao> equipamentoOptional = equipamentoRepository.findById(id);

        if (equipamentoOptional.isPresent()) {
            return new EquipamentoMedicaoDTO(equipamentoOptional.get());
        } else {
            throw new EquipamentoMedicaoNaoEncontradoException("Equipamento de Medição não encontrado no banco de dados!");
        }
    }

    public Page<EquipamentoMedicaoDTO> listarTodos(Pageable paginacao) {
        return equipamentoRepository.findAll(paginacao).map(EquipamentoMedicaoDTO::new);
    }

    public EquipamentoMedicaoDTO atualizar(EquipamentoMedicaoDTO equipamentoDTO) {
        Optional<EquipamentoMedicao> equipamentoOptional = equipamentoRepository.findById(equipamentoDTO.id());

        if (equipamentoOptional.isPresent()) {
            EquipamentoMedicao equipamento = new EquipamentoMedicao(equipamentoDTO);

            return new EquipamentoMedicaoDTO(equipamentoRepository.save(equipamento));
        } else {
            throw new EquipamentoMedicaoNaoEncontradoException("Equipamento de Medição não encontrado no banco de dados!");
        }
    }

    public void excluir(Long id) {
        Optional<EquipamentoMedicao> equipamentoOptional = equipamentoRepository.findById(id);

        if (equipamentoOptional.isPresent()) {
            equipamentoRepository.delete(equipamentoOptional.get());
        } else {
            throw new EquipamentoMedicaoNaoEncontradoException("Equipamento de Medição não encontrado no banco de dados!");
        }
    }
}
