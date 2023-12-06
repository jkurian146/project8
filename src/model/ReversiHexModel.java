package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ModelEvent;
import controller.ModelEventType;
import controller.ModelListener;
import discs.DiscType;
import player.PlayerTurn;
import discs.Disc;
import discs.DiscColor;
import discs.GameDisc;

// class invariant: only the current player can alter the board (make a move)

/**
 * A 'ReversiHexModel' defines a hexagonal Reversi game.
 */
public class ReversiHexModel extends BoardUtils implements ReversiModel {
  protected boolean gameOn;
  protected Disc[][] gameBoard;
  protected PlayerTurn pt;
  protected DiscType type;
  protected Map<PlayerTurn, DiscColor> playerColorMap;
  protected int numRows;
  protected int numColumns;
  protected StringBuilder playerAction;
  protected GameState state;

  protected List<ModelListener> modelListeners = new ArrayList<>();

  /**
   * Constructor for a Reversi hexagonal model.
   * Sets the type of disc for this game as a hexagonal disc.
   */
  public ReversiHexModel() {
    this.gameOn = false;
    this.numRows = 0;
    this.numColumns = 0;
    this.type = DiscType.HEXDISC;
    this.gameBoard = null;
    // class invariant: only the current player can alter the board (make a move)
    // the class invariant is enforced here because we always start the game off with player one
    this.pt = PlayerTurn.PLAYER1;
    this.playerAction = new StringBuilder();
    this.state = GameState.ONGOING;
    this.playerColorMap = new HashMap<>();
    playerColorMap.put(PlayerTurn.PLAYER1, DiscColor.BLACK);
    playerColorMap.put(PlayerTurn.PLAYER2, DiscColor.WHITE);
  }

  /**
   * Constructor for a Reversi hexagonal Mock.
   * Sets the board to whatever developer specifies
   * and sets the game to on.
   */
  public ReversiHexModel(Disc[][] mockBoard) {
    this.gameBoard = mockBoard;
    this.gameOn = true;
    this.numRows = 0;
    this.numColumns = 0;
    this.type = DiscType.HEXDISC;
    // class invariant: only the current player can alter the board (make a move)
    // the class invariant is enforced here because we always start the game off with player one
    this.pt = PlayerTurn.PLAYER1;
    this.playerAction = new StringBuilder();
    this.state = GameState.ONGOING;
    this.playerColorMap = new HashMap<>();
    playerColorMap.put(PlayerTurn.PLAYER1, DiscColor.BLACK);
    playerColorMap.put(PlayerTurn.PLAYER2, DiscColor.WHITE);
  }

  /**
   * The addListeners method adds relevant listeners.
   */

  public void addListener(ModelListener ml) {
    modelListeners.add(ml);
  }

  /**
   * The notifyListeners method notifies relevant listeners.
   */
  public void notifyListeners(ModelEvent modelEvent) {
    for (ModelListener ml : this.modelListeners) {
      ml.update(modelEvent);
    }
  }

  private void placeGameDiscs(int spacesMaxLeft, int spacesMaxRight, int i) {
    for (int j = 0; j < this.gameBoard[0].length; j++) {
      if (j >= spacesMaxLeft && j < this.gameBoard.length - spacesMaxRight) {
        this.gameBoard[i][j] = new GameDisc(this.type, DiscColor.FACEDOWN);
      }
    }
  }

  /**
   * Helps create the initial game board when the middle row is odd.
   */

  private void initBoardWithOddMiddle() {
    int middle = this.gameBoard.length / 2;
    double maxSpaces = (double) this.gameBoard.length - ((double) (this.gameBoard.length + 1) / 2);
    int spacesMaxLeft = (int) Math.ceil(maxSpaces / 2);
    int spacesMaxRight = (int) (maxSpaces - spacesMaxLeft);
    boolean middleCrossed = false;
    for (int i = 0; i < this.gameBoard.length; i++) {
      int distanceFromMiddle = Math.abs(middle - i);

      if (!middleCrossed) {
        if (distanceFromMiddle % 2 == 0) {
          spacesMaxLeft -= 1;
        }
      } else {
        if (distanceFromMiddle % 2 != 0) {
          spacesMaxLeft += 1;
        }
      }

      this.placeGameDiscs(spacesMaxLeft, spacesMaxRight, i);

      if (!middleCrossed) {
        if (distanceFromMiddle % 2 == 0) {
          spacesMaxRight -= 1;
        }
      } else {
        if (spacesMaxRight < 0) {
          spacesMaxRight = 0;
        }
        if (distanceFromMiddle % 2 != 0) {
          spacesMaxRight += 1;
        }
      }

      if (i == middle) {
        middleCrossed = true;
      }
    }
  }

  /**
   * Helps create the initial game board when the middle row is even.
   */
  private void initBoardWithEvenMiddle() {
    int middle = this.gameBoard.length / 2;

    double maxSpaces = (double) this.gameBoard.length - ((double) (this.gameBoard.length + 1) / 2);
    int spacesMaxLeft = (int) Math.ceil(maxSpaces / 2);
    int spacesMaxRight = (int) (maxSpaces - spacesMaxLeft);
    boolean middleCrossed = false;
    StringBuilder sb1 = new StringBuilder();

    for (int i = 0; i < this.gameBoard.length; i++) {
      int distanceFromMiddle = Math.abs(middle - i);

      if (middleCrossed) {
        if (distanceFromMiddle % 2 != 0) {
          spacesMaxRight += 1;
        }
      }

      this.placeGameDiscs(spacesMaxLeft, spacesMaxRight, i);

      if (!middleCrossed) {
        if (distanceFromMiddle % 2 != 0) {
          spacesMaxRight -= 1;
        } else {
          spacesMaxLeft -= 1;
        }
      } else {
        if (distanceFromMiddle % 2 != 0) {
          spacesMaxLeft += 1;
        }
      }

      if (i == middle) {
        middleCrossed = true;
        spacesMaxLeft = 0;
      }
    }
    System.out.println(sb1.toString());
  }


  /**
   * Creates the initial game board of a hexagonal reversi.
   */
  private void initBoard() {
    int middle = this.gameBoard.length / 2;

    if (middle % 2 == 0) {
      this.initBoardWithEvenMiddle();
    } else {
      this.initBoardWithOddMiddle();
    }
    setStartingPieces();
  }

  private void setPiece(int x, int y, DiscColor color) {
    GameDisc replacementDisc = new GameDisc(this.type, color);

    this.gameBoard[y][x] = replacementDisc;
  }

  private void setStartingPieces() {
    int middle = this.gameBoard.length / 2;
    if (middle % 2 != 0) {
      setPiecesForEvenMiddle(middle);
    } else {
      setPiecesForOddMiddle(middle);
    }
  }

  private void setPiecesForOddMiddle(int middle) {
    this.setPiece(middle - 1, middle, DiscColor.BLACK);
    this.setPiece(middle, middle + 1, DiscColor.BLACK);
    this.setPiece(middle, middle - 1, DiscColor.BLACK);
    this.setPiece(middle - 1, middle - 1, DiscColor.WHITE);
    this.setPiece(middle - 1, middle + 1, DiscColor.WHITE);
    this.setPiece(middle + 1, middle, DiscColor.WHITE);
  }

  private void setPiecesForEvenMiddle(int middle) {
    this.setPiece(middle + 1, middle, DiscColor.BLACK);
    this.setPiece(middle, middle - 1, DiscColor.BLACK);
    this.setPiece(middle, middle + 1, DiscColor.BLACK);
    this.setPiece(middle + 1, middle + 1, DiscColor.WHITE);
    this.setPiece(middle + 1, middle - 1, DiscColor.WHITE);
    this.setPiece(middle - 1, middle, DiscColor.WHITE);
  }


  private void checkStartGameConditions(int boardSize) {
    if (this.gameOn) {
      throw new IllegalStateException("Game has already started");
    } else if (boardSize <= 3 || boardSize % 2 == 0) {
      throw new IllegalArgumentException("Invalid Board Sizes");
    }
  }

  @Override
  public void startGame(int boardSize) {
    this.numRows = boardSize;
    this.numColumns = boardSize;
    checkStartGameConditions(boardSize);
    this.gameOn = true;
    this.gameBoard = new Disc[numRows][numColumns];
    initBoard();
    //notifyListeners(new ModelEvent(ModelEventType.PLAYER1TURN, "It's Your Turn Player 1"));
  }

  @Override
  public int getDimensions() {
    this.gameNotYetStarted();
    return this.numRows;
  }

  // checks if the game has been started.
  private void gameNotYetStarted() {
    if (!this.gameOn) {
      throw new IllegalStateException("The game hasn't started.");
    }
  }

  @Override
  public boolean checkValidCoordinates(int x, int y) {
    if (x >= this.gameBoard.length || y >= this.gameBoard.length || x < 0 || y < 0) {
      return false;
    }
    return this.gameBoard[y][x] != null;
  }

  @Override
  public void makeMove(int x, int y) {
    List<Integer> originalCoordinate = new ArrayList<>();
    originalCoordinate.add(x);
    originalCoordinate.add(y);
    if (!this.checkValidCoordinates(x, y)) {
      notifyListeners(new ModelEvent(ModelEventType.ILLEGALMOVE,
              "Illegal Move: In Grid Out Of Bounds"));
      if (this.pt == PlayerTurn.PLAYER1) {
        notifyListeners(new ModelEvent(ModelEventType.PLAYER1TURN,
                "Illegal move occured it is still Player 1 Turn"));
      } else {
        notifyListeners(new ModelEvent(ModelEventType.PLAYER2TURN,
                "Illegal move occured it is still Player 2 Turn"));
      }
      return;
    }
    if (this.getDiscAt(x, y).getColor() != DiscColor.FACEDOWN) {
      notifyListeners(new ModelEvent(ModelEventType.ILLEGALMOVE,
              "Illegal Move: In Grid Selected Facedown"));
      if (this.pt == PlayerTurn.PLAYER1) {
        notifyListeners(new ModelEvent(ModelEventType.PLAYER1TURN,
                "Illegal move occured it is still Player 1 Turn"));
      } else {
        notifyListeners(new ModelEvent(ModelEventType.PLAYER2TURN,
                "Illegal move occured it is still Player 2 Turn"));
      }
      return;
    }

    List<List<List<Integer>>> moves = BoardUtils.bfs(this, x, y);
    boolean allEmptyLists = moves.stream().allMatch(List::isEmpty);

    if (allEmptyLists) {
      notifyListeners(new ModelEvent(ModelEventType.ILLEGALMOVE,
              "Illegal Move: In Grid No Moves"));
      if (this.pt == PlayerTurn.PLAYER1) {
        notifyListeners(new ModelEvent(ModelEventType.PLAYER1TURN,
                "Illegal move occured it is still Player 1 Turn"));
      } else {
        notifyListeners(new ModelEvent(ModelEventType.PLAYER2TURN,
                "Illegal move occured it is still Player 2 Turn"));
      }
      return;
    }
    // class invariant: only the current player can alter the board (make a move)
    // the class invariant is enforced here because we are only applying a color filter
    // with the current player color
    for (List<List<Integer>> l : moves) {
      l.forEach(innerList -> {
        applyColorFilter(innerList, this.getPlayerColor(this.pt));

        innerList.forEach(item -> this.playerAction.append(item).append(" "));
        this.playerAction.append(System.lineSeparator()); // Add a newline after each inner list
      });
    }
    // class invariant: only the current player can alter the board (make a move)
    // the class invariant is enforced here because when we execute a valid move
    // we can now switch to the opposite color.
    this.togglePlayer();
    if (this.pt == PlayerTurn.PLAYER1) {
      notifyListeners(new ModelEvent(ModelEventType.PLAYER1TURN,
              "It's Your Turn Player 1"));
    } else {
      notifyListeners(new ModelEvent(ModelEventType.PLAYER2TURN,
              "It's Your Turn Player 2"));
    }
  }

  // a method that applies the current players color to newly captured cells.
  private void applyColorFilter(List<Integer> list, DiscColor discColor) {
    this.setPiece(list.get(0), list.get(1), discColor);
  }

  // determines if the last n log entries were passes
  private boolean consecutivePasses(int n) {
    List<String> moveHistory = Arrays.asList(this.playerAction.toString().split("\n"));
    if (moveHistory.size() >= n) {
      boolean areLastNEqual = moveHistory.subList(moveHistory.size() - n, moveHistory.size())
              .stream().allMatch("pass"::equals);
      return areLastNEqual;
    } else {
      return false;
    }
  }

  // returns the current gameState
  @Override
  public GameState getCurrentGameState() {
    this.gameNotYetStarted();
    return this.state;
  }

  @Override
  public Disc[][] getCurrentBoardState() {
    this.gameNotYetStarted();

    int rows = this.gameBoard.length;
    Disc[][] copy = new Disc[rows][];

    for (int i = 0; i < rows; i++) {
      if (this.gameBoard[i] != null) {
        int cols = this.gameBoard[i].length;
        copy[i] = new Disc[cols];
        System.arraycopy(this.gameBoard[i], 0, copy[i], 0, cols);
      }
    }
    return copy;
  }

  @Override
  public boolean canPlayerPlay(int x, int y) {
    try {
      boolean flipped = !this.isDiscFlipped(x, y);
      return flipped;
    } catch (IllegalArgumentException iae) {
      return false;
    }
  }

  @Override
  public Boolean isGameOver() {
    this.gameNotYetStarted();
    boolean noMoves = this.noMoreLegalMoves();
    boolean twoPassesInARow = this.consecutivePasses(2);
    boolean currentPlayerLost = this.getScore(this.pt) == 0;
    boolean oppositePlayerLost = this.getScore(this.getOpponent(this.pt)) == 0;

    if (twoPassesInARow) {
      this.state = GameState.STALEMATE;
      return true;
    }
    if (currentPlayerLost) {
      this.state = (this.pt == PlayerTurn.PLAYER1) ? GameState.PLAYER2WIN : GameState.PLAYER1WIN;
      return true;
    }
    if (oppositePlayerLost) {
      this.state = (this.pt == PlayerTurn.PLAYER1) ? GameState.PLAYER1WIN : GameState.PLAYER2WIN;
      return true;
    }
    if (noMoves) {
      this.state = GameState.STALEMATE;
      return true;
    }
    return false;
  }

  // determines if there are any legal moves by running
  // a bfs on every facedown cell.
  private Boolean noMoreLegalMoves() {
    for (int i = 0; i < this.gameBoard.length; i++) {
      for (int j = 0; j < this.gameBoard[0].length; j++) {
        if (this.gameBoard[j][i] == null) {
          int doNothing = 0;
        } else if (this.gameBoard[j][i].getColor() == DiscColor.FACEDOWN) {
          List<List<List<Integer>>> moves = bfs(this, i, j);
          boolean allEmptyLists = moves.stream().allMatch(List::isEmpty);
          if (!allEmptyLists) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public PlayerTurn currentTurn() {
    this.gameNotYetStarted();
    return this.pt;
  }

  // gets the current player color
  @Override
  public DiscColor getPlayerColor(PlayerTurn player) {
    return playerColorMap.get(player);
  }

  // gets the opponents color
  @Override
  public PlayerTurn getOpponent(PlayerTurn player) {
    if (PlayerTurn.PLAYER2 == player) {
      return PlayerTurn.PLAYER1;
    }
    return PlayerTurn.PLAYER2;
  }

  // gets a player score.
  @Override
  public int getScore(PlayerTurn player) {
    this.gameNotYetStarted();
    int countDiscsForThisPlayer = 0;
    for (Disc[] row : this.gameBoard) {
      for (Disc disc : row) {
        if (disc == null) {
          countDiscsForThisPlayer += 0;
        } else if (disc.getColor() == this.getPlayerColor(player)) {
          countDiscsForThisPlayer++;
        }
      }
    }
    return countDiscsForThisPlayer;
  }


  @Override
  public boolean doesPlayerHaveLegalMove() {
    DiscColor currentColor = this.getPlayerColor(this.pt);
    int playerScore = this.getScore(this.pt);
    return playerScore != 0;
  }

  @Override
  public String getType() {
    return "Hexagonal";
  }

  @Override
  public Disc getDiscAt(int x, int y) {
    this.gameNotYetStarted();
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("getDiscAt: POSN provided by user is invalid");
    }
    return this.gameBoard[y][x];
  }

  @Override
  public boolean isDiscFlipped(int x, int y) {
    this.gameNotYetStarted();
    if (!this.checkValidCoordinates(x, y)) {
      throw new IllegalArgumentException("isDiscFlipped: POSN provided by user is invalid");
    }
    return this.gameBoard[y][x].getColor() != DiscColor.FACEDOWN;
  }

  @Override
  public void pass() {
    this.gameNotYetStarted();
    // class invariant: only the current player can alter the board (make a move)
    // the class invariant is enforced here because when the user wants to pass
    // we are also toggling the player.
    this.togglePlayer();
    PlayerTurn currentTurn = this.currentTurn();
    ModelEventType modelEventType = (currentTurn == PlayerTurn.PLAYER1) ? ModelEventType.PLAYER1TURN
            : ModelEventType.PLAYER2TURN;
    if (modelEventType == ModelEventType.PLAYER1TURN) {
      notifyListeners(new ModelEvent(ModelEventType.PLAYER1TURN, "It's Your Turn Player 1"));
    } else {
      notifyListeners(new ModelEvent(ModelEventType.PLAYER2TURN, "It's Your Turn Player 2"));
    }
    this.playerAction.append("pass\n");
  }

  @Override
  public List<ReadOnlyReversiModel> getGameStates() {
    return null;
  }

  @Override
  public List<List<Integer>> getMoves() {
    return null;
  }

  // toggles a player after a move has been made or pass has been attempted
  private void togglePlayer() {
    this.gameNotYetStarted();
    if (this.pt == PlayerTurn.PLAYER1) {
      this.pt = PlayerTurn.PLAYER2;
    } else {
      this.pt = PlayerTurn.PLAYER1;
    }
  }
}