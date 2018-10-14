/*

Autores: Alejandro Gleason Antonio Vázquez Y Angelica Güemes
Esta clase se basa en el uso de threads y el ciclo de animación, dibuja un canvas de x círculos de diferentes tamaños y colores.

*/

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

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

public class GamePanel extends JPanel implements Runnable { //Runnable debe tener el método Run
	//DECLARACION DE INSTANCIAS.-.-.-.--.-.-.-.-.-.-.-..-
	private Thread animator; //Controla la animación general sobre un thread
	//Pantalla y graficos y juego
	private static final int PWIDTH = 1100;//Tamaño del canvas
	private static final int PHEIGHT = 688;
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	private volatile boolean loading = false;
  private Image dbImage = null;
	private Graphics dbg;
	private GameStateContext gc=new GameStateContext();
	private GameStateRunning gr=new GameStateRunning(gc);
  private Image img;

	public GamePanel(){
		img = Toolkit.getDefaultToolkit().createImage("background.jpg");
		setBackground(Color.white);
		setPreferredSize(new Dimension(PWIDTH,PHEIGHT));
		setFocusable(true);
		requestFocus();
		readyForTermination();
		addMouseListener(new MouseAdapter(){
	    public void mousePressed(MouseEvent e){
	      gr.testPress(e.getX(),e.getY()); }
	  });

	}//GamePanel() cierra

	public void addNotify(){
		super.addNotify();//Acceso con super
		startGame();
	}//addNotify() cierra

	private void startGame(){
		ExecutorService application = Executors.newCachedThreadPool();
		application.execute(this);
		application.shutdown();
		/*if(animator == null || !running){
			animator = new Thread(this);
			animator.start();
		}*/
	}//startGame() cierra

	public void stopGame(){
		running = false;
	}//stopGame() cierra, runner está a cargo de que corra

	public void run(){//Run hace el update, render y pintar la pantalla
		running = true;
		while(running){//Mientras que running sea verdadero
			//El ciclo de animacion se compone de update, render y sleep
			gameUpdate();
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
		if( !gameOver){
			gr.update();
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
		}dbg.drawImage(img, 0, 0, null);//Background image is created
		gr.render(dbg);
	}//gameRender()

	//REVISAR
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
			Toolkit.getDefaultToolkit().sync( ); // sincroniza la pantalla por posibles errores en LINUX
			g.dispose();
		}catch (Exception e){
			System.out.println("Graphics error" + e);
		}
	}

	private void gameOverMessage(){
		Graphics g;
		g = this.getGraphics();
		g.drawString("GameOver", 10, 10);
	}


	//Metodo que observa teclas del teclado para terminar el programa de escape
	private void readyForTermination(){
		System.out.println("tecla");
		addKeyListener( new KeyAdapter(){//Clase anonima para leer las teclas
			public void keyPressed(KeyEvent e){//Sobrecarga para leer teclas
				int keyCode = e.getKeyCode();
				gr.evaluateKey(keyCode);
				//int keyCode = e.getKeyCode(); //Se convierte la tecla a valor entero ASCII
				if((keyCode == KeyEvent.VK_ESCAPE)||( keyCode == KeyEvent.VK_Q)||(keyCode == KeyEvent.VK_END)||((keyCode == KeyEvent.VK_C) && e.isControlDown()) ){
					stopGame();
				}
				//evaluateKey(keyCode);//Se llamas al metodo evaluateKey para cambiar las imagenes*/
			}
		});
	}


	private void testPress(int x, int y){
		if(!gameOver ){
			//System.out.println("CLICK");
			//bullets.add(new Bullets(470, 450, currentImag, 10, x, y));
		}
	}


}
