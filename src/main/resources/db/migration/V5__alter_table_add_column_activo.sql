ALTER TABLE medicos ADD COLUMN activo TINYINT;
UPDATE medicos set activo = 1;