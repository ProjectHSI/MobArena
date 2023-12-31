// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mobarena.proto

// Protobuf Java Version: 3.25.0
package io.github.projecthsi.mobarena.protobuf;

public interface MobSpawnEntryOrBuilder extends
    // @@protoc_insertion_point(interface_extends:mob_arena.MobSpawnEntry)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.mob_arena.EntityType entityType = 1;</code>
   * @return The enum numeric value on the wire for entityType.
   */
  int getEntityTypeValue();
  /**
   * <code>.mob_arena.EntityType entityType = 1;</code>
   * @return The entityType.
   */
  io.github.projecthsi.mobarena.protobuf.EntityType getEntityType();

  /**
   * <code>uint32 amount = 2;</code>
   * @return The amount.
   */
  int getAmount();

  /**
   * <code>uint32 spawnRound = 3;</code>
   * @return The spawnRound.
   */
  int getSpawnRound();

  /**
   * <code>uint32 modulo = 4;</code>
   * @return The modulo.
   */
  int getModulo();

  /**
   * <code>string spawnPoint = 5;</code>
   * @return The spawnPoint.
   */
  java.lang.String getSpawnPoint();
  /**
   * <code>string spawnPoint = 5;</code>
   * @return The bytes for spawnPoint.
   */
  com.google.protobuf.ByteString
      getSpawnPointBytes();

  /**
   * <code>bool countTowardsLimit = 6;</code>
   * @return The countTowardsLimit.
   */
  boolean getCountTowardsLimit();
}
