import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{
	
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param isRunning zmienna s�u��ca do okre�lenia czy gra jest uruchominoa
	 *
	 * @param WIDTH zmienna s�u��ca do okre�lenia szeroko�ci okna
	 *
	 * @param HEIGHT zmienna s�u��ca do okre�lenia wysoko�ci okna
	 *
	 * @param TITLE zmienna s�u��ca do okre�lenia tytu�u gry
	 * 
	 * @param stan zmienna s�u��ca do okre�lenia stanu wci�ni�tego klawisza
	 * 
	 * @param PAUSE zmienna s�u��ca do okre�laniu stanu gry, w tym wypadku pauzy
	 * 
	 * @param GAME zmienna s�u��ca do okre�laniu stanu gry, w tym wypadku trwaj�cej gry
	 * 
	 * @param GG zmienna s�u��ca do okre�laniu stanu gry, w tym wypadku wygranej
	 * 
	 * @param BG zmienna s�u��ca do okre�laniu stanu gry, w tym wypadku pora�ki
	 * 
	 * @param BG zmienna s�u��ca do okre�laniu stanu gry, w tym wypadku uko�czenia gry
	 * 
	 * @param streak zmienna s�u��ca do okre�laniu serii wygranych
	 * 
	 * @param STATE zmienna s�u��ca do okre�lania aktualnie wyst�puj�cego stanu gry
	 * 
	 * @param path zmienna s�u��ca do �cie�ki do map
	 * 
	 * @param isEnter zmienna s�u�aca do okre�lenia czy klawisz enter zosta� wci�ni�ty w odpowiednim momencie
	 */

	private boolean isRunning = false;
	
	public static int WIDTH = 640,HEIGHT = 480;
	public static final String TITLE = "PACMON";
	
	public Thread thread;
	
	public static int stan = 0;
	
	
	public static Player player;
	public static Level level;
	public static SpriteSheet spritesheet;
	
	public static States states;
	
	public static final int PAUSE = 0,GAME = 1,GG = 2,BG = 3,END = 4;
	public static int streak = 0;
	public static int STATE = -1;
	public static String path = "map/map3.png";
	
	
	public static boolean isEnter = false;
	
	
	public Game(){
		Dimension dimension = new Dimension(Game.WIDTH,Game.HEIGHT);
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		
		addKeyListener(this);
		
		STATE = PAUSE;
		
		player = new Player(Game.WIDTH/2,Game.HEIGHT/2);
		level = new Level(path);
		spritesheet = new SpriteSheet("/sprites/spritesheet.png");
		states = new States(STATE);
		
		new Texture();
		
	}
	
	/**
	 * Metoda przy u�yciu, kt�rej odpalana jest gra
	 */
	
	public synchronized void start(){
		if(isRunning) return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Metoda przy u�yciu, kt�rej gra jest zatrzymywana
	 */
	
	public synchronized void stop(){
		if(!isRunning) return;
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda przy u�yciu, kt�rej kontrolowany jest stan gry
	 */
	
	private void tick(){
		states.tick();
	}
	
	/**
     * Metoda renderuj�ca, w tym przypadku dotyczy map oraz ekran�w stan�w
     *
     */
	
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		if(STATE == GAME){
			player.render(g);
			level.render(g);
		}else{
			if(STATE == PAUSE){
				int boxWidth = 500;
				int boxHeight = 200;
				int xx = Game.WIDTH/2 - boxWidth/2;
				int yy = Game.HEIGHT/2 - boxHeight/2;
				g.setColor(new Color(0,20,80));
				g.fillRect(xx, yy, boxWidth, boxHeight);
				
				
				g.setColor(Color.yellow);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,40));
				g.drawString("PACMAN", xx+160, yy+40);
				
				g.setColor(Color.white);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,19));
				g.drawString("> Wcisnij enter aby rozpoczac <", xx+100, yy+100);
				g.drawImage(Texture.playerRight,xx+10,yy+60,64,64,null);
				g.drawImage(Texture.ghost,xx+420,yy+60,64,64,null);
				
			}else if(STATE == GG){
				int boxWidth = 500;
				int boxHeight = 200;
				int xx = Game.WIDTH/2 - boxWidth/2;
				int yy = Game.HEIGHT/2 - boxHeight/2;
				g.setColor(new Color(0,20,80));
				g.fillRect(xx, yy, boxWidth, boxHeight);
				
				
				g.setColor(Color.yellow);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,40));
				g.drawString("PACMAN", xx+160, yy+40);
				
				g.setColor(Color.orange);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,22));
				g.drawString("GG WELL PLAYED!", xx+147, yy+100);
				g.drawImage(Texture.playerRight,xx+10,yy+60,64,64,null);
				g.drawImage(Texture.playerLeft,xx+425,yy+60,64,64,null);
				
				g.setColor(Color.white);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,19));
				g.drawString("> Wcisnij enter aby kontynuowac <", xx+100, yy+150);
				
			}else if(STATE == BG){
				int boxWidth = 500;
				int boxHeight = 200;
				int xx = Game.WIDTH/2 - boxWidth/2;
				int yy = Game.HEIGHT/2 - boxHeight/2;
				g.setColor(new Color(0,20,80));
				g.fillRect(xx, yy, boxWidth, boxHeight);
				
				
				g.setColor(Color.yellow);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,40));
				g.drawString("PACMAN", xx+160, yy+40);
				
				g.setColor(Color.orange);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,22));
				g.drawString("BG YOU LOST!", xx+165, yy+100);
				g.drawImage(Texture.ghost,xx+10,yy+60,64,64,null);
				g.drawImage(Texture.ghost,xx+420,yy+60,64,64,null);
				
				g.setColor(Color.white);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,19));
				g.drawString("> Wcisnij enter aby kontynuowa� <", xx+90, yy+150);
				
			}else if(STATE == END){
				int boxWidth = 500;
				int boxHeight = 200;
				int xx = Game.WIDTH/2 - boxWidth/2;
				int yy = Game.HEIGHT/2 - boxHeight/2;
				g.setColor(new Color(0,20,80));
				g.fillRect(xx, yy, boxWidth, boxHeight);
				
				
				g.setColor(Color.yellow);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,40));
				g.drawString("PACMAN", xx+160, yy+40);
				
				g.setColor(Color.white);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,22));
				g.drawString("VICTORY!", xx+195, yy+100);
				
				g.setColor(Color.orange);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,22));
				g.drawString("Credits: Kamil Ossowski", xx+120, yy+150);
				
				
				g.setColor(Color.white);
				g.setFont(new Font(Font.DIALOG,Font.BOLD,19));
				g.drawString("> Wcisnij enter aby zakonczyc <", xx+100, yy+180);
			}
		}
			
			
		g.dispose();
		bs.show();
		
	}
	
	/**
     * Metoda odpowiadaj�ca za dzia�anie gry oraz wypisywanie ilo�ci fps'�w
     *
     */
	
	public void run(){
		requestFocus();
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 60.0;
		double delta = 0;
		double ns = 1000000000 / targetTick;
		
		while(isRunning){
			long now = System.nanoTime();
			delta+=(now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				tick();
				render();
				fps++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println(fps);
				fps = 0;
				timer+=1000;
			}
		}
		
		stop();
		
	}
	
	public static void main(String[] args){
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle(Game.TITLE);
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		game.start();
		
	}
	
	
	/**
     * Metoda odpowiadaj�ca za zmiane stanu w przypadku wci�niecia danego klawisza
     *
     */

	@Override
	public void keyPressed(KeyEvent e) {
		/*if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
		if(e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;*/
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			stan = 1;
			/*(player.up =false;
			player.down =false;
			player.left =false;
			player.right =true;*/
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			stan = 2;
			/*player.up =false;
			player.down =false;
			player.right =false;
			player.left =true;*/
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			stan = 3;
			/*player.right =false;
			player.down =false;
			player.left =false;
			player.up =true;*/
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			stan = 4;
			/*player.up =false;
			player.right =false;
			player.left =false;
			player.down =true;*/
		}else if(STATE == PAUSE || STATE == GG || STATE == BG){
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				isEnter = true;
			}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				System.exit(1);
			}
		}else if(STATE == END){
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				System.exit(1);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
}
