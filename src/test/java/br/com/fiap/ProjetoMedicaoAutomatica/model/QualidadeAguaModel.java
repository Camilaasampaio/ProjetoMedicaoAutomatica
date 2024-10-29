package br.com.fiap.ProjetoMedicaoAutomatica.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class QualidadeAguaModel {

    @Expose
    private int id;
    @Expose
    private String data;
    @Expose
    private String descricaoCloro;
    @Expose
    private int idLocal;
    @Expose
    private int idEquipamento;
}
