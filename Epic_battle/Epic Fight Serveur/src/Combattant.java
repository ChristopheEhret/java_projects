
public class Combattant {
	int posX, posY;
	boolean attaque, vivant, avance;
	String nom;
	int id;
	String direction;
	int nbMorts, nbKills;
	
	public Combattant(int x, int y, boolean att, boolean vie, String log, int ID, String dir, boolean av, int morts, int kills){
		posX = x;
		posY = y;
		attaque = att;
		vivant = vie;
		nom = log;
		id = ID;
		direction = dir;
		avance = av;
		nbMorts = morts;
		nbKills = kills;
	}
	public void setPos(int x, int y){
		posX = x;
		posY = y;
	}
	public void setAttaque(boolean a){
		attaque = a;
	}
	public void setVivant(boolean v){
		vivant = v;
	}
	public void setDirection(String dir){
		direction = dir;
	}
	public void setAvance(boolean av){
		avance = av;
	}
	public void addKill(){
		nbKills ++;
	}
	public void addMort(){
		nbMorts++;
	}
	public void setKills(int k){
		nbKills = k;
	}
	public void setMorts(int m){
		nbMorts = m;
	}
	
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public boolean getAttaque(){
		return attaque;
	}
	public boolean getVivant(){
		return vivant;
	}
	public String getNom(){
		return nom;
	}
	public int getId(){
		return id;
	}
	public String getDirection(){
		return direction;
	}
	public boolean getAvance(){
		return avance;
	}
	public int getNbMorts(){
		return nbMorts;
	}
	public int getNbKills(){
		return nbKills;
	}
}
