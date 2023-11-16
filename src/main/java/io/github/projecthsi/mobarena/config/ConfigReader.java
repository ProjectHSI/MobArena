package io.github.projecthsi.mobarena.config;

import io.github.projecthsi.mobarena.protobuf.Config;

/*
    "Foreign" in this file refers to file types that aren't a protobuf.
    We need special handling for those.
*/

class ForeignConfigReader {
    //private
}

class ConfigReader {
    public void importForeign() {

    }

    public void testFunction() {
        io.github.projecthsi.mobarena.protobuf.Config.Builder builder = Config.newBuilder();

        //builder.
    }
}
