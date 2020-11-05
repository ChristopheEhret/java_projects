 public class Obstacle
 {
   int posX;
   int posY;
   int largeur;
   int hauteur;
 
   public Obstacle(int x, int y, int larg, int haut)
   {
     this.posX = x;
     this.posY = y;
     this.largeur = larg;
     this.hauteur = haut;
   }
 
   public int getposX() {
     return this.posX;
   }
   public int getposY() {
     return this.posY;
   }
   public int getLargeur() {
     return this.largeur;
   }
   public int getHauteur() {
     return this.hauteur;
   }
 }