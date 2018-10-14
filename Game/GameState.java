

import java.awt.*;

public interface GameState{
  public void end();
  public void render(Graphics g);
  public void update();
}
