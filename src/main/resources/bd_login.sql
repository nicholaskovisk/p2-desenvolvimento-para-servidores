CREATE DATABASE bd_login
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bd_login;

CREATE TABLE usuario (
    usuario VARCHAR(10) NOT NULL PRIMARY KEY,
    nome VARCHAR(30) NOT NULL DEFAULT '',
    senha CHAR(32) NOT NULL
);
