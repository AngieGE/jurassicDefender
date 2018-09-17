/*

Autores: Alejandro Gleason Antonio Vázquez Y Angelica Güemes
Esta clase se deriva de Shape y define las propiedades del Circle así como su método de dibujo.

*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
//Se importan librerias para la lectura de imagenes
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.util.ArrayList;

public class Dinosaur extends Shape{
	private int vel;
	private BufferedImage dino = getImage("dino.png");
	private int addX = 10;
	private int addY = 10;

	//Metodo para leer la imagen del archivo
	private BufferedImage getImage(String imageDirection) {
		try { //Se tiene que utilizar un try catch para usar ImageIO
			return ImageIO.read(getClass().getResource(imageDirection));
		} catch (IOException e) {
			System.out.println("Error al cargar imagen");
			return null;
		}
	}

	public Dinosaur(int vel){
			this.x = 0;
			this.y = 0;//rand.nextInt((1100 - 0) + 1)*-1;
			this.vel=vel;
	}

	public void draw (Graphics g){
			g.drawImage(dino, x, 500, null);//dibuja la catapulta en la pantalla
	}

	public void move(){
		if((this.x) >= 1100){
			x=0;
		}
		this.setX(vel);
	}

	public void setX(int n){//n es num de pixeles
		this.x += n; //suma o resta el num de pixeles a mover para la coord

	}
}
