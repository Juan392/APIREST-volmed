CREATE TABLE medicos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    documento VARCHAR(14) NOT NULL UNIQUE,
    especialidad VARCHAR(100) NOT NULL,
    calle VARCHAR(100) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    codigo_postal VARCHAR(9) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
);