
package juego;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class InicializadorWinniLeven {

	public static void main(String[] args) {
		
		// Propiedades del Juego
		int anchoVentana = 1080;
		int largoVentana = 720;
		int tiempoDeEsperaEntreActualizaciones = 5;
		int golesAFavor = 0;
		int golesEnContra = 0;
		int enemigosPorLinea = 7;
        int filasDeEnemigos = 5;

		// Activar aceleracion de graficos en 2 dimensiones
		System.setProperty("sun.java2d.opengl", "true");

		// Crear un objeto de tipo JFrame que es la ventana donde va estar el juego
		JFrame ventana = new JFrame("WINNI LEVEN MESSA VS ROLANDO");

		// Cerrar la aplicacion cuando el usuario hace click en la 'X'
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Abrir la ventana en el centro de la pantalla
		ventana.setLocationRelativeTo(null);

		// Mostrar la ventana
		ventana.setVisible(true);
		
        // Crear un "Jpanel" llamado Juego y agregarlo a la ventana
        JuegoWinniLeven juegoWinniLeven = new JuegoWinniLeven(anchoVentana, largoVentana, tiempoDeEsperaEntreActualizaciones, enemigosPorLinea,
                filasDeEnemigos);
        
     // Agregar a la ventana el JComponent (Juego hereda de JComponent)
        ventana.add(juegoWinniLeven);

        // Enviar los eventos recibidos de movimientos del teclado al juego (esto es
        // porque la clase Juego implementa: MouseMotionListener)
        ventana.addKeyListener(juegoWinniLeven);

        // Achicar la ventana lo maximo posible para que entren los componentes
        ventana.pack();

        // Crear un thread y pasarle como parametro al juego que implementa la interfaz
        // "Runnable"
        Thread thread = new Thread(juegoWinniLeven);

        // Arrancar el juego
        thread.start();
	}

}
