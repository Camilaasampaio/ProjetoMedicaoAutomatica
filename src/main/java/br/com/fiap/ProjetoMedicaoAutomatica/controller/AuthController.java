package br.com.fiap.ProjetoMedicaoAutomatica.controller;

import br.com.fiap.ProjetoMedicaoAutomatica.config.security.TokenService;
import br.com.fiap.ProjetoMedicaoAutomatica.dto.LoginDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.dto.TokenDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.dto.UsuarioCadastroDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.dto.UsuarioExibicaoDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.model.Usuario;
import br.com.fiap.ProjetoMedicaoAutomatica.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());

        Authentication auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity registrar(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO){
        UsuarioExibicaoDTO usuarioSalvo = null;
        usuarioSalvo = usuarioService.salvarUsuario(usuarioCadastroDTO);

        return ResponseEntity.ok(usuarioSalvo);
    }
}
