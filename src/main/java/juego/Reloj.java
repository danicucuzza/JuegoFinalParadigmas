package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Reloj {
	// Implementacion del puntaje del juego

	private int posicionX;
	private int posicionY;
	private Font font;
	private Color color;
	private int tiempo = 100;

	public Reloj(int posicionX, int posicionY, Font font, Color color) {
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.font = font;
		this.color = color;
	}
	public void dibujarse(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.drawString("TIME: " + String.valueOf(tiempo), posicionX = 400, posicionY = 25);
	}
}