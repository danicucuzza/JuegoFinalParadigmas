package juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;



// Implemento KeyListener para poder leer en los metods keyPressed y keyReleased los codigos de tecla que apreto el usuario
// Implemento Runnable para crear un Threads que ejecute en paralelo con mi programa
public class JuegoWinniLeven extends JComponent implements KeyListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	private int anchoJuego;
	private int largoJuego;
	private int tiempoDeEsperaEntreActualizaciones;
	private ElementoBasico pelota;
	private ElementoBasico messi;
	//private ElementoBasicoJugador jugador2;
	private GolesMessi golesMessi;
	private GolesNeymar golesNeymar;
	private NombreJuegoWinniLeven nombreJuegoWinniLeven;
	private Reloj reloj;
	private ElementoBasico fondo;
	protected boolean pararJuego;
	private boolean juegoCorriendo;
	private Sonidos sonidos;


	public JuegoWinniLeven(int anchoJuego, int largoJuego, int tiempoDeEsperaEntreActualizaciones) {
		this.anchoJuego = anchoJuego;
		this.largoJuego = largoJuego;
		this.pelota = createPelota();
		this.fondo = new Fondo(0,0, 0, 0, 600, 640,null);
		this.reloj = new Reloj(100,25,new Font("Impact",8,20), Color.orange);
		this.messi = new Messi(280,580, 0, 0, 40, 40, Color.green);
		this.golesMessi = new GolesMessi(490, 25, new Font("Impact", 8, 20), Color.magenta, 0);
		this.golesNeymar = new GolesNeymar(10, 25, new Font("Impact", 8, 20), Color.magenta, 0);
		this.nombreJuegoWinniLeven = new NombreJuegoWinniLeven(200,30, new Font("Impact", 8, 30), Color.green, 3);
		this.juegoCorriendo = true;
		this.pararJuego = false;
		this.tiempoDeEsperaEntreActualizaciones = tiempoDeEsperaEntreActualizaciones;
		reloj.setPuntaje(20);
		cargarSonidos();
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(anchoJuego, largoJuego);
	}
	/*
	 * Actualizar la actualizacion y el dibujado del juego de esta forma no es
	 * recomendable dado que tendra distintas velocidades en distinto hardware Se
	 * hizo asi por simplicidad para facilitar el aprendizaje Lo recomendado es
	 * separar la parte de dibujado de la de actualizacion y usar interpolation
	 */
	@Override
	public void run() {
		while (juegoCorriendo) {
			actualizarJuego();
			dibujarJuego();
			esperar(tiempoDeEsperaEntreActualizaciones);
		}
	}
	private void cargarSonidos() {
		try {
			sonidos = new Sonidos();
			sonidos.agregarSonido("polca", "sonidos/polca.wav");
			sonidos.agregarSonido("salto", "sonidos/salto.wav");
			sonidos.agregarSonido("diamante", "sonidos/diamante.wav");
			sonidos.agregarSonido("muerte", "sonidos/muerte.wav");
			sonidos.agregarSonido("rescate", "sonidos/rescate.wav");
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// derecha 
		if (arg0.getKeyCode() == 39) {
			jugador1.setPosicionX(jugador1.getPosicionX()+30);
			sonidos.tocarSonido("salto");

		}   
		//  izquierda 
		if (arg0.getKeyCode() == 37) {
			jugador1.setPosicionX(jugador1.getPosicionX()-30);
			sonidos.tocarSonido("salto");
		}
		//	arriba
		if (arg0.getKeyCode() == 38) {
			jugador1.setPosicionY(jugador1.getPosicionY()-30);
			sonidos.tocarSonido("salto");
		}
		//	abajo
			if (arg0.getKeyCode() == 40) {
				jugador1.setPosicionY(jugador1.getPosicionY()+30);
			sonidos.tocarSonido("salto");
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	protected void paintComponent(Graphics g) {
		limpiarPantalla(g);
		// si el juego no esta parado entonces dibujar todos los elementos y los enemigos
		if (!pararJuego) {
			fondo.dibujarse(g);
			jugador1.dibujarse(g);
			//jugador2.dibujarse(g);
			reloj.dibujarse(g);
			pelota.dibujarse(g);
			golesJugador1.dibujarse(g);
			golesJugador2.dibujarse(g);
			nombreJuegoWinniLeven.dibujarse(g);
		} else {
			// si el juego esta parado entonces dibujar el fin del juego y cambiar el atributo juegoCorriendo a false
			dibujarFinJuego(g);
			juegoCorriendo = false;
		}
		if (pararJuego2) {
			// si el juego esta parado entonces dibujar el fin del juego y cambiar el atributo juegoCorriendo a false
			dibujarFinJuego2(g);{
				juegoCorriendo = false;
			}
		}
	}
	// En este metodo se actualiza el estado de todos los elementos del juego
	private void actualizarJuego() {
		verificarEstadoAmbiente();
		pelota.moverse();
		jugador1.moverse();
		//jugador2.moverse();
	}
	private void dibujarJuego() {
		this.repaint();
	}
	
	/*
	 * public void agregarJugador2(Jugador2 jugador) { this.Jugador2.add(jugador);
	 * 
	 * }
	 */
	
	public void agregarJugador1(Messi jugador) {
		this.Jugador1.add(jugador);
	}
	
	private ElementoBasico createPelota() {
        return new Pelota(anchoJuego / 2, largoJuego - 50, 1, -1, 40, 40, Color.white);
    }
	
	private void mostrarMensaje(Graphics g, String mensaje, int x, int y) {
		this.limpiarPantalla(g);
		g.setColor(Color.magenta);
		g.setFont(new Font("Impact", 8, 30));
		g.drawString(mensaje, x, y);
	}
	// dibujar el fin del juego
	private void dibujarFinJuego(Graphics g) {
		mostrarMensaje(g, "TE GOLEARON PA ",230, 250);
	}
	private void dibujarFinJuego2(Graphics g) {
		mostrarMensaje(g, "GOLEASTE A NEY",25,250);
	}

	// En este metodo verifico las colisiones, los rebotes de la pelota contra las paredes, la colision entre enemigos y el fin de juego
	private void verificarEstadoAmbiente() {
		
		//Gol jugador2
		verificarGolEnContra();  
        
        //Gol jugador1
		verificarGolAFavor();
        
        //Rebote de la pelota con los jugadores
		verificarReboteEntrePelotaYJugador1();
		verificarReboteEntrePelotaYJugador2();
		
		verificarFinDeJuego();
		verificarCollisionJugadorParedes();
		verificarRebotePelotaContraParedLateral();
	}
	
	private void verificarRebotePelotaContraParedLateral() {
        if (pelota.getPosicionX() <= 0 || pelota.getPosicionX() + pelota.getAncho() >= anchoJuego) {
            pelota.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
        }
    }
	
	private void verificarGolAFavor() {
		if (pelota.getPosicionY() <= 0){
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
            jugador1.setPosicionY(580);
			jugador1.setPosicionX(280);
			//jugador2.setPosicionY(280);
			//jugador2.setPosicionX(580);
			golesJugador1.golAFavor();
        }
    }
	
	private void verificarGolEnContra() {
		if (pelota.getPosicionY() + pelota.getLargo() >= largoJuego){
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
            jugador1.setPosicionY(580);
			jugador1.setPosicionX(280);
			//jugador2.setPosicionY(280);
			//jugador2.setPosicionX(580);
			golesJugador2.golEnContra();
        }
    }
	
	private void verificarReboteEntrePelotaYJugador1() {
        if (jugador1.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
        }
    }
	
	private void verificarReboteEntrePelotaYJugador2 () {
		if (jugador1.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
        }
    }
	
	// metodo para limpiar la pantalla
	private void limpiarPantalla(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, anchoJuego, largoJuego);
	}
	// metodo para esperar una cantidad de milisegundos
	private void esperar(int milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	private void verificarCollisionJugadorParedes() {
		if(jugador1.getPosicionX()>=600||jugador1.getPosicionX()<=0||jugador1.getPosicionY()>=640||jugador1.getPosicionY()<=40) {
			golesJugador2.golEnContra();
			jugador1.setPosicionY(580);
			jugador1.setPosicionX(280);
			sonidos.tocarSonido("muerte");
		}
	}
	
	private void verificarFinDeJuego() {
	if (golesJugador1.getGolesAFavor() == 3) {
		pararJuego2 = true;
	}
	if (golesJugador2.getGolesEnContra() == 3) {
		pararJuego = true;
	}
	if (reloj.getPuntaje()== 0) {
		pararJuego = true;
	}
	}
}