package types;

public class Vec2 {
  public float x = 0.f;
  public float y = 0.f;

  public Vec2(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Vec2() {
  }

  public Vec2 add(Vec2 other) {
    return new Vec2(this.x + other.x, this.y + other.y);
  }

  public Vec2 hProd(Vec2 other) {
    return new Vec2(this.x * other.x, this.y * other.y);
  }

  public Vec2 prod(float scalar) {
    return new Vec2(this.x * scalar, this.y * scalar);
  }
}
