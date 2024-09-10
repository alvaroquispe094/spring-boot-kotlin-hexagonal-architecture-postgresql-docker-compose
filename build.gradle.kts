import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	id("jacoco")
	id("org.sonarqube") version "4.2.1.3168"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "com.example.countries-docker"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.4"

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
	implementation ("org.springframework.kafka:spring-kafka")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// PostgreSql
	runtimeOnly("org.postgresql:postgresql")

	// Swagger
	implementation("io.springfox:springfox-boot-starter:3.0.0")

	// Logstash
	implementation("net.logstash.logback:logstash-logback-encoder:7.2")

	// Testing
	//testImplementation("org.springframework.boot:spring-boot-starter-test")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	// Arch unit
	testImplementation("com.tngtech.archunit:archunit:1.0.0")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.0.0")

	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
	//testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.3")
}


dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JacocoReport> {
	reports {
		html.required.set(true)

	}
}

jacoco {
	toolVersion = "0.8.2"
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // Asegura que los tests se ejecuten antes del reporte

	reports {
		//xml.required.set(true) // Para la integración con otras herramientas
		html.required.set(true) // Para generar un reporte HTML
	}
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.jacocoTestReport) // Verificación después del reporte
	violationRules {
		rule {
			limit {
				counter = "INSTRUCTION" // Contar líneas de código
				value = "COVEREDRATIO"
				minimum = "0.40".toBigDecimal() // Umbral del 80% de cobertura
			}
		}
		/*rule {
			enabled = true
			element = "CLASS"

			limit {
				counter = "BRANCH" // Cobertura de ramas (branch coverage)
				value = "COVEREDRATIO"
				minimum = "0.70".toBigDecimal() // Umbral del 70% de cobertura de ramas
			}
		}*/
	}
}

tasks.check {
	dependsOn(tasks.jacocoTestCoverageVerification) // Falla si no se cumple el umbral de cobertura
}

tasks.build {
	dependsOn(tasks.test) // Ejecutar las pruebas antes del build
	dependsOn(tasks.jacocoTestReport) // Generar el reporte como parte del build
	dependsOn(tasks.jacocoTestCoverageVerification) // Verificar la cobertura como parte del build
}

tasks.test {
	finalizedBy(tasks.jacocoTestCoverageVerification) // Ejecuta el reporte después de los tests.
}