package provider.model;

import java.awt.Color;
import java.util.Objects;

/**
 * represents one cell in the game of reversi. It is a hexagonal shaped cell, and has
 * coordinates, and a state of the cell.
 */
public final class Cell {
  private CellState stateOfCell;
  private final Coordinate cellsCoordinate;

  /**
   * This represents the cell state that a cell can have. It can be either an empty cell,
   * a cell with a white piece on it, or a cell with a black piece on it.
   */
  public enum CellState {
    BLACKFILLED,
    WHITEFILLED,
    EMPTY
  }

  /**
   * This constructs a celll with the given state of the cell, and the coordinate of the cell.
   *
   * @param stateOfCell     - represents whether it has a piece on it (black or white) or is empty.
   * @param cellsCoordinate - represents where it is on the board with [col][row] set up.
   */
  
  public Cell(CellState stateOfCell, Coordinate cellsCoordinate) {
    this.stateOfCell = stateOfCell;
    this.cellsCoordinate = cellsCoordinate;
    
  }

  /**
   * This constructs an empty cell with the given coordinates.
   *
   * @param cellsCoordinate - Represents the coordinate of the given cell.
   */
  public Cell(Coordinate cellsCoordinate) {
    this.cellsCoordinate = cellsCoordinate;
    this.stateOfCell = CellState.EMPTY;
  }

  /**
   * this returns the color of this cell given its cell state.
   * @return if the cell state has a black piece on it, it returns black, if it has a white piece
   *        it returns white, and if it has no piece it throws an illegal argument
   */
  public Color getColor() {

    Objects.requireNonNull(this);
    if (stateOfCell.equals(CellState.BLACKFILLED)) {
      return Color.BLACK;
    }
    else if (stateOfCell.equals(CellState.WHITEFILLED)) {
      return Color.WHITE;
    }
    else {
      throw new IllegalArgumentException("there is no colored piece on an empty cell");
    }
  }
  

  /**
   * this gets the q coordinate within a cells coordinate.
   * @return the column of the cell.
   */
  public int getQCoord() {
    return this.cellsCoordinate.getQ();
  }

  /**
   * this gets the r coordinate within a cells coordinate.
   * @return the row of the cell
   */
  public int getRCoord() {
    return this.cellsCoordinate.getR();
  }

  /**
   * returns the coordinate of this cell as long as the cell is not null.
   * @return the cells coordinates.
   */
  public Coordinate getCellsCoordinate() {
    return Objects.requireNonNull(this.cellsCoordinate);
  }

  /**
   * returns the state of this cell.
   * @return the CellState, either BlackFilled, WhiteFilled, or empty, of this cell.
   */
  public CellState getStateOfCell() {

    return this.stateOfCell;
  }

  /**
   * this changes the state of a cell to the state that is associated with the given color.
   * @param color - represents the color of the piece that would go on the cell.
   */
  public void fillMyCell(Color color) {
    if (this.stateOfCell.equals(CellState.EMPTY)) {
      if (color.equals(Color.BLACK)) {
        this.stateOfCell = CellState.BLACKFILLED;
      }
      else if (color.equals(Color.WHITE)) {
        this.stateOfCell = CellState.WHITEFILLED;
      }
      else {
        throw new IllegalArgumentException("cannot be fill a cell with a color that doesn't exist");
      }
    } else {
      throw new IllegalStateException("cannot fill a cell thats already filled");
    }
  }

  /**
   * changes the state of the cell to be the opposite of what it is currently. If it is white
   * then it flips it to black, if it is black then it flips it to white. This should not be
   * called on an empty cell, so if it is empty it should throw an exception.
   */
  public void flipMyColor() {
    if (this.stateOfCell.equals(CellState.WHITEFILLED)) {
      this.stateOfCell = CellState.BLACKFILLED;
    }
    else if (this.stateOfCell.equals(CellState.BLACKFILLED)) {
      this.stateOfCell = CellState.WHITEFILLED;
    }
    else {
      throw new IllegalStateException("can't flip a non-filled cell");
    }
  }

}
