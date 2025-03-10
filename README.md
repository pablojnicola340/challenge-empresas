# Challenge Backend - Empresas

Este proyecto es una implementación de un backend en Spring Boot para gestionar empresas y sus transferencias, siguiendo una arquitectura hexagonal. Incluye tres endpoints principales:
1. Obtener empresas que hicieron transferencias en el último mes.
2. Obtener empresas que se adhirieron en el último mes.
3. Adherir una nueva empresa.

## Requisitos

- Java 17
- Docker
- MySQL
- Maven

---

## Configuración del Proyecto

### 1. Clonar el Repositorio

git clone https://github.com/pablojnicola340/challenge-empresas.git
cd challenge-empresas

### 2. Configurar MySQL con Docker

Este proyecto utiliza MySQL como base de datos. Para facilitar su ejecución en entornos de desarrollo, utilizamos Docker con volúmenes persistentes.

#### Descargar y ejecutar la imagen de MySQL con manejo de volumenes:

 docker pull mysql:latest 
 docker run --name mysql-empresas_db \  
 -e MYSQL_ROOT_PASSWORD=root \
 -e MYSQL_DATABASE=empresas_db \
 -e MYSQL_USER=admin \
 -e MYSQL_PASSWORD=admin \
 -p 3306:3306 \
 -v mysql_empresas_data:/var/lib/mysql \
 -v $(pwd)/scripts:/docker-entrypoint-initdb.d \
 -d mysql:8

#### Acceder a la base de datos dentro del contenedor:

sudo docker exec -it mysql-container mysql -u root -p

#### Acceder a la consola de MySQL:

docker exec -it mysql-container mysql -u user -p

#### Crear el esquema y las tablas:

CREATE TABLE Empresa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuit VARCHAR(11) NOT NULL UNIQUE,
    razon_social VARCHAR(255) NOT NULL,
    fecha_adhesion DATE NOT NULL
);

CREATE TABLE Transferencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    importe DECIMAL(19, 2) NOT NULL,
    id_empresa BIGINT NOT NULL,
    cuenta_debito VARCHAR(255) NOT NULL,
    cuenta_credito VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES Empresa(id)
);

CREATE TABLE Adhesion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empresa BIGINT NOT NULL,
    fecha_adhesion DATE NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES Empresa(id)
);

#### Datos de prueba:

INSERT INTO Empresa (cuit, razon_social, fecha_adhesion) VALUES ('20345678901', 'Empresa A', '2023-09-01');
INSERT INTO Empresa (cuit, razon_social, fecha_adhesion) VALUES ('30345678901', 'Empresa B', '2023-09-15');

INSERT INTO Transferencia (importe, id_empresa, cuenta_debito, cuenta_credito) VALUES (1000.00, 1, 'Cuenta1', 'Cuenta2');
INSERT INTO Transferencia (importe, id_empresa, cuenta_debito, cuenta_credito) VALUES (2000.00, 2, 'Cuenta3', 'Cuenta4');

INSERT INTO Adhesion (id_empresa, fecha_adhesion) VALUES (1, '2023-09-01');
INSERT INTO Adhesion (id_empresa, fecha_adhesion) VALUES (2, '2023-09-15');

### 3. Ejecutar el Proyecto
Compilar y ejecutar con Maven:

mvn clean install
mvn spring-boot:run

El servicio estará disponible en http://localhost:8080.

### Endpoints
#### 1. Obtener empresas con transferencias en el último mes
Método: GET

URL: /empresas/transferencias-ultimo-mes

Respuesta: Lista de empresas que hicieron transferencias en el último mes.

#### 2. Obtener empresas adheridas en el último mes
Método: GET

URL: /empresas/adheridas-ultimo-mes

Respuesta: Lista de empresas que se adhirieron en el último mes.

#### 3. Adherir una nueva empresa
Método: POST

URL: /empresas

Body:

{
  "cuit": "20345678901",
  "razonSocial": "Empresa A",
  "fechaAdhesion": "2023-09-01"
}

Respuesta: Ninguna (código de estado 200 si la operación es exitosa).

## Documentación de la API

http://localhost:8080/swagger-ui.html

### Pruebas Unitarias

mvn test

#### Las pruebas cubren:

Controladores (EmpresaControllerTest).

Servicios (EmpresaServiceTest).

## Consideraciones

Las fechas de las transferencias están almacenadas en la base de datos.

La fecha de adhesión se registra automáticamente al adherir una empresa.
