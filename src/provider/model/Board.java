package provider.model;

import provider.model.Cell;

/**
 * represents the hexagonal board of our reversi game.
 */
public final class Board {
  private final Cell[][] gameBoard;
  

  /**
   * constructs and makes our board. It makes a square shaped board, with the
   * default of 7x7 size, and nulls out the correct cells in order to make it look like a
   * hexagon shape.
   */
  public Board() {
    gameBoard = new Cell[7][7]; // initialize to whatever number u want default to be
    for (int row = 0; row < 7; row++) {
      for (int col = 0; col < 7; col++) {
        gameBoard[row][col] = new Cell(Cell.CellState.EMPTY, new Coordinate(row, col));
      }
    }
    this.nullifyTopHalfMakeHexShape();
    this.nullifyBottomToMakeHexShape();
  }
  
  /**
   * constructs and makes our board. It makes a square shaped board, with the
   *    * given size, and nulls out the correct cells in order to make it look like a
   *    * hexagon shape.
   * @param dimension represents the length/height of the rectangular board before
   *                  cells are nulled out. it must be odd and it must be the same for
   *                  the height and width of the square.
   */
  public Board(int dimension) {
    this.gameBoard = new Cell[dimension][dimension];
    for (int rows = 0; rows < dimension; rows++) {
      for (int cols = 0; cols < dimension; cols++) {
        gameBoard[rows][cols] = new Cell(Cell.CellState.EMPTY, new Coordinate(rows, cols));

      }
    }
    this.nullifyTopHalfMakeHexShape();
    this.nullifyBottomToMakeHexShape();

  }

  /**
   * Copy constructor for a board, given another board.
   */
  public Board(Board other) {
    // Create a new 2D array with the same dimensions as the original
    this.gameBoard = new Cell[other.gameBoard.length][];
    for (int row = 0; row < other.gameBoard.length; row++) {
      // Create a new array for each row
      this.gameBoard[row] = new Cell[other.gameBoard[row].length];
      for (int col = 0; col < other.gameBoard[row].length; col++) {
        // Create a new Cell object for each cell and add it to the new array
        this.gameBoard[row][col] = new Cell(Cell.CellState.EMPTY, new Coordinate(row, col));
      }
    }
    this.nullifyTopHalfMakeHexShape();
    this.nullifyBottomToMakeHexShape();

  }


  /**
   * This nulls out the correct values above the middle line in the hexagon, where no values
   * are nulled out.
   */
  private void nullifyTopHalfMakeHexShape() {

    int numToSubtract = 0;
    int numRows = gameBoard.length;
    for (int row = 0; row < (int) Math.floor(numRows / 2.0); row++) {
      for (int count = (int) Math.floor(numRows / 2.0) - 1; count >= 0; count--) {
        if (count - numToSubtract < 0) {
          break;
        }
        gameBoard[count  - numToSubtract][row] = null;
      }
      numToSubtract++;
    }

  }

  /**
   * this nulls out all the correct values below the middle line, where no cells are nulled out.
   */
  private void nullifyBottomToMakeHexShape() {
    //this time start at the end of the list and count is
    int numRows = gameBoard.length;
    int numTimes = 0;
    for (int row = numRows - 1; row >= (int) Math.floor(numRows / 2.0) + 1; row--) {
      for (int count = numRows - 1; count > (int) Math.floor(numRows / 2.0) + numTimes; count--) {
        //start at last col of last row
        gameBoard[count][row] = null;
      }
      numTimes++;
    }

  }

  /**
   * this returns the 2d array of cells on the game board.
   * @return the 2d array of cells in the game.
   */
  public Cell[][] getCells() {
    return this.gameBoard;
  }
}



