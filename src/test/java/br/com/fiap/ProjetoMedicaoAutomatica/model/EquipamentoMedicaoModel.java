package br.com.fiap.ProjetoMedicaoAutomatica.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class EquipamentoMedicaoModel {

    @Expose
    private int id;
    @Expose
    private String nome;
    @Expose
    private String tipo;
    @Expose
    private String fabricante;
    @Expose
    private String dataInstalacao;
}
