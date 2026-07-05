#!/bin/bash

echo "=========================================================="
echo "           2. INICIANDO INFRAESTRUCTURA (DOCKER)"
echo "=========================================================="

echo "Levantando contenedor de PostgreSQL..."
docker compose up -d

echo "Esperando 5 segundos a que la base de datos esté lista..."
sleep 5

echo ""
echo "=========================================================="
echo "           3. COMPILANDO Y ARRANCANDO SPRING BOOT"
echo "=========================================================="
cd NoPainNoMain
# Verificamos si mvnw existe en la carpeta actual
if [ -f "mvnw" ]; then
    chmod +x mvnw
    
    echo "Abriendo ventana de terminal para el servidor Spring Boot..."
    
    # Abre una nueva ventana de terminal y ejecuta Spring Boot ahí
    gnome-terminal --title="Spring Boot Server" -- ./mvnw clean spring-boot:run
    
    echo "Esperando 20 segundos a que Hibernate cree las tablas..."
    sleep 20
    
    echo "Instalando dependencias necesarias y cargando datos..."
    docker cp "src/main/resources/import.sql" nopainnomain-db:/tmp/import.sql
    docker exec -i nopainnomain-db psql -U Admin2026 -d nopainnomain_db -f /tmp/import.sql
    
    echo "Proceso de despliegue y carga de datos completado con éxito."
else
    echo "Error: No se encontró mvnw. Asegúrate de estar en la carpeta donde está el pom.xml."
fi