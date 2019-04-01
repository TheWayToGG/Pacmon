import java.awt.Graphics;
import java.awt.Rectangle;


public class Tile extends Rectangle{
	
	private static final long serialVersionUID = 1L;

	public Tile(int x, int y){
		setBounds(x,y,32,32);
	}
	
	/**
	 * Metoda renderuj¹ca sprite kafelka
	 */
	
	public void render(Graphics g){
		g.drawImage(Texture.tile,x,y,width,height,null);
	}
}
