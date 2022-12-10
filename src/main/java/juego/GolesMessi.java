package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GolesMessi {
	// Implemetancion de goles hechos por el jugador

		private int posicionX;
		private int posicionY;
		private Font font;
		private Color color;
		private int golesAFavor;
	
		public GolesMessi(int posicionX, int posicionY, Font font, Color color, int golesAFavor) {
			this.posicionX = posicionX;
			this.posicionY = posicionY;
			this.font = font;
			this.color = color;
			this.golesAFavor = golesAFavor;
		}

		public void dibujarse(Graphics g) {
			g.setColor(color);
			g.setFont(font);
			g.drawString("GOLES:   " + String.valueOf(golesAFavor), posicionX, posicionY);
		}
	
	
		public void golAFavor() {
			golesAFavor++;
		}
	
		public int getGolesAFavor() {
			return golesAFavor;	
		}
	}
