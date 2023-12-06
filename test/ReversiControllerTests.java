import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import controller.MockReversiController;
import controller.ModelEventType;
import controller.PlayerEventType;
import discs.DiscColor;
import model.GameState;
import model.ReversiHexModel;
import model.ReversiHexModelAI;
import player.Player;
import player.PlayerTurn;
import strategy.StrategyType;
import view.MockReversiGUI;
import controller.PlayerListener;
import controller.ModelListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 * The ReversiControllerTests class is for testing the controller.
 */
public class ReversiControllerTests {
  ReversiHexModel model;
  MockReversiGUI mockView;

  @Before
  public void initData() {
    this.model = new ReversiHexModel();
  }

  @Test
  public void testPlayerListenerCommunicatesCorrectlyButDoesNotChangeAnything() {
    model.startGame(7);
    this.mockView = new MockReversiGUI(model);

    PlayerListener listener = new PlayerListener();
    mockView.addListener(listener);

    int xCoordinate = 2;
    int yCoordinate = 2;
    JButton buttonToClick = mockView.boardButtons[yCoordinate][xCoordinate];

    MouseEvent mockMouseEvent = new MouseEvent(buttonToClick,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent);
    }

    KeyEvent mockEnterKey = new KeyEvent(buttonToClick,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick.getKeyListeners()) {
      kl.keyPressed(mockEnterKey);
    }

    Assert.assertEquals(listener.getMostRecentEvent().getPlayerEventType(), PlayerEventType.MOVE);
    // Assert that the view did not actually make the move, but rather just communicate
    // to the model through the player listener that the player wants to move
    // to the disc at 2, 2.
    // Similarly, the player turn should not switch to the other player, as nothing
    // happened yet.
    Assert.assertEquals(model.getDiscAt(2, 2).getColor(), DiscColor.FACEDOWN);
    Assert.assertNotEquals(PlayerTurn.PLAYER2, model.currentTurn());

    KeyEvent mockSpaceBar =  new KeyEvent(buttonToClick,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick.getKeyListeners()) {
      kl.keyPressed(mockSpaceBar);

      Assert.assertEquals(listener.getMostRecentEvent().getPlayerEventType(), PlayerEventType.PASS);
      // Assert that it is still player 1's turn, but the view communicated with the model
      // through the player listener correctly by updating the most recent player event to pass.
      Assert.assertEquals(PlayerTurn.PLAYER1, model.currentTurn());
    }
  }


  @Test
  public void testModelListenerNotifiesWhenPlayerTurnsChanges() {
    model.startGame(5);
    ModelListener listener = new ModelListener();
    model.addListener(listener);

    Assert.assertEquals(ModelEventType.PLAYER1TURN,
            listener.getMostRecentEvent().getModelEventType());
    model.pass();
    Assert.assertEquals(ModelEventType.PLAYER2TURN,
            listener.getMostRecentEvent().getModelEventType());
  }

  @Test
  public void testModelListenerListensToIllegalMoveProperly() {
    model.startGame(5);
    ModelListener listener = new ModelListener();
    model.addListener(listener);

    // When making an illegal move, we do not want to switch the player turn.
    model.makeMove(2, 2);
    Assert.assertEquals(ModelEventType.PLAYER1TURN,
            listener.getMostRecentEvent().getModelEventType());
  }

  // Play a game between two players, with two controllers, and two views.
  // Assert that game passes as expected.
  @Test
  public void testControllerWith2Players() {
    model.startGame(5);
    MockReversiGUI viewPlayer1 = new MockReversiGUI(model);
    MockReversiGUI viewPlayer2 = new MockReversiGUI(model);
    Player player1 = new Player(PlayerTurn.PLAYER1);
    Player player2 = new Player(PlayerTurn.PLAYER2);
    MockReversiController controller1 = new MockReversiController(model, viewPlayer1, player1);
    MockReversiController controller2 = new MockReversiController(model, viewPlayer2, player2);
    ModelListener mListener = new ModelListener();
    model.addListener(mListener);
    controller1.addListener(controller2);
    controller2.addListener(controller1);

    int x1 = 0;
    int y1 = 1;

    JButton buttonToClick1 = viewPlayer1.boardButtons[y1][x1];
    MouseEvent mockMouseEvent1 = new MouseEvent(buttonToClick1,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick1.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent1);
    }

    KeyEvent mockEnterKey1 = new KeyEvent(buttonToClick1,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick1.getKeyListeners()) {
      kl.keyPressed(mockEnterKey1);
    }
    controller1.listen();

    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(0, 1).getColor());
    Assert.assertEquals(PlayerTurn.PLAYER2, model.currentTurn());

    int x2 = 3;
    int y2 = 3;

    JButton buttonToClick2 = viewPlayer2.boardButtons[y2][x2];
    MouseEvent mockMouseEvent2 = new MouseEvent(buttonToClick2,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick2.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent2);
    }
    KeyEvent mockEnterKey2 = new KeyEvent(buttonToClick2,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick2.getKeyListeners()) {
      kl.keyPressed(mockEnterKey2);
    }
    controller2.listen();

    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(3, 3).getColor());
    Assert.assertEquals(PlayerTurn.PLAYER1, model.currentTurn());

    int x3 = 2;
    int y3 = 4;

    JButton buttonToClick3 = viewPlayer1.boardButtons[y3][x3];
    MouseEvent mockMouseEvent3 = new MouseEvent(buttonToClick3,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick3.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent3);
    }
    KeyEvent mockEnterKey3 = new KeyEvent(buttonToClick3,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick3.getKeyListeners()) {
      kl.keyPressed(mockEnterKey3);
    }
    controller1.listen();

    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(2, 4).getColor());
    Assert.assertEquals(PlayerTurn.PLAYER2, model.currentTurn());


    int x4 = 2;
    int y4 = 0;

    JButton buttonToClick4 = viewPlayer2.boardButtons[y4][x4];
    MouseEvent mockMouseEvent4 = new MouseEvent(buttonToClick4,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick4.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent4);
    }
    KeyEvent mockEnterKey4 = new KeyEvent(buttonToClick4,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick4.getKeyListeners()) {
      kl.keyPressed(mockEnterKey4);
    }
    controller2.listen();

    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(2, 0).getColor());
    Assert.assertEquals(PlayerTurn.PLAYER1, model.currentTurn());

    int x5 = 3;
    int y5 = 1;

    JButton buttonToClick5 = viewPlayer1.boardButtons[y5][x5];
    MouseEvent mockMouseEvent5 = new MouseEvent(buttonToClick5,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick5.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent5);
    }
    KeyEvent mockEnterKey5 = new KeyEvent(buttonToClick5,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick5.getKeyListeners()) {
      kl.keyPressed(mockEnterKey5);
    }
    controller1.listen();

    Assert.assertEquals(DiscColor.BLACK, model.getDiscAt(3, 1).getColor());
    Assert.assertEquals(PlayerTurn.PLAYER2, model.currentTurn());

    int x6 = 0;
    int y6 = 3;

    JButton buttonToClick6 = viewPlayer2.boardButtons[y6][x6];
    MouseEvent mockMouseEvent6 = new MouseEvent(buttonToClick6,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick6.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent6);
    }
    KeyEvent mockEnterKey6 = new KeyEvent(buttonToClick6,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick6.getKeyListeners()) {
      kl.keyPressed(mockEnterKey6);
    }
    controller2.listen();

    Assert.assertEquals(DiscColor.WHITE, model.getDiscAt(0, 3).getColor());
    Assert.assertEquals(PlayerTurn.PLAYER1, model.currentTurn());

    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(GameState.STALEMATE, model.getCurrentGameState());
  }

  // Test that double pass works and ends the game in a tie
  // when players hit the pass key consecutively
  //
  @Test
  public void testDoublePass() {
    model.startGame(7);
    MockReversiGUI viewPlayer1 = new MockReversiGUI(model);
    MockReversiGUI viewPlayer2 = new MockReversiGUI(model);
    Player player1 = new Player(PlayerTurn.PLAYER1);
    Player player2 = new Player(PlayerTurn.PLAYER2);
    MockReversiController controller1 = new MockReversiController(model, viewPlayer1, player1);
    MockReversiController controller2 = new MockReversiController(model, viewPlayer2, player2);
    ModelListener mListener = new ModelListener();
    model.addListener(mListener);
    controller1.addListener(controller2);
    controller2.addListener(controller1);

    int coordinate = 3;
    JButton buttonToClick = viewPlayer1.boardButtons[coordinate][coordinate];

    MouseEvent mockMouseEvent = new MouseEvent(buttonToClick,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent);
    }

    KeyEvent mockEnterKey = new KeyEvent(buttonToClick,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick.getKeyListeners()) {
      kl.keyPressed(mockEnterKey);
    }
    controller1.listen();

    Assert.assertEquals(PlayerTurn.PLAYER2, model.currentTurn());

    JButton buttonToClick2 = viewPlayer2.boardButtons[coordinate][coordinate];

    MouseEvent mockMouseEvent2 = new MouseEvent(buttonToClick2,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick2.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent2);
    }

    KeyEvent mockEnterKey2 = new KeyEvent(buttonToClick2,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick2.getKeyListeners()) {
      kl.keyPressed(mockEnterKey2);
    }
    controller2.listen();

    Assert.assertEquals(PlayerTurn.PLAYER1, model.currentTurn());
    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(GameState.STALEMATE, model.getCurrentGameState());
  }

  // Test game against AI Player
  // Play a game against AI using MAXIMIZE strategy and lose, assert that everything is as expected
  @Test
  public void testAIController() {
    ReversiHexModelAI aiModel = new ReversiHexModelAI(StrategyType.MAXIMIZE);
    aiModel.startGame(7);
    this.mockView = new MockReversiGUI(aiModel);
    Player player = new Player(PlayerTurn.PLAYER1);
    MockReversiController controller = new MockReversiController(aiModel, mockView, player);
    PlayerListener pListener = new PlayerListener();
    mockView.addListener(pListener);
    ModelListener mListener = new ModelListener();
    aiModel.addListener(mListener);

    int xCoordinate = 2;
    int yCoordinate = 2;
    JButton buttonToClick = mockView.boardButtons[yCoordinate][xCoordinate];

    MouseEvent mockMouseEvent = new MouseEvent(buttonToClick,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent);
    }

    KeyEvent mockEnterKey = new KeyEvent(buttonToClick,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick.getKeyListeners()) {
      kl.keyPressed(mockEnterKey);
    }
    controller.listen();
    // Assert that the ai made a move and captured the disc that the player originally captured
    // with their move to (2, 2)
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2,2).getColor());

    int xCoordinate2 = 3;
    int yCoordinate2 = 1;
    JButton buttonToClick2 = mockView.boardButtons[yCoordinate2][xCoordinate2];

    MouseEvent mockMouseEvent2 = new MouseEvent(buttonToClick2,
            MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
            0, 0, 0, 1, false);
    for (MouseListener ml : buttonToClick2.getMouseListeners()) {
      ml.mousePressed(mockMouseEvent2);
    }

    KeyEvent mockEnterKey2 = new KeyEvent(buttonToClick2,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick2.getKeyListeners()) {
      kl.keyPressed(mockEnterKey2);
    }
    controller.listen();
    // Assert that the ai made a move and captured a new disc
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5,2).getColor());

    KeyEvent mockSpaceBar =  new KeyEvent(buttonToClick,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick.getKeyListeners()) {
      kl.keyPressed(mockSpaceBar);
    }
    controller.listen();
    // Assert that the ai made a move to capture some new discs after the player passed
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2,4).getColor());

    int xCoordinate3 = 5;
    int yCoordinate3 = 5;
    JButton buttonToClick3 = mockView.boardButtons[yCoordinate3][xCoordinate3];
    KeyEvent mockSpaceBar2 =  new KeyEvent(buttonToClick3,
            KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
    for (KeyListener kl : buttonToClick3.getKeyListeners()) {
      kl.keyPressed(mockSpaceBar2);
    }
    controller.listen();
    // Assert that the ai made a move to capture some new discs after the player passed
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3,0).getColor());

    // The AI has now won the game.
    Assert.assertTrue(aiModel.isGameOver());
    Assert.assertEquals(GameState.PLAYER2WIN, aiModel.getCurrentGameState());
  }
}