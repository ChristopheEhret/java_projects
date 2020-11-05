import java.io.Serializable;

public class Ennemi implements Serializable{
	int posX, posY, iD, xDroite, xGauche;
	boolean goDroite;
	public Ennemi(int positionX, int positionY, int allerXDroite, int allerXGauche, boolean direction, int identite){
		posX = positionX;
		posY = positionY;
		xDroite = allerXDroite;
		xGauche = allerXGauche;
		goDroite = direction;
		iD = identite;
	}
	public void avancer(){
		if(goDroite == true){
			posX +=2;
		}
		if(goDroite == false){
			posX -=2;
		}
		if(posX >= xDroite){
			goDroite = false;
		}
		if(posX <= xGauche){
			goDroite = true;
		}
	}
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public int getID(){
		return iD;
	}
	public void goDroite(){
		posX -= 6;
		xDroite -=6;
		xGauche -=6;
	}
	public void goGauche(){
		posX += 6;
		xDroite +=6;
		xGauche +=6;
	}
	public boolean getDirection(){
		return goDroite;
	}
	public void setPlace(int place){
		iD = place;
	}
	public int getPlace(){
		return iD;
	}
	public void ajoutePosX(int x){
		posX += x;
		xDroite += x;
		xGauche += x;
	}
}
