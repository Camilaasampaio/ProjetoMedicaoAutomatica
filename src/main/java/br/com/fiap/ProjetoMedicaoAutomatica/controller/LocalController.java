package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.LocalDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.service.LocalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/local")
public class LocalController {

    @Autowired
    private LocalService localService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LocalDTO salvar(@RequestBody @Valid LocalDTO local) {
        return localService.salvarLocal(local);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<LocalDTO> listarTodos(@PageableDefault(size = 4, page = 0) Pageable paginacao) {
        return localService.listarTodos(paginacao);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LocalDTO> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(localService.buscarPorId(id));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public LocalDTO atualizar(@RequestBody @Valid LocalDTO local) {
        return localService.atualizar(local);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Valid Long id) {
        localService.excluir(id);
    }
}
