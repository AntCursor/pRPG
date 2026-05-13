package assets;

public enum TileType {
  DARK_GRASS(0, "tiles/darkgrass.png"),
  GRASS(1, "tiles/grass.png"),
  SAND(2, "tiles/sand.png"),
  STONE(3, "tiles/stone.png", true),

  MENU_BACK(4, "ui/menu_back.png"),
  TITLE(5, "ui/title.png"),
  GAMEOVER(6, "ui/overgame.png"),

  LEVI(7, "entities/levi1.png"),
  LEVI_FACE(8, "entities/levi.png"),

  MIKU(9, "entities/miku.png"),
  OGRE(10, "entities/ogre.png"),
  OK(11, "entities/ok.png"),
  SANS(12, "entities/sans.png"),

  CRASH_FACE(13, "entities/crash.png"),
  CRASH(14, "entities/crash1.png"),

  MAN(15, "entities/man.png"),
  ENEMAN(16, "entities/redman.png"),
  BATTLE_BG(17, "ui/battle_bg.png"),

  BONZI(18, "entities/bonzi.png");

  public final int id;
  public final String path;
  public final boolean isSolid;

  TileType(int id, String path) {
    this.id = id;
    this.path = path;
    this.isSolid = false;
  }

  TileType(int id, String path, boolean isSolid) {
    this.id = id;
    this.path = path;
    this.isSolid = isSolid;
  }

  public static TileType fromId(int id) {
    for (TileType t : values())
      if (t.id == id)
        return t;
    return DARK_GRASS;
  }
}
