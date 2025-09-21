# 🦷 DentalCarePro - Sistema de Gestión de Clínica Odontológica

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue.svg)](https://www.postgresql.org/)
[![API Docs](https://img.shields.io/badge/API%20Docs-Swagger%20UI-85EA2D.svg)](http://localhost:8080/docs)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 📋 Descripción

**DentalCarePro** es un sistema web integral para la gestión de clínicas odontológicas desarrollado con **Spring Boot**. La aplicación permite administrar pacientes, odontólogos, citas, tratamientos y odontogramas de manera eficiente y segura, con una **API REST profesional** y **documentación interactiva**.

## ✨ Características Principales

- 🦷 **Gestión Completa de Pacientes** - Registro, historiales médicos y odontogramas automáticos
- 👨‍⚕️ **Administración de Odontólogos** - Perfiles profesionales y especialidades
- 📅 **Sistema de Citas** - Programación inteligente con validación de disponibilidad
- 💊 **Catálogo de Medicamentos** - Gestión de prescripciones y tratamientos
- 📋 **Odontogramas Digitales** - Generación automática según edad del paciente
- 🔐 **Seguridad Robusta** - Autenticación multicapa y autorización por roles
- 📚 **API Documentada** - Documentación interactiva estilo FastAPI con Swagger UI
- 🎯 **Arquitectura REST** - APIs modernas para integración con sistemas externos

## 🏗️ Arquitectura

### Stack Tecnológico
- **Backend**: Spring Boot 3.2.2 + Spring Security
- **Lenguaje**: Java 17
- **Base de Datos**: PostgreSQL 12+
- **ORM**: Spring Data JPA (Hibernate)
- **Documentación**: SpringDoc OpenAPI 3 (Swagger UI)
- **Monitoreo**: Spring Boot Actuator
- **Frontend**: HTML5, CSS3, JavaScript (SPA)
- **Build**: Gradle 7+ con Wrapper

### Patrón de Arquitectura
El proyecto implementa una **arquitectura hexagonal** con Spring Boot:
- **🌐 Capa de Presentación**: Controllers REST (@RestController) con documentación OpenAPI
- **🧠 Capa de Dominio**: Entidades JPA (@Entity) con validaciones
- **💾 Capa de Persistencia**: Repositories Spring Data (@Repository)
- **⚙️ Capa de Configuración**: Security, OpenAPI y perfiles de entorno
- **📊 Capa de Monitoreo**: Health checks y métricas con Actuator

## 📊 Modelo de Datos

### Entidades Principales

#### 👤 Paciente
- **ID**: Cédula (String)
- **Datos Personales**: Nombres, apellidos, teléfono, dirección, email
- **Datos Médicos**: Alergias, condiciones médicas, fecha de nacimiento
- **Seguridad**: Password, pregunta y respuesta de seguridad
- **Relaciones**: 
  - One-to-Many con CitaOdontologica
  - One-to-One con Odontograma

#### 🩺 Odontólogo
- **ID**: Generado automáticamente
- **Datos Profesionales**: Nombres, apellidos, especialidad
- **Contacto**: Teléfono, email
- **Seguridad**: Password, pregunta y respuesta de seguridad
- **Relaciones**:
  - One-to-Many con CitaOdontologica
  - One-to-One con Consultorio

#### 📅 CitaOdontologica
- **ID**: Generado automáticamente
- **Datos**: Fecha, hora, motivo de consulta
- **Relaciones**:
  - Many-to-One con Paciente
  - Many-to-One con Odontólogo
  - One-to-Many con Tratamiento

#### 🦷 Odontograma
- **ID**: Generado automáticamente
- **Funcionalidad**: Registro dental automático (16 dientes adultos, 10 niños)
- **Relaciones**:
  - One-to-One con Paciente
  - One-to-Many con Diente

#### 💊 Sistema de Medicamentos
- **Medicamento**: Catálogo de medicamentos
- **MedicamentoxTratamiento**: Relación many-to-many entre medicamentos y tratamientos
- **Tratamiento**: Procedimientos realizados en las citas

## 🔐 Seguridad y Configuración

### 🛡️ Autenticación y Autorización
- **Spring Security** configurado para autenticación basada en sesiones
- **Encriptación**: BCrypt para contraseñas con salt automático
- **Roles diferenciados**: 
  - `ADMIN/ODONTOLOGO`: Acceso completo al sistema
  - `USER/PACIENTE`: Acceso limitado a su información personal
- **Recuperación segura**: Sistema de preguntas y respuestas de seguridad
- **CSRF Protection**: Configurado para APIs REST

### 🌐 Endpoints de Seguridad
```http
POST /api/login                    # Autenticación con email/password
POST /api/logout                   # Cierre de sesión seguro
GET  /api/pregunta-seguridad       # Obtener pregunta de seguridad por email
POST /api/verificarRespuestaSeguridad # Verificar respuesta de seguridad
```

### 🔒 Configuración de Acceso
- **Endpoints Públicos**: Documentación API, login, recuperación de contraseña
- **Endpoints Protegidos**: Gestión de datos, operaciones CRUD
- **Documentación API**: Acceso público a Swagger UI y OpenAPI specs
- **Monitoreo**: Health checks disponibles públicamente

## ⚙️ Configuración por Perfiles

### 🔧 Perfiles de Entorno
El proyecto incluye configuración específica por entorno:

#### **Desarrollo** (`application-dev.properties`)
```properties
# Base de datos local
spring.datasource.url=jdbc:postgresql://localhost:5432/DentalCarePro_dev
# Logs detallados
logging.level.org.springframework.security=DEBUG
# Swagger habilitado
springdoc.swagger-ui.enabled=true
```

#### **Producción** (`application-prod.properties`)
```properties
# Base de datos remota
spring.datasource.url=${DATABASE_URL}
# Logs optimizados
logging.level.org.springframework.security=WARN
# Configuración de seguridad estricta
server.ssl.enabled=true
```

### 🌍 Variables de Entorno
```bash
# Activar perfil específico
export SPRING_PROFILES_ACTIVE=dev
# o para producción
export SPRING_PROFILES_ACTIVE=prod
```

## 🚀 API REST y Documentación Interactiva

### 📚 Documentación Automática
La aplicación incluye **documentación interactiva completa** similar a FastAPI:

- **🌐 Swagger UI**: `http://localhost:8080/docs` - Interfaz interactiva para probar APIs
- **📄 OpenAPI Spec**: `http://localhost:8080/api-docs` - Especificación OpenAPI 3.0
- **⚕️ Health Check**: `http://localhost:8080/actuator/health` - Estado del sistema

### ✨ Características de la Documentación
- 🔍 **Exploración Interactiva** - Prueba endpoints directamente desde el navegador
- 📊 **Esquemas Automáticos** - Modelos de datos generados automáticamente
- 🔒 **Autenticación Integrada** - Soporte para testing con autenticación real
- 📱 **Responsive Design** - Interfaz moderna optimizada para móviles y desktop
- 🏷️ **Tags Organizados** - APIs agrupadas por funcionalidad

### 🎯 Tags de la API
- 🦷 **Pacientes** - Gestión completa de pacientes y odontogramas
- 👨‍⚕️ **Odontólogos** - Administración de profesionales de la salud
- 📅 **Citas** - Programación y seguimiento de citas odontológicas
- 💊 **Medicamentos** - Catálogo farmacológico y prescripciones
- 🏥 **Tratamientos** - Procedimientos clínicos y seguimiento
- 🔐 **Autenticación** - Login, logout y gestión de sesiones

### Endpoints Principales

#### 🦷 Pacientes
```http
GET    /paciente/all               # Listar todos los pacientes
POST   /paciente/new               # Crear nuevo paciente (con odontograma automático)
GET    /paciente/FiltroxNombre     # Buscar pacientes por nombre
PATCH  /paciente/update            # Actualizar información del paciente
DELETE /paciente/delete            # Eliminar paciente
GET    /paciente/Odontograma       # Obtener odontograma específico
POST   /paciente/actualizarOdontograma  # Actualizar estado dental
```

#### 👨‍⚕️ Odontólogos
```http
GET    /odontologo/all             # Listar todos los odontólogos
POST   /odontologo/new             # Registrar nuevo odontólogo
GET    /odontologo/FiltroxNombre   # Buscar odontólogos por nombre
PATCH  /odontologo/update          # Actualizar perfil profesional
DELETE /odontologo/delete          # Eliminar odontólogo
```

#### 📅 Citas Odontológicas
```http
GET    /citaOdontologica/all       # Listar todas las citas
POST   /citaOdontologica/new       # Agendar nueva cita
PATCH  /citaOdontologica/update    # Reprogramar cita
DELETE /citaOdontologica/delete    # Cancelar cita
GET    /citaOdontologica/tratamientosEnPaciente  # Historial de tratamientos
```

#### 💊 Medicamentos y Tratamientos
```http
GET    /medicamento/all            # Catálogo de medicamentos
POST   /medicamento/new            # Agregar nuevo medicamento
GET    /tratamiento/all            # Listar tratamientos
POST   /tratamiento/new            # Registrar tratamiento
POST   /medicamentoxTratamiento/new # Asociar medicamentos a tratamientos
```

#### 🔐 Autenticación y Seguridad
```http
POST   /api/login                  # Iniciar sesión
POST   /api/logout                 # Cerrar sesión
GET    /api/pregunta-seguridad     # Recuperación de contraseña
POST   /api/verificarRespuestaSeguridad  # Verificar pregunta de seguridad
```

## 🛠️ Instalación y Configuración

### 📋 Prerrequisitos
- **Java 17+** - [Descargar OpenJDK](https://adoptium.net/)
- **PostgreSQL 12+** - [Descargar PostgreSQL](https://www.postgresql.org/download/)
- **Git** - Para clonar el repositorio

### 🚀 Instalación Rápida

#### 1️⃣ Clonar el Repositorio
```bash
git clone https://github.com/JhonierSerna14/Backend-DentalCarePro.git
cd Backend-DentalCarePro
```

#### 2️⃣ Configurar Base de Datos
```sql
-- Conectarse a PostgreSQL y crear la base de datos
CREATE DATABASE DentalCarePro;
CREATE USER dentalcare_user WITH ENCRYPTED PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE DentalCarePro TO dentalcare_user;
```

#### 3️⃣ Configurar Variables de Entorno
```bash
# Linux/macOS
export DB_URL=jdbc:postgresql://localhost:5432/DentalCarePro
export DB_USERNAME=dentalcare_user
export DB_PASSWORD=your_secure_password
export SPRING_PROFILES_ACTIVE=dev

# Windows PowerShell
$env:DB_URL="jdbc:postgresql://localhost:5432/DentalCarePro"
$env:DB_USERNAME="dentalcare_user"
$env:DB_PASSWORD="your_secure_password"
$env:SPRING_PROFILES_ACTIVE="dev"
```

#### 4️⃣ Ejecutar la Aplicación

**🪟 Windows:**
```powershell
# Método recomendado (usa el script helper)
.\run.ps1

# O manualmente
.\gradlew.bat bootRun
```

**🐧 Linux/macOS:**
```bash
# Método recomendado
./run.sh

# O manualmente
./gradlew bootRun
```

#### 5️⃣ Verificar Instalación
- **🌐 Aplicación**: `http://localhost:8080`
- **📚 Documentación API**: `http://localhost:8080/docs`
- **⚕️ Health Check**: `http://localhost:8080/actuator/health`

### 🔧 Configuración Avanzada

#### Personalizar Puerto y Contexto
```properties
# application.properties
server.port=8080
server.servlet.context-path=/api/v1
springdoc.swagger-ui.path=/docs
```

#### Configurar CORS para Frontend
```properties
# application.properties
cors.allowed-origins=http://localhost:3000,https://dentalcarepro.com
cors.allowed-methods=GET,POST,PUT,DELETE,PATCH,OPTIONS
```

### 🔍 Scripts de Desarrollo

#### Configurar JAVA_HOME (Windows)
```powershell
# Para usuario actual
.\set-java-home.ps1 -Path "C:\Program Files\Java\jdk-17" -Scope User

# Para toda la máquina (requiere admin)
.\set-java-home.ps1 -Path "C:\Program Files\Java\jdk-17" -Scope Machine
```

## 🧪 Testing y Calidad

### 🔬 Ejecutar Tests
```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar tests con reporte detallado
./gradlew test --info

# Ejecutar tests específicos
./gradlew test --tests "PacienteControllerTest"
```

### 📊 Cobertura de Código
```bash
# Generar reporte de cobertura
./gradlew jacocoTestReport

# Ver reporte
open build/reports/jacoco/test/html/index.html
```

### 🔍 Análisis de Código
```bash
# Verificar calidad del código
./gradlew check

# Generar reportes de análisis
./gradlew spotbugsMain
```

## 📈 Monitoreo y Observabilidad

### 📊 Spring Boot Actuator
La aplicación incluye endpoints de monitoreo:

```http
GET /actuator/health          # Estado general del sistema
GET /actuator/info            # Información de la aplicación
GET /actuator/metrics         # Métricas de rendimiento
GET /actuator/env             # Variables de entorno (solo dev)
```

### 🔍 Logs Estructurados
```bash
# Ver logs en tiempo real
tail -f logs/spring.log

# Filtrar logs por nivel
grep "ERROR" logs/spring.log
```

### 📊 Métricas Personalizadas
La aplicación registra métricas específicas:
- Número de citas creadas por día
- Tiempo de respuesta de APIs
- Errores de autenticación
- Uso de memoria y CPU

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── GestionClinicaOdontologicaApplication.java    # Aplicación principal
│   │   ├── config/
│   │   │   ├── WebSecurityConfig.java                   # Configuración de seguridad
│   │   │   └── OpenAPIConfig.java                       # Configuración Swagger/OpenAPI
│   │   ├── controller/                                  # Controladores REST
│   │   │   ├── AuthController.java                      # Autenticación
│   │   │   ├── PacienteController.java                  # Gestión de pacientes
│   │   │   ├── OdontologoController.java                # Gestión de odontólogos
│   │   │   ├── CitaOdontologicaController.java          # Gestión de citas
│   │   │   ├── TratamientoController.java               # Gestión de tratamientos
│   │   │   ├── MedicamentoController.java               # Catálogo de medicamentos
│   │   │   └── ConsultorioController.java               # Gestión de consultorios
│   │   ├── entity/                                      # Entidades JPA
│   │   │   ├── Paciente.java                           # Paciente + seguridad
│   │   │   ├── Odontologo.java                         # Odontólogo + especialidad
│   │   │   ├── CitaOdontologica.java                   # Cita + estados
│   │   │   ├── Odontograma.java                        # Odontograma automático
│   │   │   ├── Diente.java                             # Estado dental individual
│   │   │   ├── Tratamiento.java                        # Procedimientos
│   │   │   ├── Medicamento.java                        # Catálogo farmacológico
│   │   │   └── MedicamentoxTratamiento.java            # Prescripciones
│   │   └── repository/                                 # Repositorios Spring Data
│   │       ├── PacienteRepository.java
│   │       ├── OdontologoRepository.java
│   │       ├── CitaOdontologicaRepository.java
│   │       └── ...
│   └── resources/
│       ├── application.properties                      # Configuración base
│       ├── application-dev.properties                  # Perfil desarrollo
│       ├── application-prod.properties                 # Perfil producción
│       ├── static/                                     # Assets frontend
│       │   ├── css/, js/, images/                      # Recursos estáticos
│       │   ├── index.html                              # SPA principal
│       │   └── manifest.json                           # PWA manifest
│       └── templates/                                  # Plantillas (si es necesario)
├── test/                                               # Tests automatizados
│   └── java/com/example/demo/
│       ├── controller/                                 # Tests de controladores
│       ├── service/                                    # Tests de servicios
│       ├── repository/                                 # Tests de repositorios
│       └── integration/                                # Tests de integración
├── docs/                                               # Documentación adicional
│   ├── API_DOCS.md                                     # Guía de API
│   ├── DATABASE_SCHEMA.md                              # Esquema de BD
│   └── DEPLOYMENT.md                                   # Guía de despliegue
├── scripts/                                            # Scripts de desarrollo
│   ├── run.ps1, run.sh                                 # Scripts de inicio
│   └── set-java-home.ps1                               # Configuración Java
├── build.gradle                                        # Configuración Gradle
├── gradlew, gradlew.bat                                # Gradle Wrapper
└── README.md                                           # Este archivo
```

## � Características Técnicas y Funcionalidades

### ✅ Funcionalidades Implementadas

#### 🦷 **Gestión Integral de Pacientes**
- ✅ Registro completo con validaciones
- ✅ Odontograma automático (adultos: 16 dientes, niños: 10 dientes)
- ✅ Historial médico completo (alergias, condiciones médicas)
- ✅ Sistema de preguntas de seguridad para recuperación
- ✅ Actualización de estado dental por diente

#### 👨‍⚕️ **Administración de Odontólogos**
- ✅ Perfiles profesionales con especialidades
- ✅ Asignación de consultorios
- ✅ Gestión de horarios y disponibilidad
- ✅ Autenticación con roles diferenciados

#### 📅 **Sistema de Citas Inteligente**
- ✅ Programación con validación de disponibilidad
- ✅ Estados de cita (Programada, Completada, Cancelada)
- ✅ Asociación automática paciente-odontólogo
- ✅ Historial completo de citas por paciente

#### 💊 **Gestión Farmacológica**
- ✅ Catálogo completo de medicamentos
- ✅ Asociación medicamentos-tratamientos (many-to-many)
- ✅ Prescripciones detalladas
- ✅ Seguimiento de tratamientos activos

#### 🔐 **Seguridad Avanzada**
- ✅ Autenticación basada en sesiones (JSESSIONID)
- ✅ Encriptación BCrypt con salt automático
- ✅ Roles diferenciados (ADMIN/ODONTOLOGO, USER/PACIENTE)
- ✅ Recuperación segura por pregunta-respuesta
- ✅ Protección CSRF para APIs REST

#### 📚 **Documentación y APIs**
- ✅ **Swagger UI interactivo** (igual que FastAPI)
- ✅ **OpenAPI 3.0** specification completa
- ✅ **Documentación automática** de esquemas
- ✅ **Testing en vivo** desde navegador
- ✅ **Ejemplos de respuesta** y validaciones

#### ⚙️ **DevOps y Configuración**
- ✅ **Perfiles de entorno** (dev, prod)
- ✅ **Scripts de inicio** multiplataforma
- ✅ **Health checks** y métricas con Actuator
- ✅ **Gradle wrapper** para builds consistentes
- ✅ **Variables de entorno** para configuración

**⚡ Desarrollado con Spring Boot y ❤️**

[![Spring Boot](https://img.shields.io/badge/Powered%20by-Spring%20Boot-6DB33F.svg)](https://spring.io/projects/spring-boot)
[![Made with Java](https://img.shields.io/badge/Made%20with-Java-ED8B00.svg)](https://www.java.com/)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-316192.svg)](https://www.postgresql.org/)

**🌟 ¡Dale una estrella si te gusta el proyecto! ⭐**

</div>
