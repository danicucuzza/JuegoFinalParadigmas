package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GolesNeymar {
	// Clase para mostrar la cantidad de goles en contra.

	    private int posicionX;
	    private int posicionY;
	    private Font font;
	    private Color color;
	    private int golesEnContra;

	    public GolesNeymar(int posicionX, int posicionY, Font font, Color color, int golesEnContra) {
	        this.posicionX = posicionX;
	        this.posicionY = posicionY;
	        this.font = font;
	        this.color = color;
	        this.golesEnContra = golesEnContra;
	    }

	    public void dibujarse(Graphics g) {
	        g.setColor(color);
	        g.setFont(font);
	        g.drawString("GOLES EN CONTRA:   " + String.valueOf(golesEnContra), posicionX, posicionY);
	    }

	    public void golEnContra() {
	        golesEnContra++;
	    }

	    public int getGolesEnContra() {
	        return golesEnContra;
	    }
}
