import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {
	
	/**
	 * @param sheet zmienna s�u��ca do przypisania puli obraz�w
	 */
	
	private BufferedImage sheet;
	
	public SpriteSheet(String path){
		try{
			sheet = ImageIO.read(getClass().getResource(path));
		}catch(IOException e){
			System.out.println("Failed to load sheet!");
		}
	}
	
	
	/**
	 * Metoda s�u��ca do pobierania odpowiednich sprite'�w z puli o rozmiarze 16x16
	 */
	
	public BufferedImage getSprite(int xx,int yy){
		return sheet.getSubimage(xx, yy, 16, 16);
	}
	
}
