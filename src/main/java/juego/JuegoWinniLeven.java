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

	public JuegoWinniLeven(int anchoJuego, int largoJuego, int tiempoDeEsperaEntreActualizaciones, int enemigosPorLinea, int filasDeEnemigos) {
		this.pantallaActual = PANTALLA_INICIO;
		this.anchoJuego = anchoJuego;
		this.largoJuego = largoJuego;
		this.pelota = createPelota();
		this.cancha = new Cancha(0,0, 0, 0, 1080, 720, Color.green);
		this.reloj = new Reloj(25 ,630,new Font("Impact",8,20), Color.black);
		this.messa = new Messa(100, 274, 0, 0, 90, 90, Color.blue);
		this.rolando = new Rolando(990, 274, 0, 0, 60, 80, Color.yellow);
		this.dabiMartino = new ArqueroLocal(50, 274, 0, 0, 80, 80, Color.blue);
		this.alejoBecar = new ArqueroVisitante(950, 274, 0, 0, 80, 80, Color.yellow);
		this.arcoLocal = new ArcoLocal (5, 274, 0, 0, 80, 170, Color.black);
		this.arcoVisitante = new ArcoVisitante (994, 274, 0, 0, 80, 170, Color.black);
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
    			pelotaBuscaArcoVisitante();
    		}
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
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
	//metodo para que el enemigo busque la posicion de la pelota
	public void enemigoBuscaPelota() {
		double azar = Math.random();
		if( azar < 0.60) {
			if(rolando.getPosicionX() < pelota.getPosicionX()) {
				rolando.setVelocidadX(+0.5);
			}
			if(rolando.getPosicionX() > pelota.getPosicionX()) {
				rolando.setVelocidadX(-0.5);
			}
			if(rolando.getPosicionY() < pelota.getPosicionY()) {
				rolando.setVelocidadY(+0.5);
			}
			if(rolando.getPosicionY() > pelota.getPosicionY()) {
				rolando.setVelocidadY(-0.5);
			}

		}
		else if (azar > 0.90) {
			rolando.setVelocidadX(0);
		}
	}
	public void arquerosBuscanPelota(ElementoBasico arquero) {
		if(arquero.getPosicionY() < pelota.getPosicionY() && arquero.getPosicionY() < 400) {
			arquero.setVelocidadY(+0.5);
		}
		else if(arquero.getPosicionY() > pelota.getPosicionY()  && arquero.getPosicionY() > 200) {
			arquero.setVelocidadY(-0.5);
		}
		else {
			arquero.setVelocidadY(0);
		}
			
		
	}
	//metodo para que la pelota vaya al arco visitante
	public void pelotaBuscaArcoVisitante() {
		if(pelota.getPosicionX() > arcoLocal.getPosicionX()) {
			pelota.setVelocidadX(+3);
		} if (pelota.getPosicionY() < 270) {
			pelota.setVelocidadY(+1);
		} if (pelota.getPosicionY() > 420) {
			pelota.setVelocidadY(-1);
		}
	}
	
	//metodo para que la pelota vaya al arco local
	public void pelotaBuscaArcoLocal() {	
		if(pelota.getPosicionX() > arcoLocal.getPosicionX()) {
			pelota.setVelocidadX(-3);
		} if (pelota.getPosicionY() < 270) {
			pelota.setVelocidadY(+1);
		} if (pelota.getPosicionY() > 420) {
			pelota.setVelocidadY(-1);
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
		arquerosBuscanPelota(dabiMartino);
		arquerosBuscanPelota(alejoBecar);
	}
	private void dibujarJuego() {
		this.repaint();
	}
	
	//metodo para crear la pelota
	private ElementoBasico createPelota() {
		return new Pelota(anchoJuego / 2, largoJuego / 2, 0, 0, 20, 20, Color.white);
    }
	
	//metodo para que Rolando busque el arco
	private void buscaArco() {
		if(rolando.getPosicionX() < arcoLocal.getPosicionX()) {
			rolando.setVelocidadX(+2);
			} 
		
		if(rolando.getPosicionX() > arcoLocal.getPosicionX()) {
			rolando.setVelocidadX(-2);
			} 
		
		if(rolando.getPosicionY() < arcoLocal.getPosicionY()) {
			rolando.setVelocidadY(+2);
			} 
		
		if(rolando.getPosicionY() > arcoLocal.getPosicionY()) {
			rolando.setVelocidadY(-2);
		}
	}
	
	//metodo para utilizar los seguimientos de pelota
	private void dominioDeBalon() {
		if (pelota.hayColision(messa) && dominarPelota == true) {
			pelota.setPosicionX(messa.getPosicionX()+70);
			pelota.setPosicionY(messa.getPosicionY()+70);
			enemigoBuscaPelota();
		}
		if (pelota.hayColision(rolando) && dominarPelota == true) {
			pelota.setPosicionX(rolando.getPosicionX()-30);
			pelota.setPosicionY(rolando.getPosicionY()+70);
			buscaArco();
		}
	}
	
	//metodo para setear las posiciones
	private void setPosiciones () {
			messa.setPosicionY(274);
			messa.setPosicionX(150);
			rolando.setPosicionX(900);
			rolando.setPosicionY(274);
			dabiMartino.setPosicionX(50);
			dabiMartino.setPosicionY(274);
			alejoBecar.setPosicionX(950);
			alejoBecar.setPosicionY(274);
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

	// En este metodo verifico las colisiones, los rebotes de la pelota contra las paredes, la colision entre enemigos y el fin de juego
	private void verificarEstadoAmbiente() {
		verificarReboteEnArco();
		verificarChoqueArquero();
		verificarReboteEntrePelotaYMessa();
		verificarReboteEntrePelotaYRolando();
		verificarRebotePelotaContraParedLateral();
		verificarReboteContraFondos();
		verificarGoles(); 
		verificarFinDeJuego();
	}
	
	//verificar rebotes contra las paredes
	private void verificarRebotePelotaContraParedLateral() {
        if (pelota.getPosicionX() <= 68 || pelota.getPosicionX() + pelota.getAncho() >= (anchoJuego - 70)) {
        	if (pelota.getPosicionY() < 274 || pelota.getPosicionY() > 420)
            pelota.rebotarEnEjeX();
        	pelota.setVelocidadX(pelota.getVelocidadX()/2);
            dominarPelota = true;
        }
        if (messa.getPosicionX() <= 0) {
        	messa.setPosicionX(messa.getPosicionX()+30);
        }
        if (messa.getPosicionX() >= (anchoJuego -160)) {
    		messa.setPosicionX(messa.getPosicionX()-30);
    	}
        if (rolando.getPosicionX() <= 0) {
        	rolando.setPosicionX(rolando.getPosicionX()+30);
        }
        if (rolando.getPosicionX() >= (anchoJuego -160)) {
    		rolando.setPosicionX(rolando.getPosicionX()-30);
    	}
    }
	
	//verifica rebote contra los fondos
	private void verificarReboteContraFondos() {
        if (pelota.getPosicionY() <= 30 || pelota.getPosicionY() + pelota.getLargo() >= (largoJuego - 30)) {
            pelota.rebotarEnEjeY();
    		pelota.setVelocidadY(pelota.getVelocidadY()/2);
            dominarPelota = true;
            sonidos.tocarSonido("toc");
        }
        if (messa.getPosicionY() <= 0) {
        	messa.setPosicionY(messa.getPosicionY()+30);
        }
        if (messa.getPosicionY() >= (largoJuego -90)) {
    		messa.setPosicionY(messa.getPosicionY()-30);
        }
        if (rolando.getPosicionY() <= 0) {
        	rolando.setPosicionY(rolando.getPosicionY()+30);
        }
        if (rolando.getPosicionY() >= (largoJuego -90)) {
    		rolando.setPosicionY(rolando.getPosicionY()-30);
    	}
    }
	
	//metodo para verificar los goles
	private void verificarGoles() {
		if ((pelota.getPosicionY() > 270) && (pelota.getPosicionY() < 420)) {
			if (pelota.getPosicionX() <= 68) {
				if (pelota.hayColision(arcoLocal)) {
					golesRolando.golEnContra();
					esperar(2000);
					sonidos.tocarSonido("toc");
					pantallaEsperar.dibujarse(this.getGraphics());
					pelota = createPelota();
					setPosiciones();
					dominarPelota = true;
				} 
			}
			if (pelota.getPosicionX() >= 990) {
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
		}
	
	public void verificarReboteEnArco() {
		if (pelota.hayColision(arcoLocal) || pelota.hayColision(arcoVisitante)) {
			if ((pelota.getPosicionY() < 270) || (pelota.getPosicionY() > 420)) {
				pelota.rebotar();
				dominarPelota = true;
			}
		}
	}
	
	public void verificarChoqueArquero() {
		if (messa.hayColision(alejoBecar)) {
			messa.rebotarEnEjeX();
		}
		if (rolando.hayColision(dabiMartino)) {
			rolando.rebotarEnEjeX();
		}
	}
		
	//metodo para verificar rebotes de pelota contra jugador 1
	private void verificarReboteEntrePelotaYMessa() {
        if (messa.hayColision(pelota)) {
        	dominioDeBalon();
            sonidos.tocarSonido("toc");
        }
        if (dabiMartino.hayColision(pelota)) {
            pelota.rebotarEnEjeX();
            pelota.setVelocidadX(5);
            sonidos.tocarSonido("toc");
        }
    }
	
	//metodo para verificar rebotes de pelota contra jugador 2
	private void verificarReboteEntrePelotaYRolando() {
		if (rolando.hayColision(pelota)) {
			dominioDeBalon(); 
			dominarPelota = true;
            double azar = Math.random();
    		if(azar < 0.60) {
    			dominarPelota = false;
    			pelotaBuscaArcoLocal();
    		}
        }
		if (alejoBecar.hayColision(pelota)) {
            pelota.rebotarEnEjeX();
            pelota.setVelocidadX(-5);
            sonidos.tocarSonido("toc");
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