package juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
// Implementacion del jugador1
public class ArqueroLocal extends ElementoBasico {
	private BufferedImage img;

	public ArqueroLocal(int posicionX, int posicionY, double velocidadX, double velocidadY, int ancho, int largo, Color color)  {
		super(posicionX, posicionY, velocidadX, velocidadY, ancho, largo, color);


		try {
			String path = Paths.get(ArqueroLocal.class.getClassLoader().getResource("imagenes/cono.png").toURI()).toString();
			this.img = ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
	public void dibujarse(Graphics g) {
		try {
			g.drawImage(img, getPosicionX(), getPosicionY(), this.getAncho(), this.getLargo(), null);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
}
