import java.io.Serializable;

public class Obstacle implements Serializable{
	int posX, posY, hauteur, largeur;
	public Obstacle(int positionX, int positionY, int haut, int larg){
		posX = positionX;
		posY = positionY;
		hauteur = haut;
		largeur = larg;
	}
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public int getHauteur(){
		return hauteur;
	}
	public int getLargeur(){
		return largeur;
	}
	public void enlevePosX(){
		posX -= 6;
	}
	public void ajoutePosX(){
		posX += 6;
	}
	public void ajoutePosX(int i){
		posX += i;
	}
}
