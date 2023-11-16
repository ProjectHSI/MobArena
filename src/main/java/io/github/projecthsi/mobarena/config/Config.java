package io.github.projecthsi.mobarena.config;

/*
    "Foreign" in this file refers to file types that aren't a protobuf.
    We need special handling for those.
*/

import io.github.projecthsi.mobarena.plugin.MobArena;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

class ForeignConfigShared {
}

public class Config {

}

class ConfigShared {
    private static final ConfigShared Instance = new ConfigShared();

    public ConfigShared() {
        Path dataFolderPath = Paths.get(MobArena.getInstance().getDataFolder().getPath(), "config");

        fileTypeConversionTable = new HashMap<>();
        fileTypeConversionTable.put(FileType.PROTOBUF, "proto");
        fileTypeConversionTable.put(FileType.JSON, "json");
        fileTypeConversionTable.put(FileType.XML, "xml");
    }

    public enum FileType {
        PROTOBUF,
        JSON,
        XML//,   YAML has been excluded as the Java formatter
//        YAML   doesn't support YAML.
    }

    private Path configPath;
    private HashMap<FileType, String> fileTypeConversionTable;

    public Path getConfigPath() {
        return configPath;
    }

    public HashMap<FileType, String> getFileTypeConversionTable() {
        return fileTypeConversionTable;
    }

    public static ConfigShared getInstance() {
        return Instance;
    }
}