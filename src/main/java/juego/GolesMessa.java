package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GolesMessa {
	private Font font;
		private Color color;
		private int golesAFavor = 0;
	
		public GolesMessa(int posicionX, int posicionY, Font font, Color color, int golesAFavor) {
			this.font = font;
			this.color = color;
			this.golesAFavor = golesAFavor;
		}

		public void dibujarse(Graphics g) {
			g.setColor(color.CYAN);
			g.setFont(font);
			g.drawString("ARGENTINA: " + String.valueOf(golesAFavor), 25, 25);
		}
	
	
		public void golAFavor() {
			golesAFavor++;
		}
	
		public int getGolesAFavor() {
			return golesAFavor;	
		}
	}
