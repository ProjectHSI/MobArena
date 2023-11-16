package io.github.projecthsi.mobarena.plugin;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MobArenaLoader implements PluginLoader {
    private record Dependency(String name, String groupId, String artifact, String version) {}
    private record MavenRepository(String name, String id, String type, String URI, ArrayList<Dependency> dependencies) {}

    private ArrayList<MavenRepository> dependencyTree;

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        // Initialization code for the above variables.
        dependencyTree = new ArrayList<>();

        // MavenCentral
        MavenRepository mavenCentral = new MavenRepository("Maven Central", "central", "default", "https://repo1.maven.org/maven2/", new ArrayList<>());

        // Protobuf
        final String protobufVersion = "3.25.0";

        // protobuf-java
        Dependency protobufJavaDependency = new Dependency("Protobuf Java", "com.google.protobuf", "protobuf-java", protobufVersion);
        mavenCentral.dependencies.add(protobufJavaDependency);
        // protobuf-java-util
        Dependency protobufJavaUtilDependency = new Dependency("Protobuf Java Util", "com.google.protobuf", "protobuf-java-util", protobufVersion);
        mavenCentral.dependencies.add(protobufJavaUtilDependency);

        // Adventure

        // adventure-text-serializer-ansi
        Dependency adventureAnsiTextSerializer = new Dependency("Adventure Text Serializer (ANSI)", "net.kyori", "adventure-text-serializer-ansi", "4.14.0");

        mavenCentral.dependencies.add(adventureAnsiTextSerializer);

        dependencyTree.add(mavenCentral);



        MavenLibraryResolver resolver = new MavenLibraryResolver();

        for (MavenRepository mavenRepository : dependencyTree) {
            resolver.addRepository(new RemoteRepository.Builder(mavenRepository.id(), mavenRepository.type(), mavenRepository.URI()).build());

            for (Dependency dependency : mavenRepository.dependencies) {
                resolver.addDependency(new org.eclipse.aether.graph.Dependency(new DefaultArtifact(dependency.groupId() + ":" + dependency.artifact() + ":" + dependency.version()), null));
            }
        }

        classpathBuilder.addLibrary(resolver);
    }
}
