plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("javazoom:jlayer:1.0.1")
    implementation("org.rjung.util:launchpad:1.0")
}


tasks.test {
    useJUnitPlatform()
}