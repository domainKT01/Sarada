plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "com.solproe"
version = "2.1.1"

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.base", "javafx.swing",
        "javafx.graphics", "javafx.media", "javafx.web")
}

application {
    mainClass = "com.solproe.App"
}

repositories {
    mavenCentral()
    google()
}

tasks.jar {
    manifest {
        attributes(mapOf(
            "Main-Class" to application.mainClass.get(),
            "Created-By" to "Gradle Kotlin DSL",
            "Implementation-Version" to version
        ))
    }
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from("src/main/resources") {
        into("/") // Ajusta la ruta si es necesario
    }
}

dependencies {

    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.20-RC")

    //tests
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    //ui
    implementation("org.openjfx:javafx-controls:25-ea+2")
    implementation("org.openjfx:javafx-graphics:25-ea+2")
    implementation("org.openjfx:javafx-base:25-ea+2")
    implementation("org.openjfx:javafx-fxml:25-ea+2")
    implementation("org.openjfx:javafx-swing:25-ea+2")
    implementation("org.openjfx:javafx-media:25-ea+2")
    implementation("org.openjfx:javafx-web:25-ea+2")

    //business
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.dagger:dagger:2.48")
    annotationProcessor("com.google.dagger:dagger-compiler:2.48")
    implementation("javax.inject:javax.inject:1")

    //service
    implementation("org.apache.poi:poi:5.3.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")

}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

jlink {
    imageZip.set(project.file("${buildDir}/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "sarada" // Cambia "app" al nombre de tu aplicación
    }
    extraModulePaths.set(javafx.modules)
    addExtraModulePath(javafx.modules.toString())
}