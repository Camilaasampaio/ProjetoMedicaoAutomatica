package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.LocalDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.service.LocalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value="API de CRUD para os locais")
@RestController
@RequestMapping("/api/local")
public class LocalController {

    @Autowired
    private LocalService localService;

    @ApiOperation(value = "Cria um novo local")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LocalDTO salvar(@RequestBody @Valid LocalDTO local) {
        return localService.salvarLocal(local);
    }

    @ApiOperation(value = "Lista todos os locais")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<LocalDTO> listarTodos(@PageableDefault(size = 4, page = 0) Pageable paginacao) {
        return localService.listarTodos(paginacao);
    }

    @ApiOperation(value = "Busca um local pelo ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LocalDTO> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(localService.buscarPorId(id));
    }

    @ApiOperation(value = "Atualiza um local existente")
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public LocalDTO atualizar(@RequestBody @Valid LocalDTO local) {
        return localService.atualizar(local);
    }

    @ApiOperation(value = "Apaga um local pelo ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Valid Long id) {
        localService.excluir(id);
    }
}
