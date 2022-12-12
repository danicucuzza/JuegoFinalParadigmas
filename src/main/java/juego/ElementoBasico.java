package juego;

import java.awt.Color;
import java.awt.Graphics;


public abstract class ElementoBasico implements Elemento {
	private double posicionX;
	private double posicionY;
	private double velocidadX;
	private double velocidadY;
	private int ancho;
	private int largo;
	private Color color;

	public ElementoBasico (int posicionX, int posicionY, double velocidadX, double velocidadY, int ancho, int largo, Color color) {
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.ancho = ancho;
		this.largo = largo;
		this.color = color;
	}
	public abstract void dibujarse(Graphics g);
	// devuelve true si hay colision entre este elemento y el elemento que le pasamos como parametro
	public boolean hayColision(Elemento elemento) {
		if (this.getPosicionX() < elemento.getPosicionX() + elemento.getAncho() &&
				this.getPosicionX() + this.getAncho() > elemento.getPosicionX() &&
				this.getPosicionY() < elemento.getPosicionY() + elemento.getLargo() &&
				this.getLargo() + this.getPosicionY() > elemento.getPosicionY()) {
			return true;
		}
		return false;
	}

	public void moverse() {
		posicionX = posicionX + velocidadX;
		posicionY = posicionY + velocidadY;
	}

	public void rebotarEnEjeX() {
		velocidadX = -velocidadX;
	}

	public void rebotarEnEjeY() {
		velocidadY = -velocidadY;
	}

	public double getVelocidadX() {
		return velocidadX;
	}

	public void setVelocidadX(double velocidadX) {
		this.velocidadX = velocidadX;
	}

	public double getVelocidadY() {
		return velocidadY;
	}

	public void setVelocidadY(double velocidadY) {
		this.velocidadY = velocidadY;
	}

	public int getPosicionX() {
		return (int) posicionX;
	}

	public int getPosicionY() {
		return (int) posicionY;
	}
	
	public int moverPosicionX() {
		return (int) (posicionX+100);
	}
	
	public int moverPosicionY() {
		return (int) (posicionY+100);
	}

	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}

	public int getAncho() {
		return ancho;
	}

	public int getLargo() {
		return largo;
	}

	public Color getColor() {
		return color;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setLargo(int largo) {
		this.largo = largo;
	}

	public void setColor(Color color) {
		this.color = color;
	}


}


