--###################################################
--# Script para la generación de las bases de datos.#
--###################################################

CREATE DATABASE dbreservas;
USE dbreservas;


CREATE TABLE bar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE mesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    capacidad INT,
    zona VARCHAR(100),
    estado VARCHAR(50),
    fusionadaCon VARCHAR(50),
    bar_id INT,
    CONSTRAINT fk_bar FOREIGN KEY (bar_id) REFERENCES bar(id)
);

CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cliente VARCHAR(255),
    correo_electronico VARCHAR(255),
    telefono VARCHAR(255),
    numero_comensales INT,
    zona_preferida VARCHAR(255),
    fecha_hora DATETIME,
    estado VARCHAR(50),
    mensaje TEXT,
    fecha_solicitud DATETIME,
    mesa_id INT,
    CONSTRAINT fk_mesa FOREIGN KEY (mesa_id) REFERENCES mesa(id)
);

INSERT INTO bar (id, nombre) VALUES
(1, 'Bar Prueba'); -- Introduce aquí el nombre de tu bar

INSERT INTO mesa (nombre, capacidad, zona, estado, fusionadaCon, bar_id) VALUES
('M01', 2, 'Interior', 'disponible', null, 1),
('M02', 2, 'Interior', 'disponible', null, 1),
('M03', 4, 'Interior', 'disponible', null, 1),
('T01', 2, 'Terraza', 'disponible', null, 1),
('T02', 4, 'Terraza', 'disponible', null, 1);


--####################################################################--

CREATE DATABASE IF NOT EXISTS dbusuarios;
USE dbusuarios;

CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    nombre VARCHAR(100),
    apellidos VARCHAR(100),
    email VARCHAR(100),
    rol_id INT NOT NULL,
    bar_id INT NOT NULL,
    CONSTRAINT fk_rol FOREIGN KEY (rol_id) REFERENCES rol(id)
);
    
	
INSERT INTO dbusuarios.rol (id, nombre) VALUES(2, 'ADMIN');
INSERT INTO dbusuarios.rol (id, nombre) VALUES(1, 'CAMARERO');
INSERT INTO dbusuarios.rol (id, nombre) VALUES(3, 'CLIENTE');
INSERT INTO dbusuarios.rol (id, nombre) VALUES(4, 'COCINERO');

INSERT INTO dbusuarios.usuario
(id, matricula, contrasena, nombre, apellidos, email, rol_id, bar_id)
VALUES(1, '1234', '$2b$12$8AdW.m9SWMz9zYPPla.hIu7v/lCnMpvSE1aohURnMRMU8WKXEW3fu', 'Administrador', 'admin', 'admin@bar.com', 2, 1);
-- Con este usuario accederemos para crear los demás usuarios.
-- matricula: 1234 
-- pass: admin																																																																
		

--####################################################################--

CREATE DATABASE dbmenu;
USE dbmenu;

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    bar_id INT NOT NULL
);

CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DOUBLE,
    visible BOOLEAN DEFAULT TRUE,
    imagen VARCHAR(255),
    categoria_id INT,
    bar_id INT NOT NULL,
    inventario_id_directo INT DEFAULT NULL,
    gluten BOOLEAN DEFAULT FALSE,
    lacteos BOOLEAN DEFAULT FALSE, 
    sulfitos BOOLEAN DEFAULT FALSE,
    frutos_secos BOOLEAN DEFAULT FALSE,
    huevo BOOLEAN DEFAULT FALSE;
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);


CREATE TABLE ingrediente_menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_inventario_id INT NOT NULL, 
    cantidad_por_racion DOUBLE NOT NULL, 
    producto_menu_id INT,                   
    FOREIGN KEY (producto_menu_id) REFERENCES producto(id)
);

INSERT INTO dbmenu.categoria (id, nombre, bar_id) VALUES(1, 'Bebidas', 1);
INSERT INTO dbmenu.categoria (id, nombre, bar_id) VALUES(2, 'Tapas', 1);
INSERT INTO dbmenu.categoria (id, nombre, bar_id) VALUES(3, 'Platos principales', 1);
INSERT INTO dbmenu.categoria (id, nombre, bar_id) VALUES(4, 'Postres', 1);

--####################################################################--


CREATE DATABASE dbpedidos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dbpedidos;

CREATE TABLE comanda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bar_id INT NOT NULL,
    mesa_codigo VARCHAR(50) NOT NULL,
    fecha DATETIME NOT NULL,
    estado VARCHAR(50) DEFAULT 'en_preparacion'
);


CREATE TABLE item_comanda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL,
    producto_id INT,
    comanda_id INT,
    FOREIGN KEY (comanda_id) REFERENCES comanda(id) ON DELETE CASCADE
);


--####################################################################--


CREATE DATABASE IF NOT EXISTS dbfacturacion;
USE dbfacturacion;

CREATE TABLE factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    cliente VARCHAR(100),
    subtotal DECIMAL(10,2) NOT NULL,
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    metodo_pago VARCHAR(30),
    bar_id INT NOT NULL
);


CREATE TABLE linea_factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    factura_id INT NOT NULL,
    FOREIGN KEY (factura_id) REFERENCES factura(id) ON DELETE CASCADE
);


--####################################################################--


CREATE DATABASE IF NOT EXISTS dbnotificaciones;
USE dbnotificaciones;

CREATE TABLE notificacion (
  id INT AUTO_INCREMENT PRIMARY KEY,
  tipo VARCHAR(50) NOT NULL,
  mensaje TEXT NOT NULL,
  fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  leida BOOLEAN DEFAULT FALSE,
  bar_id INT NOT NULL
);


--####################################################################--


CREATE DATABASE dbinventario;
USE dbinventario;

CREATE TABLE categoria (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL,
  bar_id INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY nombre (nombre)
);


CREATE TABLE proveedor (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL,
  telefono VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL,
  bar_id INT NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE producto_inventario (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) DEFAULT NULL,
  unidad VARCHAR(255) DEFAULT NULL,
  stock_actual DOUBLE NOT NULL,
  stock_minimo DOUBLE NOT NULL,
  bar_id INT NOT NULL,
  categoria_id INT DEFAULT NULL,
  proveedor_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_categoria (categoria_id),
  KEY fk_proveedor (proveedor_id),
  CONSTRAINT fk_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id),
  CONSTRAINT fk_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedor (id)
);


CREATE TABLE pedido_proveedor (
  id INT NOT NULL AUTO_INCREMENT,
  proveedor_id INT NOT NULL,
  fecha DATE NOT NULL,
  total DOUBLE NOT NULL DEFAULT 0,
  bar_id INT NOT NULL,
  PRIMARY KEY (id),
  KEY fk_pedido_proveedor_proveedor (proveedor_id),
  CONSTRAINT fk_pedido_proveedor_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedor (id)
);


CREATE TABLE detalle_pedido (
  id INT NOT NULL AUTO_INCREMENT,
  pedido_id INT NOT NULL,
  producto_id INT NOT NULL,
  cantidad DOUBLE NOT NULL,
  precio_unitario DOUBLE NOT NULL,
  PRIMARY KEY (id),
  KEY fk_detalle_pedido_pedido (pedido_id),
  KEY fk_detalle_pedido_producto (producto_id),
  CONSTRAINT fk_detalle_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido_proveedor (id),
  CONSTRAINT fk_detalle_pedido_producto FOREIGN KEY (producto_id) REFERENCES producto_inventario (id)
);
