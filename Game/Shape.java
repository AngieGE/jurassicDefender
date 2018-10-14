/*

Autores: Alejandro Gleason Antonio Vázquez Y Angelica Güemes
Esta clase es implementada por Circle.

*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

public abstract class Shape{
	protected int x;
	protected int y;
	protected Color c;
	public Rectangle rect;

	public abstract void draw (Graphics g);
	public abstract void move();

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public boolean collision (Rectangle r){

		Rectangle rec = this.getRectangle();
		return rec.intersects(r.getX(), r.getY(), r.width, r.height);
	}

	public Rectangle getRectangle(){
		return rect;
	}

	public void updateRect(int Posx, int Posy){
		x=Posx;
		y=Posy;
		rect.setLocation(this.x, this.y);
	}
}
