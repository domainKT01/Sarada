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

}

application {
    mainModule = "com.solproe"
    mainClass = "com.solproe.MainApp"
}

tasks.test {
    useJUnitPlatform()
}