# ArsMagicaLegacy
## Intro
Ars Magica Legacy is a Legacy version of Ars Magica 2 by Mithion.
If you want to support donate via [Patreon](https://www.patreon.com/minecraftschurli) and
please report any bugs on [github](https://github.com/Minecraftschurli/ArsMagicaLegacy/issues)

## Dependencies
This mod requires 
- [SimpleOreLib](https://www.curseforge.com/minecraft/mc-mods/simpleorelib) version 1.0.0.0
- [Patchouli](https://www.curseforge.com/minecraft/mc-mods/patchouli) version 1.15.2-1.2-27

## ArsMagicaAPI
### Repositories
```gradle
repositories {
    maven {
        name = "MinecraftschurliMaven"
        url "http://minecraftschurli.ddns.net/archiva/repository/internal/"
    }
}
```

### Dependencies
```gradle
dependencies {
    // compile against the API but do not include it at runtime
    compileOnly fg.deobf("minecraftschurli:arsmagicalegacy:${mc_version}-${am_version}:api")
    // at runtime, use the full jar
    runtimeOnly fg.deobf("minecraftschurli:arsmagicalegacy:${mc_version}-${am_version}")
}
```
### gradle.properties
```
mc_version=[CURRENT_MC_VERSION]
am_version=[CURRENT_ARSMAGICA_VERSION]
```
