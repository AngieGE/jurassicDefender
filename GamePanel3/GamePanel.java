/*

Autores: Alejandro Gleason Antonio Vázquez Y Angelica Güemes
Esta clase se basa en el uso de threads y el ciclo de animación, dibuja un canvas de x círculos de diferentes tamaños y colores.

*/

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

public class GamePanel extends JPanel implements Runnable { //Runnable debe tener el método Run

	//DECLARACION DE INSTANCIAS.-.-.-.--.-.-.-.-.-.-.-..-
	private Thread animator; //Controla la animación general sobre un thread

	//Pantalla y graficos y juego
	private static final int PWIDTH = 1100;//Tamaño del canvas
	private static final int PHEIGHT = 688;
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	private volatile boolean isPaused = false;
	private Image img;
	private Graphics dbg, cat1;
	private Image dbImage = null;
	private int vidas = 4;

	//Dino
	//public Circle dino; //Guarda la relacion ente clase Circle y GamePanel

	//Enemies
	ArrayList<Meteorito> enemies = new ArrayList<Meteorito>(); //Arreglo con todos los enemigos

	//Weapon
	ArrayList<Bullets> bullets = new ArrayList<Bullets>(); //Arreglo con todas las balas
	private int currentImag=1; //variable que almacena el numero de la imagen actual
	private BufferedImage catapulta = getImage("catapultMiddle.png");; //la imagen de la catapulta mostrado en la pantalla
	private BufferedImage catapulta2 = getImage("catapultMiddle.png");; //la imagen de la catapulta mostrado en la pantalla
	private BufferedImage vida1 = getImage("life.png");; //la imagen de la catapulta mostrado en la pantalla
	private BufferedImage vida2 = getImage("life.png");; //la imagen de la catapulta mostrado en la pantalla
	private BufferedImage vida3 = getImage("life.png");; //la imagen de la catapulta mostrado en la pantalla
	private BufferedImage vida4 = getImage("life.png");; //la imagen de la catapulta mostrado en la pantalla
	private Dinosaur mydino;

	public GamePanel(){
		img = Toolkit.getDefaultToolkit().createImage("background.jpg");
		setBackground(Color.white);
		setPreferredSize(new Dimension(PWIDTH,PHEIGHT));
		setFocusable(true);
		requestFocus();
		readyForTermination();
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				testPress(e.getX(),e.getY()); }
		});
		//dino = new Circle(0,520);
		mydino =new Dinosaur(5);
	}//GamePanel() cierra

	public void addNotify(){
		super.addNotify();//Acceso con super
		startGame();
	}//addNotify() cierra

	private void startGame(){
		if(animator == null || !running){
			animator = new Thread(this);
			animator.start();
		}
	}//startGame() cierra

	public void stopGame(){
		running = false;
	}//stopGame() cierra, runner está a cargo de que corra

	public void run(){//Run hace el update, render y pintar la pantalla
		running = true;
		while(running){//Mientras que running sea verdadero
			//El ciclo de animacion se compone de update, render y sleep
			gameUpdate();
			gameRender();
			paintScreen();
			try{
				Thread.sleep(20);//tiempo en milisegundos
			}catch(InterruptedException ex){}
		}
		System.exit(0);
	}//run() cierra

	private void gameUpdate(){//Cuando la tecla se presiona se hace el Update
		if(!isPaused && !gameOver){
			//dino.move();
			//dino.setX(5);

			//Avanzan los enemigos
			for (int i=0; i< enemies.size()-1; i++){
				enemies.get(i).move();
			}
			reduceLife();
			//Se crean enemigos y se eliminan los que ya salieron de la pantalla
			createEnemy(5);
			mydino.setX(5);
			//Avanzan las balas
			for (int i=0; i< bullets.size()-1; i++){
				bullets.get(i).move2(currentImag);
			}
			//Se eliminann las baas que ya salieron de la pantalla
			deleteBullets();

			//Revisa colision  meteorito bala
			for (int i=0; i< enemies.size()-1; i++){
				for (int j=0; j< bullets.size()-1; j++)
					if (enemies.get(i).collision(bullets.get(j).getRectangle())){
						System.out.println("COLLISION");
						enemies.remove(enemies.get(i));
				 	}
			}

		}
	}//gameUpdate() closes

	private void gameRender(){
		if(dbImage == null){
			dbImage = createImage(PWIDTH,PHEIGHT);//Si no hay imagen se crea
			if(dbImage == null){
				System.out.println("dbImage is null");
				return; //exit
			}else{
				dbg = dbImage.getGraphics();
				//cat1 = dbImage.getGraphics(); //No borrar plis
			}
		}
		dbg.drawImage(img, 0, 0, null);//Background image is created
		//dino.draw(dbg);//Se dibuja un circulo que simula ser el dinosaurio
		mydino.draw(dbg);
		dbg.drawImage(catapulta,400, 400, null);//dibuja la catapulta en la pantalla
		dbg.drawImage(vida1, 350, 605, null);
		dbg.drawImage(vida2, 450, 605, null);
		dbg.drawImage(vida3, 550, 605, null);
		dbg.drawImage(vida4, 650, 605, null);

		//cat1.drawImage(catapulta2,100, 400, null); // No borrar plis

		//Print bullets
		for (int i=0; i< bullets.size()-1; i++){
				bullets.get(i).draw(dbg);
		}

		//Print enemies
		for (int i=0; i< enemies.size()-1; i++){
			enemies.get(i).draw(dbg);
		}

	}//gameRender()

	public void paintComponent(Graphics g){ //background
		super.paintComponent(g);
		if(dbImage != null)
			g.drawImage(dbImage, 0, 0, null);
	}

	private void paintScreen(){
		Graphics g;
		try{
			g=this.getGraphics();
			if((g!=null)&&dbImage!=null){
				g.drawImage(dbImage,0,0,null);
			}
			Toolkit.getDefaultToolkit( ).sync( ); // sincroniza la pantalla por posibles errores en LINUX
			g.dispose();
		}catch (Exception e){
			System.out.println("Graphics error" + e);
		}
	}

	public void createEnemy(int min){

		//Se eliminan los enemigos que esten fuera de pantalla
		for (int i=0; i< enemies.size()-1; i++){
			if(enemies.get(i).getY() > PHEIGHT){
				enemies.remove(i); //System.out.println("Se elimina un enemigo" + enemies.get(i).getY());
			}
		}

		//Se crea un nuevo enemigo si hay menos de min
		if(enemies.size() <min){
			enemies.add(new Meteorito(8 ));
		}
	}

	public void deleteBullets(){
		//Se eliminan las bullets que esten fuera de pantalla
		for (int i=0; i< bullets.size()-1; i++){
			if( (bullets.get(i).getY() < 0)|| (bullets.get(i).getX() > PWIDTH) || (bullets.get(i).getX() < 0) ){
				bullets.remove(i);
				//System.out.println("Bye en pos y= " + bullets.get(i).getRectangle().y + " x= "+bullets.get(i).getRectangle().x);
				//System.out.println(bullets.size());
			}
		}
	}

	public void reduceLife(){
			for (int i=0; i< enemies.size()-1; i++){
				//System.out.println("Coordenadas en y son " + enemies.get(i).getRectangle().y);
				if( enemies.get(i).getY() == 520 ){
				//System.out.println("Vidas restantes: " + vidas);
				vidas--;
				if(vidas <= 0){
					JOptionPane.showMessageDialog(null, "Perdiste tus 4 vidas :(");
					System.exit(0);
				}
			}
		}
	}

	private void gameOverMessage(){
		Graphics g;
		g = this.getGraphics();
		g.drawString("GameOver", 10, 10);
	}

	//Metodo que observa teclas del teclado para terminar el programa de escape
	private void readyForTermination(){
		addKeyListener( new KeyAdapter(){//Clase anonima para leer las teclas
			public void keyPressed(KeyEvent e){//Sobrecarga para leer teclas
				int keyCode = e.getKeyCode(); //Se convierte la tecla a valor entero ASCII
				if((keyCode == KeyEvent.VK_ESCAPE)||(keyCode == KeyEvent.VK_Q)||(keyCode == KeyEvent.VK_END)||((keyCode == KeyEvent.VK_C) && e.isControlDown()) ){
					stopGame();
				}
				evaluateKey(keyCode);//Se llama al metodo evaluateKey para cambiar las imagenes
				//si el jugador selecciona la tecla de izquierda o derecha
			}
		});
	}

	//metodo que llama el metodo de cambio de imagen si se selleciona izquierda o derecha
	private void evaluateKey(int k){
		/*switch para la teclas(si es a la derecha avanza y si es ala izquierda retrocede la secuencia*/
		switch(k) {
			case KeyEvent.VK_RIGHT  :
				changeImage(true);
				break; //optional
			case KeyEvent.VK_LEFT  :
				changeImage(false);
				break;
			case KeyEvent.VK_SPACE  :
				bullets.add(new Bullets(450, 450, currentImag, 10));
				break;
		}
	}

	private void testPress(int x, int y){
		if(!gameOver && !isPaused){

			//Whatever
		}
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

	//Metodo que cambia la imagen cargada sin importar si se lecciona izquierda o derecha
    //de manera secuencial (primero el perico, luego el perro y luego el gato)
    private void changeImage(boolean advance){
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
}
