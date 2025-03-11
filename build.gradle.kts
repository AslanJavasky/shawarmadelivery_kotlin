import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("jvm") version "2.1.0"// "1.9.25"
    kotlin("plugin.spring") version "2.1.0"//"1.9.25"
    id("org.springframework.boot") version "3.4.3"//"3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
//    id("nu.studer.jooq") version "9.0"
    id("java")
    id("org.liquibase.gradle") version "2.1.1"
}

group = "com.aslanjavasky"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.liquibase:liquibase-core:4.31.1")
//    implementation("org.liquibase.gradle:org.liquibase.gradle.gradle.plugin:2.1.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.postgresql:postgresql:42.7.5")
//    jooqGenerator("org.postgresql:postgresql:42.7.5")
    implementation("org.springframework.security:spring-security-crypto:6.4.2")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


val springProps = project.file("src/main/resources/application.properties")
    .inputStream()
    .use {
        Properties().apply { load(it) }
    }

//jooq {
//    version.set("3.19.18")
//    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
//
//    configurations {
//        create("main") {  // name of the jOOQ configuration
//            generateSchemaSourceOnCompilation.set(true)
//
//            jooqConfiguration.apply {
////                logging = org.jooq.meta.jaxb.Logging.DEBUG
//                jdbc.apply {
////					logger.WARM
////                    driver = "org.postgresql.Driver"
////                    url = "jdbc:postgresql://localhost:5432/shawarma_db_kotlin"
////                    user = "postgres"
////                    password = "pg555111"
//                    driver = springProps.getProperty("spring.datasource.driver-class-name")
//                    url = springProps.getProperty("spring.datasource.url")
//                    user = springProps.getProperty("spring.datasource.username")
//                    password = springProps.getProperty("spring.datasource.password")
////					properties.add(Property().apply {
////						key = "ssl"
////						value = "true"
////					})
//                }
//                generator.apply {
//                    name = "org.jooq.codegen.DefaultGenerator"
//                    database.apply {
//                        name = "org.jooq.meta.postgres.PostgresDatabase"
//                        inputSchema = "public"
//                        includes = ".*"
////                        excludes = "deliveries"
////						forcedTypes.addAll(listOf(
////							ForcedType().apply {
////								name = "varchar"
////								includeExpression = ".*"
////								includeTypes = "JSONB?"
////							},
////							ForcedType().apply {
////								name = "varchar"
////								includeExpression = ".*"
////								includeTypes = "INET"
////							},
////                            ForcedType().apply {
////                                name = "FORCE_QUOTED"
////                                includeExpression = ".*"
////                                includeTypes = ".*"
////                            }
////						))
//                    }
//                    generate.apply {
//                        isTables = true
//                        isPojos = true
//                        isDaos = true
//                        isSpringAnnotations = true
//                        isDeprecated = false
//                        isRecords = true
//                        isImmutablePojos = true
//                        isFluentSetters = true
//                    }
//                    target.apply {
//                        packageName = "com.aslanjavasky.shawarmadelviry.generated.jooq"
////                        directory = "target/generated-sources/jooq"
//                        directory = "build/generated-sources/jooq"
//                    }
//                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
//                }
//            }
//        }
//    }
//}


