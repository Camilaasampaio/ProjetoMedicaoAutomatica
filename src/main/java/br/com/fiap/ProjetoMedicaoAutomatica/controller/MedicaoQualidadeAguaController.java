package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.MedicaoQualidadeAguaDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.service.MedicaoQualidadeAguaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// API de CRUD para as medições de qualidade de água
@RestController
@RequestMapping("/api/qualidade/agua")
public class MedicaoQualidadeAguaController {

    @Autowired
    private MedicaoQualidadeAguaService qualidadeAguaService;

    // Cria uma nova medição de qualidade de água")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MedicaoQualidadeAguaDTO salvar(@RequestBody @Valid MedicaoQualidadeAguaDTO qualidadeAgua) {
        return qualidadeAguaService.salvarQualidadeAgua(qualidadeAgua);
    }

    // Lista todas as medições de qualidade de água
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<MedicaoQualidadeAguaDTO> listarTodos(@PageableDefault(size = 4, page = 0) Pageable paginacao) {
        return qualidadeAguaService.listarTodos(paginacao);
    }

    // Busca um medição de qualidade de água pelo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MedicaoQualidadeAguaDTO> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(qualidadeAguaService.buscarPorId(id));
    }

    // Atualiza um medição de qualidade de água existente
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public MedicaoQualidadeAguaDTO atualizar(@RequestBody @Valid MedicaoQualidadeAguaDTO qualidadeAgua) {
        return qualidadeAguaService.atualizar(qualidadeAgua);
    }

    // Apaga uma medição de qualidade de água pelo ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Valid Long id) {
        qualidadeAguaService.excluir(id);
    }
}
