plugins {
	id("java")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.code.gson:gson:2.11.0")
}

tasks {
	withType<JavaCompile> {
		options.compilerArgs.add("-Xlint:unused")
	}
}