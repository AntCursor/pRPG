package types;

public class Color {
  private Color() {
  }

  public static int rgb(int r, int g, int b) {
    return (0xff << 24) | (r << 16) | (g << 8) | b;
  }

  public static int rgba(int r, int g, int b, int a) {
    return (a << 24) | (r << 16) | (g << 8) | b;
  }

  public static int r(int col) {
    return (col >> 16) & 0xff;
  }

  public static int g(int col) {
    return (col >> 8) & 0xff;
  }

  public static int b(int col) {
    return col & 0xff;
  }

  public static int a(int col) {
    return (col >> 24) & 0xff;
  }
}
