package provider.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import provider.controller.ControllerFeatures;
import provider.model.Board;
import provider.model.Cell;
import provider.model.Coordinate;
import provider.model.PlayerType;
import provider.model.ReadonlyReversiModel;

/**
 * Represents the main panel placed on the JFrame where the model grid can be drawn out and
 * visualized for a game of Reversi.
 */
public class ReversiPanel extends JPanel {
  private final ReadonlyReversiModel readonlyReversiModel;
  private Coordinate initialSelectedCoord;

  private Coordinate  lastClickedCoord;
  private String keyAsString;

  private ControllerFeatures features;


  /**
   * Constructs a panel for the game using a read only model of the game that is not mutable.
   * @param readonlyReversiModel the read only interface for the game
   */
  public ReversiPanel(ReadonlyReversiModel readonlyReversiModel) {
    this.readonlyReversiModel = readonlyReversiModel;
    //this.mouseAdapter = new ReversiMouseAdapter(this.readonlyReversiModel);
    this.initialSelectedCoord = new Coordinate(-1, -1);
    this.lastClickedCoord  = new Coordinate(-1, -1);
    this.addKeyListener(new ReversiKeyAdapterInside(readonlyReversiModel));
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addMouseListener(new ReversiMouseAdapterInside(readonlyReversiModel));
    this.keyAsString = "";
    addFeatures(features);
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    this.setBackground(Color.darkGray);
    drawMiddleRow(g2d);
    drawTopHalf(g2d);
    drawBottomHalf(g2d);
  }

  /**
   * Draws the middle row of hexagon cells on the panel centered in the frame. The number of
   * cells drawn in this row is based on the user's preferred board size.
   * @param g2d the Graphics2D object to draw the hexagons on
   */
  private void drawMiddleRow(Graphics2D g2d) {
    Board gameBoard = readonlyReversiModel.getGameBoard();
    int boardSize = this.readonlyReversiModel.getBoardSize();
    int hexagonSize = 30;
    int centerRow = (int) Math.floor(boardSize / 2.0);
    int displacementAmount = ((int) Math.floor(boardSize / 2.0) + 1) * hexagonSize;

    int row = (int) Math.floor(boardSize / 2.0);
    for (int col = 0; col < boardSize; col++) {
      if (gameBoard.getCells()[col][row] == null) {
        continue;
      }
      this.drawBothHalvesOfBoard(g2d, col, row, centerRow, displacementAmount);

    }

  }

  /**
   * Draws the top half of the hexagon board on the panel centered in the frame. The number of
   * cells drawn in these upper rows is based on the user's preferred board size. After having
   * drawn the middle row, the upper rows position themselves based on the centered middle row.
   * @param g2d the Graphics2D object to draw the hexagons on
   */
  private void drawTopHalf(Graphics2D g2d) {
    Board gameBoard = readonlyReversiModel.getGameBoard();
    int boardSize = this.readonlyReversiModel.getBoardSize();
    int centerRow = (int) Math.floor(boardSize / 2.0);
    for (int col = 0; col < boardSize; col++) {

      for (int row = centerRow - 1; row >= 0; row--) {
        if (gameBoard.getCells()[col][row] == null) {
          continue;
        }
        this.drawBothHalvesOfBoard(g2d, col, row, centerRow, (30 * row) + 30);
      }
    }
  }

  /**
   * Draws the bottom half of the hexagon board on the panel centered in the frame. The number of
   * cells drawn in these lower rows is based on the user's preferred board size. After having
   * drawn the middle row and upper rows, the lower rows position themselves based on the centered
   * middle row.
   * @param g2d the Graphics2D object to draw the hexagons on
   */
  private void drawBottomHalf(Graphics2D g2d) {
    Board gameBoard = readonlyReversiModel.getGameBoard();
    int boardSize = this.readonlyReversiModel.getBoardSize();
    int centerRow = (int) Math.floor(boardSize / 2.0);
    for (int col = 0; col < boardSize; col++) {
      for (int row = centerRow + 1; row < boardSize; row++) {
        if (gameBoard.getCells()[col][row] == null) {
          continue;
        }
        this.drawBothHalvesOfBoard(g2d, col, row, centerRow, (30 * row) + 30);
      }
    }
  }


  /**
   * Abstracted helper to draw a singular hexagon on the panel. This helper is recursively called
   * when drawing the top, middle, and bottom of the hexagon board.
   * @param g2d the Graphics2D object to draw and fill with
   * @param color the preferred color to set the fill color of the hexagon
   * @param xPos the x position in relation to physical coordinates, not logical to place the cell
   * @param yPos the y position in relation to physical coordinates, not logical to place the cell
   * @param hexagon the object that outlines the hexagon shape
   */
  private void drawOneHexagonOnPanel(Graphics2D g2d, Color color, double xPos,
                                     double yPos, DrawHexagonCell hexagon) {
    g2d.translate(xPos, yPos);
    double rotationAngle = Math.toDegrees(5);
    AffineTransform transform = AffineTransform.getRotateInstance(rotationAngle);
    Shape rotatedHexagon = transform.createTransformedShape(hexagon);
    g2d.setColor(color);
    g2d.draw(rotatedHexagon);
    g2d.setStroke(new BasicStroke(5));
    g2d.setColor(color);
    g2d.fill(rotatedHexagon);
    g2d.translate(-xPos, -yPos);
  }

  /**
   * Adds the controller features to the panel to call the controller's move and pass methods,
   * and sets them to this features.
   * @param features the features to be added
   */
  public void addFeatures(ControllerFeatures features) {
    //controller that takes in a f
    this.features = features;
  }

  /**
   * To select a cell and make it cyan.
   */
  public void selectCell() {

  }

  /**
   * If key pressed is "enter", then go through the entire board
   * get all the cells that are THIS PLAYER's STATE and change color
   * accordingly.
   */
  public void makeMove() {

  }

  /**
   * Displays a message in the view showing the current player has passed on their turn.
   */
  public void passOnTurn() {
    JOptionPane.showMessageDialog(this,
            readonlyReversiModel.getCurrentPlayer() + " has passed!",
            "Pass", JOptionPane.PLAIN_MESSAGE);

  }

  /**
   * Displays an error message for the current player when an illegal move is attempted by the
   * user.
   */
  public void displayErrorMsg() {
    JOptionPane.showMessageDialog(this,
            "Illegal move for " + readonlyReversiModel.getCurrentPlayer(),
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * This class extends MouseAdapter and serves as a mouse listener for handling mouse events
   * related to Reversi gameplay. It tracks the selected row and column based on mouse clicks
   * and interacts with a ReadonlyReversiModel.
   */
  public class ReversiMouseAdapterInside extends MouseAdapter {
    private final ReadonlyReversiModel model;

    public ReversiMouseAdapterInside(ReadonlyReversiModel model) {
      this.model = model;
    }

    /**
     * Invoked when the mouse is clicked. Updates the selected row and column based on
     * the mouse coordinates and converts them to cell coordinates on the game board.
     * @param e The MouseEvent representing the mouse click event.
     */
    public void mouseClicked(MouseEvent e) {
      if (initialSelectedCoord != null) { 
        int selectedRow = e.getY() - 220;
        int selectedCol = e.getX() - 220;
        int cellSize = 30;
        int q = (int) (selectedCol * Math.sqrt(3) / 3 - 1.0 / 3 * selectedRow) / cellSize;
        int r = (int) (2.0 / 3 * selectedRow) / cellSize;
        initialSelectedCoord = new Coordinate(q, r); //this holds first selected cell
        if (initialSelectedCoord.equals(lastClickedCoord)) {
          initialSelectedCoord = null;
        }

        if (q < 0 || q >= model.getBoardSize()
                || r < 0 || r >= model.getBoardSize()) {
          System.out.println("You have clicked off of the board! Please try again :)" + "\n");
        } else {
          System.out.println(initialSelectedCoord.getQ() + "," + initialSelectedCoord.getR());
        }
      }
      lastClickedCoord = initialSelectedCoord;
      repaint();
    }

  }


  public Coordinate getSelectedCellToMakeMoveOn() {
    return this.initialSelectedCoord;
  }

  /**
   * Abstracted helper to set the starting disks in the panel. A starting configuration has
   * players alternating the neighboring cells of the center cell in the hexagon board.
   * @param g2d the Graphics2D object to draw a disk with
   * @param color the preferred color of the Player disk
   * @param xPos the x position of the disk in relation to physical coordinates, not logical, to
   *             place it in the center of the hex cell
   * @param yPos the y position of the disk in relation to physical coordinates, not logical, to
   *             place it in the center of the hex cell
   * @param hexagonSize the preferred size of the hexagon cell to place the disk in relation to
   *                    that size
   */
  private void setStartingDisk(Graphics2D g2d, Color color, double xPos, double yPos,
                               int hexagonSize) {
    g2d.setColor(color);
    g2d.fillOval((int) xPos - 14, (int) yPos - 14, hexagonSize, hexagonSize);
  }


  /**
   * Abstracted helper that is called to draw the entire board. The drawing process is the same
   * for all cells, but offset amounts and row and column starting positions differ for both
   * halves, so they lead to differentiated methods drawTopHalf, drawBottomHalf, and
   * drawMiddleRow. Using the displacement amount to start each row by, what column and row
   * position to draw by and calls the draw singular hexagon helper to draw the whole board.
   * @param g2d the Graphics2D object to draw with
   * @param col the logical column to associate the hexagon cell with
   * @param row the logical row to associate the hexagon cell with
   * @param centerRow the middle row index to then draw both halves from
   * @param displacementAmount the displacement to start each row with when making the hex board
   */
  private void drawBothHalvesOfBoard(Graphics2D g2d, int col, int row,
                                     int centerRow, int displacementAmount) {
    int hexagonSize = 30;

    DrawHexagonCell hexagon = new DrawHexagonCell(hexagonSize);
    double xPos = (col * 2 * hexagonSize + 200); // spaces between cells
    double yPos = row * Math.sqrt(3) * hexagonSize + 200;
    xPos += displacementAmount;

    if (initialSelectedCoord.getQ() == col && initialSelectedCoord.getR() == row) {
      if (lastClickedCoord.getQ() == col && lastClickedCoord.getR() == row
              && keyAsString.equals("enter")) {
        if (readonlyReversiModel.getCurrentPlayer().equals(PlayerType.PLAYER1)) {
          this.drawOneHexagonOnPanel(g2d, Color.lightGray, xPos, yPos, hexagon);
          this.setStartingDisk(g2d, Color.white, xPos, yPos, hexagonSize);
          repaintAfterMove(g2d, xPos, yPos);
        } else if (readonlyReversiModel.getCurrentPlayer()
                .equals(PlayerType.PLAYER2)) {
          this.drawOneHexagonOnPanel(g2d, Color.lightGray, xPos, yPos, hexagon);
          this.setStartingDisk(g2d, Color.black, xPos, yPos, hexagonSize);
          repaintAfterMove(g2d, xPos, yPos);
          repaint();
        }
        repaint();
      } else {
        this.drawOneHexagonOnPanel(g2d, Color.cyan, xPos, yPos, hexagon);
      }
    }
    else {
      this.drawOneHexagonOnPanel(g2d, Color.lightGray, xPos, yPos, hexagon);
    }
    //bottom left
    if (row == centerRow + 1 && col == centerRow - 1) {
      this.setStartingDisk(g2d, Color.white, xPos, yPos, hexagonSize);
    }

    //at bottom right cell
    if (row == centerRow + 1 && col == centerRow) {
      this.setStartingDisk(g2d, Color.black, xPos, yPos, hexagonSize);
    }

    //at top right cell
    if (row == centerRow - 1 && col == centerRow + 1) {
      this.setStartingDisk(g2d, Color.black, xPos, yPos, hexagonSize);
    }

    //at top left cell
    if (row == centerRow - 1 && col == centerRow) {
      this.setStartingDisk(g2d, Color.white, xPos, yPos, hexagonSize);
    }

    //at straight left cell
    if (col == row - 1 && row == centerRow) {
      this.setStartingDisk(g2d, Color.black, xPos, yPos, hexagonSize);
    }

    //at straight right cell
    if (col == row + 1 && row == centerRow) {
      this.setStartingDisk(g2d, Color.white, xPos, yPos, hexagonSize);
    }

    if (keyAsString.equals("enter")) {
      if (this.readonlyReversiModel.getGameBoard().getCells()[col][row].
              getStateOfCell().equals(Cell.CellState.BLACKFILLED)) {
        this.setStartingDisk(g2d, Color.BLACK, xPos, yPos, hexagonSize);
      } else if (this.readonlyReversiModel.getGameBoard().getCells()[col][row].
              getStateOfCell().equals(Cell.CellState.WHITEFILLED)) {
        this.setStartingDisk(g2d, Color.WHITE, xPos, yPos, hexagonSize);
      }
    } else if (keyAsString.equals("p")) {
    }
  }
  

  /**
   * After a move has been made and the gamestate has been updated, repaints the panel with the
   * respective flipped cells as flipped with disks in them according to their color.
   * @param g2d the Graphics object to draw on
   * @param xPos the cell's x position to fill
   * @param yPos the cell's y position to fill
   */
  public void repaintAfterMove(Graphics2D g2d, double xPos, double yPos) {
    for (int col = 0; col < readonlyReversiModel.getBoardSize(); col++) {
      for (int row = 0; row < readonlyReversiModel.getBoardSize(); row++) {
        if (readonlyReversiModel.getGameBoard().getCells()[col][row] != null) {
          if (readonlyReversiModel.getGameBoard().getCells()[col][row].getStateOfCell()
                  .equals(Cell.CellState.WHITEFILLED)) {
            System.out.println(readonlyReversiModel.getGameBoard().getCells()[col][row]);
            setStartingDisk(g2d, Color.WHITE, xPos, yPos, 30);
          } else if (readonlyReversiModel.getGameBoard().getCells()[col][row].getStateOfCell()
                  .equals(Cell.CellState.BLACKFILLED)) {
            System.out.println(readonlyReversiModel.getGameBoard().getCells()[col][row]);
            setStartingDisk(g2d, Color.BLACK, xPos, yPos, 30);
          }

        }
      }
    }
  }

  /**
   * This class extends KeyAdapter and serves as a key listener for handling keyboard events
   * related to Reversi gameplay. It interacts with a ReadonlyReversiModel to respond to specific
   * key presses such as passing or making a move.
   */
  public class ReversiKeyAdapterInside extends KeyAdapter {
    private final ReadonlyReversiModel model;

    public ReversiKeyAdapterInside(ReadonlyReversiModel model) {
      this.model = model;
    }

    /**
     * Invoked when a key is pressed. Responds to specific key events such as "p" for passing
     * and Enter key for making a move.
     *
     * @param e The KeyEvent representing the key press event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
      int keyEvent = e.getKeyCode();

      if (keyEvent == KeyEvent.VK_P) {
        System.out.println("The current player has passed");
        keyAsString = "p";
      }

      if (keyEvent == KeyEvent.VK_ENTER) {
        System.out.println("The current player has made a move.");
        keyAsString = "enter";
        features.makeMove();
      }
      repaint();
    }
  }

}
