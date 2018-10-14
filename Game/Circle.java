/*

Autores: Alejandro Gleason Antonio Vázquez Y Angelica Güemes
Esta clase se deriva de Shape y define las propiedades del Circle así como su método de dibujo.

*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.IOException;


public class Circle extends Shape {
	private int width, height, radio;
	BufferedImage img = getImage("life.png");

	public Circle(){//Circle constructor
		this.c = Color.red;
	}

	public void draw (Graphics g){
		g.drawImage(img, x,y,width, height, null);
	}

	public void move(){

	}

	public void setBounds(int x, int y, int w, int h){
		rect = new Rectangle(x, y, w, h);
	}

	public void setPosition(int x, int y){//Hacer primero siempre el position
		this.x = x;
		this.y = y;
	}

	public void setSize(int w, int h){
		this.width = w;
		this.height = h;
		//o rect = new Rectangle(x,y, w, h);
	}

	//Metodo para leer la imagen del archivo
	private BufferedImage getImage(String imageDirection) {
		try { //Se tiene que utilizar un try catch para usar ImageIO
			return ImageIO.read(getClass().getResource(imageDirection));
		} catch (IOException e) {
			System.out.println("Error al cargar imagen");
			return null;
		}
	}
}
