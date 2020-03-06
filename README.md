# Ars Magica: Legacy
## Intro
Ars Magica Legacy is a Legacy version of Ars Magica 2 by Mithion.  
You can find the downloads on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy).  
If you want to support development consider donating on [Patreon](https://www.patreon.com/minecraftschurli).  
For donating we reward you with a donators-only role on [Discord](https://discord.gg/tzgtYHB).  
With that role you will always be up-to-date with development and can give feedback directly to the devs.

## Issues
Please report any bugs here on [github](https://github.com/Minecraftschurli/ArsMagicaLegacy/issues)  

## Dependencies
This mod requires 
- [Patchouli by Vazkii](https://www.curseforge.com/minecraft/mc-mods/patchouli) on version 1.15.2-1.2-29 or higher
- [SimpleOreLib](https://www.curseforge.com/minecraft/mc-mods/simpleorelib) version 1.0.0.0

## Contributing
**We still need help**  
If you want to help with development join us on [Discord](https://discord.gg/tzgtYHB) and ping an admin  
You can find our current roadmap [here](ROADMAP.md)

## ArsMagicaAPI
Ars Magica: Legacy provides an API for mod developers to add their own content like custom spell-components, affinities, ...  
To use it you just need to add the following to your `build.gradle`

```gradle
repositories {
    maven {
        name = "MinecraftschurliMaven"
        url "http://minecraftschurli.ddns.net/archiva/repository/internal/"
    }
}

dependencies {
    // compile against the API but do not include it at runtime
    compileOnly fg.deobf("minecraftschurli:arsmagicalegacy:[CURRENT_MC_VERSION]-[CURRENT_ARSMAGICA_VERSION]:api")
    // at runtime, use the full jar
    runtimeOnly fg.deobf("minecraftschurli:arsmagicalegacy:[CURRENT_MC_VERSION]-[CURRENT_ARSMAGICA_VERSION]")
}
```
