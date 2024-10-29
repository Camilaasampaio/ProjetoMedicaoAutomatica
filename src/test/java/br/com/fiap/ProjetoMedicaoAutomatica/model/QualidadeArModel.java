package br.com.fiap.ProjetoMedicaoAutomatica.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class QualidadeArModel {

    @Expose
    private int id;
    @Expose
    private String data;
    @Expose
    private String valorSo2;
    @Expose
    private String valorNo2;
    @Expose
    private int idLocal;
    @Expose
    private int idEquipamento;
}
