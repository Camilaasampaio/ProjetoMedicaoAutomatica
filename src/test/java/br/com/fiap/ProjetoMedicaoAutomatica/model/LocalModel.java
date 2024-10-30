package br.com.fiap.ProjetoMedicaoAutomatica.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class LocalModel {

    @Expose
    private int id;
    @Expose
    private String nome;
    @Expose
    private String endereco;
    @Expose
    private String cidade;
}
