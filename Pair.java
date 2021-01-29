public class Pair {
  public int x;
  public int y;

  public Pair(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean equals(Pair other) {
    return (this.x == other.x && this.y == other.y);
  }
}