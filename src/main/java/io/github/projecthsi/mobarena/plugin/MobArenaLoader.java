package io.github.projecthsi.mobarena.plugin;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import org.jetbrains.annotations.NotNull;

public class MobArenaLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        /*
         MavenLibraryResolver resolver = new MavenLibraryResolver();
         resolver.addDependency(new Dependency(new DefaultArtifact("dev.dejvokep:boosted-yaml:1.3"), null));
         resolver.addRepository(new RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/").build());

         classpathBuilder.addLibrary(resolver);
        */
    }
}
