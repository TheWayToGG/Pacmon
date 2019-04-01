
public class States {
	
	public States(int state){
		
	}
	
	public void tick(){
		if(Game.STATE == Game.GAME){
			Game.player.tick();
			Game.level.tick();
		}else if(Game.STATE == Game.PAUSE){
			if(Game.isEnter == true){
				Game.isEnter = false;
				Game.player = new Player(Game.WIDTH/2,Game.HEIGHT/2);
				if(Game.streak == 0){
					Game.path = "map/map3.png";
				}else if(Game.streak == 1){
					Game.path = "map/map4.png";
				}else if(Game.streak == 2){
					Game.path = "map/map.png";
				}else if(Game.streak == 3){
					Game.path = "map/map2.png";
				}
				Game.level = new Level(Game.path);
				Game.spritesheet = new SpriteSheet("/sprites/spritesheet.png");
				Game.stan = 0;
				Game.STATE = Game.GAME;
			}
		}else if(Game.STATE == Game.GG){
			if(Game.isEnter == true){
				Game.isEnter = false;
				if(Game.streak == 4){
					Game.STATE = Game.END;
				}
				Game.streak++;
				Game.STATE = Game.PAUSE;
			}
		}else if(Game.STATE == Game.BG){
			if(Game.isEnter == true){
				Game.isEnter = false;
				Game.streak = 0;
				Game.STATE = Game.PAUSE;
			}
		}
	}
	
}
