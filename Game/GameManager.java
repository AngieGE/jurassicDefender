
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.*;
import java.lang.*;
//Se importan librerias para la lectura de imagenes
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager{
  //attributes
  //Enemies
  private static final int PWIDTH = 1100;//Tamaño del canvas
  private static final int PHEIGHT = 688;

	ArrayList<Shape> enemies = new ArrayList<Shape>(); //Arreglo con todos los enemigos
	//Weapon
	ArrayList<Shape> bullets = new ArrayList<Shape>(); //Arreglo con todas las balas
	ArrayList<Shape> bulletsClick = new ArrayList<Shape>(); //Arreglo con todas las balas
	private int currentImag=1; //variable que almacena el numero de la imagen actual
	private BufferedImage catapulta = getImage("catapultMiddle.png");; //la imagen de la catapulta mostrado en la pantalla
	//Vidas
	ArrayList<Shape> vidasFlores = new ArrayList<Shape>(); //Arreglo con todas las balas
	private Dinosaur mydino;

	private int vidas = 4;


  //Constructor
  public GameManager(){
    mydino = (Dinosaur) ShapeFactory.getInstance().createShape(3);
		//Crea las vidas
		int x=350;
		for(int i=0; i< 4; i++){
			Circle aux = (Circle) ShapeFactory.getInstance().createShape(4);
			aux.setPosition(x, 600);
			aux.setSize(50,70);
			aux.setBounds(x, 600, 50, 70);
			vidasFlores.add(aux);
			x+=100;
		}

  }
  private void rainMeteorites(){

  }

  public void update(){
    //Avanzan los enemigos
    for (int i=0; i < enemies.size()-1; i++){
      enemies.get(i).move();
    }
    //Se crean enemigos y se eliminan los que ya salieron de la pantalla
    setEnemy(4);//set
    mydino.move();
    //Avanzan las balas
    for (int i=0; i< bullets.size(); i++){
      bullets.get(i).move();
    }
    //Se eliminann las balas que ya salieron de la pantalla
    deleteBullets();
    //Revisa vidas menos
    reduceLife();
    //Revisa colision  meteorito bala
    checkCollision();
  }

  public void render(Graphics g){
		mydino.draw(g);
		g.drawImage(catapulta,400, 400, null);//dibuja la catapulta en la pantalla
		//Print bullets
		for (int i=0; i< bullets.size(); i++){
			bullets.get(i).draw(g);
		}
		//Print enemies
		for (int i=0; i< enemies.size()-1; i++){
			enemies.get(i).draw(g);
		}
		//Print flores
		for(int i=0; i< vidasFlores.size(); i++){
			vidasFlores.get(i).draw(g);
		}
  }

  public void setEnemy(int min){//setEnemies
		//Se eliminan los enemigos que esten fuera de pantalla
		for (int i=0; i< enemies.size()-1; i++){
			if(enemies.get(i).getY() > PHEIGHT){
				enemies.remove(i); //System.out.println("Se elimina un enemigo" + enemies.get(i).getY());
			}
		}
		//Se crea un nuevo enemigo si hay menos de min
		if(enemies.size() < min){
			enemies.add((Meteorito)ShapeFactory.getInstance().createShape(2));
		}
	}


  public void deleteBullets(){
		//Se eliminan las bullets que esten fuera de pantalla
		for (int i=0; i< bullets.size()-1; i++){
			if( (bullets.get(i).getY() < 0)|| (bullets.get(i).getX() > PWIDTH) || (bullets.get(i).getX() < 0) ){
				bullets.remove(i);
			}
		}
	}

	public void checkCollision(){
		for (int i=0; i< enemies.size()-1; i++){
			for (int j=0; j< bullets.size()-1; j++)
				if (enemies.get(i).collision(bullets.get(j).getRectangle())){
					System.out.println("COLLISION");
					enemies.remove(enemies.get(i));
				}
		}
	}


  public void reduceLife(){
		//Si colisiona con las flores los dos objetos se eliminan y sele resta una vida
		if(vidas <= 0){
			JOptionPane.showMessageDialog(null, "Perdiste tus 4 vidas :(");
			System.exit(0);
		}
		for (int i=0; i< enemies.size()-1; i++){
			for (int j=0; j< vidasFlores.size(); j++)
				if (enemies.get(i).collision(vidasFlores.get(j).getRectangle())){
					//System.out.println("COLLISION");
					enemies.remove(enemies.get(i));
					vidasFlores.remove(vidasFlores.get(j));
					//vidas--;
				}
		}
	}


  //metodo que llama el metodo de cambio de imagen si se selleciona izquierda o derecha
	public void evaluateKey(int k){
		/*switch para la teclas(si es a la derecha avanza y si es ala izquierda retrocede la secuencia*/
		switch(k) {
			case KeyEvent.VK_RIGHT  :
				changeImage(true);
				break; //optional
			case KeyEvent.VK_LEFT  :
				changeImage(false);
				break;
			case KeyEvent.VK_P  :
			case KeyEvent.VK_ESCAPE:
				//Que el juego haga pausa
				break;
			case KeyEvent.VK_SPACE  :
				Bullets aux = (Bullets) ShapeFactory.getInstance().createShape(1);
				aux.setPosition(470, 450);
				aux.setVel(10);
				aux.setImage(currentImag);
				aux.setBounds(470, 450);
				bullets.add(aux);
				break;
		}
	}



  //Metodo para leer la imagen del archivo
	public BufferedImage getImage(String imageDirection) {
		try { //Se tiene que utilizar un try catch para usar ImageIO
			return ImageIO.read(getClass().getResource(imageDirection));
		} catch (IOException e) {
			System.out.println("Error al cargar imagen");
			return null;
		}
	}

	public void changeImage(boolean advance){
		//Image 1 middle, image 2 right, image 3 left
		if(advance){
			if(currentImag==1){
				this.catapulta = getImage("catapultRight.png");
				this.currentImag=2;
			}else if (currentImag==3){
				this.catapulta = getImage("catapultMiddle.png");
				this.currentImag=1;
			}
		}else if (!advance)
			if(currentImag==1){
				this.catapulta = getImage("catapultLeft.png");
				this.currentImag=3;
			}else if (currentImag==2){
				this.catapulta = getImage("catapultMiddle.png");
				this.currentImag=1;
			}
	}

  public void mover(KeyEvent e){

  }

	public void testPress(int x, int y){
			System.out.println("CLICK");
			Bullets aux =  (Bullets) ShapeFactory.getInstance().createShape(5);
				aux.setClick(x, y);
				aux.setVel(10);
				aux.setPosition(470, 450);
				aux.setBounds(470, 450);
				bullets.add(aux);
	}
}
