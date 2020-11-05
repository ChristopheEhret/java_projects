import java.io.Serializable;
import java.util.ArrayList;

public class EnnemiQuiTire implements Serializable {
	int posX, posY, tempsDeRecharge = 100;
	boolean versLaDroite = true;
	boolean tire = false; // boolean qui sinifie que le joueur est dans la ligne
							// de mire et que l'ennemis est entrain de tirer;
	boolean doitTirer = false; // boolean qui sinifie que l'ennemi doit tirer

	public EnnemiQuiTire(int positionX, int positionY, boolean direction) {
		posX = positionX;
		posY = positionY;
		versLaDroite = direction;
	}

	public void actualise() {
		if (tire) {
			if (tempsDeRecharge > 0) {
				tempsDeRecharge-= 3;
			}
			if (tempsDeRecharge <= 0) {
				tempsDeRecharge = 400;
				doitTirer = true;
			}
		}
	}

	public void goDroite() {
		posX -= 6;
	}

	public void goGauche() {
		posX += 6;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void ajoutePosX(int x) {
		posX += x;
	}

	public void ajoutePosX() {
		posX += 6;
	}

	public void enlevePosX() {
		posX -= 6;
	}

	public void setModeTire() {
		tire = true;
	}

	public void setModeRepos() {
		tire = false;
	}

	// l'ennemis est-t'il en train de tirer?
	public boolean getDoitTirer() {
		return doitTirer;
	}

	// défini que l'ennemi doit arreter de tirer car il a déjà tiré
	public void arreteDeTirer() {
		doitTirer = false;
	}

	public boolean getDirection() {
		return versLaDroite;
	}
}
