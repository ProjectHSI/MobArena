package io.github.projecthsi.mobarena.config;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.github.projecthsi.mobarena.protobuf.Config;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

/*
    "Foreign" in this file refers to file types that aren't a protobuf.
    We need special handling for those.
*/

class ForeignConfigWriter {
    //private static final ForeignConfigWriter Instance = new ForeignConfigWriter();

    //String json = "";

    //public void ForeignConfigReader() {

    //}

    public static String convertProtobufToForeignFormat(@NotNull com.google.protobuf.MessageOrBuilder messageOrBuilder, @NotNull ConfigShared.FileType fileType) throws InvalidProtocolBufferException {
        if (fileType == ConfigShared.FileType.PROTOBUF) {
            throw new IllegalArgumentException("Attempted to convert a protobuf to a protobuf.");
        }

        /* TODO: Consider deleting the this if statement, considering that com.google.protobuf.util only has 1
            formatter, and that's JSON. We don't need to have support for multiple formatters if we can't add multiple
            formatters. */
        if (fileType == ConfigShared.FileType.JSON) {
            return JsonFormat.printer().print(messageOrBuilder);
        }
        return null;
    }

    //public static ForeignConfigWriter getInstance() {
    //    return Instance;
    //}
}

class ConfigWriter {
    private static void writeFile(byte[] data, Path filePath) throws IOException {
        File configFile = filePath.toFile();

        //noinspection ResultOfMethodCallIgnored
        configFile.createNewFile();

        try (FileOutputStream fileOutputStream = new FileOutputStream(configFile)) {
            fileOutputStream.write(data);
        }
    }

    private static /*Config*/ void getProtobufConfig() {
        Config.Builder configBuilder = Config.newBuilder();

        //configBuilder.putAllArenas();
    }
}
