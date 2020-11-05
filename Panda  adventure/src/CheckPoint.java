import java.io.Serializable;

public class CheckPoint implements Serializable{
	int posX = 0, posY = 0;
	boolean passe = false;
	public CheckPoint(int positionX, int positionY){
		posX = positionX;
		posY = positionY;
	}
	public void ajoutePosX(){
		posX += 6;
	}
	public void enlevePosX(){
		posX -= 6;
	}
	public void ajoutePosX(int x){
		posX += x;
	}
	public boolean getPasse(){
		return passe;
	}
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public void setPasse(){
		passe = true;
	}
}
