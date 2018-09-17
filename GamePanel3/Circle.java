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
	private int width, height;//El círculo agrega a una figura el radio
	BufferedImage img = getImage("life.png");

	public Circle(int x, int y, int w,int h){//Circle constructor
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.c = Color.red;
		rect= new Rectangle(x,y, w, h);
	}

	public void draw (Graphics g){
		g.drawImage(img, x,y,width, height, null);
	}
	public void move(){

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