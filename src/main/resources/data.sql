-- ******************************************************
-- 1. LIMPIEZA DE DATOS (Orden Correcto: HIJOS ANTES QUE PADRES)
-- ******************************************************

DELETE FROM reservas;      -- 1. ELIMINAR HIJO PRINCIPAL
DELETE FROM personas;      -- 2. ELIMINAR PADRES
DELETE FROM articulos;
DELETE FROM salas;
DELETE FROM TBL_EMPLOYEES; -- 3. ELIMINAR TABLA DE PRUEBA

-- ******************************************************
-- 2. INSERCIONES DE SEGURIDAD Y RECURSOS
-- ******************************************************

-- Hash BCrypt para la contraseña '12345'
-- INSERT INTO personas (id, nombre, email, password, rol) VALUES
   --                                                         (1, 'Ana Pérez', 'ana.perez@organizacion.com', '12345', 'ADMIN'),
     --                                                       (2, 'Juan Gómez', 'juan.gomez@organizacion.com', '11', 'USER'),
       --                                                     (3, 'María López', 'maria.lopez@organizacion.com', '22', 'USER');

INSERT INTO personas ( nombre, email, password, rol) VALUES
                                                            ('Ana Pérez', 'ana.perez@organizacion.com', '12345', 'ADMIN'),
                                                            ('Juan Gómez', 'juan.gomez@organizacion.com', '11', 'USER'),
                                                            ('María López', 'maria.lopez@organizacion.com', '22', 'USER');

INSERT INTO articulos (id, nombre, disponible) VALUES
                                                   (1, 'Proyector Epson EB-X05', TRUE),
                                                   (2, 'Laptop HP EliteBook', FALSE),
                                                   (3, 'Cámara Sony Alpha a6400', TRUE),
                                                   (999, 'Sin Articulos', TRUE);
INSERT INTO salas (id, nombre, capacidad) VALUES
                                              (1, 'Sala de Reuniones 1A', 8),
                                              (2, 'Sala de Conferencias B2', 20),
                                              (3, 'Aula de Capacitación C3', 15),
                                              (4, 'Aula de Capacitación2 C4', 15);

INSERT INTO TBL_EMPLOYEES (id, first_name, last_name, email) VALUES
    (1, 'Dan', 'Vega', 'danvega@gmail.com');

-- ******************************************************
-- 3. INSERCIÓN DE RESERVAS (Debe ser la última inserción)
-- ******************************************************

INSERT INTO reservas (id_articulo, id_sala, id_persona, fecha_hora_inicio, fecha_hora_fin, estado_sala ) VALUES
                                                                                               (1, NULL, 1, '2025-09-11 10:00:00', '2025-09-11 11:00:00', 'ACTIVA'), -- id_persona=1 (Ana)
                                                                                               (NULL, 2, 2, '2025-09-12 14:00:00', '2025-09-12 16:00:00', 'ACTIVA'), -- id_persona=2 (Juan)
                                                                                               (2, NULL, 3, '2025-09-13 09:00:00', '2025-09-13 10:00:00', 'ACTIVA'); -- id_persona=3 (María)