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

CREATE SEQUENCE T_QUALIDADE_AR_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE T_QUALIDADE_AGUA_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE T_EQUIPAMENTO_MEDICAO_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE T_LOCAL_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE;

CREATE SEQUENCE SEQ_USUARIOS
  START WITH 1
  INCREMENT BY 1''
  NOCACHE
  NOCYCLE;

CREATE TABLE TBL_USUARIOS (
    USUARIO_ID INTEGER DEFAULT SEQ_USUARIOS.NEXTVAL NOT NULL,
    NOME VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    SENHA VARCHAR2(100) NOT NULL,
    ROLE VARCHAR2(50) DEFAULT 'USER'
);

ALTER TABLE TBL_USUARIOS
ADD CONSTRAINT email_unico UNIQUE (EMAIL);