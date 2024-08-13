-- Crear la base de datos
CREATE DATABASE alumnosDB;

-- Conectar a la base de datos
\c alumnosDB;

-- Crear la tabla
CREATE TABLE alumnos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    telefono VARCHAR(10)
);

-- Insertar registros en la tabla
INSERT INTO alumnos (nombre, apellido_paterno, apellido_materno, fecha_nacimiento, telefono) VALUES
('Juan', 'Pérez', 'García', '2000-01-15', '1234567890'),
('Ana', 'López', 'Martínez', '1999-05-23', '2345678901'),
('Luis', 'González', 'Fernández', '1998-07-12', '3456789012'),
('Marta', 'Rodríguez', 'Sánchez', '2001-09-30', '4567890123'),
('Carlos', 'Martínez', 'Ramírez', '1997-12-05', '5678901234'),
('Laura', 'Sánchez', 'Vázquez', '2002-03-17', '6789012345'),
('José', 'Hernández', 'Morales', '2000-11-23', '7890123456'),
('Patricia', 'García', 'Reyes', '1998-06-18', '8901234567'),
('Ricardo', 'Jiménez', 'Cruz', '1999-02-27', '9012345678'),
('Isabel', 'Vásquez', 'Moreno', '2001-08-10', '0123456789'),
('Pedro', 'Mendoza', 'Bravo', '2000-04-14', '1234509876'),
('Cristina', 'Serrano', 'Gómez', '1997-10-30', '2345610987'),
('Alejandro', 'Romero', 'Rivas', '1999-07-22', '3456721098'),
('Julieta', 'Castro', 'Lara', '1998-12-11', '4567832109'),
('Daniel', 'Sosa', 'Quintero', '2001-03-29', '5678943210'),
('Sandra', 'Guerrero', 'Paredes', '1998-09-15', '6789054321'),
('Jorge', 'Reyes', 'Araya', '2000-06-06', '7890165432'),
('Carla', 'Gutiérrez', 'García', '1999-01-25', '8901276543'),
('Manuel', 'Cordero', 'Hernández', '2002-05-18', '9012387654'),
('Rosa', 'Torres', 'Álvarez', '1997-11-11', '0123498765');
