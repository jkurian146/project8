package provider.view;

import java.awt.geom.Path2D;

/**
 * Draws a hexagon with the correct size and angles using lines. This will be used to draw the
 * individual hexagon cells in the panel.
 */
public final class DrawHexagonCell extends Path2D.Double {

  /**
   * Draws a hexagon shape object using the given preferred size.
   * @param size the double preferred size to make the shape
   */
  public DrawHexagonCell(double size) {
    double angle = Math.PI / 3;
    moveTo(Math.cos(0) * size, Math.sin(0) * size);
    for (int i = 0; i < 6; i++) {
      double x =  Math.cos(angle * i) * size;
      double y = Math.sin(angle * i) * size;
      lineTo(x, y);
    }
    closePath();
  }
}
