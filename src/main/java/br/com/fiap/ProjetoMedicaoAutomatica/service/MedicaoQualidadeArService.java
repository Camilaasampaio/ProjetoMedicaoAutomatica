package br.com.fiap.ProjetoMedicaoAutomatica.service;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.MedicaoQualidadeArDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.exceptions.MedicaoQualidadeArNaoEncontradoException;
import br.com.fiap.ProjetoMedicaoAutomatica.model.MedicaoQualidadeAr;
import br.com.fiap.ProjetoMedicaoAutomatica.repository.MedicaoQualidadeArRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicaoQualidadeArService {

    @Autowired
    private MedicaoQualidadeArRepository qualidadeArRepository;

    public MedicaoQualidadeArDTO salvarQualidadeAr(MedicaoQualidadeArDTO qualidadeArDTO) {
        MedicaoQualidadeAr qualidadeAr = new MedicaoQualidadeAr(qualidadeArDTO);
        return new MedicaoQualidadeArDTO(qualidadeArRepository.save(qualidadeAr));
    }

    public MedicaoQualidadeArDTO buscarPorId(Long id) {
        Optional<MedicaoQualidadeAr> qualidadeArOptional = qualidadeArRepository.findById(id);

        if (qualidadeArOptional.isPresent()) {
            return new MedicaoQualidadeArDTO(qualidadeArOptional.get());
        } else {
            throw new MedicaoQualidadeArNaoEncontradoException("Medição da qualidade do ar não encontrada no banco de dados!");
        }
    }

    public Page<MedicaoQualidadeArDTO> listarTodos(Pageable paginacao) {
        return qualidadeArRepository.findAll(paginacao).map(MedicaoQualidadeArDTO::new);
    }

    public MedicaoQualidadeArDTO atualizar(MedicaoQualidadeArDTO qualidadeArDTO) {
        Optional<MedicaoQualidadeAr> qualidadeArOptional = qualidadeArRepository.findById(qualidadeArDTO.id());

        if (qualidadeArOptional.isPresent()) {
            MedicaoQualidadeAr qualidadeAr = new MedicaoQualidadeAr(qualidadeArDTO);
            return new MedicaoQualidadeArDTO(qualidadeArRepository.save(qualidadeAr));
        } else {
            throw new MedicaoQualidadeArNaoEncontradoException("Medição da qualidade do ar não encontrada no banco de dados!");
        }
    }

    public void excluir(Long id) {
        Optional<MedicaoQualidadeAr> qualidadeArOptional = qualidadeArRepository.findById(id);

        if (qualidadeArOptional.isPresent()) {
            qualidadeArRepository.delete(qualidadeArOptional.get());
        } else {
            throw new MedicaoQualidadeArNaoEncontradoException("Medição da qualidade do ar não encontrada no banco de dados!");
        }
    }
}
