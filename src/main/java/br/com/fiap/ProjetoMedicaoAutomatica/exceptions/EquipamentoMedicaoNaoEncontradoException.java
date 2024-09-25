package br.com.fiap.ProjetoMedicaoAutomatica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EquipamentoMedicaoNaoEncontradoException extends RuntimeException {

    public EquipamentoMedicaoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
