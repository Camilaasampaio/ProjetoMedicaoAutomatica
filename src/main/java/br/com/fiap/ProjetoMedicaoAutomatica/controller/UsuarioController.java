package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.UsuarioCadastroDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.dto.UsuarioExibicaoDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.model.Usuario;
import br.com.fiap.ProjetoMedicaoAutomatica.service.UsuarioService;
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

@Api(value="API de CRUD para usuários da aplicação")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @ApiOperation(value = "Cria um novo usuário")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioExibicaoDTO salvar(@RequestBody @Valid UsuarioCadastroDTO usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @ApiOperation(value = "Lista todos os usuários")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<UsuarioExibicaoDTO> listarTodos(@PageableDefault(size = 4, page = 0) Pageable paginacao) {
        return usuarioService.listarTodos(paginacao);
    }

    @ApiOperation(value = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioExibicaoDTO> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @ApiOperation(value = "Atualiza um usuário existente")
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Usuario atualizar(@RequestBody @Valid Usuario usuario) {
        return usuarioService.atualizar(usuario);
    }

    @ApiOperation(value = "Apaga um usuário pelo ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Valid Long id) {
        usuarioService.excluir(id);
    }
}
