// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mobarena.proto

// Protobuf Java Version: 3.25.0
package io.github.projecthsi.mobarena.protobuf;

public final class MobArenaOuter {
  private MobArenaOuter() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mob_arena_FillArea_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mob_arena_FillArea_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mob_arena_SpawnPoint_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mob_arena_SpawnPoint_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mob_arena_MobSpawnEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mob_arena_MobSpawnEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016mobarena.proto\022\tmob_arena\032\010mc.proto\"\201\001" +
      "\n\010FillArea\022*\n\rstartPosition\030\001 \001(\0132\023.mob_" +
      "arena.Location\022(\n\013endPosition\030\002 \001(\0132\023.mo" +
      "b_arena.Location\022\037\n\005world\030\003 \001(\0132\020.mob_ar" +
      "ena.World\"A\n\nSpawnPoint\022\014\n\004name\030\001 \001(\t\022%\n" +
      "\010fillArea\030\002 \001(\0132\023.mob_arena.FillArea\"\235\001\n" +
      "\rMobSpawnEntry\022)\n\nentityType\030\001 \001(\0162\025.mob" +
      "_arena.EntityType\022\016\n\006amount\030\002 \001(\r\022\022\n\nspa" +
      "wnRound\030\003 \001(\r\022\016\n\006modulo\030\004 \001(\r\022\022\n\nspawnPo" +
      "int\030\005 \001(\t\022\031\n\021countTowardsLimit\030\006 \001(\010B9\n&" +
      "io.github.projecthsi.mobarena.protobufB\r" +
      "MobArenaOuterP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          io.github.projecthsi.mobarena.protobuf.MCOuter.getDescriptor(),
        });
    internal_static_mob_arena_FillArea_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_mob_arena_FillArea_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mob_arena_FillArea_descriptor,
        new java.lang.String[] { "StartPosition", "EndPosition", "World", });
    internal_static_mob_arena_SpawnPoint_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_mob_arena_SpawnPoint_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mob_arena_SpawnPoint_descriptor,
        new java.lang.String[] { "Name", "FillArea", });
    internal_static_mob_arena_MobSpawnEntry_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_mob_arena_MobSpawnEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mob_arena_MobSpawnEntry_descriptor,
        new java.lang.String[] { "EntityType", "Amount", "SpawnRound", "Modulo", "SpawnPoint", "CountTowardsLimit", });
    io.github.projecthsi.mobarena.protobuf.MCOuter.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
