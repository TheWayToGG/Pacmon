import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Ghost extends Rectangle{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param random zmienna s³u¿¹ca do okreœlenia stanu SI, w tym wypadku random
	 *
	 * @param smart zmienna s³u¿¹ca do okreœlenia stanu SI, w tym wypadku smart
	 *
	 * @param state zmienna s³u¿¹ca do okreœlenia bie¿¹cego stanu SI
	 *
	 * @param sheet zmienna s³u¿¹ca do przypisania puli obrazów
	 *
	 * @param right zmienna s³u¿¹ca do okreœlenia kierunku ruch ducha, w tym wypadku w prawo
	 *
	 * @param left zmienna s³u¿¹ca do okreœlenia kierunku ruch ducha, w tym wypadku w lewo
	 *
	 * @param up zmienna s³u¿¹ca do okreœlenia kierunku ruch ducha, w tym wypadku w górê
	 *
	 * @param down zmienna s³u¿¹ca do okreœlenia kierunku ruch ducha, w tym wypadku w dó³
	 *
	 * @param dir s³u¿¹ca do okreœlenia aktualnego kierunku ruchu ducha
	 *
	 * @param randomGen zmienna s³u¿¹ca do wprowadzenia elemtu losowoœci w ruchu ducha
	 *
	 *@param time s³u¿¹ca zapisu czasu trwania danej sekwencji
	 *
	 *@param targetTime zmienna s³u¿¹ca do okreœlenia finalnego czasu trwania danej sekwencji
	 *
	 *@param spd zmienna s³u¿¹ca do okreœlenia prêdkosci ducha
	 */
	
	
	
	private int random =0,smart = 1;
	
	private int state = random;
	
	private int right = 0,left = 1,up = 2,down = 3;
	
	private int dir = -1;
	
	public Random randomGen;
	
	private int time = 0;
	
	private int targetTime = 60*4;
	
	public int spd = 2;

	public Ghost(int x,int y){
		randomGen = new Random();
		setBounds(x,y,32,32);
		dir = randomGen.nextInt(4);
	}
	
	/**
	 * Metoda s³u¿¹ca do kontroli ruchu ducha
	 */
	
	public void tick(){
		
		if(state == random){
			
			if(dir == right){
				
				if(canMove(x+spd,y)){
					x+=spd;
				}else{
					dir = randomGen.nextInt(4);
				}
				
			}else if(dir == left){
				
				if(canMove(x-spd,y)){
					x-=spd;
				}else{
					dir = randomGen.nextInt(4);
				}
				
			}else if(dir == up){
				
				if(canMove(x,y-spd)){
					y-=spd;
				}else{
					dir = randomGen.nextInt(4);
				}
				
			}else if(dir == down){
				
				if(canMove(x,y+spd)){
					y+=spd;
				}else{
					dir = randomGen.nextInt(4);
				}
				
			}
			
			time++;
			
			if(time == targetTime){
				state = smart;
				time = 0;
			}
			
			
		}else if(state == smart){
			
			
			if(x < Game.player.x){
				if(canMove(x+spd,y)){
					x+=spd;
				}
			}
			if(x > Game.player.x){
				if(canMove(x-spd,y)){
					x-=spd;
				}
			}
			if(y < Game.player.y){
				if(canMove(x,y+spd)){
					y+=spd;
				}
			}
			if(y > Game.player.y){
				if(canMove(x,y-spd)){
					y-=spd;
				}
			}
			
			
			
			time++;
			
			if(time == targetTime){
				state = random;
				time = 0;
			}
			
		}
		
		
	}
	
	/**
	 * Metoda s³u¿¹ca do sprawdzenia czy dany ruch jest dopuszczalny
	 */
	
private boolean canMove(int nextx,int nexty){
		
		Rectangle bounds = new Rectangle(nextx,nexty,width,height);
		Level level = Game.level;
		
		for(int xx = 0; xx < level.tiles.length; xx++){
			for(int yy = 0; yy < level.tiles[0].length; yy++){
				if(level.tiles[xx][yy] != null){
					if(bounds.intersects(level.tiles[xx][yy])){
						return false;
					}
				}
			}
		}
		
		return true;
	}

	/**
	 * Metoda renderuj¹ca sprite ducha
	 */
	
	public void render(Graphics g){
		g.drawImage(Texture.ghost,x,y,width,height,null);
	}
}
