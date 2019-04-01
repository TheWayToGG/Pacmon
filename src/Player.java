import java.awt.Graphics;
import java.awt.Rectangle;



public class Player extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @param right zmienna s³u¿¹ca do okreœlenia kierunku ruch gracza, w tym wypadku w prawo
	 *
	 * @param left zmienna s³u¿¹ca do okreœlenia kierunku ruch gracza, w tym wypadku w lewo
	 *
	 * @param up zmienna s³u¿¹ca do okreœlenia kierunku ruch gracza, w tym wypadku w górê
	 *
	 * @param down zmienna s³u¿¹ca do okreœlenia kierunku ruch gracza, w tym wypadku w dó³
	 * 
	 * @param speed zmienna s³u¿¹ca do okreœlenia prêdkoœci gracza
	 * 
	 * @param poter zmienna s³u¿¹ca do okreœlenia aktualnego kierunku w jakim porusza siê gracz
	 */
	
	
	public boolean right,left,up,down;


	private int speed = 4;
	private int poter = 0;
	

	public Player(int x,int y){
		setBounds(x,y,32,32);
	}
	
	/**
	 * Metoda przy u¿yciu, której porusza siê duch oraz okreœlane s¹ stany gry w przypadku zebrania wszystkich monet lub dotkniecia ducha
	 */
	
	public void tick(){
		if(right && canMove(x+speed,y))x+=speed;
		if(left && canMove(x-speed,y))x-=speed;
		if(up && canMove(x,y-speed))y-=speed;
		if(down && canMove(x,y+speed))y+=speed;
		
		Level level = Game.level;
		
		for(int i = 0; i < level.coins.size(); i++){
			
			if(this.intersects(level.coins.get(i))){
				level.coins.remove(i);
				break;
			}
			
		}
		
		if(level.coins.size() == 0){
			//GG, win!
			if(Game.streak == 3){
				Game.STATE = Game.END;
			}else{
				Game.STATE = Game.GG;
			}
		}
		
		for(int i = 0; i < level.ghosts.size(); i++){
			//gege you lose!
			if(this.intersects(level.ghosts.get(i))){
					Game.STATE = Game.BG;
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
	 * Metoda s³u¿¹ca do sprawdzenia czy dany ruch jest niedopuszczalny
	 */
	
	private boolean cantMove(int nextx,int nexty){
		
		Rectangle bounds = new Rectangle(nextx,nexty,width,height);
		Level level = Game.level;
		
		for(int xx = 0; xx < level.tiles.length; xx++){
			for(int yy = 0; yy < level.tiles[0].length; yy++){
				if(level.tiles[xx][yy] != null){
					if(bounds.intersects(level.tiles[xx][yy])){
						return true;
					}
				}
			}
		}
		poter = 0;
		
		return false;
	}
	
	
	/**
     * Metoda renderuj¹ca, w tym przypadku dotyczy postaci gracza w zale¿noœci od kierunku w jakim siê porusza
     *
     */
	
	
	public void render(Graphics g){
		if(/*right*/ Game.stan == 1 && canMove(x+speed,y)){
			g.drawImage(Texture.playerRight,x,y,width,height,null);
			Game.player.up =false;
			Game.player.down =false;
			Game.player.left =false;
			Game.player.right =true;
			poter = 1;
		}else if(/*left*/ Game.stan == 2 && canMove(x-speed,y)){
			g.drawImage(Texture.playerLeft,x,y,width,height,null);
			Game.player.up =false;
			Game.player.down =false;
			Game.player.right =false;
			Game.player.left =true;
			poter = 2;
		}else if(/*up*/ Game.stan == 3 && canMove(x,y-speed)){
			g.drawImage(Texture.playerUp,x,y,width,height,null);
			Game.player.right =false;
			Game.player.down =false;
			Game.player.left =false;
			Game.player.up =true;
			poter = 3;
		}else if(/*down*/ Game.stan == 4 && canMove(x,y+speed)){
			g.drawImage(Texture.playerDown,x,y,width,height,null);
			Game.player.up =false;
			Game.player.right =false;
			Game.player.left =false;
			Game.player.down =true;
			poter = 4;
		}else{
			if(Game.stan == 1 && cantMove(x+speed,y)){
				if(poter == 1){
					poter = 0;
					g.drawImage(Texture.player,x,y,width,height,null);
				}else if(poter == 2){
					g.drawImage(Texture.playerLeft,x,y,width,height,null);
				}else if(poter == 3){
					g.drawImage(Texture.playerUp,x,y,width,height,null);
				}else if(poter == 4){
					g.drawImage(Texture.playerDown,x,y,width,height,null);
				}else if(poter == 0){
					g.drawImage(Texture.player,x,y,width,height,null);
				}
			}else if(Game.stan == 2 && cantMove(x-speed,y)){
				if(poter == 1){
					g.drawImage(Texture.playerRight,x,y,width,height,null);
				}else if(poter == 2){
					poter = 0;
					g.drawImage(Texture.player,x,y,width,height,null);
				}else if(poter == 3){
					g.drawImage(Texture.playerUp,x,y,width,height,null);
				}else if(poter == 4){
					g.drawImage(Texture.playerDown,x,y,width,height,null);
				}else if(poter == 0){
					g.drawImage(Texture.player,x,y,width,height,null);
				}
			}else if(Game.stan == 3 && cantMove(x,y-speed)){
				if(poter == 1){
					g.drawImage(Texture.playerRight,x,y,width,height,null);
				}else if(poter == 2){
					g.drawImage(Texture.playerLeft,x,y,width,height,null);
				}else if(poter == 3){
					poter = 0;
					g.drawImage(Texture.player,x,y,width,height,null);
				}else if(poter == 4){
					g.drawImage(Texture.playerDown,x,y,width,height,null);
				}else if(poter == 0){
					g.drawImage(Texture.player,x,y,width,height,null);
				}
			}else if(Game.stan == 4 && cantMove(x,y+speed)){
				if(poter == 1){
					g.drawImage(Texture.playerRight,x,y,width,height,null);
				}else if(poter == 2){
					g.drawImage(Texture.playerLeft,x,y,width,height,null);
				}else if(poter == 3){
					g.drawImage(Texture.playerUp,x,y,width,height,null);
				}else if(poter == 4){
					poter = 0;
					g.drawImage(Texture.player,x,y,width,height,null);
				}else if(poter == 0){
					g.drawImage(Texture.player,x,y,width,height,null);
				}
			}else{
				g.drawImage(Texture.player,x,y,width,height,null);
			}
		}
	}
	
	
}
