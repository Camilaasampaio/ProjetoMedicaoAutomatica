package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.EquipamentoMedicaoDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.service.EquipamentoMedicaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// API de CRUD para os equipamentos
@RestController
@RequestMapping("/api/equipamento")
public class EquipamentoMedicaoController {

    @Autowired
    private EquipamentoMedicaoService equipamentoMedicaoService;

    // Cria um novo equipamento
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EquipamentoMedicaoDTO salvar(@RequestBody @Valid EquipamentoMedicaoDTO equipamentoMedicao) {
        return equipamentoMedicaoService.salvarEquipamento(equipamentoMedicao);
    }

    // Lista todos os equipamentos
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<EquipamentoMedicaoDTO> listarTodos(@PageableDefault(size = 4, page = 0) Pageable paginacao) {
        return equipamentoMedicaoService.listarTodos(paginacao);
    }

    // Busca um equipamento pelo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EquipamentoMedicaoDTO> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(equipamentoMedicaoService.buscarPorId(id));
    }

    // Atualiza um equipamento existente
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public EquipamentoMedicaoDTO atualizar(@RequestBody @Valid EquipamentoMedicaoDTO equipamentoMedicao) {
        return equipamentoMedicaoService.atualizar(equipamentoMedicao);
    }

    // Apaga um equipamento pelo ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Valid Long id) {
        equipamentoMedicaoService.excluir(id);
    }
}
