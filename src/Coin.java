import java.awt.Graphics;
import java.awt.Rectangle;


public class Coin extends Rectangle{
	
	
	private static final long serialVersionUID = 1L;
	
	 /**
     * @param n
     * Zmienna wykorzystywana do pêtli
     */
	
	private int n = 2;
	
	/**
     * @param p
     * Zmienna wykorzystywana do pêtli
     */
	
	private int p = 0;
	
	
	/**
     * Okreœlenie hitboxów monety
     *
     */
	
	public Coin(int x,int y){
		setBounds(x,y,8,8);
	}
	
	/**
     * Metoda renderuj¹ca, w tym przypadku dotyczy monet na ekranie
     *
     */
	
	public void render(Graphics g){
		if(n%30==0){
			g.drawImage(Texture.coin2,x,y,32,32,null);
			p+=1;
			if(p==20){
				n+=1;
				p=0;
			}	
		}else{
			g.drawImage(Texture.coin1,x,y,32,32,null);
			n+=1;
		}
			
			
			
	}


}
