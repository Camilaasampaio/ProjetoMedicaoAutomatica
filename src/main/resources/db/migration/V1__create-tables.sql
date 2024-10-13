CREATE TABLE IF NOT EXISTS t_equipamento_medicao (
    cd_equipamento INT NOT NULL AUTO_INCREMENT,
    nm_equipamento VARCHAR(100) NOT NULL,
    ds_tipo        VARCHAR(100) NOT NULL,
    nm_fabricante  VARCHAR(100) NOT NULL,
    dt_instalacao  DATETIME,
    PRIMARY KEY (cd_equipamento)
);

CREATE TABLE IF NOT EXISTS t_local (
    cd_local    INT NOT NULL AUTO_INCREMENT,
    nm_local    VARCHAR(100) NOT NULL,
    nm_endereco VARCHAR(100) NOT NULL,
    nm_cidade   VARCHAR(100) NOT NULL,
    PRIMARY KEY (cd_local)
);

CREATE TABLE IF NOT EXISTS t_qualidade_agua_tempo_real (
    cd_qualidadeagua INT NOT NULL AUTO_INCREMENT,
    dt_tempo         DATETIME NOT NULL,
    ds_cloro         VARCHAR(100),
    cd_local         INT NOT NULL,
    cd_equipamento   INT NOT NULL,
    PRIMARY KEY (cd_qualidadeagua),
    FOREIGN KEY (cd_local) REFERENCES t_local(cd_local),
    FOREIGN KEY (cd_equipamento) REFERENCES t_equipamento_medicao(cd_equipamento)
);

CREATE TABLE IF NOT EXISTS t_qualidade_ar_tempo_real (
    cd_qualidade   INT NOT NULL AUTO_INCREMENT,
    dt_tempo       DATETIME NOT NULL,
    vl_so2         VARCHAR(100) NOT NULL,
    vl_no2         VARCHAR(100) NOT NULL,
    cd_local       INT NOT NULL,
    cd_equipamento INT NOT NULL,
    PRIMARY KEY (cd_qualidade),
    FOREIGN KEY (cd_local) REFERENCES t_local(cd_local),
    FOREIGN KEY (cd_equipamento) REFERENCES t_equipamento_medicao(cd_equipamento)
);

CREATE TABLE IF NOT EXISTS TBL_USUARIOS (
    USUARIO_ID INT NOT NULL AUTO_INCREMENT,
    NOME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    SENHA VARCHAR(100) NOT NULL,
    ROLE VARCHAR(50) DEFAULT 'USER',
    PRIMARY KEY (USUARIO_ID),
    UNIQUE (EMAIL)
);