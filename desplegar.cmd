@echo off
title Despliegue Automatico - NoPainNoMain
cls

echo ==========================================================
echo           1. VERIFICANDO / DESCARGANDO REPOSITORIO
echo ==========================================================

:: Verifica si la carpeta del proyecto ya existe
if exist "NoPainNoMain" (
    echo Carpeta existente detectada. Actualizando codigo con Git Pull...
    git pull origin main
) else (
    echo Descargando repositorio por primera vez con Git Clone...
    git clone https://github.com/No-pain-No-main/project-backend.git
    cd project-backend
)

echo ==========================================================
echo           2. INICIANDO INFRAESTRUCTURA (DOCKER)
echo ==========================================================

:: Como el archivo docker-compose.yml está en la raíz de project-backend, lo ejecutamos aquí
echo Levantando contenedor de PostgreSQL...
docker compose up -d

echo Esperando 5 segundos a que la base de datos este lista...
timeout /t 5 /nobreak > nul

echo.
echo ==========================================================
echo           3. COMPILANDO Y ARRANCANDO SPRING BOOT
echo ==========================================================


cd NoPainNoMain

:: Verifica que el wrapper de Maven exista antes de ejecutarlo
if exist "mvnw.cmd" (
    echo Iniciando servidor Spring Boot en ventana secundaria...
    :: 'start' evita que el script se quede bloqueado aquí
    start "Spring Boot Server" cmd /c "mvnw.cmd clean spring-boot:run"
    
    echo Esperando 15 segundos a que Hibernate cree las tablas...
    timeout /t 20 /nobreak > nul
    
    echo Instalando dependencias necesarias y cargando datos...
    docker cp "src\main\resources\import.sql" nopainnomain-db:/tmp/import.sql
    docker exec -i nopainnomain-db psql -U Admin2026 -d nopainnomain_db -f /tmp/import.sql
    
    echo Proceso de despliegue y carga de datos completado con éxito.
) else (
    echo Error: No se encontró mvnw.cmd. Asegúrate de estar en la carpeta correcta y de que el proyecto esté clonado correctamente.
)

pause