# DWF-ProyectoFase1-SpiderCoders

Proyecto base vacio con:
- Maven (WAR)
- JPA (Hibernate) para MySQL
- JSF
- Spring MVC

## Requisitos
- Java 11
- Maven 3.8+
- Eclipse IDE for Enterprise Java and Web Developers

## Importar en Eclipse
1. File -> Import -> Existing Maven Projects.
2. Seleccionar la carpeta `DWF-ProyectoFase1-SpiderCoders`.
3. Finalizar importacion.
4. Si Eclipse lo pide: Maven -> Update Project.

## Compilar
```bash
mvn clean package
```

## Estructura base
- `src/main/java/com/spidercoders/dwf/controladores` Controladores Spring MVC
- `src/main/java/com/spidercoders/dwf/dao` DAOs
- `src/main/java/com/spidercoders/dwf/pojos` Entidades
- `src/main/java/com/spidercoders/dwf/utilidades` Utilidades
- `src/main/resources/META-INF/persistence.xml` Configuracion JPA
- `src/main/webapp/index.xhtml` Vista inicial JSF
- `src/main/webapp/WEB-INF/views/home.jsp` Vista Spring MVC

## Notas
- Configura usuario/password de MySQL en `persistence.xml`.
- URL Spring MVC: `/app/home`
- URL JSF: `/index.xhtml`
