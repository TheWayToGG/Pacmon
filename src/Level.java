import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class Level {
	
	
	/**
	 * @param width zmienna odpowiedzialna za szerokoœæ hotbox'a
	 *
	 * @param height zmienna odpowiedzialna z wysokoœæ hotbox'a
	 * 
	 * @param Tile tablica dwuwymiarowa dla kafelków
	 * 
	 * @param Coins lista zawieraj¹ca monety
	 * 
	 * @param Enemy list zawieraj¹ca duchy
	 */
	
	public int width;
	public int height;
	
	public Tile[][] tiles;
	
	public List<Coin> coins;
	public List<Ghost> ghosts;
	
	public Level(String path){
		coins = new ArrayList<>();
		ghosts = new ArrayList<>();
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.width = map.getWidth();
			this.height = map.getHeight();
			int[] pixels = new int[width*height];
			tiles = new Tile[width][height];
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int xx =0; xx < width; xx++){
				for(int yy =0; yy <height; yy++){
					int val = pixels[xx + (yy*width)];
					
					if(val == 0xFF000000){
						//Tile
						tiles[xx][yy] = new Tile(xx*32,yy*32);
					}else if(val == 0xFF0000FF){
						//player
						Game.player.x = xx*32;
						Game.player.y = yy*32;
					}else if(val == 0xFFFF0000){
						ghosts.add(new Ghost(xx*32,yy*32));
					}else{
						coins.add(new Coin(xx*32,yy*32));
					}
					
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metoda pozwalaj¹ca na ruch ducha
	 */
	
	public void tick(){
		for(int i = 0; i < ghosts.size(); i++){
			ghosts.get(i).tick();
		}
	}
	
	
	/**
     * Metoda renderuj¹ca, w tym przypadku dotyczy monet, duchów oraz kafelków na mapie
     *
     */
	
	
	public void render(Graphics g){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(tiles[x][y] != null) tiles[x][y].render(g);
			}
		}
		
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).render(g);
		}
		
		for(int i = 0; i < ghosts.size(); i++){
			ghosts.get(i).render(g);
		}
	}
	
}
