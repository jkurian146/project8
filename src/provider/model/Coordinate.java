package provider.model;

/**
 * Represents a 2D coordinate. For a hexagon cell, we are using the axial coordinate system to
 * represent a hexagon cell's location.
 */
public final class Coordinate {
  private final int q;
  private final int r;

  /**
   * Constructor that creates a 2D coordinate, q and r. q is relative to column, r is relative to
   * row.
   * @param q the column
   * @param r the row
   * @throws IllegalArgumentException if a negative integer is passed in for either coordinate
   */

  public Coordinate(int q, int r) {
    this.q = q;
    this.r = r;

  }

  /**
   * Returns the q coordinate.
   * @return the integer column
   */
  public int getQ() {
    return q;
  }

  /**
   * Returns the r coordinate.
   * @return the integer row
   */
  public int getR() {
    return r;
  }

}
