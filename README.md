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
git clone https://github.com/JhonierSerna14/Backend-DentalCarePro
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

---

⚡ **Desarrollado con Spring Boot y ❤️**

## ▶️ Quick start (Windows)

1. Asegúrate que Java 17+ esté instalado. Puedes verificar con:

```powershell
java -version
```

2. Si Java está instalado pero Gradle no, usa el script helper incluido:

```powershell
# Inicia la aplicación (usa el wrapper incluido)
.\run.ps1
```

## ▶️ Quick start (WSL / Linux / macOS)

```bash
# Ejecuta el helper para Unix-like
./run.sh
```

## 🔧 Persistir JAVA_HOME en Windows (opcional)

Si quieres evitar tener que configurar `JAVA_HOME` en cada sesión, puedes establecerlo permanentemente (usuario o máquina):

### Establecer para usuario (no requiere permisos de administrador)
```powershell
# Reemplaza la ruta con tu instalación real de JDK
.\set-java-home.ps1 -Path "C:\\Program Files\\Java\\jdk-25" -Scope User
```

### Establecer para toda la máquina (requiere permisos de administrador)
```powershell
Start-Process powershell -Verb runAs -ArgumentList "-NoProfile -ExecutionPolicy Bypass -File \"$PWD\\set-java-home.ps1\" -Path 'C:\\Program Files\\Java\\jdk-25' -Scope Machine"
```

Después de ejecutar, cierra y vuelve a abrir la terminal (o reinicia sesión) para que la variable sea visible en nuevas sesiones.

## 📌 Notas
- Si el helper detecta que `JAVA_HOME` no está configurado, intentará usar instalaciones comunes (p. ej. `C:\Program Files\Java\jdk-25`). Si no encuentra Java, mostrará instrucciones para instalarlo.
- La documentación interactiva de la API está disponible en `http://localhost:8080/docs` una vez que la aplicación esté en ejecución.