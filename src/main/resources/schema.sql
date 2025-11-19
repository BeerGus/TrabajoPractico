/*DROP TABLE IF EXISTS TBL_EMPLOYEES;

CREATE TABLE TBL_EMPLOYEES (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               first_name VARCHAR(250) NOT NULL,
                               last_name VARCHAR(250) NOT NULL,
                               email VARCHAR(250) DEFAULT NULL
);
DROP TABLE IF EXISTS personas;
CREATE TABLE personas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE articulos (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(255) NOT NULL,
                           disponible BOOLEAN NOT NULL
);
CREATE TABLE salas (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       nombre VARCHAR(255) NOT NULL,
                       capacidad INT NOT NULL
);
CREATE TABLE reservas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          id_articulo INT,
                          id_sala INT,
                          id_persona INT NOT NULL,
                          fecha_hora_inicio DATETIME NOT NULL,
                          fecha_hora_fin DATETIME NOT NULL,
                          FOREIGN KEY (id_articulo) REFERENCES articulos(id),
                          FOREIGN KEY (id_sala) REFERENCES salas(id),
                          FOREIGN KEY (id_persona) REFERENCES personas(id)
);



-- TABLA DE PRUEBA (EMPLOYEES - Se puede eliminar si no se usa más)
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS TBL_EMPLOYEES; -- Se puede eliminar en cualquier momento ya que no tiene FKs

-- 2. ELIMINAR PADRES SEGUIDOS
DROP TABLE IF EXISTS articulos;
DROP TABLE IF EXISTS salas;
DROP TABLE IF EXISTS personas;


CREATE TABLE TBL_EMPLOYEES (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               first_name VARCHAR(250) NOT NULL,
                               last_name VARCHAR(250) NOT NULL,
                               email VARCHAR(250) DEFAULT NULL
);

-- TABLA SALAS

CREATE TABLE salas (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       nombre VARCHAR(255) NOT NULL,
                       capacidad INT NOT NULL
);


-- TABLA PERSONAS (Usuarios con Seguridad y Roles)

CREATE TABLE personas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL, -- ¡NUEVO! Campo para la contraseña hasheada
                          rol VARCHAR(50) NOT NULL        -- ¡NUEVO! Campo para el rol (ADMIN, USER)
);

-- TABLA ARTICULOS

CREATE TABLE articulos (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(255) NOT NULL,
                           disponible BOOLEAN NOT NULL
);


-- TABLA RESERVAS

CREATE TABLE reservas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          id_articulo INT,
                          id_sala INT,
                          id_persona INT NOT NULL,
                          fecha_hora_inicio DATETIME NOT NULL,
                          fecha_hora_fin DATETIME NOT NULL,

    -- Definiciones de las relaciones
                          FOREIGN KEY (id_articulo) REFERENCES articulos(id),
                          FOREIGN KEY (id_sala) REFERENCES salas(id),
                          FOREIGN KEY (id_persona) REFERENCES personas(id)
);

 */


-- ******************************************************
-- 1. SENTENCIAS DROP TABLE (Orden Inverso de Creación)
-- ******************************************************

-- ELIMINAR TABLAS HIJAS (las que contienen FOREIGN KEYS)
DROP TABLE IF EXISTS reservas;

-- ELIMINAR TABLAS PADRES/PRINCIPALES (las que son referenciadas)
DROP TABLE IF EXISTS articulos;
DROP TABLE IF EXISTS salas;
DROP TABLE IF EXISTS personas;

-- ELIMINAR TABLA DE PRUEBA (sin dependencias)
DROP TABLE IF EXISTS TBL_EMPLOYEES;

-- ******************************************************
-- 2. SENTENCIAS CREATE TABLE (Estructura de Seguridad)
-- ******************************************************

-- TABLA PERSONAS (Usuarios con Seguridad y Roles)
CREATE TABLE personas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL, -- Campo para contraseña hasheada (BCrypt)
                          rol VARCHAR(50) NOT NULL        -- Campo para el rol (ADMIN, USER)
);

-- TABLA ARTICULOS
CREATE TABLE articulos (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(255) NOT NULL,
                           disponible BOOLEAN NOT NULL
);

-- TABLA SALAS
CREATE TABLE salas (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       nombre VARCHAR(255) NOT NULL,
                       capacidad INT NOT NULL
);

-- TABLA DE PRUEBA (EMPLOYEES)
CREATE TABLE TBL_EMPLOYEES (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               first_name VARCHAR(250) NOT NULL,
                               last_name VARCHAR(250) NOT NULL,
                               email VARCHAR(250) DEFAULT NULL
);

-- TABLA RESERVAS (Relacional)
CREATE TABLE reservas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          id_articulo INT,
                          id_sala INT,
                          id_persona INT NOT NULL,
                          fecha_hora_inicio DATETIME NOT NULL,
                          fecha_hora_fin DATETIME NOT NULL,
                          estado_sala  VARCHAR(20) NOT NULL, -- ACTIVA / CANCELADA
    -- Definiciones de las relaciones (FOREIGN KEY)
                          FOREIGN KEY (id_articulo) REFERENCES articulos(id),
                          FOREIGN KEY (id_sala) REFERENCES salas(id),
                          FOREIGN KEY (id_persona) REFERENCES personas(id)
);