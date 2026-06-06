-- 1. Población de Tipos de Documento (Document Types)
INSERT INTO document_type (id, name) VALUES (1, 'Cédula de Ciudadanía');
INSERT INTO document_type (id, name) VALUES (2, 'Cédula de Extranjería');
INSERT INTO document_type (id, name) VALUES (3, 'Pasaporte');

-- 2. Población de Géneros (Genders)
INSERT INTO gender (id, name) VALUES (1, 'Masculino');
INSERT INTO gender (id, name) VALUES (2, 'Femenino');
INSERT INTO gender (id, name) VALUES (3, 'Otro');

-- 3. Población de Estados del Estudiante (User Statuses)
INSERT INTO user_status (id, name) VALUES (1, 'Activo');
INSERT INTO user_status (id, name) VALUES (2, 'Suspendido');
INSERT INTO user_status (id, name) VALUES (3, 'Inactivo');

-- 4. Población de Estados de la Máquina (Machine Statuses)
INSERT INTO machine_status (id, name) VALUES (1, 'Disponible');
INSERT INTO machine_status (id, name) VALUES (2, 'Inactiva');
INSERT INTO machine_status (id, name) VALUES (3, 'Reservada');

-- 5. Población de Tipos de Máquina (Machine Types)
INSERT INTO machine_type (id, name) VALUES (1, 'Caminadora');
INSERT INTO machine_type (id, name) VALUES (2, 'Elíptica');
INSERT INTO machine_type (id, name) VALUES (3, 'Bicicleta de Spinning');

-- 6. Población de Estados de la Reserva (Booking Statuses)
INSERT INTO booking_status (id, name) VALUES (1, 'Activa');
INSERT INTO booking_status (id, name) VALUES (2, 'Cancelada');
INSERT INTO booking_status (id, name) VALUES (3, 'Finalizada');

-- 7. Población de Franjas Horarias (Time Slots)
INSERT INTO time_slot (id, name) VALUES (1, '7am-8am');
INSERT INTO time_slot (id, name) VALUES (2, '8am-9am');
INSERT INTO time_slot (id, name) VALUES (3, '9am-10am');
INSERT INTO time_slot (id, name) VALUES (4, '10am-11am');
INSERT INTO time_slot (id, name) VALUES (5, '11am-12pm');
INSERT INTO time_slot (id, name) VALUES (6, '12pm-1pm');
INSERT INTO time_slot (id, name) VALUES (7, '1pm-2pm');
INSERT INTO time_slot (id, name) VALUES (8, '2pm-3pm');
INSERT INTO time_slot (id, name) VALUES (9, '3pm-4pm');
INSERT INTO time_slot (id, name) VALUES (10, '4pm-5pm');