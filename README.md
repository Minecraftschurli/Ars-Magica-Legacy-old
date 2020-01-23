# ArsMagicaLegacy
## Intro
Legacy version of Ars Magica (2)

## Dependencies
This mod requires [SimpleOreLib](https://www.curseforge.com/minecraft/mc-mods/simpleorelib) version 1.0.0.0

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
