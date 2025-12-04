plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "3.1.1"
}

group = "com.solproe"
version = "2.8.0"
""
javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.base", "javafx.swing",
        "javafx.graphics", "javafx.media", "javafx.web")
}

application {
    mainClass = "com.solproe.App"
    mainModule = "com.solproe"
}

repositories {
    mavenCentral()
    google()
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get(),
            "Created-By" to "Gradle Kotlin DSL",
            "Implementation-Version" to version
        )
    }

    // Solo empaqueta tus recursos y clases propias
    from(sourceSets.main.get().output)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
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
    implementation("org.apache.poi:poi:5.3.0") {
        exclude(group = "commons-io", module = "commons-io")
    }
    implementation("org.apache.poi:poi-ooxml:5.3.0") {
        exclude(group = "commons-io", module = "commons-io")
    }

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")

    implementation("commons-io:commons-io:2.16.1")


    //task manager
    implementation("org.quartz-scheduler:quartz:2.3.0") {
        exclude(group = "commons-io", module = "commons-io")
    }

    // https://mvnrepository.com/artifact/org.jetbrains/annotations
    implementation("org.jetbrains:annotations:24.0.0")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    create("feature")
}

java {
    registerFeature("feature") {
        usingSourceSet(sourceSets["main"])
    }
    modularity.inferModulePath.set(true)
}
