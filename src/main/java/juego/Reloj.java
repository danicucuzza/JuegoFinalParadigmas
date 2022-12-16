package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class Reloj{
	// Implementacion del puntaje del juego

	private int posicionX;
	private int posicionY;
	private Font font;
	private Color color;
	private int reloj;
	
	Timer timer = new Timer ();
	TimerTask tiempo = new TimerTask() {
		public void run () {
			reloj--;
		}
	};
	
	public Reloj(int posicionX, int posicionY, Font font, Color color) {
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.font = font;
		this.color = color;
		timer.schedule(tiempo, 0, 1000);
	}
	
	public void dibujarse(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.drawString("TIME: " + String.valueOf(reloj), posicionX = 400, posicionY = 25);
	}

	public int getReloj () {
		return reloj;
	}
	
	public int relojRestart () {
		return reloj = 60;
	}
}