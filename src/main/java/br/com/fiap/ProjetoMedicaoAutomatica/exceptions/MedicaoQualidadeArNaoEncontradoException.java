package br.com.fiap.ProjetoMedicaoAutomatica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MedicaoQualidadeArNaoEncontradoException extends RuntimeException {

    public MedicaoQualidadeArNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
