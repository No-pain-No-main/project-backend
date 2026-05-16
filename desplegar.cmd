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
    echo Ejecutando servidor mediante Maven Wrapper...
    call mvnw.cmd spring-boot:run
) else (
    echo ERROR: No se encontro el archivo mvnw.cmd en la raiz del proyecto.
)

pause