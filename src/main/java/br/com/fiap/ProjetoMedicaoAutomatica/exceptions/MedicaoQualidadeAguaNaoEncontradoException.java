package br.com.fiap.ProjetoMedicaoAutomatica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MedicaoQualidadeAguaNaoEncontradoException extends RuntimeException {

    public MedicaoQualidadeAguaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
