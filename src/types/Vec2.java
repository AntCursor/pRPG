package types;

public class Vec2 {
  public float x = 0.f;
  public float y = 0.f;

  Vec2(float x, float y) {
    this.x = x;
    this.y = y;
  }

  Vec2 add(Vec2 other) {
    return new Vec2(this.x + other.x, this.y + other.y);
  }
}
