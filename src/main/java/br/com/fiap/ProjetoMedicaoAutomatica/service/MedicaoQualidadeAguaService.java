package br.com.fiap.ProjetoMedicaoAutomatica.service;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.MedicaoQualidadeAguaDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.exceptions.MedicaoQualidadeAguaNaoEncontradoException;
import br.com.fiap.ProjetoMedicaoAutomatica.model.MedicaoQualidadeAgua;
import br.com.fiap.ProjetoMedicaoAutomatica.repository.MedicaoQualidadeAguaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicaoQualidadeAguaService {

    @Autowired
    private MedicaoQualidadeAguaRepository qualidadeAguaRepository;

    public MedicaoQualidadeAguaDTO salvarQualidadeAgua(MedicaoQualidadeAguaDTO qualidadeAguaDTO) {
        MedicaoQualidadeAgua qualidadeAgua = new MedicaoQualidadeAgua(qualidadeAguaDTO);
        return new MedicaoQualidadeAguaDTO(qualidadeAguaRepository.save(qualidadeAgua));
    }

    public MedicaoQualidadeAguaDTO buscarPorId(Long id) {
        Optional<MedicaoQualidadeAgua> qualidadeAguaOptional = qualidadeAguaRepository.findById(id);

        if (qualidadeAguaOptional.isPresent()) {
            return new MedicaoQualidadeAguaDTO(qualidadeAguaOptional.get());
        } else {
            throw new MedicaoQualidadeAguaNaoEncontradoException("Medição da qualidade da água não encontrada no banco de dados!");
        }
    }

    public Page<MedicaoQualidadeAguaDTO> listarTodos(Pageable paginacao) {
        return qualidadeAguaRepository.findAll(paginacao).map(MedicaoQualidadeAguaDTO::new);
    }

    public MedicaoQualidadeAguaDTO atualizar(MedicaoQualidadeAguaDTO qualidadeAguaDTO) {
        Optional<MedicaoQualidadeAgua> qualidadeAguaOptional = qualidadeAguaRepository.findById(qualidadeAguaDTO.id());

        if (qualidadeAguaOptional.isPresent()) {
            MedicaoQualidadeAgua qualidadeAgua = new MedicaoQualidadeAgua(qualidadeAguaDTO);
            return new MedicaoQualidadeAguaDTO(qualidadeAguaRepository.save(qualidadeAgua));
        } else {
            throw new MedicaoQualidadeAguaNaoEncontradoException("Medição da qualidade da água não encontrada no banco de dados!");
        }
    }

    public void excluir(Long id) {
        Optional<MedicaoQualidadeAgua> qualidadeAguaOptional = qualidadeAguaRepository.findById(id);

        if (qualidadeAguaOptional.isPresent()) {
            qualidadeAguaRepository.delete(qualidadeAguaOptional.get());
        } else {
            throw new MedicaoQualidadeAguaNaoEncontradoException("Medição da qualidade da água não encontrada no banco de dados!");
        }
    }
}
