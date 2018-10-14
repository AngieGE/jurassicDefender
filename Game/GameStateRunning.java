

import java.awt.*;
import java.io.*;

public class GameStateRunning implements GameState {
  private GameStateContext gc;
  private GameManager gm;

  public GameStateRunning(GameStateContext gameContext) {
    this.gc = gameContext;
    gm = new GameManager();
  }

  public void end() {
    //gc.setCurrent(gc.getGameOver());
  }

  public void render(Graphics g) {
    g.setColor(Color.black);
    gm.render(g);
  }

  public void update() {
    gm.update();
  }


  public void evaluateKey(int e) {
    System.out.println("tecla2");
    gm.evaluateKey(e);
  }

  public void testPress(int x, int y) {
    System.out.println("tecla2");
    gm.testPress( x,  y);
  }
}