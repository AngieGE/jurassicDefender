/*

Autores: Alejandro Gleason Antonio Vázquez Y Angelica Güemes
Esta clase se deriva de Shape y define las propiedades del Circle así como su método de dibujo.

*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

public class Circle extends Shape {
	private int radio;//El círculo agrega a una figura el radio
	private int addX = 10;
	private int addY = 10;

	public Circle(int x, int y){//Circle constructor
		this.x = x;
		this.y = y;
		this.radio = 55;
		this.c = Color.red;
	}

	public void draw (Graphics g){
		g.setColor(c);
		g.fillOval(x, y, radio, radio);
	}
	public void move(){
		if((this.x+radio) >= 500){
			addX = -addX;
			x = 490-radio;
		}
		if(x<=0){
			addX = -addX;
			x = 10;
		}
		if((this.y+radio) >= 400){
			addY = -addY;
		}if(y<=0){
			addY=-addY;
			y = 10;
		}
		x += addX;
		y +=addY;
		rect.setLocation(x,y);
	}

	public void setX(int n){//n es num de pixeles
		
		if(x > 1000){//Validacion para no salirse de la pantalla
			this.x -=n;
			x = 0;
		}else{
			this.x += n; //suma o resta el num de pixeles a mover para la coord
		}
	}
	public void setY(int n){//n es num de pixeles
		this.y += n; //suma o resta el num de pixeles a mover
	}
}