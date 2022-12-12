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
<<<<<<< HEAD
	private ElementoBasico rolando;
=======
	private ElementoBasico neymor;
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
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

	public JuegoWinniLeven(int anchoJuego, int largoJuego, int tiempoDeEsperaEntreActualizaciones, int enemigosPorLinea, int filasDeEnemigos) {
		this.pantallaActual = PANTALLA_INICIO;
		this.anchoJuego = anchoJuego;
		this.largoJuego = largoJuego;
		this.pelota = createPelota();
		this.cancha = new Cancha(0,0, 0, 0, 650, 720, Color.green);
		this.reloj = new Reloj(25 ,630,new Font("Impact",8,20), Color.black);
<<<<<<< HEAD
		this.messa = new Messa(280, 560, 0, 0, 70, 70, Color.blue);
		this.rolando = new Rolando(280, 25, 0, 0, 50, 60, Color.yellow);
		this.dabiMartino = new ArqueroLocal(290, 580, 0, 0, 30, 30, Color.blue);
		this.alejoBecar = new ArqueroVisitante(270, 20, 0, 0, 30, 30, Color.yellow);
		this.arcoLocal = new Arco (250, 680, 0, 0, 150, 70, Color.black);
		this.arcoVisitante = new Arco (250, -40, 0, 0, 150, 70, Color.black);
=======
		this.messa = new Messa(280, 580, 0, 0, 40, 40, Color.blue);
		this.neymor = new Neymor(280, 25, 0, 0, 40, 40, Color.yellow);
		this.dabiMartino = new dabiMartino(280, 580, 0, 0, 30, 30, Color.blue);
		this.alejoBecar = new alejoBecar(280, 25, 0, 0, 30, 30, Color.yellow);
		this.arcoLocal = new Arco (265, 610, 0, 0, 70, 30, Color.black);
		this.arcoVisitante = new Arco (265, -6, 0, 0, 70, 30, Color.black);
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
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
                sonidos.tocarSonido("sonidos/cancha.wav");
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
<<<<<<< HEAD
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
    			pelota.rebotarEnEjeY();
    			pelota.setVelocidadX(+1);
    			pelota.rebotarEnEjeX();
    			pelota.setVelocidadY(-7);
    			}
    		} 
        }
=======
                messa.setVelocidadX(1);
            }
    		if (arg0.getKeyCode() == KeyEvent.VK_A) {
                messa.setVelocidadX(-1);
            }
    		if (arg0.getKeyCode() == KeyEvent.VK_S) {
                messa.setVelocidadY(1);
            }
    		if (arg0.getKeyCode() == KeyEvent.VK_W) {
                messa.setVelocidadY(-1);
            }
        }
    }
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
	
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
	
	public void enemigoBuscaPelota() {
		double azar = Math.random();
		if( azar < 0.60) {
<<<<<<< HEAD
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
				dabiMartino.setVelocidadX(+0.5);
			}
			if(dabiMartino.getPosicionX() > pelota.getPosicionX()) {
				dabiMartino.setVelocidadX(-0.5);
			}
			if(alejoBecar.getPosicionX() < pelota.getPosicionX()) {
				alejoBecar.setVelocidadX(+0.5);
			}
			if(alejoBecar.getPosicionX() > pelota.getPosicionX()) {
				alejoBecar.setVelocidadX(-0.5);
			}
		}
		else if (azar > 0.90) {
			rolando.setVelocidadX(0);
=======
			if(neymor.getPosicionX() < pelota.getPosicionX()) {
				neymor.setVelocidadX(+0.5);
			}
			if(neymor.getPosicionX() > pelota.getPosicionX()) {
				neymor.setVelocidadX(-0.5);
			}
			if(neymor.getPosicionY() < pelota.getPosicionY()) {
				neymor.setVelocidadY(+1);
			}
			if(neymor.getPosicionY() > pelota.getPosicionY()) {
				neymor.setVelocidadY(-1);
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
			neymor.setVelocidadX(0);
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
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
<<<<<<< HEAD
            rolando.dibujarse(g);
=======
            neymor.dibujarse(g);
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
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
<<<<<<< HEAD
		rolando.moverse();
=======
		neymor.moverse();
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
		dabiMartino.moverse();
		alejoBecar.moverse();
		enemigoBuscaPelota();
	}
	private void dibujarJuego() {
		this.repaint();
	}
	
<<<<<<< HEAD
	//metodo para crear la pelota
	private ElementoBasico createPelota() {
		return new Pelota(anchoJuego / 2, largoJuego / 2, 0, 0, 30, 30, Color.white);
    }
	
	//metodo para que la pelota siga al jugador 1
	private void seguirJugadorMessa() {
		pelota.setPosicionX(messa.getPosicionX());
		pelota.setPosicionY(messa.getPosicionY());
	}
=======
	private ElementoBasico createPelotaLocal() {
        return new Pelota(anchoJuego / 2, 500, 1, -1, 15, 15, Color.white);
    }
	
	private ElementoBasico createPelotaVisitante() {
        return new Pelota(anchoJuego / 2, 50, -1, 1, 15, 15, Color.white);
    }
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
	
	//metodo para que la pelota siga al jugador 2
	private void seguirJugadorRolando() {
		pelota.setPosicionX(rolando.getPosicionX());
		pelota.setPosicionY(rolando.getPosicionY());
	}
	
	//metodo para utilizar los seguimientos de pelota
	private void dominioDeBalon() {
		if (pelota.hayColision(messa) && dominarPelota == true) {
			seguirJugadorMessa();
		}
		if (pelota.hayColision(rolando) && dominarPelota == true)
			seguirJugadorRolando();
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
        if (pelota.getPosicionX() <= 0 || pelota.getPosicionX() + pelota.getAncho() >= anchoJuego) {
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
        if (messa.getPosicionX() <= 0 || messa.getPosicionX() + messa.getAncho() >= anchoJuego) {
        	messa.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
        }
        if (neymor.getPosicionX() <= 0 || neymor.getPosicionX() + neymor.getAncho() >= anchoJuego) {
        	neymor.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
        }
    }
	
<<<<<<< HEAD
	//verifica rebote contra los fondos
	private void verificarReboteContraFondos() {
        if (pelota.getPosicionY() <= 0 || pelota.getPosicionY() + pelota.getLargo() >= largoJuego) {
            pelota.rebotarEnEjeY();
            dominarPelota = true;
=======
	private void verificarReboteContraFondos() {
        if (pelota.getPosicionY() <= 0 || pelota.getPosicionY() + pelota.getLargo() >= largoJuego) {
            pelota.rebotarEnEjeY();
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
            sonidos.tocarSonido("toc");
        }
        if (messa.getPosicionY() <= 0 || messa.getPosicionY() + messa.getLargo() >= largoJuego) {
        	messa.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
        }
<<<<<<< HEAD
        if (rolando.getPosicionY() <= 0 || rolando.getPosicionY() + rolando.getLargo() >= largoJuego) {
        	rolando.rebotarEnEjeY();
=======
        if (neymor.getPosicionY() <= 0 || neymor.getPosicionY() + neymor.getLargo() >= largoJuego) {
        	neymor.rebotarEnEjeY();
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
            sonidos.tocarSonido("toc");
        }
    }
	
<<<<<<< HEAD
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
		if (pelota.hayColision(arcoLocal)) {
			golesRolando.golEnContra();
			esperar(2000);
			sonidos.tocarSonido("toc");
			pantallaEsperar.dibujarse(this.getGraphics());
			pelota = createPelota();
			setPosiciones();
			dominarPelota = true;
=======
	private void verificarGoles() {
		if (pelota.hayColision(arcoLocal)) {
			golesNeymor.golEnContra();
			esperar(2000);
			sonidos.tocarSonido("toc");
			pantallaEsperar.dibujarse(this.getGraphics());
			pelota = createPelotaLocal();
			messa.setPosicionX(580);
			messa.setPosicionX(280);
			neymor.setPosicionX(280);
			neymor.setPosicionY(25);
			dabiMartino.setPosicionX(580);
			dabiMartino.setPosicionX(280);
			alejoBecar.setPosicionX(280);
			alejoBecar.setPosicionY(25);
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
		} 
		if (pelota.hayColision(arcoVisitante)) {
			golesMessa.golAFavor();
			esperar(2000);
			sonidos.tocarSonido("toc");
			pantallaEsperar.dibujarse(this.getGraphics());
<<<<<<< HEAD
			pelota = createPelota();
			setPosiciones();
			dominarPelota = true;
=======
			pelota = createPelotaLocal();
			messa.setPosicionY(580);
			messa.setPosicionX(280);
			neymor.setPosicionX(280);
			neymor.setPosicionY(25);
			dabiMartino.setPosicionX(580);
			dabiMartino.setPosicionX(280);
			alejoBecar.setPosicionX(280);
			alejoBecar.setPosicionY(25);
>>>>>>> 44382b5aa00ac268ac737ba0e23d799dcc102b5c
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
            pelota.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
            pelota.setVelocidadY(pelota.getVelocidadY()-0.1);
        }
        if (dabiMartino.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            sonidos.tocarSonido("toc");
        }
    }
	
	//metodo para verificar rebotes de pelota contra jugador 2
	private void verificarReboteEntrePelotaYRolando() {
		if (rolando.hayColision(pelota)) {
			pelota.setVelocidadY(-5);
			pelota.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
            pelota.setVelocidadY(pelota.getVelocidadY()+5);
            dominarPelota = true;
        }
		if (alejoBecar.hayColision(pelota)) {
            pelota.rebotarEnEjeY();
            pelota.rebotarEnEjeX();
            sonidos.tocarSonido("toc");
            pelota.setVelocidadY(pelota.getVelocidadY()+0.1);
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