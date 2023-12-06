package provider.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import provider.controller.ControllerFeatures;
import provider.model.Coordinate;
import provider.model.ReadonlyReversiModel;


/**
 * Represents the frame for the GUI which allows the window to pop up when running the main
 * allowing the GUI to be viewed and interacted with in the Reversi Game.
 */
public class ReversiFrame extends JFrame implements ReversiView {
  private final ReversiPanel panel;
  ReversiPanel.ReversiMouseAdapterInside mouseAdapter;
  ReversiPanel.ReversiKeyAdapterInside keyAdapter;

  /**
   * Creates a frame window for a game of Reversi. This allows the panel to be placed on top with
   * the hexagon board drawn out.
   * @param readOnlyModel The read only model of the game to be viewed using the frame. Read only
   *                     is used so that the actual model of the game cannot be mutated
   */
  public ReversiFrame(ReadonlyReversiModel readOnlyModel) {
    super();
    //readOnlyModel = new ReversiModel(5);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE); //java will keep running forever if u
    this.setPreferredSize(new Dimension(1000, 1000));//makes sure pack doesn't shrink
    this.panel = new ReversiPanel(readOnlyModel);
    this.panel.addMouseListener(mouseAdapter);
    this.panel.addKeyListener(keyAdapter);
    this.add(panel); //adding the panel into the frame so the drawings will show up
    this.refresh();
    this.pack();
  }

  @Override
  public void selectCell() {
    this.panel.selectCell();

  }
  
  @Override
  public void makeMove() {
    this.panel.makeMove();
  }

  @Override
  public void passOnTurn() {
    this.panel.passOnTurn();
  }

  public Coordinate getSelectedCellToMoveOn() {
    return this.panel.getSelectedCellToMakeMoveOn();
  }

  @Override
  public void displayErrorMsg() {
    this.panel.displayErrorMsg();
  }

  @Override
  public void refresh() {
    this.panel.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(ControllerFeatures features) {
    this.panel.addFeatures(features);
  }

}
