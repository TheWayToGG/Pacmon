import java.awt.image.BufferedImage;


public class Texture {
	
	/**
     * @param player
     * Zmienna wykorzystywana przypisania sprite'a gracza w pozycji bezczynnej
     *@param playerRight
     * Zmienna wykorzystywana przypisania sprite'a gracza podczas ruchu w prawo
     *@param playerLeft
     * Zmienna wykorzystywana przypisania sprite'a gracza podczas ruchu w lewo
     *@param playerUp
     * Zmienna wykorzystywana przypisania sprite'a gracza podczas ruchu w górê
     *@param playerDown
     * Zmienna wykorzystywana przypisania sprite'a gracza podczas ruchu w dó³
     *@param ghost
     * Zmienna wykorzystywana przypisania sprite'a ducha
     *@param coin1
     * Zmienna wykorzystywana przypisania sprite'a monety w pozycji 1
     *@param coin2
     * Zmienna wykorzystywana przypisania sprite'a monety w pozycji 2
     *@param tile
     * Zmienna wykorzystywana przypisania sprite'a kafelka
     */
	
	public static BufferedImage player;
	public static BufferedImage playerRight;
	public static BufferedImage playerLeft;
	public static BufferedImage playerUp;
	public static BufferedImage playerDown;
	public static BufferedImage ghost;
	public static BufferedImage coin1;
	public static BufferedImage coin2;
	public static BufferedImage tile;
	
	/**
	 * Metoda s³u¿¹ca do przypisania odpowiednich tesktur(sprite'ów) danym zmiennym
	 */
	
	public Texture(){
		player = Game.spritesheet.getSprite(51, 0);
		playerRight = Game.spritesheet.getSprite(0, 0);
		playerLeft = Game.spritesheet.getSprite(17, 0);
		playerUp = Game.spritesheet.getSprite(67, 0);
		playerDown = Game.spritesheet.getSprite(83, 0);
		ghost = Game.spritesheet.getSprite(0, 18);
		coin1 = Game.spritesheet.getSprite(18, 18);
		coin2 = Game.spritesheet.getSprite(35, 18);
		tile = Game.spritesheet.getSprite(35, 0);
	}
	
}
