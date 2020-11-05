public class Projectile {
	int posX, posY;
	boolean versLaDroite;

	public Projectile(int positionX, int positionY, boolean direction) {
		posX = positionX;
		posY = positionY;
		versLaDroite = direction;
	}

	public void goDroite() {
		posX -= 6;
	}

	public void goGauche() {
		posX += 6;
	}

	public void avance() {
		if (versLaDroite) {
			posX += 4;
		}
		if (!versLaDroite) {
			posX -= 4;
		}
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean getDirection() {
		return versLaDroite;
	}

	public void ajoutePosX() {
		posX += 6;
	}

	public void enlevePosX() {
		posX -= 6;
	}
}
