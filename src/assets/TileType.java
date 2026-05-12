package assets;

public enum TileType {
  DARK_GRASS((byte) 0, "darkgrass.png"),
  GRASS((byte) 1, "grass.png"),
  SAND((byte) 2, "sand.png"),
  STONE((byte) 3, "stone.png", true);

  public final byte id;
  public final String path;
  public final boolean isSolid;

  TileType(byte id, String path) {
    this.id = id;
    this.path = path;
    this.isSolid = false;
  }

  TileType(byte id, String path, boolean isSolid) {
    this.id = id;
    this.path = path;
    this.isSolid = isSolid;
  }

  public static TileType fromId(byte id) {
    for (TileType t : values())
      if (t.id == id)
        return t;
    return DARK_GRASS;
  }
}
