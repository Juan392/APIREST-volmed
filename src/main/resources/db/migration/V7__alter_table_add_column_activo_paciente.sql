ALTER TABLE pacientes ADD COLUMN activo TINYINT;
UPDATE pacientes set activo = 1;