package br.com.fiap.ProjetoMedicaoAutomatica.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ErroApiModel {

    @Expose
    private String timestamp;
    @Expose
    private int status;
    @Expose
    private String error;
    @Expose
    private String path;
}
