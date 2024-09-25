package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.MedicaoQualidadeArDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.service.MedicaoQualidadeArService;
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

@Api(value="API de CRUD para as medições de qualidade de ar")
@RestController
@RequestMapping("/api/qualidade/ar")
public class MedicaoQualidadeArController {

    @Autowired
    private MedicaoQualidadeArService qualidadeArService;

    @ApiOperation(value = "Cria uma nova medição de qualidade de ar")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MedicaoQualidadeArDTO salvar(@RequestBody @Valid MedicaoQualidadeArDTO qualidadeAr) {
        return qualidadeArService.salvarQualidadeAr(qualidadeAr);
    }

    @ApiOperation(value = "Lista todas as medições de qualidade de ar")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<MedicaoQualidadeArDTO> listarTodos(@PageableDefault(size = 4, page = 0) Pageable paginacao) {
        return qualidadeArService.listarTodos(paginacao);
    }

    @ApiOperation(value = "Busca um medição de qualidade de ar pelo ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicaoQualidadeArDTO> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(qualidadeArService.buscarPorId(id));
    }

    @ApiOperation(value = "Atualiza um medição de qualidade de ar existente")
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public MedicaoQualidadeArDTO atualizar(@RequestBody @Valid MedicaoQualidadeArDTO qualidadeAr) {
        return qualidadeArService.atualizar(qualidadeAr);
    }

    @ApiOperation(value = "Apaga uma medição de qualidade de ar pelo ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Valid Long id) {
        qualidadeArService.excluir(id);
    }
}
