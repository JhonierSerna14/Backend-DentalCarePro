# DentalCarePro - Sistema de Gestión de Clínica Odontológica

## 📋 Descripción

DentalCarePro es un sistema web integral para la gestión de clínicas odontológicas desarrollado con Spring Boot. La aplicación permite administrar pacientes, odontólogos, citas, tratamientos y odontogramas de manera eficiente y segura.

## 🏗️ Arquitectura

### Stack Tecnológico
- **Backend**: Spring Boot 3.2.2
- **Lenguaje**: Java 17
- **Base de Datos**: PostgreSQL
- **ORM**: Spring Data JPA (Hibernate)
- **Seguridad**: Spring Security
- **Frontend**: HTML, CSS, JavaScript (SPA)
- **Herramienta de Construcción**: Gradle

### Patrón de Arquitectura
El proyecto sigue una arquitectura en capas típica de Spring Boot:
- **Capa de Presentación**: Controladores REST (@RestController)
- **Capa de Dominio**: Entidades JPA (@Entity)
- **Capa de Persistencia**: Repositorios Spring Data (@Repository)
- **Capa de Configuración**: Configuración de seguridad y aplicación

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

## 🔐 Seguridad

### Autenticación y Autorización
- **Spring Security** configurado para autenticación basada en sesiones
- **Roles**: 
  - `ADMIN`: Odontólogos con acceso completo
  - `USER`: Pacientes con acceso limitado
- **Endpoints públicos**: Login, registro, recuperación de contraseña
- **Protección CSRF**: Deshabilitada para APIs REST
- **Encoder**: BCrypt para encriptación de contraseñas

### Endpoints de Seguridad
```
POST /api/login                    - Autenticación
POST /api/logout                   - Cerrar sesión
GET  /api/pregunta-seguridad       - Obtener pregunta de seguridad
POST /api/verificarRespuestaSeguridad - Verificar respuesta de seguridad
```

## 🚀 API REST

### Endpoints Principales

#### Pacientes
```
GET    /paciente/all               - Listar todos los pacientes
POST   /paciente/new               - Crear nuevo paciente
GET    /paciente/find              - Buscar paciente por cédula
PATCH  /paciente/update            - Actualizar paciente
DELETE /paciente/delete            - Eliminar paciente
```

#### Odontólogos
```
GET    /odontologo/all             - Listar todos los odontólogos
POST   /odontologo/new             - Crear nuevo odontólogo
GET    /odontologo/find            - Buscar odontólogo por ID
PATCH  /odontologo/update          - Actualizar odontólogo
DELETE /odontologo/delete          - Eliminar odontólogo
```

#### Citas Odontológicas
```
GET    /citaOdontologica/all       - Listar todas las citas
POST   /citaOdontologica/new       - Crear nueva cita
GET    /citaOdontologica/find      - Buscar cita por ID
PATCH  /citaOdontologica/update    - Actualizar cita
DELETE /citaOdontologica/delete    - Eliminar cita
```

#### Tratamientos
```
GET    /tratamiento/all            - Listar todos los tratamientos
POST   /tratamiento/new            - Crear nuevo tratamiento
GET    /tratamiento/find           - Buscar tratamiento por ID
PATCH  /tratamiento/update         - Actualizar tratamiento
DELETE /tratamiento/delete         - Eliminar tratamiento
```

## � Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- PostgreSQL 12 o superior
- Gradle 7.0 o superior

### Configuración de Base de Datos

1. **Crear base de datos en PostgreSQL:**
```sql
CREATE DATABASE DentalCarePro;
```

2. **Configurar credenciales en `application.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/DentalCarePro
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### Ejecución

1. **Clonar el repositorio:**
```bash
git clone https://github.com/tu-usuario/DentalCarePro.git
cd DentalCarePro
```

2. **Ejecutar la aplicación:**
```bash
./gradlew bootRun
```

3. **Acceder a la aplicación:**
- URL: `http://localhost:8080`
- La aplicación creará automáticamente las tablas necesarias

## 🧪 Testing

### Ejecutar Tests
```bash
./gradlew test
```

### Coverage
El proyecto incluye tests unitarios para validar la funcionalidad principal de controladores y servicios.

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── GestionClinicaOdontologicaApplication.java
│   │   ├── config/
│   │   │   └── WebSecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── PacienteController.java
│   │   │   ├── OdontologoController.java
│   │   │   ├── CitaOdontologicaController.java
│   │   │   └── ...
│   │   ├── entity/
│   │   │   ├── Paciente.java
│   │   │   ├── Odontologo.java
│   │   │   ├── CitaOdontologica.java
│   │   │   ├── Odontograma.java
│   │   │   └── ...
│   │   └── repository/
│   │       ├── PacienteRepository.java
│   │       ├── OdontologoRepository.java
│   │       └── ...
│   └── resources/
│       ├── application.properties
│       ├── static/          # Frontend assets
│       └── templates/       # Thymeleaf templates
└── test/
    └── java/com/example/demo/
        └── GestionClinicaOdontologicaApplicationTests.java
```

## 🔧 Características Técnicas

### Funcionalidades Implementadas
- ✅ Gestión completa de pacientes y odontólogos
- ✅ Sistema de citas odontológicas
- ✅ Odontograma automático (adultos/niños)
- ✅ Gestión de tratamientos y medicamentos
- ✅ Autenticación y autorización
- ✅ Recuperación de contraseña por pregunta de seguridad
- ✅ API REST completa
- ✅ Frontend SPA integrado

### Próximas Funcionalidades
- 🔄 Notificaciones de citas
- 🔄 Reportes y estadísticas
- 🔄 Sistema de facturación
- 🔄 Integración con servicios de correo

## � Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

## 📞 Contacto

- **Desarrollador**: [Tu Nombre]
- **Email**: [tu.email@ejemplo.com]
- **Proyecto**: [https://github.com/tu-usuario/DentalCarePro](https://github.com/tu-usuario/DentalCarePro)

---

⚡ **Desarrollado con Spring Boot y ❤️**
   - **Contraseña**: password (o configurar con `.\configure-password.ps1`)

### 🏃 Ejecutar Aplicación
```bash
# Método 1: Script principal (recomendado)
.\run.ps1

# Método 2: Script específico PostgreSQL
.\run-postgres.ps1

# Método 3: Gradle directo
.\gradlew bootRun
```

## 📋 Funcionalidades

### 👥 Gestión de Usuarios
- **Pacientes**: Registro, perfil, historial médico
- **Odontólogos**: Gestión de especialistas y consultorios

### 📅 Sistema de Citas
- Programación de citas odontológicas
- Gestión de horarios disponibles
- Vinculación paciente-odontólogo

### 🏥 Gestión Clínica
- **Consultorios**: Administración de espacios
- **Tratamientos**: Registro de procedimientos
- **Medicamentos**: Control de prescripciones
- **Odontograma**: Sistema visual de estado dental

### 🔐 Seguridad
- Autenticación con Spring Security
- Gestión de sesiones
- Protección de endpoints

## 🌐 Acceso
- **Aplicación Web**: http://localhost:8080

## 🛠️ Tecnologías
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Frontend**: React, HTML5, CSS3, JavaScript
- **Base de Datos**: PostgreSQL
- **Build**: Gradle
- **ORM**: Hibernate

## 📁 Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/     # Controladores REST
│   │   ├── entity/         # Entidades JPA
│   │   ├── repository/     # Repositorios de datos
│   │   └── config/         # Configuración de seguridad
│   └── resources/
│       ├── static/         # Archivos estáticos (React build)
│       └── application.properties
└── test/                   # Pruebas unitarias
```

## 🔧 Comandos Útiles
```bash
# Script principal
.\run.ps1

# Configurar PostgreSQL
.\setup-postgres.ps1

# Compilar proyecto
.\gradlew compileJava

# Ejecutar pruebas
.\gradlew test

# Limpiar proyecto
.\gradlew clean

# Ver estado de PostgreSQL (Docker)
docker ps
docker logs postgres-clinica

# Detener PostgreSQL (Docker)
docker stop postgres-clinica

# Conectar a PostgreSQL
psql -h localhost -U postgres -d clinicaBD
```

## �️ Configuración de Base de Datos
La aplicación se conecta a PostgreSQL con la siguiente configuración por defecto:
```properties
Host: localhost
Puerto: 5432
Base de datos: clinicaBD
Usuario: postgres
Contraseña: password
```

Para usar una configuración diferente, modifica `src/main/resources/application.properties`.