package assets;

import java.util.Iterator;
import java.util.Optional;

import core.GameContext;
import types.List;

public class AssetManager {
  private GameContext game;
  private List<Integer> keys = new List<>();
  private List<Object> assets = new List<>();

  public AssetManager(GameContext game) {
    this.game = game;
  }

  public Object load(int key, String path) {
    Object obj = game.loadImage(path);
    keys.add(key);
    assets.add(obj);
    return obj;
  }

  public Optional<Object> get(int key) {
    Iterator<Object> assetsIter = assets.iterator();
    Iterator<Integer> keysIter = keys.iterator();

    while (assetsIter.hasNext()) {
      Integer ikey = keysIter.next();
      Object iasset = assetsIter.next();

      if (ikey.equals(key))
        return Optional.of(iasset);
    }

    return Optional.empty();
  }

  public Object loadIfNotPresent(int key, String path) {
    Optional<Object> val = get(key);
    if (val.isPresent())
      return val.get();

    return load(key, path);
  }

  public void loadTiles(TileType... types) {
    for (TileType t : types) {
      this.loadIfNotPresent(t.id, t.path);
    }
  }
}
