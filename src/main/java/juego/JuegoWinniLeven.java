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

import javax.swing.JPanel;


// Implemento KeyListener para poder leer en los metods keyPressed y keyReleased los codigos de tecla que apreto el usuario
// Implemento Runnable para crear un Threads que ejecute en paralelo con mi programa
public class JuegoWinniLeven extends JPanel implements KeyListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	
    private final static int PANTALLA_INICIO = 1;
    private final static int PANTALLA_JUEGO = 2;
    private final static int PANTALLA_PERDEDOR = 3;
    private final static int PANTALLA_GANADOR = 4;
	private int anchoJuego;
	private int largoJuego;
	private int tiempoDeEsperaEntreActualizaciones;
	private ElementoBasico pelota;
	private ElementoBasico messa;
	private ElementoBasico neymor;
	private ElementoBasico cancha;
	private GolesMessa golesMessa;
	private GolesNeymor golesNeymor;
	private Sonidos sonidos;
	private Reloj reloj;
	private int pantallaActual;
    private PantallaImagen portada;
    private PantallaImagen pantallaGanador;
    private PantallaImagen pantallaEsperar;
    private PantallaImagen pantallaPerdedor;

	public JuegoWinniLeven(int anchoJuego, int largoJuego, int tiempoDeEsperaEntreActualizaciones) {
		this.pantallaActual = PANTALLA_INICIO;
		this.anchoJuego = anchoJuego;
		this.largoJuego = largoJuego;
		this.pelota = createPelotaLocal();
		this.cancha = new Cancha(0,0, 0, 0, 600, 640,null);
		this.reloj = new Reloj(25 ,630,new Font("Impact",8,20), Color.black);
		this.messa = new Messa(280, 580, 0, 0, 40, 40, Color.blue);
		this.neymor = new Neymor(280, 25, 0, 0, 40, 40, Color.yellow);
		this.golesMessa = new GolesMessa(25 ,630, new Font("Impact", 8, 20), Color.black, 0);
		this.golesNeymor = new GolesNeymor(630, 25, new Font("Impact", 8, 20), Color.black, 0);
//		this.nombreJuegoWinniLeven = new NombreJuegoWinniLeven(200,30, new Font("Impact", 8, 30), Color.green, 3);
        this.portada = new PantallaImagen(anchoJuego, largoJuego, "imagenes/portada.png");
        this.pantallaGanador = new PantallaImagen(anchoJuego, largoJuego, "imagenes/ganaste.png");
        this.pantallaEsperar = new PantallaImagen(anchoJuego, largoJuego, "imagenes/esperar.png");
        this.pantallaPerdedor = new PantallaImagen(anchoJuego, largoJuego, "imagenes/perdiste.png");
		this.tiempoDeEsperaEntreActualizaciones = tiempoDeEsperaEntreActualizaciones;
		cargarSonidos();
		inicializarJuego();
	}
	
    private void inicializarJuego() {
        this.pantallaPerdedor = null;
        this.golesMessa = new GolesMessa(10, 20, new Font("Arial", 8, 20), Color.blue, 0);
        this.golesNeymor = new GolesNeymor(10, 45, new Font("Arial", 8, 20), Color.blue, 0);  
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
        while (true) {
            if (pantallaActual == PANTALLA_JUEGO) {
                actualizarJuego();
            }
            dibujarJuego();
            esperar(tiempoDeEsperaEntreActualizaciones);
        }
    }
	
//	Carga Sonidos del resource
	private void cargarSonidos() {
		try {
			sonidos = new Sonidos();
			sonidos.agregarSonido("cancha", "sonidos/cancha.wav");
			sonidos.agregarSonido("salto", "sonidos/salto.wav");
			sonidos.agregarSonido("diamante", "sonidos/diamante.wav");
			sonidos.agregarSonido("muerte", "sonidos/muerte.wav");
			sonidos.agregarSonido("rescate", "sonidos/rescate.wav");
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	public void keyPressed(KeyEvent arg0) {
        if (pantallaActual == PANTALLA_INICIO) {
            inicializarJuego();
            pantallaActual = PANTALLA_JUEGO;
        }

        if (pantallaActual == PANTALLA_PERDEDOR || pantallaActual == PANTALLA_GANADOR) {
            pantallaActual = PANTALLA_INICIO;
        }
    }

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		//CONTROLES PLAYER 1 (messa) = W.A.S.D.
		//derecha 
		if (arg0.getKeyCode() == KeyEvent.VK_D) {
			messa.setPosicionX(messa.getPosicionX()+30);
			sonidos.tocarSonido("salto");
		}//izquierda
		if (arg0.getKeyCode() == KeyEvent.VK_A) {
			messa.setPosicionX(messa.getPosicionX()-30);
			sonidos.tocarSonido("salto");
		}//arriba
		if (arg0.getKeyCode() == KeyEvent.VK_W) {
			messa.setPosicionY(messa.getPosicionY()-30);
			sonidos.tocarSonido("salto");
		}//abajo
		if (arg0.getKeyCode() == KeyEvent.VK_S) {
			messa.setPosicionY(messa.getPosicionY()+30);
			sonidos.tocarSonido("salto");
		}
		
		//CONTROLES PLAYER 2 (neymor) U.H.J.K.
		//derecha = K
		if (arg0.getKeyCode() == KeyEvent.VK_K) {
			neymor.setPosicionX(neymor.getPosicionX()+30);
			sonidos.tocarSonido("salto");
	    }//izquierda = H
		if (arg0.getKeyCode() == KeyEvent.VK_H) {
			neymor.setPosicionX(neymor.getPosicionX()-30);
			sonidos.tocarSonido("salto");
		}//arriba = U
		if (arg0.getKeyCode() == KeyEvent.VK_U) {
			neymor.setPosicionY(neymor.getPosicionY()-30);
			sonidos.tocarSonido("salto");
		}//abajo = J
		if (arg0.getKeyCode() == KeyEvent.VK_J) {
			neymor.setPosicionY(neymor.getPosicionY()+30);
			sonidos.tocarSonido("salto");
		}		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	protected void paintComponent(Graphics g) {
		
		this.limpiarPantalla(g);
        if (pantallaActual == PANTALLA_INICIO) {
        	dibujarInicioJuego(g);
        }
        if (pantallaActual == PANTALLA_PERDEDOR) {
            pantallaPerdedor.dibujarse(g);
//            mostrarMensaje(g, "TE GOLEARON PA ",25, largoJuego / 2);
        }
        if (pantallaActual == PANTALLA_GANADOR) {
            pantallaGanador.dibujarse(g);
//            mostrarMensaje(g,"GOLEASTE A NEY            SO UN CRA", 25, largoJuego / 2);
        }
        if (pantallaActual == PANTALLA_JUEGO) {
        	cancha.dibujarse(g);
        	pelota.dibujarse(g);
            messa.dibujarse(g);
            neymor.dibujarse(g);
            golesMessa.dibujarse(g);
            golesNeymor.dibujarse(g);
            reloj.dibujarse(g);      
        }
    }
	
    private void dibujarInicioJuego(Graphics g) {
        portada.dibujarse(g);
    }

	// En este metodo se actualiza el estado de todos los elementos del juego
	private void actualizarJuego() {
		verificarEstadoAmbiente();
		pelota.moverse();
		messa.moverse();
		neymor.moverse();
	}
	private void dibujarJuego() {
		this.repaint();
	}
	
	private ElementoBasico createPelotaLocal() {
        return new Pelota(anchoJuego / 2, 580, 1, -1, 40, 40, Color.white);
    }
	
	private ElementoBasico createPelotaVisitante() {
        return new Pelota(anchoJuego / 2, 25, -1, 1, 40, 40, Color.white);
    }
	
	private void mostrarMensaje(Graphics g, String mensaje, int x, int y) {
		this.limpiarPantalla(g);
		g.setColor(Color.magenta);
		g.setFont(new Font("Impact", 8, 30));
		g.drawString(mensaje, x, y);
	}

	// En este metodo verifico las colisiones, los rebotes de la pelota contra las paredes, la colision entre enemigos y el fin de juego
	private void verificarEstadoAmbiente() {
		verificarGolMessa();
		verificarGolNeymor(); 
		verificarReboteEntrePelotaYMessa();
		verificarReboteEntrePelotaYNeymor();
		verificarFinDeJuego();
		verificarRebotePelotaContraParedLateral();
	}
	
	private void verificarRebotePelotaContraParedLateral() {
        if (pelota.getPosicionX() <= 0 || pelota.getPosicionX() + pelota.getAncho() >= anchoJuego) {
            pelota.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
        }
    }
	
	private void verificarGolMessa() {
		if (pelota.getPosicionY() <= 0){
			golesMessa.golAFavor();
			esperar(2000);
            sonidos.tocarSonido("toc");
            pantallaEsperar.dibujarse(this.getGraphics());    
            pelota = createPelotaVisitante();
            messa.setPosicionY(580);
			messa.setPosicionX(280);
			neymor.setPosicionX(280);
			neymor.setPosicionY(25);
        }
    }
	
	private void verificarGolNeymor() {
		if (pelota.getPosicionY() + pelota.getLargo() >= largoJuego){
            golesNeymor.golEnContra();
            esperar(2000);
            sonidos.tocarSonido("toc");
            pantallaEsperar.dibujarse(this.getGraphics());
            pelota = createPelotaLocal();
            messa.setPosicionY(580);
			messa.setPosicionX(280);
			neymor.setPosicionX(280);
			neymor.setPosicionY(25);
        }
    }
	
	private void verificarReboteEntrePelotaYMessa() {
        if (messa.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
            pelota.setVelocidadY(pelota.getVelocidadY()-2);
        }
    }
	
	private void verificarReboteEntrePelotaYNeymor() {
		if (neymor.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
            pelota.setVelocidadY(pelota.getVelocidadY()+2);
        }
    }
	
	// metodo para limpiar la pantalla
	private void limpiarPantalla(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, anchoJuego, largoJuego);
	}
	
	// metodo para esperar una cantidad de milisegundos
	private void esperar(int milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	//Metodo para verificar si termino el partido
	private void verificarFinDeJuego() {
	if (golesMessa.getGolesAFavor() == 3) {
        pantallaActual = PANTALLA_GANADOR;
	}
	if (golesNeymor.getGolesEnContra() == 3) {
		pantallaActual = PANTALLA_PERDEDOR;
	}
}
}