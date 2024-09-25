package br.com.fiap.ProjetoMedicaoAutomatica.service;

import br.com.fiap.ProjetoMedicaoAutomatica.dto.UsuarioCadastroDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.dto.UsuarioExibicaoDTO;
import br.com.fiap.ProjetoMedicaoAutomatica.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.ProjetoMedicaoAutomatica.model.Usuario;
import br.com.fiap.ProjetoMedicaoAutomatica.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDTO salvarUsuario(UsuarioCadastroDTO usuarioDTO) {
        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioExibicaoDTO(usuarioSalvo);
    }

    public UsuarioExibicaoDTO buscarPorId(Long id) {
        Optional<Usuario> usuarioOptional =
                usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            return new UsuarioExibicaoDTO(usuarioOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não existe no banco de dados!");
        }
    }

    public Page<UsuarioExibicaoDTO> listarTodos(Pageable paginacao) {
        return usuarioRepository.findAll(paginacao).map(UsuarioExibicaoDTO::new);
    }

    public void excluir(Long id) {
        Optional<Usuario> usuarioOptional =
                usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            usuarioRepository.delete(usuarioOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não existe no banco de dados!");
        }
    }

    public Usuario atualizar(Usuario usuario) {
        Optional<Usuario> usuarioOptional =
                usuarioRepository.findById(usuario.getUsuarioId());

        if (usuarioOptional.isPresent()) {
            return usuarioRepository.save(usuario);
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não existe no banco de dados!");
        }
    }
}
