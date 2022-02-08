CREATE TABLE estado(
    id bigint NOT NULL,
    nome character varying(255) NOT NULL,
    CONSTRAINT pk_estado PRIMARY KEY (id));
       
CREATE TABLE cidade(
    id bigint NOT NULL,
    nome character varying(255) NOT NULL,
    estado_id bigint NOT NULL,
    CONSTRAINT pk_cidade PRIMARY KEY (id),
    CONSTRAINT fk_estado FOREIGN KEY (estado_id)
        REFERENCES estado (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION);

 CREATE TABLE cozinha(
    id bigint NOT NULL,
    nome character varying(255) NOT NULL,
    CONSTRAINT pk_cozinha PRIMARY KEY (id));
    
CREATE TABLE forma_pagamento(
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    CONSTRAINT pk_forma_pagamento PRIMARY KEY (id));
    
CREATE TABLE permissao(
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    CONSTRAINT pk_permissao PRIMARY KEY (id));
    
CREATE TABLE public.restaurante(
    id bigint NOT NULL,
    data_atualizacao timestamp without time zone,
    data_cadastro timestamp without time zone,
    endereco_bairro character varying(255),
    endereco_cep character varying(255),
    endereco_complemento character varying(255),
    endereco_logradouro character varying(255),
    endereco_numero character varying(255),
    nome character varying(255),
    taxa_frete numeric(19, 2) NOT NULL,
    cozinha_id bigint NOT NULL,
    endereco_cidade_id bigint,
    CONSTRAINT pk_restaurante PRIMARY KEY (id),
    CONSTRAINT fk_cozinha FOREIGN KEY (cozinha_id)
        REFERENCES cozinha (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_endereco_cidade FOREIGN KEY (endereco_cidade_id)
        REFERENCES cidade (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION);
            
CREATE TABLE produto(
    id bigint NOT NULL,
    ativo boolean NOT NULL,
    descricao character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    preco numeric(19, 2) NOT NULL,
    restaurante_id bigint,
    CONSTRAINT pk_produto PRIMARY KEY (id),
    CONSTRAINT fk_restaurante FOREIGN KEY (restaurante_id)
        REFERENCES restaurante (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION);    
        