package assets;

import ui.UIRenderer;

import java.util.Iterator;
import java.util.Optional;

import types.List;

public class AssetManager<K, V> {
  private UIRenderer renderer;
  private List<K> keys = new List<>();
  private List<V> assets = new List<>();

  public AssetManager(UIRenderer renderer) {
    this.renderer = renderer;
  }

  @SuppressWarnings("unchecked")
  public V load(K key, String path) {
    V obj = (V) renderer.loadImage(path);
    keys.add(key);
    assets.add(obj);
    return obj;
  }

  public Optional<V> get(K key) {
    Iterator<V> assetsIter = assets.iterator();
    Iterator<K> keysIter = keys.iterator();

    while (assetsIter.hasNext()) {
      K ikey = keysIter.next();
      V iasset = assetsIter.next();

      if (ikey.equals(key))
        return Optional.of(iasset);
    }

    return Optional.empty();
  }

  public V loadIfNotPresent(K key, String path) {
    Optional<V> val = get(key);
    if (val.isPresent())
      return val.get();

    return load(key, path);
  }

}
