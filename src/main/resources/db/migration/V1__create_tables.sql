CREATE TABLE ingredientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL,
    preco DOUBLE PRECISION,
    estoque_atual INTEGER
);

CREATE TABLE cafe_personalizado (
    id BIGSERIAL PRIMARY KEY,
    nome_final VARCHAR(255),
    sabor_classico_identificado VARCHAR(255),
    preco_total DOUBLE PRECISION
);

CREATE TABLE cafe_ingredientes_base (
    cafe_id BIGINT NOT NULL,
    ingrediente_id BIGINT NOT NULL,
    PRIMARY KEY (cafe_id, ingrediente_id),
    CONSTRAINT fk_cafe_base_cafe FOREIGN KEY (cafe_id) REFERENCES cafe_personalizado (id) ON DELETE CASCADE,
    CONSTRAINT fk_cafe_base_ingrediente FOREIGN KEY (ingrediente_id) REFERENCES ingredientes (id) ON DELETE CASCADE
);

CREATE TABLE cafe_ingredientes_adicionais (
    cafe_id BIGINT NOT NULL,
    ingrediente_id BIGINT NOT NULL,
    PRIMARY KEY (cafe_id, ingrediente_id),
    CONSTRAINT fk_cafe_adic_cafe FOREIGN KEY (cafe_id) REFERENCES cafe_personalizado (id) ON DELETE CASCADE,
    CONSTRAINT fk_cafe_adic_ingrediente FOREIGN KEY (ingrediente_id) REFERENCES ingredientes (id) ON DELETE CASCADE
);

CREATE TABLE receitas_classicas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    preco_base DOUBLE PRECISION
);

CREATE TABLE receita_ingredientes (
    receita_id BIGINT NOT NULL,
    ingrediente_id BIGINT NOT NULL,
    PRIMARY KEY (receita_id, ingrediente_id),
    CONSTRAINT fk_receita_ing_receita FOREIGN KEY (receita_id) REFERENCES receitas_classicas (id) ON DELETE CASCADE,
    CONSTRAINT fk_receita_ing_ingrediente FOREIGN KEY (ingrediente_id) REFERENCES ingredientes (id) ON DELETE CASCADE
);

CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    nome_cliente VARCHAR(255),
    cafe_id BIGINT UNIQUE,
    data_hora TIMESTAMP,
    status VARCHAR(255),
    CONSTRAINT fk_pedido_cafe FOREIGN KEY (cafe_id) REFERENCES cafe_personalizado (id) ON DELETE SET NULL
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    administrador BOOLEAN NOT NULL
)