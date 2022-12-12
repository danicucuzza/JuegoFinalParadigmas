package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PantallaPerdedor extends PantallaImagen {


    public PantallaPerdedor(int ancho, int largo, String resource) {
        super(ancho, largo, resource);
    }

    public void dibujarse(Graphics graphics) {
        super.dibujarse(graphics);
        mostrarMensaje(graphics, "PERDISTE, TE GOLEARON PA");
    }

    private void mostrarMensaje(Graphics g, String mensaje) {
        g.setColor(Color.blue);
        g.setFont(new Font("Arial", 8, 30));
        g.drawString(mensaje, 10, 40);
    }

}
