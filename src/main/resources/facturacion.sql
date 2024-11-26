/* Creación base de datos FACTURACION */
DROP DATABASE IF EXISTS `facturacion`;
CREATE DATABASE `facturacion`;
USE `facturacion`;

CREATE TABLE IF NOT EXISTS enderezo(
	idEnderezo INT PRIMARY KEY,
    descricion VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente(
	idCliente INT PRIMARY KEY,
    nifCliente CHAR(9) NOT NULL,
    nomeApelidos VARCHAR(150) NOT NULL,
    idEnderezo INT NOT NULL,
    FOREIGN KEY (idEnderezo) REFERENCES enderezo(idEnderezo)
);

CREATE TABLE IF NOT EXISTS factura(
	idFactura INT PRIMARY KEY NOT NULL,
    prezo NUMERIC(10,2) NOT NULL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)
);

/* INSERTAR DATOS NAS TABLAS */

INSERT INTO `enderezo` (`idEnderezo`, `descricion`) VALUES 
(1,'Plaza Roja Nº1'),
(2,'Alameda Nº34'),
(3, 'Calle del Sol Nº10'),
(4, 'Avenida de la Libertad Nº45'),
(5, 'Calle Mayor Nº12'),
(6, 'Paseo de la Playa Nº5'),
(7, 'Calle de la Luna Nº8'),
(8, 'Plaza del Parque Nº21'),
(9, 'Avenida del Mar Nº30'),
(10, 'Plaza de la Rosa Nº3');

SELECT * FROM enderezo;

INSERT INTO `cliente` (`idCliente`, `nifCliente`, `nomeApelidos`, `idEnderezo`) VALUES
(1, '11111111A', 'Mario Lago Lago', 5),
(2, '11122333V', 'Pepe López López', 4),
(3, '33344555C', 'Laura Pérez Gómez', 3),
(4, '44555606G', 'Carlos Martínez Rodríguez', 7),
(5, '55566677W', 'Ana Sánchez García', 6),
(6, '66777888F', 'Sergio Fernández López', 5),
(7, '77788999S', 'María García Pérez', 5),
(8, '88999000H', 'Javier Rodríguez Martínez', 9),
(9, '99000111I', 'Lucía López Sánchez', 10);

SELECT * FROM cliente;

INSERT INTO `factura` (`idFactura`, `prezo`, `idCliente`) VALUES
(1, 150.3, 1),
(2, 123.0, 9),
(3, 6423.45, 8),
(4, 20.12, 3),
(5, 132.99, 4),
(6, 2.78, 1),
(7, 167.43, 3),
(8, 987.36, 1),
(9, 2344.65, 2),
(10, 123.78, 7),
(11, 867.65, 1),
(12, 4354.45, 5),
(13, 3150.03, 5),
(14, 47.85, 6),
(15, 20.54, 3);

SELECT * FROM factura;


DELIMITER $$

DROP PROCEDURE IF EXISTS pr_DniPrezo $$
CREATE PROCEDURE pr_DniPrezo (
    IN dniCliente VARCHAR(15)
)
BEGIN
SELECT f.prezo
FROM factura f
         INNER JOIN cliente c ON f.idCliente = c.idCliente
WHERE c.nifCliente = dniCliente;
END $$
DELIMITER ;
