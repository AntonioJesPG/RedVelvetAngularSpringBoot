drop database if exists proyecto;
create database proyecto;
use proyecto;

create table tipo(
`id` int NOT NULL,
`tipo` varchar(250) NOT NULL,
PRIMARY KEY (`id`)
);


create table rol(
`id` int NOT NULL,
`rol` varchar(250) NOT NULL,
PRIMARY KEY (`id`)
);

create table usuario( 
  `id` int NOT NULL,
  `nombre` varchar(250) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL UNIQUE,
  `password` varchar(500) DEFAULT NULL,
  `direccion` varchar(500) DEFAULT NULL,
  `ciudad` varchar(500) DEFAULT NULL,
  `telefono` varchar(500) DEFAULT NULL,
  `saldo` float DEFAULT NULL,
  `id_rol` int NOT NULL,
   PRIMARY KEY (`id`),
   KEY `fk_usuario_rol_idx` (`id_rol`),
   CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`) ON UPDATE CASCADE
  );
  
  create table producto(
     `id` int NOT NULL,
     `nombre` varchar(250) DEFAULT NULL,
     `precio` float DEFAULT NULL,
     `imagen` longblob DEFAULT NULL,
     `id_tipo` int NOT NULL,
     PRIMARY KEY (`id`),
	 KEY `fk_producto_tipo_idx` (`id_tipo`),
     CONSTRAINT `fk_producto_tipo` FOREIGN KEY (`id_tipo`) REFERENCES `tipo` (`id`) ON UPDATE CASCADE
  );
  
  create table historial(
	`id` int NOT NULL,
    `id_usuario` int NOT NULL,
    `id_producto` int NOT NULL,
    `nombre_producto` varchar(250) NOT NULL,
    `cantidad` int DEFAULT NULL,
    `precio` float NOT NULL,
    `codigo` varchar(250) NOT NULL,
    `fecha` varchar(250) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY (`codigo`),
    KEY `fk_historialUsuario_idx` (`id_usuario`),
    KEY `fk_historialProducto_idx` (`id_producto`),
    CONSTRAINT `fk_historialUsuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
  );
  
    create table cesta(
	`id` int NOT NULL,
    `id_usuario` int NOT NULL,
    `id_producto` int NOT NULL,
    `cantidad` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_cestaUsuario_idx` (`id_usuario`),
    KEY `fk_cestaProducto_idx` (`id_producto`),
    CONSTRAINT `fk_cestaUsuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_cestaProducto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
  );
  
    
  