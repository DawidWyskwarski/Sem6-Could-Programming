plugins {
    kotlin("jvm") version "2.3.10"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.g0dkar:qrcode-kotlin:4.5.0")

    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.4")

    implementation("software.amazon.awssdk:s3:2.25.0")
    implementation("software.amazon.awssdk:dynamodb:2.25.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveBaseName.set("qr-code-handler")
    archiveClassifier.set("")
    archiveVersion.set("")

    mergeServiceFiles()
}