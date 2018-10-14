/*Esta clase representa el contexto actual del estado del juego*/

import java.awt.*;
import javax.swing.*;

public class GameStateContext {

	private GameStateRunning run;
	private GameStateGameOver over;
	private GameStateLoading loading;
	private GameState currentState;

	/*Contructor*/
	public GameStateContext(){
		run = new GameStateRunning(this);
		over = new GameStateGameOver(this);
		loading = new GameStateLoading(this);
		currentState = run;
	}

	public GameState getRun(){
		return run;
	}

	public GameState getGameOver(){
		return over;
	}

	public GameState getLoading(){
		return loading;
	}

	public void setCurrentState(GameState state){
		currentState = state;
	}

	public void end(){
		currentState.end();
	}

	public void update(){
		currentState.update();
	}

	public void render(Graphics g){
		currentState.render(g);
	}
}
