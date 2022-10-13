# spring-boot-kotlin-hexagonal-architecture-postgresql-docker-compose
A simple backend with kotlin and gradle, applying hexagonal architecture

## ¿Que se ha utilizado? 
Este proyecto esta basado en [Spring Boot](http://projects.spring.io/spring-boot/) y usa las siguientes tecnologias :
- Kotlin
- Gradle
- Jdk11
- Arquitectura hexagonal
- Spring boot
- Spring Data (Hibernate & Postgresql)
- Docker
- Docker Compose

## Arquitectura Hexagonal

![alt text](https://herbertograca.files.wordpress.com/2018/11/100-explicit-architecture-svg.png?w=1200)

## Clona el repositorio

```bash
git clone https://github.com/alvaroquispe094/spring-boot-kotlin-hexagonal-architecture-postgresql-docker-compose.git
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


## Pasos para correr la aplicacion usando Docker Compose

**1. Instalación**

+ Descargar e instalar [Docker](https://docs.docker.com/get-docker/)


**2. Correr aplicación**

+ Ubicarse en la raiz del proyecto y ejecutar `docker-compose up`
