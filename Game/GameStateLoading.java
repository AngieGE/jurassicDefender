

import java.awt.*;
import java.io.*;

public class GameStateLoading implements GameState{
  GameStateContext gameContext;

  public GameStateLoading (GameStateContext gameContext){
    this.gameContext=gameContext;
  }

  public void end(){
    System.out.println("En GameOver no se puede terminar");
  }

  public void render(Graphics g){
    g.setColor(Color.black);
  }

  public void update(){
    //No es necesario actualizar algo por el momento
  }

}
