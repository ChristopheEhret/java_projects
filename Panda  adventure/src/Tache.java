
public class Tache {
	int posX, posY;
	public Tache(int positionX, int positionY){
		posX = positionX;
		posY = positionY;
	}
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public void enlevePosX(){
		posX -= 6;
	}
	public void ajoutePosX(){
		posX += 6;
	}
}
