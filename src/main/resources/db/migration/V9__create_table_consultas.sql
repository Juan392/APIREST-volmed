CREATE TABLE consultas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    medico_id INT,
    paciente_id INT,
    fecha DATETIME NOT NULL,
    FOREIGN KEY(medico_id) REFERENCES medicos(id),
    FOREIGN KEY(paciente_id) REFERENCES pacientes(id)
);