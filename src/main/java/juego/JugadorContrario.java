package juego;

import java.awt.Color;
import java.awt.Graphics;

// Clase abstracta para enemigo, implementa ElementoBasico
public abstract class JugadorContrario {

	public JugadorContrario(int posicionX, int posicionY, double velocidadX, double velocidadY, int ancho, int largo, Color color) {
		super();
	}

	// Todos los enemigos deben implementar el metodo destruirse
	public abstract void destruirse(Graphics graphics);

}


