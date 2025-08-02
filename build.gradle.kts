// versions
val minecraftVersion = "1.21.1"
val minecraftDep = "=1.21.1"
// https://parchmentmc.org/docs/getting-started
val parchmentVersion = "2024.11.17"
// https://fabricmc.net/develop
val loaderVersion = "0.16.14"

// buildscript
plugins {
	id("fabric-loom") version "1.11.+"
	id("maven-publish")
}

base.archivesName = "sheared"
group = "one.devos.nautical"

val buildNum = providers.environmentVariable("GITHUB_RUN_NUMBER")
    .filter(String::isNotEmpty)
	.map { "build.$it" }
    .orElse("local")
    .get()

version = "1.0.0+mc$minecraftVersion.$buildNum"

repositories {
	maven("https://maven.parchmentmc.org")
	maven("https://api.modrinth.com/maven")
}

dependencies {
	// dev environment
	minecraft("com.mojang:minecraft:$minecraftVersion")
	mappings(loom.layered {
        officialMojangMappings { nameSyntheticMembers = false }
		parchment("org.parchmentmc.data:parchment-$minecraftVersion:$parchmentVersion@zip")
	})
	modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
}

tasks.withType(ProcessResources::class) {
	val properties: Map<String, Any> = mapOf(
		"version" to version,
		"minecraft_dependency" to minecraftDep
	)

	inputs.properties(properties)

	filesMatching("fabric.mod.json") {
		expand(properties)
	}
}

java {
	withSourcesJar()
}

publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	repositories {
		maven("https://mvn.devos.one/snapshots") {
			name = "devOsSnapshots"
			credentials(PasswordCredentials::class)
		}
        maven("https://mvn.devos.one/releases") {
            name = "devOsReleases"
            credentials(PasswordCredentials::class)
        }
	}
}
