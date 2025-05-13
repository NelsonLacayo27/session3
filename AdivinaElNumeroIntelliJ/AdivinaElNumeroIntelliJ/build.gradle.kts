plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.compose") version "1.5.0"
}

repositories {
    // 1) Repositorio de Compose Desktop
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    // 2) Repositorios estándar
    google()
    mavenCentral()
}

dependencies {
    // Trae UI, Material y demás
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.example.adivinaelnumero.MainKt"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

kotlin {
    jvmToolchain(17)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
