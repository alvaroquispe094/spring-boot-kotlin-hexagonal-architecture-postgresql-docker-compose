# spring-boot-kotlin-hexagonal-architecture-postgresql-docker-compose
A simple backend with kotlin and gradle, applying hexagonal architecture

## Que se ha utilizado?? 
Este proyecto esta basado en [Spring Boot](http://projects.spring.io/spring-boot/) y usa las siguientes tecnologias :
-Kotlin
- Gradle
- Arquitectura hexagonal
- Spring boot
- Spring Data (Hibernate & Postgresql)
- Docker
- Docker Compose

** Clona el repositorio **

```bash
git clone https://github.com/alvaroquispe094/spring-boot-kotlin-hexagonal-architecture-postgresql-docker-compose
```


## Pasos para correr la aplicacion localmente

**1. Crear la base de datos en postgresql localmente**

```bash
DB: 'countries-docker' (script adjunto en la raiz del repositorio)
```

**2. Modifica tu usuario y contraseña de postgresql**

+ abrir `src/main/resources/application.yml`

+ cambiar `spring.datasource.username` y `spring.datasource.password` con tu usuario y contraseña de posgresql


**3. Correr aplicación**

+ ejecutar la clase principal `src/main/kotlin/com/CountriesDockerApplication.kt` como java application

+ cambiar `spring.datasource.username` y `spring.datasource.password` con tu usuario y contraseña de posgresql


## Pasos para correr la aplicacion usando Docker Compose

**1. Crear la base de datos en postgresql localmente**

```bash
Descargar e instalar docker [Docker]([http://projects.spring.io/spring-boot/](https://docs.docker.com/get-docker/))
```

**2. Modifica tu usuario y contraseña de postgresql**

+ Ubicarse en la raiz del proyecto y ejecutar ` docker-compose up
