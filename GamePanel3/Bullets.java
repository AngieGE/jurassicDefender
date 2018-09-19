/*

Autor: Alejandro Gleason
Fecha: 09 de septiembre del 2018
Esta clase se deriva de Shape y define las propiedades del Circle así como su método de dibujo.

*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

public class Bullets extends Shape {
    private int radio;//El círculo agrega a una figura el radio
    private int currentImage;
    private int vel; //Velocidad de la bala
    private double clickx=4000, clicky=4000;
    private double nexty, m;
    public double angle;

    public Bullets(int x, int y, int ci, int vel){//Circle constructor
        this.x = x;
        this.y = y;
        this.radio = 55;
        this.c = Color.black;
        this.currentImage=ci;
        this.vel=vel;
        rect= new Rectangle(x, y, radio, radio);
    }

    public Bullets(int x, int y, int ci, int vel, int cx, int cy){//Circle constructor
        this.x = x;
        this.y = y;
        this.radio = 55;
        this.c = Color.black;
        this.currentImage=ci;
        this.vel=vel;
        this.clickx=cx;
        this.clicky=cy;
        rect= new Rectangle(x, y, radio, radio);
    }

    public void draw (Graphics g){
        g.setColor(c);
        g.fillOval(x, y, radio, radio);
    }

    public void move(){
        if (clickx !=4000 && clicky != 4000){
            angle = Math.atan2(clicky - 450,clickx - 470);
            setX((int) ((vel) * Math.cos(angle)));
            setY( (int) ((vel) * Math.sin(angle)));
        }else{
            if (currentImage==1){ //Middle
                this.setY(-vel);
            }else if (currentImage==2){ //Right
                this.setY(-vel);
                this.setX(vel);
            }else if (currentImage==3){ //Left
                this.setY(-vel);
                this.setX(-vel);
            }
        }
        updateRect(this.getX(),this.getY());
    }

    public void setX(int n){//n es num de pixeles
        this.x += n; //suma o resta el num de pixeles a mover para la coord
        updateRect(this.getX(),this.getY());
    }

    public void setY(int n){//n es num de pixeles //suma o resta el num de pixeles a mover
        this.y += n;
        updateRect(this.getX(),this.getY());
    }
}
