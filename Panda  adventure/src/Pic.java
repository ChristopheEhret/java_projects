import java.io.Serializable;

public class Pic implements Serializable {
	int posX, posY;
	int largeur = 45, hauteur = 25;

	public Pic(int x, int y) {
		posX = x;
		posY = y;
	}

	public void ajoutePosX() {
		posX += 6;
	}

	public void ajoutePosX(int x) {
		posX += x;
	}

	public void enlevePosX() {
		posX -= 6;
	}

	public void enlevePosX(int x) {
		posX -= x;
	}
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
}
