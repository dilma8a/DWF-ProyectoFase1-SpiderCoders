# DWF-ProyectoFase1-SpiderCoders

Proyecto base con:
- Maven (WAR)
- JSF 2.3 (MyFaces)
- Spring MVC 5
- Servlet API 4 (Tomcat 9)
- JPA (javax.persistence + EclipseLink)
- Login con roles simulados en session

## Requisitos
- Java 11
- Maven 3.8+
- Apache Tomcat 9.x

## Importar en Eclipse
1. File -> Import -> Existing Maven Projects.
2. Seleccionar la carpeta `DWF-ProyectoFase1-SpiderCoders`.
3. Finalizar importacion.
4. Si Eclipse lo pide: Maven -> Update Project.

## Compilar
```bash
mvn clean package
```

## Ejecutar en Tomcat 9
1. Generar WAR con `mvn clean package`.
2. Desplegar `target/DWF-ProyectoFase1-SpiderCoders.war` en Tomcat 9.
3. Abrir `http://localhost:8080/DWF-ProyectoFase1-SpiderCoders/`.

## Estructura base
- `src/main/java/com/spidercoders/dwf/controladores` Servlets
- `src/main/java/com/spidercoders/dwf/dao` DAOs
- `src/main/java/com/spidercoders/dwf/pojos` Entidades
- `src/main/java/com/spidercoders/dwf/utilidades` Utilidades
- `src/main/resources/META-INF/persistence.xml` Configuracion JPA
- `src/main/webapp/index.xhtml` Vista inicial JSF
- `src/main/webapp/login.xhtml` Vista login JSF
- `src/main/webapp/home.xhtml` Vista home JSF
- `src/main/webapp/admin.xhtml` Vista ADMIN
- `src/main/webapp/docente.xhtml` Vista DOCENTE
- `src/main/webapp/alumno.xhtml` Vista ALUMNO
- `src/main/webapp/acceso-denegado.xhtml` Vista de acceso denegado

## Notas
- Configura usuario/password de MySQL en `persistence.xml`.
- URL Home: `/home`
- URL Login: `/login`

## Seguridad por roles (simulada en servlets)
- Login: `/login`
- Portal con redireccion por rol: `/portal`
- Ruta ADMIN: `/admin/inicio`
- Ruta DOCENTE: `/docente/inicio`
- Ruta ALUMNO: `/alumno/inicio`

Usuarios de prueba en memoria:
- `admin` / `admin123` -> `ADMIN`
- `docente` / `docente123` -> `DOCENTE`
- `alumno` / `alumno123` -> `ALUMNO`
