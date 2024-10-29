package br.com.fiap.ProjetoMedicaoAutomatica.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ErroModel {

    @Expose
    private String erro;
}
