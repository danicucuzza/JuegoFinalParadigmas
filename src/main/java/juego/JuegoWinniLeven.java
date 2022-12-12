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
	private ElementoBasico rolando;
	private ElementoBasico dabiMartino;
	private ElementoBasico alejoBecar;
	private ElementoBasico cancha;
	private ElementoBasico arcoLocal;
	private ElementoBasico arcoVisitante;
	private GolesMessa golesMessa;
	private GolesRolando golesRolando;
	private Sonidos sonidos;
	private Reloj reloj;
	private int pantallaActual;
    private PantallaImagen portada;
    private PantallaImagen pantallaGanador;
    private PantallaImagen pantallaEsperar;
    private PantallaImagen pantallaPerdedor;
    private boolean dominarPelota = true;
    private boolean buscaArco = true;

	public JuegoWinniLeven(int anchoJuego, int largoJuego, int tiempoDeEsperaEntreActualizaciones, int enemigosPorLinea, int filasDeEnemigos) {
		this.pantallaActual = PANTALLA_INICIO;
		this.anchoJuego = anchoJuego;
		this.largoJuego = largoJuego;
		this.pelota = createPelota();
		this.cancha = new Cancha(0,0, 0, 0, 650, 720, Color.green);
		this.reloj = new Reloj(25 ,630,new Font("Impact",8,20), Color.black);
		this.messa = new Messa(280, 560, 0, 0, 70, 70, Color.blue);
		this.rolando = new Rolando(280, 25, 0, 0, 50, 60, Color.yellow);
		this.dabiMartino = new ArqueroLocal(270, 580, 0, 0, 30, 30, Color.blue);
		this.alejoBecar = new ArqueroVisitante(270, 20, 0, 0, 30, 30, Color.yellow);
		this.arcoLocal = new Arco (250, 685, 0, 0, 150, 70, Color.black);
		this.arcoVisitante = new Arco (250, -35, 0, 0, 150, 70, Color.black);
		this.golesMessa = new GolesMessa(25 ,630, new Font("Impact", 8, 20), Color.black, 0);
		this.golesRolando = new GolesRolando(630, 25, new Font("Impact", 8, 20), Color.black, 0);
//		this.nombreJuegoWinniLeven = new NombreJuegoWinniLeven(200,30, new Font("Impact", 8, 30), Color.green, 3);
        this.portada = new PantallaImagen(anchoJuego, largoJuego, "imagenes/portada.png");
        this.pantallaGanador = new PantallaImagen(anchoJuego, largoJuego, "imagenes/ganaste.png");
        this.pantallaEsperar = new PantallaImagen(anchoJuego, largoJuego, "imagenes/esperar.png");
        this.pantallaPerdedor = new PantallaImagen(anchoJuego, largoJuego, "imagenes/perdiste.png");
		this.tiempoDeEsperaEntreActualizaciones = tiempoDeEsperaEntreActualizaciones;
		cargarSonidos();
		inicializarJuego();
		sonidos.tocarSonido("cancha");
	}
	
    private void inicializarJuego() {
        this.pantallaPerdedor = null;
        this.golesMessa = new GolesMessa(10, 20, new Font("Arial", 8, 20), Color.blue, 0);
        this.golesRolando = new GolesRolando(10, 45, new Font("Arial", 8, 20), Color.blue, 0);  
    }
    
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(anchoJuego, largoJuego);
	}

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
        if (pantallaActual == PANTALLA_JUEGO) {
        	if (arg0.getKeyCode() == KeyEvent.VK_D) {
                messa.setVelocidadX(2);
            }
    		if (arg0.getKeyCode() == KeyEvent.VK_A) {
                messa.setVelocidadX(-2);
            }
    		if (arg0.getKeyCode() == KeyEvent.VK_S) {
                messa.setVelocidadY(2);
            }
    		if (arg0.getKeyCode() == KeyEvent.VK_W) {
                messa.setVelocidadY(-2);
            } //patear pelota sobre eje Y jugador 1
    		if (arg0.getKeyCode() == KeyEvent.VK_J) {
    			dominarPelota = false;
    			pelotaBuscaArco();
    		} 
        }
    }
	
	public void pelotaBuscaArco() {
		if(pelota.getPosicionX() < arcoVisitante.getPosicionX()) {
			pelota.setVelocidadX(+2);
		} 
		if(pelota.getPosicionX() > arcoVisitante.getPosicionX()) {
			pelota.setVelocidadX(-2);
		}
		if (pelota.getPosicionY() < arcoVisitante.getPosicionY()) {
			pelota.setVelocidadY(+5);
		}
		if (pelota.getPosicionY() > arcoVisitante.getPosicionY()) {
			pelota.setVelocidadY(-5);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_D || arg0.getKeyCode() == KeyEvent.VK_A) {
            messa.setVelocidadX(0);
        }
		if (arg0.getKeyCode() == KeyEvent.VK_W || arg0.getKeyCode() == KeyEvent.VK_S) {
			messa.setVelocidadY(0);
		}
		
		//CONTROLES PLAYER 2 (rolando) U.H.J.K.
		//derecha = K
//		if (arg0.getKeyCode() == KeyEvent.VK_K) {
//			rolando.setPosicionX(rolando.getPosicionX()+30);
//			sonidos.tocarSonido("salto");
//	    }//izquierda = H
//		if (arg0.getKeyCode() == KeyEvent.VK_H) {
//			rolando.setPosicionX(rolando.getPosicionX()-30);
//			sonidos.tocarSonido("salto");
//		}//arriba = U
//		if (arg0.getKeyCode() == KeyEvent.VK_U) {
//			rolando.setPosicionY(rolando.getPosicionY()-30);
//			sonidos.tocarSonido("salto");
//		}//abajo = J
//		if (arg0.getKeyCode() == KeyEvent.VK_J) {
//			rolando.setPosicionY(rolando.getPosicionY()+30);
//			sonidos.tocarSonido("salto");
//		}
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
	public void enemigoDispara() { 
	}
	
	public void enemigoBuscaPelota() {
		double azar = Math.random();
		if( azar < 0.60) {
			if(rolando.getPosicionX() < pelota.getPosicionX()) {
				rolando.setVelocidadX(+1);
			}
			if(rolando.getPosicionX() > pelota.getPosicionX()) {
				rolando.setVelocidadX(-1);
			}
			if(rolando.getPosicionY() < pelota.getPosicionY()) {
				rolando.setVelocidadY(+1);
			}
			if(rolando.getPosicionY() > pelota.getPosicionY()) {
				rolando.setVelocidadY(-1);
			}
			if(dabiMartino.getPosicionX() < pelota.getPosicionX()) {
				dabiMartino.setVelocidadX(+1);
			}
			if(dabiMartino.getPosicionX() > pelota.getPosicionX()) {
				dabiMartino.setVelocidadX(-1);
			}
			if(alejoBecar.getPosicionX() < pelota.getPosicionX()) {
				alejoBecar.setVelocidadX(+1);
			}
			if(alejoBecar.getPosicionX() > pelota.getPosicionX()) {
				alejoBecar.setVelocidadX(-1);
			}
		}
		else if (azar > 0.90) {
			rolando.setVelocidadX(0);
			dabiMartino.setVelocidadX(0);
			alejoBecar.setVelocidadX(0);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		this.limpiarPantalla(g);
        if (pantallaActual == PANTALLA_INICIO) {
        	dibujarInicioJuego(g);
        }
        if (pantallaActual == PANTALLA_PERDEDOR) {
        	 if (this.pantallaPerdedor == null) {
                 this.pantallaPerdedor = new PantallaPerdedor(anchoJuego, largoJuego, "imagenes/perdiste.png");
             }
        	 pantallaPerdedor.dibujarse(g);
        }
        if (pantallaActual == PANTALLA_GANADOR) {
            pantallaGanador.dibujarse(g);
        }
        if (pantallaActual == PANTALLA_JUEGO) {
        	cancha.dibujarse(g);
        	pelota.dibujarse(g);
            messa.dibujarse(g);
            rolando.dibujarse(g);
            dabiMartino.dibujarse(g);
            alejoBecar.dibujarse(g);
            arcoLocal.dibujarse(g);
            arcoVisitante.dibujarse(g);
            golesMessa.dibujarse(g);
            golesRolando.dibujarse(g);
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
		rolando.moverse();
		dabiMartino.moverse();
		alejoBecar.moverse();
		enemigoBuscaPelota();
	}
	private void dibujarJuego() {
		this.repaint();
	}
	
	//metodo para crear la pelota
	private ElementoBasico createPelota() {
		return new Pelota(anchoJuego / 2, largoJuego / 2, 0, 0, 30, 30, Color.white);
    }
	
	//metodo para que la pelota siga al jugador 1
	private void seguirJugadorMessa() {
		pelota.setPosicionX(messa.getPosicionX());
		pelota.setPosicionY(messa.getPosicionY());
	}
	
	//metodo para que la pelota siga al jugador 2
	private void seguirJugadorRolando() {
		pelota.setPosicionX(rolando.getPosicionX());
		pelota.setPosicionY(rolando.getPosicionY());
	}
	
	private void buscaArco() {
		if(rolando.getPosicionX() < arcoLocal.getPosicionX()) {
			rolando.setVelocidadX(+1);
			}
		if(rolando.getPosicionX() > arcoLocal.getPosicionX()) {
			rolando.setVelocidadX(-1);
			}
		if(rolando.getPosicionY() < arcoLocal.getPosicionY()) {
			rolando.setVelocidadY(+1);
			}
		if(rolando.getPosicionY() > arcoLocal.getPosicionY()) {
			rolando.setVelocidadY(-1);
		}
	}
	
	
	//metodo para utilizar los seguimientos de pelota
	private void dominioDeBalon() {
		if (pelota.hayColision(messa) && dominarPelota == true) {
			seguirJugadorMessa();
			buscaArco = false;
		}
		if (pelota.hayColision(rolando) && dominarPelota == true)
			seguirJugadorRolando();
			buscaArco();
			buscaArco = true;
	}

	// En este metodo verifico las colisiones, los rebotes de la pelota contra las paredes, la colision entre enemigos y el fin de juego
	private void verificarEstadoAmbiente() {
		verificarGoles(); 
		verificarReboteEntrePelotaYMessa();
		verificarReboteEntrePelotaYRolando();
		verificarFinDeJuego();
		verificarRebotePelotaContraParedLateral();
		verificarReboteContraFondos();
	}
	
	//verificar rebotes contra las paredes
	private void verificarRebotePelotaContraParedLateral() {
        if (pelota.getPosicionX() <= 30 || pelota.getPosicionX() + pelota.getAncho() >= (anchoJuego - 50)) {
            pelota.rebotarEnEjeX();
            dominarPelota = true;
            sonidos.tocarSonido("toc");
        }
        if (messa.getPosicionX() <= 0 || messa.getPosicionX() + messa.getAncho() >= anchoJuego) {
        	messa.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
        }
        if (rolando.getPosicionX() <= 0 || rolando.getPosicionX() + rolando.getAncho() >= anchoJuego) {
        	rolando.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
        }
    }
	
	//verifica rebote contra los fondos
	private void verificarReboteContraFondos() {
        if (pelota.getPosicionY() <= 30 || pelota.getPosicionY() + pelota.getLargo() >= (largoJuego - 30)) {
            pelota.rebotarEnEjeY();
            dominarPelota = true;
            sonidos.tocarSonido("toc");
        }
        if (messa.getPosicionY() <= 0 || messa.getPosicionY() + messa.getLargo() >= largoJuego) {
        	messa.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
        }
        if (rolando.getPosicionY() <= 0 || rolando.getPosicionY() + rolando.getLargo() >= largoJuego) {
        	rolando.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
        }
    }
	
	//metodo para setear las posiciones
	private void setPosiciones () {
			messa.setPosicionY(560);
			messa.setPosicionX(280);
			rolando.setPosicionX(280);
			rolando.setPosicionY(25);
			dabiMartino.setPosicionX(580);
			dabiMartino.setPosicionX(280);
			alejoBecar.setPosicionX(280);
			alejoBecar.setPosicionY(25);
	}
	
	//metodo para verificar los goles
	private void verificarGoles() {
		if ((pelota.getPosicionX() > 240) && (pelota.getPosicionX() < 380)) {
			if (pelota.hayColision(arcoLocal)) {
				if ((pelota.getPosicionX() > 240) && (pelota.getPosicionX() < 380)) {
					golesRolando.golEnContra();
					esperar(2000);
					sonidos.tocarSonido("toc");
					pantallaEsperar.dibujarse(this.getGraphics());
					pelota = createPelota();
					setPosiciones();
					dominarPelota = true;
					}
				} 
			if (pelota.hayColision(arcoVisitante)) {
				golesMessa.golAFavor();
				esperar(2000);
				sonidos.tocarSonido("toc");
				pantallaEsperar.dibujarse(this.getGraphics());
				pelota = createPelota();
				setPosiciones();
				dominarPelota = true;
			}
		}
	}
	
	//metodo para verificar rebotes de pelota contra jugador 1
	private void verificarReboteEntrePelotaYMessa() {
        if (messa.hayColision(pelota)) {
        	dominioDeBalon();
//            pelota.setVelocidadY(+1);
//            pelota.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
//            pelota.setVelocidadY(pelota.getVelocidadY()-0.1);
        }
        if (dabiMartino.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
        }
    }
	
	//metodo para verificar rebotes de pelota contra jugador 2
	private void verificarReboteEntrePelotaYRolando() {
		if (rolando.hayColision(pelota)) {
			dominioDeBalon();
//			pelota.setVelocidadY(-5);
//			pelota.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
//            pelota.setVelocidadY(pelota.getVelocidadY()+5);
            dominarPelota = true;
            double azar = Math.random();
    		if(azar < 0.15) {
    			dominarPelota = false;
    			pelota.rebotarEnEjeY();
    			pelota.setVelocidadY(+7);
    			pelota.rebotarEnEjeX();
    			pelota.setVelocidadX(-1);
    			
    		}
        }
		if (alejoBecar.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
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
		if (golesRolando.getGolesEnContra() == 3) {
			pantallaActual = PANTALLA_PERDEDOR;
		}
	}
}