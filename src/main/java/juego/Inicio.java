
package juego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Inicio extends JPanel implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;
	private int anchoJuego;
	private int largoJuego;
	private ElementoBasico portada;
	private Sonidos sonidos;


	public Inicio(int anchoJuego, int largoJuego) {
		this.anchoJuego = anchoJuego;
		this.largoJuego = largoJuego;
		this.portada = new Portada(0,0, 0, 0, 600, 640,null);
		cargarSonidos();
		sonidos.tocarSonido("polca");
	}
	protected void paintComponent(Graphics g) {
		portada.dibujarse(g);
	}
	private void cargarSonidos() {
		try {
			sonidos = new Sonidos();
			sonidos.agregarSonido("polca", "sonidos/polca.wav");
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}	
	public Dimension getPreferredSize() {
		return new Dimension(anchoJuego, largoJuego);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


}

