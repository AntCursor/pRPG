package assets;

public enum TileType {
  DARK_GRASS((byte) 0, "darkgrass.png"),
  GRASS((byte) 1, "grass.png"),
  SAND((byte) 2, "sand.png"),
  STONE((byte) 3, "stone.png");

  public final byte id;
  public final String path;

  TileType(byte id, String path) {
    this.id = id;
    this.path = path;
  }

  public static TileType fromId(byte id) {
    for (TileType t : values())
      if (t.id == id)
        return t;
    return DARK_GRASS;
  }
}
