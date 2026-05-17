-- 1. Población de Tipos de Documento (Document Types)
INSERT INTO document_types (id, name) VALUES (1, 'Cédula de Ciudadanía');
INSERT INTO document_types (id, name) VALUES (2, 'Cédula de Extranjería');
INSERT INTO document_types (id, name) VALUES (3, 'Pasaporte');

-- 2. Población de Géneros (Genders)
INSERT INTO genders (id, name) VALUES (1, 'Masculino');
INSERT INTO genders (id, name) VALUES (2, 'Femenino');
INSERT INTO genders (id, name) VALUES (3, 'Otro');

-- 3. Población de Estados del Estudiante (User Statuses)
INSERT INTO user_statuses (id, name) VALUES (1, 'Activo');
INSERT INTO user_statuses (id, name) VALUES (2, 'Suspendido');
INSERT INTO user_statuses (id, name) VALUES (3, 'Inactivo');

-- 4. Población de Estados de la Máquina (Machine Statuses)
INSERT INTO machine_statuses (id, name) VALUES (1, 'Disponible');
INSERT INTO machine_statuses (id, name) VALUES (2, 'Inactiva');
INSERT INTO machine_statuses (id, name) VALUES (3, 'Reservada');

-- 5. Población de Tipos de Máquina (Machine Types)
INSERT INTO machine_types (id, name) VALUES (1, 'Caminadora');
INSERT INTO machine_types (id, name) VALUES (2, 'Elíptica');
INSERT INTO machine_types (id, name) VALUES (3, 'Bicicleta de Spinning');

-- 6. Población de Estados de la Reserva (Booking Statuses)
INSERT INTO booking_statuses (id, name) VALUES (1, 'Activa');
INSERT INTO booking_statuses (id, name) VALUES (2, 'Cancelada');
INSERT INTO booking_statuses (id, name) VALUES (3, 'Finalizada');