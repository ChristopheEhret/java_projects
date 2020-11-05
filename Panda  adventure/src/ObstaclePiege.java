
 public class ObstaclePiege extends Obstacle{
	 	boolean piegeActive = false, tombe = false;
	 	int timer = 50;
		public ObstaclePiege(int positionX, int positionY, int haut, int larg) {
			super(positionX, positionY, haut, larg);
			
		}
		public boolean getActive(){
			return piegeActive;
		}
		public void setActive(){
			piegeActive = true;
		}
		public boolean getTombe(){
			return tombe;
		}
		public void tombe(){
			posY += 6;
		}
		public void actualise(){
			if(timer > 0 && piegeActive){
				timer -=2;
				if(timer <= 0){
					tombe = true;
				}
			}
			if(tombe){
				this.tombe();
			}
		}
		
}
