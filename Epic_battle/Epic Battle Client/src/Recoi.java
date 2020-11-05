 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import java.net.Socket;
 import java.util.ArrayList;
 
 class Recoi
   implements Runnable
 {
   Socket socket;
   BufferedReader in;
   PrintWriter out;
   Panneau pan;
 
   public Recoi(Socket s, Panneau p)
   {
     this.pan = p;
     this.socket = s;
     try {
       this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
       this.out = new PrintWriter(this.socket.getOutputStream());
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   public void run() {
     try {
       do { this.pan.temps = 0;
         String msgRecoi = this.in.readLine();
         if (msgRecoi.length() > 10)
         {
           String[] tableInfos = new String[10];
           int posX = 0; int posY = 0; int ID = 0;
           int nbJoueurs = 0;
           int nbMorts = 0, nbKills = 0;
           boolean attaque = false; boolean vivant = false; boolean avance = false;
 
           int index = 0;
           tableInfos[0] = "";
           for (int j = 0; j < msgRecoi.length(); j++) {
             if (msgRecoi.charAt(j) != '/')
             {
            	 tableInfos[index] += msgRecoi.charAt(j);
             } else {
               index++;
               tableInfos[index] = "";
             }
           }
 
           for (int j = 0; j < tableInfos[0].length(); j++) {
             posX = (int)(posX + (tableInfos[0].charAt(tableInfos[0].length() - 1 - j) - '0') * Math.pow(10.0D, j));
           }
           for (int j = 0; j < tableInfos[1].length(); j++) {
             posY = (int)(posY + (tableInfos[1].charAt(tableInfos[1].length() - 1 - j) - '0') * Math.pow(10.0D, j));
           }
           if (tableInfos[2].equals("OUI"))
             attaque = true;
           else {
             attaque = false;
           }
           if (tableInfos[3].equals("OUI"))
             vivant = true;
           else {
             vivant = false;
           }
           String log = tableInfos[4];
 
           for (int j = 0; j < tableInfos[5].length(); j++) {
             ID = (int)(ID + (tableInfos[5].charAt(tableInfos[5].length() - 1 - j) - '0') * Math.pow(10.0D, j));
           }
           String direction = tableInfos[6];
 
           if (tableInfos[7].equals("OUI"))
             avance = true;
           else {
             avance = false;
           }
           
           for (int j = 0; j < tableInfos[8].length(); j++) {
        	 nbMorts = (int)(nbMorts + (tableInfos[8].charAt(tableInfos[8].length() - 1 - j) - '0') * Math.pow(10.0D, j));
	       }
	       for (int j = 0; j < tableInfos[9].length(); j++) {
	         nbKills = (int)(nbKills + (tableInfos[9].charAt(tableInfos[9].length() - 1 - j) - '0') * Math.pow(10.0D, j));
	       }
           
           Combattant c = new Combattant(posX, posY, attaque, vivant, log, ID, direction, avance, nbMorts, nbKills);
 
           boolean b = false;
           for (int i = 0; i < this.pan.listeJoueurs.size(); i++) {
             if (((Combattant)this.pan.listeJoueurs.get(i)).getId() == c.getId()) {
               b = true;
               this.pan.listeJoueurs.get(i).setPos(posX, posY);
               this.pan.listeJoueurs.get(i).setAttaque(attaque);
               this.pan.listeJoueurs.get(i).setAvance(avance);
               this.pan.listeJoueurs.get(i).setDirection(direction);
               this.pan.listeJoueurs.get(i).setVivant(vivant);
               this.pan.listeJoueurs.get(i).setMorts(nbMorts);
               this.pan.listeJoueurs.get(i).setKills(nbKills);
             }
 
           }
 
           if (!b) {
             this.pan.listeJoueurs.add(c);
           }
           
           System.out.println(this.pan.temps);
           
           if(pan.idKill >= 0){
        	   if(pan.idKill == ID){
        		   if(!vivant){
        			   pan.AKill();
        		   }
        	   }
           }
 
           this.out.println("");
           this.out.flush();
 
           this.out.println("");
           this.out.flush();
         }
         if (this.socket.isClosed()) break;  } while (!this.pan.ferme);
     }
     catch (IOException e)
     {
       System.out.println(this.pan.temps);
       System.out.println("fini");
       e.printStackTrace();
     }
   }
 }

