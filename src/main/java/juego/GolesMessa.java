package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GolesMessa {
	// Implemetancion de goles hechos por el jugador

		private int posicionX;
		private int posicionY;
		private Font font;
		private Color color;
		private int golesAFavor = 0;
	
		public GolesMessa(int posicionX, int posicionY, Font font, Color color, int golesAFavor) {
			this.posicionX = posicionX;
			this.posicionY = posicionY;
			this.font = font;
			this.color = color;
			this.golesAFavor = golesAFavor;
		}

		public void dibujarse(Graphics g) {
			g.setColor(color);
			g.setFont(font);
			g.drawString("ARGENTINA: " + String.valueOf(golesAFavor), posicionX = 25, posicionY = 630);
		}
	
	
		public void golAFavor() {
			golesAFavor++;
		}
	
		public int getGolesAFavor() {
			return golesAFavor;	
		}
	}
