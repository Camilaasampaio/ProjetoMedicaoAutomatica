--Criação das Tabelas

CREATE TABLE t_auditoria (
    cd_auditoria NUMBER(10) NOT NULL,
    ds_texto     VARCHAR2(200) NOT NULL
);

ALTER TABLE t_auditoria ADD CONSTRAINT t_auditoria_pk PRIMARY KEY ( cd_auditoria );

CREATE TABLE t_equipamento_medicao (
    cd_equipamento NUMBER(10) NOT NULL,
    nm_equipamento VARCHAR2(100) NOT NULL,
    ds_tipo        VARCHAR2(100) NOT NULL,
    nm_fabricante  VARCHAR2(100) NOT NULL,
    dt_instalacao  DATE
);

ALTER TABLE t_equipamento_medicao ADD CONSTRAINT t_equipamento_medicao_pk PRIMARY KEY ( cd_equipamento );

CREATE TABLE t_local (
    cd_local    NUMBER NOT NULL,
    nm_local    VARCHAR2(100) NOT NULL,
    nm_endereco VARCHAR2(100) NOT NULL,
    nm_cidade   VARCHAR2(100) NOT NULL
);

ALTER TABLE t_local ADD CONSTRAINT t_local_pk PRIMARY KEY ( cd_local );

CREATE TABLE t_qualidade_agua_tempo_real (
    cd_qualidadeagua NUMBER(10) NOT NULL,
    dt_tempo         DATE NOT NULL,
    ds_cloro         VARCHAR2(100),
    cd_local         NUMBER NOT NULL,
    cd_equipamento   NUMBER(10) NOT NULL
);

ALTER TABLE t_qualidade_agua_tempo_real
    ADD CONSTRAINT t_qualidade_agua_tempo_real_pk PRIMARY KEY ( cd_qualidadeagua,
                                                                cd_local,
                                                                cd_equipamento );

CREATE TABLE t_qualidade_ar_tempo_real (
    cd_qualidade   NUMBER(10) NOT NULL,
    dt_tempo       DATE NOT NULL,
    vl_so2         VARCHAR2(100) NOT NULL,
    vl_no2         VARCHAR2(100) NOT NULL,
    cd_local       NUMBER NOT NULL,
    cd_equipamento NUMBER(10) NOT NULL
);

ALTER TABLE t_qualidade_ar_tempo_real
    ADD CONSTRAINT t_qualidade_ar_tempo_real_pk PRIMARY KEY ( cd_qualidade,
                                                              cd_local,
                                                              cd_equipamento );

ALTER TABLE t_qualidade_agua_tempo_real
    ADD CONSTRAINT t_equipamento_medicao_fk FOREIGN KEY ( cd_equipamento )
        REFERENCES t_equipamento_medicao ( cd_equipamento );

ALTER TABLE t_qualidade_ar_tempo_real
    ADD CONSTRAINT t_equipamento_medicao_fkv2 FOREIGN KEY ( cd_equipamento )
        REFERENCES t_equipamento_medicao ( cd_equipamento );

ALTER TABLE t_qualidade_agua_tempo_real
    ADD CONSTRAINT t_local_fk FOREIGN KEY ( cd_local )
        REFERENCES t_local ( cd_local );

ALTER TABLE t_qualidade_ar_tempo_real
    ADD CONSTRAINT t_local_fkv2 FOREIGN KEY ( cd_local )
        REFERENCES t_local ( cd_local );

CREATE SEQUENCE ID_AUDITORIA INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE T_QUALIDADE_AR_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE T_QUALIDADE_AGUA_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE T_EQUIPAMENTO_MEDICAO_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE T_LOCAL_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Automação: Registra em tabela de auditoria quando insere registro na tabela de qualidade do ar ou da agua, ajuda a ter monitoramento em tempo real
CREATE OR REPLACE PROCEDURE registra_auditoria
 (p_texto IN VARCHAR2) AS
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  INSERT INTO T_AUDITORIA(cd_auditoria, ds_texto) VALUES (ID_AUDITORIA.NEXTVAL, p_texto);
  COMMIT;
END;

CREATE OR REPLACE TRIGGER auditoria_medicao_agua 
BEFORE INSERT ON T_QUALIDADE_AGUA_TEMPO_REAL 
FOR EACH ROW 
BEGIN 
    registra_auditoria (
        'Medição de Água feita - Código: ' || :NEW.cd_qualidadeagua || ', Medido em: ' || :NEW.dt_tempo || ', Cloro: ' || :NEW.ds_cloro || ', Código Local: ' || :NEW.cd_local || ', Código equipamento: ' || :NEW.cd_equipamento
    );
END; 

CREATE OR REPLACE TRIGGER auditoria_medicao_ar
BEFORE INSERT ON T_QUALIDADE_AR_TEMPO_REAL 
FOR EACH ROW 
BEGIN 
    registra_auditoria (
        'Medição de Ar feita - Código: ' || :NEW.cd_qualidade || ', Medido em: ' || :NEW.dt_tempo || ', SO2: ' || :NEW.vl_so2 || ', NO2: ' || :NEW.vl_no2 || ', Código Local: ' || :NEW.cd_local || ', Código equipamento: ' || :NEW.cd_equipamento
    );
END; 
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------