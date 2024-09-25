package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.MedicaoQualidadeArDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.service.MedicaoQualidadeArService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qualidade/ar")
public class MedicaoQualidadeArController {

    @Autowired
    private MedicaoQualidadeArService qualidadeArService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MedicaoQualidadeArDTO salvar(@RequestBody @Valid MedicaoQualidadeArDTO qualidadeAr) {
        return qualidadeArService.salvarQualidadeAr(qualidadeAr);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<MedicaoQualidadeArDTO> listarTodos(@PageableDefault(size = 4, page = 0) Pageable paginacao) {
        return qualidadeArService.listarTodos(paginacao);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicaoQualidadeArDTO> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(qualidadeArService.buscarPorId(id));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public MedicaoQualidadeArDTO atualizar(@RequestBody @Valid MedicaoQualidadeArDTO qualidadeAr) {
        return qualidadeArService.atualizar(qualidadeAr);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Valid Long id) {
        qualidadeArService.excluir(id);
    }
}
