#!/bin/bash

# Limpiar la pantalla
clear

echo "=========================================================="
echo "           1. VERIFICANDO / DESCARGANDO REPOSITORIO"
echo "=========================================================="

# Verifica si la carpeta del proyecto ya existe
if [ -d "project-backend" ]; then
    echo "Carpeta existente detectada. Actualizando codigo con Git Pull..."
    cd project-backend
    git pull origin main
else
    echo "Descargando repositorio por primera vez con Git Clone..."
    git clone https://github.com/No-pain-No-main/project-backend.git
    cd project-backend
fi

echo ""
echo "=========================================================="
echo "           2. INICIANDO INFRAESTRUCTURA (DOCKER)"
echo "=========================================================="

# Levanta el contenedor en segundo plano
echo "Levantando contenedor de PostgreSQL..."
docker compose up -d

echo "Esperando 5 segundos a que la base de datos este lista..."
sleep 5

echo ""
echo "=========================================================="
echo "           3. COMPILANDO Y ARRANCANDO SPRING BOOT"
echo "=========================================================="

cd NoPainNoMain

# Verifica que el wrapper de Maven exista antes de ejecutarlo
if [ -f "mvnw" ]; then
    echo "Ejecutando servidor mediante Maven Wrapper..."
    # Da permisos de ejecucion al mvnw por si acaso y lo arranca
    chmod +x mvnw
    ./mvnw spring-boot:run
else
    echo "ERROR: No se encontro el archivo mvnw en la raiz del proyecto."
fi

echo ""
read -p "Presiona [Enter] para salir..."