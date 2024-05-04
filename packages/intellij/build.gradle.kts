import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask

plugins {
    id("java")
    id("org.jetbrains.grammarkit") version "2022.3.1"
    id("org.jetbrains.intellij") version "1.10.1"
}

group = "com.usebruno.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    val generateBruLexer by registering(GenerateLexerTask::class) {
        sourceFile.set(file("src/main/java/com/usebruno/plugin/bruno/language/Bru.flex"))
        targetDir.set("src/main/gen/com/usebruno/plugin/bruno/language")
        targetClass.set("BruLexer.java")
        purgeOldFiles.set(true)
    }

    val generateBruParser by registering(GenerateParserTask::class) {
        sourceFile.set(file("src/main/java/com/usebruno/plugin/bruno/language/Bru.bnf"))
        targetRoot.set("src/main/gen")
        pathToParser.set("/com/usebruno/plugin/bruno/language/parser/BruParser.java")
        pathToPsiRoot.set("/com/usebruno/plugin/bruno/language/psi")
        purgeOldFiles.set(true)
    }

    compileJava {
        dependsOn(generateBruLexer)
        dependsOn(generateBruParser)
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    clean {
        delete("src/main/gen")
    }
}
