package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GolesRolando {
	// Clase para mostrar la cantidad de goles en contra.

	    private Font font;
	    private Color color;
	    private int golesEnContra = 0;

	    public GolesRolando(int posicionX, int posicionY, Font font, Color color, int golesEnContra) {
	        this.font = font;
	        this.color = color;
	        this.golesEnContra = golesEnContra;
	    }

	    public void dibujarse(Graphics g) {
	        g.setColor(color.red);
	        g.setFont(font);
	        g.drawString("PORTUGAL: " + String.valueOf(golesEnContra), 200, 25);
	    }

	    public void golEnContra() {
	        golesEnContra++;
	    }

	    public int getGolesEnContra() {
	        return golesEnContra;
	    }
}
