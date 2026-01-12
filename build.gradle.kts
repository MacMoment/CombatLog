plugins {
    java
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14" apply false
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = "me.iiSnipez"
version = "3.0.0"
description = "Punish players who log out while in Combat"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/releases/") // PlaceholderAPI
    maven("https://maven.enginehub.org/repo/") // WorldGuard
    maven("https://mvn.lib.co.nz/public") // LibsDisguises
    maven("https://nexus.hc.to/content/repositories/pub_releases/") // Factions/MassiveCraft
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    
    // Optional soft dependencies (provided at runtime)
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.13")
    
    // LibsDisguises - optional disguise support
    compileOnly("LibsDisguises:LibsDisguises:10.0.44")
    
    // iDisguise - optional disguise support  
    compileOnly("de.robingrether.idisguise:idisguise-core:5.8.1")
    
    // Factions - optional faction support (both new and legacy)
    compileOnly("com.massivecraft:factions:2.14.0")
    compileOnly("com.massivecraft:massivecore:2.14.0")
    
    // bStats for metrics
    implementation("org.bstats:bstats-bukkit:3.1.0")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        archiveBaseName.set("CombatLog")
        
        relocate("org.bstats", "me.iiSnipez.CombatLog.bstats")
        
        minimize()
    }
    
    build {
        dependsOn(shadowJar)
    }
    
    processResources {
        val props = mapOf(
            "version" to version,
            "description" to description
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
    
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
    }
}
