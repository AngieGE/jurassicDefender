

import java.awt.*;
import java.io.*;

public class GameStateGameOver implements GameState{
  GameStateContext gameContext;

  public GameStateGameOver (GameStateContext gameContext){
    this.gameContext=gameContext;
  }

  public void end(){
    System.out.println("En GameOver no se puede terminar");
  }

  public void render(Graphics g){ 
    g.setColor(Color.black);
    //g.drawString("GameOver");
    System.out.println("GameOver");
  }

  public void update(){
    //No es necesario actualizar algo por el momento
  }

}
