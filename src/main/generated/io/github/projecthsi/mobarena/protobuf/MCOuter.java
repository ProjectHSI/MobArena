// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mc.proto

// Protobuf Java Version: 3.25.0
package io.github.projecthsi.mobarena.protobuf;

public final class MCOuter {
  private MCOuter() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mob_arena_World_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mob_arena_World_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mob_arena_Location_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mob_arena_Location_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\010mc.proto\022\tmob_arena\"#\n\005World\022\014\n\004name\030\001" +
      " \001(\t\022\014\n\004uuid\030\002 \001(\t\"L\n\010Location\022\037\n\005world\030" +
      "\001 \001(\0132\020.mob_arena.World\022\t\n\001x\030\002 \001(\001\022\t\n\001y\030" +
      "\003 \001(\001\022\t\n\001z\030\004 \001(\001*\266\016\n\nEntityType\022\t\n\005ALLAY" +
      "\020\000\022\025\n\021AREA_EFFECT_CLOUD\020\001\022\017\n\013ARMOR_STAND" +
      "\020\002\022\t\n\005ARROW\020\003\022\013\n\007AXOLOTL\020\004\022\007\n\003BAT\020\005\022\007\n\003B" +
      "EE\020\006\022\t\n\005BLAZE\020\007\022\021\n\rBLOCK_DISPLAY\020\010\022\010\n\004BO" +
      "AT\020\t\022\t\n\005CAMEL\020\n\022\007\n\003CAT\020\013\022\017\n\013CAVE_SPIDER\020" +
      "\014\022\016\n\nCHEST_BOAT\020\r\022\013\n\007CHICKEN\020\016\022\007\n\003COD\020\017\022" +
      "\007\n\003COW\020\020\022\013\n\007CREEPER\020\021\022\013\n\007DOLPHIN\020\022\022\n\n\006DO" +
      "NKEY\020\023\022\023\n\017DRAGON_FIREBALL\020\024\022\020\n\014DROPPED_I" +
      "TEM\020\025\022\013\n\007DROWNED\020\026\022\007\n\003EGG\020\027\022\022\n\016ELDER_GUA" +
      "RDIAN\020\030\022\021\n\rENDER_CRYSTAL\020\031\022\020\n\014ENDER_DRAG" +
      "ON\020\032\022\017\n\013ENDER_PEARL\020\033\022\020\n\014ENDER_SIGNAL\020\034\022" +
      "\014\n\010ENDERMAN\020\035\022\r\n\tENDERMITE\020\036\022\n\n\006EVOKER\020\037" +
      "\022\020\n\014EVOKER_FANGS\020 \022\022\n\016EXPERIENCE_ORB\020!\022\021" +
      "\n\rFALLING_BLOCK\020\"\022\014\n\010FIREBALL\020#\022\014\n\010FIREW" +
      "ORK\020$\022\020\n\014FISHING_HOOK\020%\022\007\n\003FOX\020&\022\010\n\004FROG" +
      "\020\'\022\t\n\005GHAST\020(\022\t\n\005GIANT\020)\022\023\n\017GLOW_ITEM_FR" +
      "AME\020*\022\016\n\nGLOW_SQUID\020+\022\010\n\004GOAT\020,\022\014\n\010GUARD" +
      "IAN\020-\022\n\n\006HOGLIN\020.\022\t\n\005HORSE\020/\022\010\n\004HUSK\0200\022\016" +
      "\n\nILLUSIONER\0201\022\017\n\013INTERACTION\0202\022\016\n\nIRON_" +
      "GOLEM\0203\022\016\n\nITEM_FRAME\0204\022\017\n\013LEASH_HITCH\0205" +
      "\022\r\n\tLIGHTNING\0206\022\t\n\005LLAMA\0207\022\016\n\nLLAMA_SPUT" +
      "\0208\022\016\n\nMAGMA_CUBE\0209\022\n\n\006MARKER\020:\022\014\n\010MINECA" +
      "RT\020;\022\022\n\016MINECART_CHEST\020<\022\025\n\021MINECRAFT_CO" +
      "MMAND\020=\022\025\n\021MINECRAFT_FURNACE\020>\022\023\n\017MINECA" +
      "RT_HOPPER\020?\022\030\n\024MINECART_MOB_SPAWNER\020@\022\020\n" +
      "\014MINECART_TNT\020A\022\010\n\004MULE\020B\022\020\n\014MUSHROOM_CO" +
      "W\020C\022\n\n\006OCELOT\020D\022\014\n\010PAINTING\020E\022\t\n\005PANDA\020F" +
      "\022\n\n\006PARROT\020G\022\013\n\007PHANTOM\020H\022\007\n\003PIG\020I\022\n\n\006PI" +
      "GLIN\020J\022\020\n\014PIGLIN_BRUTE\020K\022\014\n\010PILLAGER\020L\022\n" +
      "\n\006PLAYER\020M\022\016\n\nPOLAR_BEAR\020N\022\016\n\nPRIMED_TNT" +
      "\020O\022\016\n\nPUFFERFISH\020P\022\n\n\006RABBIT\020Q\022\013\n\007RAVAGE" +
      "R\020R\022\n\n\006SALMON\020S\022\t\n\005SHEEP\020T\022\013\n\007SHULKER\020U\022" +
      "\022\n\016SHULKER_BULLET\020V\022\016\n\nSLIVERFISH\020W\022\014\n\010S" +
      "KELETON\020X\022\022\n\016SKELETON_HORSE\020Y\022\t\n\005SLIME\020Z" +
      "\022\022\n\016SMALL_FIREBALL\020[\022\013\n\007SNIFFER\020\\\022\014\n\010SNO" +
      "WBALL\020]\022\013\n\007SNOWMAN\020^\022\022\n\016SPECTRAL_ARROW\020_" +
      "\022\n\n\006SPIDER\020`\022\021\n\rSPLASH_POTION\020a\022\t\n\005SQUID" +
      "\020b\022\t\n\005STRAY\020c\022\013\n\007STRIDER\020d\022\013\n\007TADPOLE\020e\022" +
      "\020\n\014TEXT_DISPLAY\020f\022\025\n\021THROWN_EXP_BOTTLE\020g" +
      "\022\020\n\014TRADER_LLAMA\020h\022\013\n\007TRIDENT\020i\022\021\n\rTROPI" +
      "CAL_FISH\020j\022\n\n\006TURTLE\020k\022\013\n\007UNKNOWN\020l\022\007\n\003V" +
      "EX\020m\022\014\n\010VILLAGER\020n\022\016\n\nVINDICATOR\020o\022\024\n\020WA" +
      "NDERING_TRADER\020p\022\n\n\006WARDEN\020q\022\t\n\005WITCH\020r\022" +
      "\n\n\006WITHER\020s\022\023\n\017WITHER_SKELETON\020t\022\020\n\014WITH" +
      "ER_SKULL\020u\022\010\n\004WOLF\020v\022\n\n\006ZOGLIN\020w\022\n\n\006ZOMB" +
      "IE\020x\022\020\n\014ZOMBIE_HORSE\020y\022\023\n\017ZOMBIE_VILLAGE" +
      "R\020z\022\024\n\020ZOMBIFIED_PIGLIN\020{B3\n&io.github.p" +
      "rojecthsi.mobarena.protobufB\007MCOuterP\001b\006" +
      "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_mob_arena_World_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_mob_arena_World_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mob_arena_World_descriptor,
        new java.lang.String[] { "Name", "Uuid", });
    internal_static_mob_arena_Location_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_mob_arena_Location_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mob_arena_Location_descriptor,
        new java.lang.String[] { "World", "X", "Y", "Z", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
