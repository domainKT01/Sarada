plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.solproe"
version = "1.0-SNAPSHOT"

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.base", "javafx.swing",
        "javafx.graphics", "javafx.media", "javafx.web")
}

repositories {
    mavenCentral()
    google()
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

application {
    mainModule = "com.solproe"
    mainClass = "com.solproe.MainApp"
}

tasks.test {
    useJUnitPlatform()
}