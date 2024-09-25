package br.com.fiap.ProjetoMedicaoAutomatica.service;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.LocalDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.exceptions.LocalNaoEncontradoException;
import br.com.fiap.ProjetoMedicaoAutomatica.model.Local;
import br.com.fiap.ProjetoMedicaoAutomatica.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    public LocalDTO salvarLocal(LocalDTO localDTO) {
        Local local = new Local(localDTO);
        return new LocalDTO(localRepository.save(local));
    }

    public LocalDTO buscarPorId(Long id) {
        Optional<Local> localOptional = localRepository.findById(id);

        if (localOptional.isPresent()) {
            return new LocalDTO(localOptional.get());
        } else {
            throw new LocalNaoEncontradoException("Local não encontrado no banco de dados!");
        }
    }

    public Page<LocalDTO> listarTodos(Pageable paginacao) {
        return localRepository.findAll(paginacao).map(LocalDTO::new);
    }

    public LocalDTO atualizar(LocalDTO localDTO) {
        Optional<Local> localOptional = localRepository.findById(localDTO.id());

        if (localOptional.isPresent()) {
            Local local = new Local(localDTO);
            return new LocalDTO(localRepository.save(local));
        } else {
            throw new LocalNaoEncontradoException("Local não encontrado no banco de dados!");
        }
    }

    public void excluir(Long id) {
        Optional<Local> localOptional = localRepository.findById(id);

        if (localOptional.isPresent()) {
            localRepository.delete(localOptional.get());
        } else {
            throw new LocalNaoEncontradoException("Local não encontrado no banco de dados!");
        }
    }
}
