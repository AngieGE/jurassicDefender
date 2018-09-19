import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.util.Random;

public class Meteorito extends Shape{
    private int radio;//El círculo agrega a una figura el radio
    private int velY;
    Random rand = new Random();


    public Meteorito(int vel){
        this.x = rand.nextInt((1100 - 0) + 1);
        this.y = 0;//rand.nextInt((1100 - 0) + 1)*-1;
        this.radio =rand.nextInt((55 - 30) + 1)+30;
        this.c = Color.DARK_GRAY;
        this.velY=vel;
        rect= new Rectangle(x,y, radio, radio);
    }

    public void draw (Graphics g){
        g.setColor(c);
        g.fillOval(x, y, radio, radio);
    }

    public void move(){
        this.y += velY;
        updateRect(this.getX(),this.getY());
    }

}
