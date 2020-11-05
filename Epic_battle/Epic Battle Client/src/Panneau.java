 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.FontFormatException;
 import java.awt.Graphics;
 import java.awt.GridLayout;
 import java.awt.Image;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import java.net.Socket;
 import java.util.ArrayList;
 import java.util.Random;
 import javax.imageio.ImageIO;
 import javax.swing.JButton;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 
 public class Panneau extends JPanel
 {
   Socket socketEnvoie;
   Socket socketRecoi;
   BufferedReader in;
   PrintWriter out;
   String login;
   String tueur = "";
   int id = 0;
 
   int temps = 0;
   
   int idKill = -1;
   int timerKill = 0;
 
   static int hitBoxFaceX = 20;
   static int hitBoxFaceY = 34;
   static int hitBoxCoteX = 20;
   static int hitBoxCoteY = 34;
   static int hitBoxEpeeFaceX = 6;
   static int hitBoxEpeeFaceY = 11;
   static int hitBoxEpeeCoteX = 14;
   static int hitBoxEpeeCoteY = 6;
   static int baseTempsAttaque = 20;
   static int tempsRafraichit = 16;
   static int largeurMurs = 20;
   static int hauteurMurs = 50;
   static int vitesse = 3;
   static int posXEpeeBas = 21;
   static int posXEpeeHaut = 23;
   static int posYEpeeCote = 20;
 
   boolean avance = false;
   boolean attaque = false;
   boolean relacheAttaque = true;
   boolean mort = true;
   boolean respawn = false;
   boolean commence = false;
   boolean initRef = false;
   boolean temp = false;
   boolean ferme = false;
   boolean envoie = false;
   int tempsAttaque = 0;
   String direction = "SUD";
 
   Combattant joueur = new Combattant(-100, -100, false, false, "", 0, "", false, 0, 0);
   ArrayList<Combattant> listeJoueurs = new ArrayList();
   ArrayList<Obstacle> listeObstacles = new ArrayList();
 
   int timer = 1;
   boolean marche = false;
 
   int hauteurPan = 0;
   int largeurPan = 0;
   boolean initLarEtHaut = false;
   static Image joueurHaut;
   static Image joueurHautAttaque;
   static Image joueurHautMarche;
   static Image joueurBas;
   static Image joueurBasAttaque;
   static Image joueurBasMarche;
   static Image joueurGauche;
   static Image joueurGaucheAttaque;
   static Image joueurGaucheMarche;
   static Image joueurDroite;
   static Image joueurDroiteAttaque;
   static Image joueurDroiteMarche;
   static Image personnageHaut;
   static Image personnageHautAttaque;
   static Image personnageHautMarche;
   static Image personnageBas;
   static Image personnageBasAttaque;
   static Image personnageBasMarche;
   static Image personnageGauche;
   static Image personnageGaucheAttaque;
   static Image personnageGaucheMarche;
   static Image personnageDroite;
   static Image personnageDroiteAttaque;
   static Image personnageDroiteMarche;
   static Image mur;
   Color c = new Color(53, 0, 0);
 
   JButton boutonCommencer = new JButton("Entrer dans l'arène");
   JLabel titre = new JLabel("Epic Fight");
   JLabel titreMort = new JLabel("Tu est mort !");
   JLabel instruction = new JLabel("Tu est le joueur rouge !");
   JLabel instruction2 = new JLabel("Tue le plus de monde possible");
   JLabel labelMort = new JLabel("Tueur : " + this.tueur);
   Font policeTitre;
   Font policeAutre;
   Font policeInGame;
 
   public Panneau(Socket sr, Socket se, String log, int ID)
   {
     this.socketRecoi = sr;
     this.socketEnvoie = se;
     this.login = log;
     this.id = ID;
 
     initBoutons();
     try {
		initPolice();
	} catch (FontFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     initImages();
     addKeyListener(new Panneau.ClavierListener());
     recupereJoueursPremier();
     Thread t = new Thread(new Panneau.Temps());
     t.start();
     Thread r = new Thread(new Recoi(this.socketRecoi, this));
     r.start();
   }
 
   protected void paintComponent(Graphics g) {
     g.setFont(this.policeAutre);
     g.setColor(Color.white);
     g.fillRect(0, 0, getWidth(), getHeight());
 
     requestFocusInWindow();
 
     if (!this.initLarEtHaut) {
       this.largeurPan = getWidth();
       this.hauteurPan = getHeight();
       this.initLarEtHaut = true;
       System.out.println(this.largeurPan);
       System.out.println(this.hauteurPan);
       initObstacles();
     }
 
     if (!this.mort) {
       g.setFont(policeInGame);
       removeAll();
 
       for (int i = 0; i < this.listeObstacles.size(); i++) {
         for (int j = 0; j * largeurMurs < ((Obstacle)this.listeObstacles.get(i)).getLargeur(); j++) {
           g.drawImage(mur, ((Obstacle)this.listeObstacles.get(i)).getposX() + j * largeurMurs, ((Obstacle)this.listeObstacles.get(i)).getposY() + ((Obstacle)this.listeObstacles.get(i)).getHauteur() - 30, this);
         }
       }
 
       g.setColor(this.c);
       for (int i = 0; i < this.listeObstacles.size(); i++) {
         g.fillRect(((Obstacle)this.listeObstacles.get(i)).getposX(), ((Obstacle)this.listeObstacles.get(i)).getposY(), ((Obstacle)this.listeObstacles.get(i)).getLargeur(), ((Obstacle)this.listeObstacles.get(i)).getHauteur() - 30);
       }
 
       for (int i = 0; i < this.listeJoueurs.size(); i++) {
         if ((!((Combattant)this.listeJoueurs.get(i)).getNom().equals(this.joueur.getNom())) && (((Combattant)this.listeJoueurs.get(i)).getVivant())) {
           if (((Combattant)this.listeJoueurs.get(i)).getDirection().equals("NORD")) {
             if (((Combattant)this.listeJoueurs.get(i)).getAttaque()) {
               g.drawImage(personnageHautAttaque, ((Combattant)this.listeJoueurs.get(i)).getPosX() - 2, ((Combattant)this.listeJoueurs.get(i)).getPosY() - hitBoxEpeeFaceY, this);
             }
             else if ((this.marche) && (((Combattant)this.listeJoueurs.get(i)).getAvance()))
               g.drawImage(personnageHautMarche, ((Combattant)this.listeJoueurs.get(i)).getPosX() - 2, ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             else {
              g.drawImage(personnageHaut, ((Combattant)this.listeJoueurs.get(i)).getPosX() - 2, ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             }
           }
 
           if (((Combattant)this.listeJoueurs.get(i)).getDirection().equals("EST")) {
             if (((Combattant)this.listeJoueurs.get(i)).getAttaque()) {
               g.drawImage(personnageDroiteAttaque, ((Combattant)this.listeJoueurs.get(i)).getPosX(), ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             }
             else if ((this.marche) && (((Combattant)this.listeJoueurs.get(i)).getAvance()))
               g.drawImage(personnageDroiteMarche, ((Combattant)this.listeJoueurs.get(i)).getPosX(), ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             else {
               g.drawImage(personnageDroite, ((Combattant)this.listeJoueurs.get(i)).getPosX(), ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             }
           }
 
           if (((Combattant)this.listeJoueurs.get(i)).getDirection().equals("OUEST")) {
             if (((Combattant)this.listeJoueurs.get(i)).getAttaque()) {
               g.drawImage(personnageGaucheAttaque, ((Combattant)this.listeJoueurs.get(i)).getPosX() - hitBoxEpeeCoteX, ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             }
             else if ((this.marche) && (((Combattant)this.listeJoueurs.get(i)).getAvance()))
               g.drawImage(personnageGaucheMarche, ((Combattant)this.listeJoueurs.get(i)).getPosX(), ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             else {
               g.drawImage(personnageGauche, ((Combattant)this.listeJoueurs.get(i)).getPosX(), ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             }
           }
 
           if (((Combattant)this.listeJoueurs.get(i)).getDirection().equals("SUD")) {
             if (((Combattant)this.listeJoueurs.get(i)).getAttaque()) {
               g.drawImage(personnageBasAttaque, ((Combattant)this.listeJoueurs.get(i)).getPosX() - 2, ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             }
             else if ((this.marche) && (((Combattant)this.listeJoueurs.get(i)).getAvance()))
               g.drawImage(personnageBasMarche, ((Combattant)this.listeJoueurs.get(i)).getPosX() - 2, ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             else {
               g.drawImage(personnageBas, ((Combattant)this.listeJoueurs.get(i)).getPosX() - 2, ((Combattant)this.listeJoueurs.get(i)).getPosY(), this);
             }
           }
 
           g.setColor(Color.black);
           g.drawString(this.listeJoueurs.get(i).getNom() , ((Combattant)this.listeJoueurs.get(i)).getPosX() - (this.listeJoueurs.get(i)).getNom().length() / 2 * 4, ((Combattant)this.listeJoueurs.get(i)).getPosY() - 15);
           g.drawString("T:" + listeJoueurs.get(i).getNbKills() +";M:" + listeJoueurs.get(i).getNbMorts() + ";", listeJoueurs.get(i).getPosX() - ("T:" + listeJoueurs.get(i).getNbKills() +";M: " + listeJoueurs.get(i).getNbMorts() + ";").length() / 2 * 4 , listeJoueurs.get(i).getPosY() + hitBoxFaceY + 15);

         }
 
       }
 
       if (this.joueur.getDirection().equals("NORD")) {
         if (this.joueur.getAttaque()) {
           g.drawImage(joueurHautAttaque, this.joueur.getPosX() - 2, this.joueur.getPosY() - hitBoxEpeeFaceY, this);
         }
         else if ((this.marche) && (this.joueur.getAvance()))
           g.drawImage(joueurHautMarche, this.joueur.getPosX() - 2, this.joueur.getPosY(), this);
         else {
           g.drawImage(joueurHaut, this.joueur.getPosX() - 2, this.joueur.getPosY(), this);
         }
       }
 
       if (this.joueur.getDirection().equals("EST")) {
         if (this.joueur.getAttaque()) {
           g.drawImage(joueurDroiteAttaque, this.joueur.getPosX(), this.joueur.getPosY(), this);
         }
         else if ((this.marche) && (this.joueur.getAvance()))
           g.drawImage(joueurDroiteMarche, this.joueur.getPosX(), this.joueur.getPosY(), this);
         else {
           g.drawImage(joueurDroite, this.joueur.getPosX(), this.joueur.getPosY(), this);
         }
       }
 
       if (this.joueur.getDirection().equals("OUEST")) {
         if (this.joueur.getAttaque()) {
           g.drawImage(joueurGaucheAttaque, this.joueur.getPosX() - hitBoxEpeeCoteX, this.joueur.getPosY(), this);
         }
         else if ((this.marche) && (this.joueur.getAvance()))
           g.drawImage(joueurGaucheMarche, this.joueur.getPosX(), this.joueur.getPosY(), this);
         else {
           g.drawImage(joueurGauche, this.joueur.getPosX(), this.joueur.getPosY(), this);
         }
       }
 
       if (this.joueur.getDirection().equals("SUD")) {
         if (this.joueur.getAttaque()) {
           g.drawImage(joueurBasAttaque, this.joueur.getPosX() - 2, this.joueur.getPosY(), this);
         }
         else if ((this.marche) && (this.joueur.getAvance()))
           g.drawImage(joueurBasMarche, this.joueur.getPosX() - 2, this.joueur.getPosY(), this);
         else {
           g.drawImage(joueurBas, this.joueur.getPosX() - 2, this.joueur.getPosY(), this);
         }
 
       }
 
       g.setColor(Color.black);
       g.drawString(this.login , this.joueur.getPosX() - this.login.length() / 2 * 4, this.joueur.getPosY() - 15);
       g.drawString("T:" + joueur.getNbKills() +";M:" + joueur.getNbMorts() + ";", joueur.getPosX() - ("T:" + joueur.getNbKills() +";M:" + joueur.getNbMorts() + ";").length() / 2 * 4 , joueur.getPosY() + hitBoxFaceY + 15);
       
       this.timer -= 1;
       if (this.timer <= 0) {
         this.marche = (!this.marche);
        this.timer = 20;
       }
     }
     else if (!this.commence) {
    	 
    	 g.setColor(Color.BLACK);
    	 g.setFont(policeTitre);
    	 g.drawString(titre.getText(), (largeurPan/2) - ((titre.getText().length()/2)*34), 100);
    	 g.setFont(policeAutre);
       	 g.drawString(instruction.getText(), (largeurPan/2) - ((instruction.getText().length()/2)*11), 400);
       	 g.drawString(instruction2.getText(), (largeurPan/2) - ((instruction2.getText().length()/2)*11), 500);

       	 boutonCommencer.setBackground(Color.white);
       	 
       	 this.setLayout(new BorderLayout());
    	 this.add(this.boutonCommencer, BorderLayout.SOUTH);
     } else {
    
    	 g.setColor(Color.BLACK);
    	 g.setFont(policeTitre);
    	 g.drawString(titreMort.getText(), (largeurPan/2) - ((titreMort.getText().length()/2)*34), 100);
    	 g.setFont(policeAutre);
    	 g.drawString(labelMort.getText(), (largeurPan/2) - ((labelMort.getText().length()/2)* 11), 300);
       	 g.drawString(instruction.getText(), (largeurPan/2) - ((instruction.getText().length()/2)*11), 400);
       	 g.drawString(instruction2.getText(), (largeurPan/2) - ((instruction2.getText().length()/2)*11), 500);

       	 this.setLayout(new BorderLayout());
    	 this.add(this.boutonCommencer, BorderLayout.SOUTH);
     }
   }
 
   public void recupereJoueursPremier()
   {
     try
     {
       if (this.ferme) {
         this.out = new PrintWriter(this.socketEnvoie.getOutputStream());
         this.out.println("QUIT");
         this.out.flush();
       } else {
         this.out = new PrintWriter(this.socketEnvoie.getOutputStream());
         this.out.println("a");
         this.out.flush();
         System.out.println(this.temps);
 
         ArrayList listeJoueurs2 = new ArrayList();
 
         int posX = 0; int posY = 0; int ID = 0;
         int nbJoueurs = 0;
         int nbMorts = 0, nbKills = 0;
         boolean attaque = false; boolean vivant = false; boolean avance = false;
 
         this.in = new BufferedReader(new InputStreamReader(this.socketEnvoie.getInputStream()));
         String msgRecoi = this.in.readLine();
         System.out.println(msgRecoi);
         System.out.println(this.temps);
 
         boolean c = false;
         String msgNbJoueurs = "";
         int z = 0;
         while (!c) {
           if (msgRecoi.charAt(z) != '/')
             msgNbJoueurs = msgNbJoueurs + msgRecoi.charAt(z);
           else {
             c = true;
           }
           z++;
         }
         for (int j = 0; j < msgNbJoueurs.length(); j++) {
           nbJoueurs = (int)(nbJoueurs + (msgNbJoueurs.charAt(msgNbJoueurs.length() - 1 - j) - '0') * Math.pow(10.0D, j));
         }
         String[] tableInfos = new String[nbJoueurs * 11];
 
         if (nbJoueurs > 0) {
           int index = 0;
           tableInfos[0] = "";
           for (int j = z; j < msgRecoi.length(); j++) {
             if (msgRecoi.charAt(j) != '/')
             {
            	 tableInfos[index] += msgRecoi.charAt(j);
             } else {
               index++;
               tableInfos[index] = "";
             }
           }
 
           int nbJoueursTraites = 0;
           boolean b = true;
           while (b) {
             posX = 0;
             posY = 0;
             ID = 0;
             attaque = false;
             vivant = false;
             avance = false;
 
             for (int j = 0; j < tableInfos[(nbJoueursTraites * 11 + 0)].length(); j++) {
               posX = (int)(posX + (tableInfos[(nbJoueursTraites * 11 + 0)].charAt(tableInfos[(nbJoueursTraites * 11 + 0)].length() - 1 - j) - '0') * Math.pow(10.0D, j));
             }
             for (int j = 0; j < tableInfos[(nbJoueursTraites * 11 + 1)].length(); j++) {
               posY = (int)(posY + (tableInfos[(nbJoueursTraites * 11 + 1)].charAt(tableInfos[(nbJoueursTraites * 11 + 1)].length() - 1 - j) - '0') * Math.pow(10.0D, j));
             }
             
             String log = tableInfos[(nbJoueursTraites * 11 + 2)];
 
             for (int j = 0; j < tableInfos[(nbJoueursTraites * 11 + 3)].length(); j++) {
               ID = (int)(ID + (tableInfos[(nbJoueursTraites * 11 + 3)].charAt(tableInfos[(nbJoueursTraites * 11 + 3)].length() - 1 - j) - '0') * Math.pow(10.0D, j));
             }
             if (tableInfos[(nbJoueursTraites * 11 + 4)].equals("OUI"))
               attaque = true;
             else {
               attaque = false;
             }
             if (tableInfos[(nbJoueursTraites * 11 + 5)].equals("OUI"))
               vivant = true;
             else {
               vivant = false;
             }
             String direction = tableInfos[(nbJoueursTraites * 11 + 6)];
 
             if (tableInfos[(nbJoueursTraites * 11 + 7)].equals("OUI"))
               avance = true;
             else {
               avance = false;
             }
             if (tableInfos[(nbJoueursTraites * 11 + 8)].equals("NON")) {
               b = false;
             }
             
             for (int j = 0; j < tableInfos[(nbJoueursTraites * 11 + 9)].length(); j++) {
            	 nbMorts = (int)(nbMorts + (tableInfos[(nbJoueursTraites * 11 + 9)].charAt(tableInfos[(nbJoueursTraites * 11 + 9)].length() - 1 - j) - '0') * Math.pow(10, j));
             }
             for (int j = 0; j < tableInfos[(nbJoueursTraites * 11 + 10)].length(); j++) {
            	 nbKills = (int)(nbKills + (tableInfos[(nbJoueursTraites * 11 + 10)].charAt(tableInfos[(nbJoueursTraites * 11 + 10)].length() - 1 - j) - '0') * Math.pow(10, j));
             }
               
             if (!log.equals(this.login)) {
               listeJoueurs2.add(new Combattant(posX, posY, attaque, vivant, log, ID, direction, avance, nbMorts, nbKills));
             }
             nbJoueursTraites++;
           }
 
         }
 
         this.listeJoueurs = listeJoueurs2;
       }
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   public void envoiePersoServeur()
   {
     try
     {
       this.envoie = true;
       this.joueur.setAvance(this.avance);
       this.joueur.setDirection(this.direction);
       this.joueur.setAttaque(this.attaque);
 
       if (this.ferme) {
         this.out = new PrintWriter(this.socketEnvoie.getOutputStream());
         this.out.println("QUIT");
         this.out.flush();
         System.out.println(id);
       }
       else
       {
         String msgVivant;
         if (this.joueur.getVivant())
           msgVivant = "OUI";
         else
           msgVivant = "NON";
         String msgAttaque;
         if (this.joueur.getAttaque())
           msgAttaque = "OUI";
         else
           msgAttaque = "NON";
         String msgAvance;
         if (this.joueur.getAvance())
           msgAvance = "OUI";
         else {
           msgAvance = "NON";
         }
         String msgEnvoie = this.joueur.getPosX() + "/" + this.joueur.getPosY() + "/" + msgVivant + "/" + msgAttaque + "/" + this.joueur.getDirection() + "/" + msgAvance + "/" + joueur.getNbMorts() + "/" + joueur.getNbKills();
         this.out = new PrintWriter(this.socketEnvoie.getOutputStream());
         this.out.println(msgEnvoie);
         this.out.flush();
 
         this.envoie = false;
       }
 
     }
     catch (IOException e)
     {
       System.out.println("fini");
       e.printStackTrace();
       this.envoie = false;
     }
   }
 
   public void calculsPositionEtMort()
   {
     if (this.joueur.getVivant()) {
       boolean b = false;
       for (int i = 0; i < this.listeJoueurs.size(); i++) {
         if ((!b) && (this.listeJoueurs.get(i).getId() != this.id) && (this.listeJoueurs.get(i).getAttaque())) {
           b = verifieTouche(this.joueur.getPosX(), this.joueur.getPosY(), this.joueur.getDirection(), this.listeJoueurs.get(i).getPosX(), this.listeJoueurs.get(i).getPosY(), this.listeJoueurs.get(i).getDirection());
           if (b) {
             this.tueur = ((Combattant)this.listeJoueurs.get(i)).getNom();
             labelMort.setText("Tueur : " + tueur);
           }
         }
       }
       this.mort = b;
       this.joueur.setVivant(!b);
       if (b) {
    	 joueur.addMort();
         envoiePersoServeur();
       }
 
       if(joueur.getAttaque()){
    	   int id = -1;
    	   boolean k = false;
    	   for(int i = 0; i < listeJoueurs.size(); i++){
    		   if (this.listeJoueurs.get(i).getId() != this.id){
    			   k = verifieTouche(this.listeJoueurs.get(i).getPosX(), this.listeJoueurs.get(i).getPosY(), this.listeJoueurs.get(i).getDirection(), this.joueur.getPosX(), this.joueur.getPosY(), this.joueur.getDirection());
    			   if(k){
    				   id = listeJoueurs.get(i).getId();
    			   }
    		   }
    	   }
    	   if(k){
    		   idKill = id;
    		   timerKill = 50;
    	   }
       }
       
       if(idKill >= 0 && timerKill > 0){
    	   timerKill --;
    	   if(timerKill <= 0){
    		   idKill = -1;
    		   timerKill = 0;
    	   }
       }
       
       if (this.avance)
       {
         int vitesseX = 0;
         int vitesseY = 0;
 
         if (this.direction.equals("NORD")) {
           vitesseY = -vitesse;
         }
         if (this.direction.equals("SUD")) {
           vitesseY = vitesse;
         }
         if (this.direction.equals("EST")) {
           vitesseX = vitesse;
         }
         if (this.direction.equals("OUEST")) {
           vitesseX = -vitesse;
         }
         this.joueur.setPos(this.joueur.getPosX() + vitesseX, this.joueur.getPosY() + vitesseY);
 
         boolean c = false;
         for (int i = 0; i < this.listeObstacles.size(); i++) {
           if (!c)
             c = verifieCollisionMurs(this.joueur.getPosX(), this.joueur.getPosY(), this.direction, ((Obstacle)this.listeObstacles.get(i)).getposX(), ((Obstacle)this.listeObstacles.get(i)).getposY(), ((Obstacle)this.listeObstacles.get(i)).getLargeur(), ((Obstacle)this.listeObstacles.get(i)).getHauteur());
         }
         if (c) {
           this.joueur.setPos(this.joueur.getPosX() - vitesseX, this.joueur.getPosY() - vitesseY);
         }
       }
       this.joueur.setDirection(this.direction);
 
       if (this.attaque) {
         this.tempsAttaque -= 1;
         if (this.tempsAttaque <= 0) {
           this.attaque = false;
           this.tempsAttaque = 0;
         }
       }
 
       this.joueur.setAttaque(this.attaque);
     }
     else if (this.respawn) {
       this.mort = false;
       this.respawn = false;
 
       this.joueur.setVivant(true);
       this.joueur.setDirection("SUD");
       this.joueur.setAttaque(false);
 
       Random rnd = new Random();
 
       this.joueur.setPos(rnd.nextInt(largeurPan - largeurMurs * 2 - hitBoxFaceY) + largeurMurs, rnd.nextInt(hauteurPan - hauteurMurs * 2 - hitBoxFaceX) + hauteurMurs);
 
       envoiePersoServeur();
     }
   }
   
   //méthode qui se déclanche si le joueur a tué qqun
   public void AKill(){
	   joueur.addKill();
	   idKill = -1;
	   timerKill = 0;
	   envoiePersoServeur();
   }
 
   public void actualiseJoueurs()
   {
     for (int i = 0; i < this.listeJoueurs.size(); i++)
       calculPosJoueur((Combattant)this.listeJoueurs.get(i));
   }
 
   public void calculPosJoueur(Combattant com)
   {
     Combattant autreJoueur = com;
     if ((autreJoueur.getAvance()) && 
       (autreJoueur.getVivant())) {
       if (autreJoueur.getDirection().equals("NORD")) {
         autreJoueur.setPos(autreJoueur.getPosX(), autreJoueur.getPosY() - vitesse);
         boolean c = false;
         for (int i = 0; i < this.listeObstacles.size(); i++) {
           if (!c)
             c = verifieCollisionMurs(autreJoueur.getPosX(), autreJoueur.getPosY(), autreJoueur.getDirection(), ((Obstacle)this.listeObstacles.get(i)).getposX(), ((Obstacle)this.listeObstacles.get(i)).getposY(), ((Obstacle)this.listeObstacles.get(i)).getLargeur(), ((Obstacle)this.listeObstacles.get(i)).getHauteur());
         }
         if (c)
           autreJoueur.setPos(autreJoueur.getPosX(), autreJoueur.getPosY() + vitesse);
       }
       if (autreJoueur.getDirection().equals("SUD")) {
         autreJoueur.setPos(autreJoueur.getPosX(), autreJoueur.getPosY() + vitesse);
         boolean c = false;
         for (int i = 0; i < this.listeObstacles.size(); i++) {
           if (!c)
             c = verifieCollisionMurs(autreJoueur.getPosX(), autreJoueur.getPosY(), autreJoueur.getDirection(), ((Obstacle)this.listeObstacles.get(i)).getposX(), ((Obstacle)this.listeObstacles.get(i)).getposY(), ((Obstacle)this.listeObstacles.get(i)).getLargeur(), ((Obstacle)this.listeObstacles.get(i)).getHauteur());
         }
         if (c)
           autreJoueur.setPos(autreJoueur.getPosX(), autreJoueur.getPosY() - vitesse);
       }
       if (autreJoueur.getDirection().equals("EST")) {
         autreJoueur.setPos(autreJoueur.getPosX() + vitesse, autreJoueur.getPosY());
         boolean c = false;
         for (int i = 0; i < this.listeObstacles.size(); i++) {
           if (!c)
             c = verifieCollisionMurs(autreJoueur.getPosX(), autreJoueur.getPosY(), autreJoueur.getDirection(), ((Obstacle)this.listeObstacles.get(i)).getposX(), ((Obstacle)this.listeObstacles.get(i)).getposY(), ((Obstacle)this.listeObstacles.get(i)).getLargeur(), ((Obstacle)this.listeObstacles.get(i)).getHauteur());
         }
         if (c)
           autreJoueur.setPos(autreJoueur.getPosX() - vitesse, autreJoueur.getPosY());
       }
       if (autreJoueur.getDirection().equals("OUEST")) {
         autreJoueur.setPos(autreJoueur.getPosX() - vitesse, autreJoueur.getPosY());
         boolean c = false;
         for (int i = 0; i < this.listeObstacles.size(); i++) {
           if (!c)
             c = verifieCollisionMurs(autreJoueur.getPosX(), autreJoueur.getPosY(), autreJoueur.getDirection(), ((Obstacle)this.listeObstacles.get(i)).getposX(), ((Obstacle)this.listeObstacles.get(i)).getposY(), ((Obstacle)this.listeObstacles.get(i)).getLargeur(), ((Obstacle)this.listeObstacles.get(i)).getHauteur());
         }
         if (c)
           autreJoueur.setPos(autreJoueur.getPosX() + vitesse, autreJoueur.getPosY());
       }
     }
   }
 
   public static boolean verifieTouche(int posXJoueur, int posYJoueur, String directionJoueur, int posXEnnemi, int posYEnnemi, String directionEnnemi)
   {
     boolean b = false;
     if ((directionJoueur.equals("NORD")) || (directionJoueur.equals("SUD"))) {
       if (directionEnnemi.equals("NORD")) {
         if ((posYEnnemi - hitBoxEpeeFaceY <= posYJoueur + hitBoxFaceY) && (posYEnnemi + hitBoxEpeeFaceY >= posYJoueur) && (posXEnnemi + (posXEpeeHaut + hitBoxEpeeFaceX) >= posXJoueur) && (posXEnnemi + posXEpeeHaut <= posXJoueur + hitBoxFaceX))
           b = true;
       }
       else if (directionEnnemi.equals("SUD")) {
         if ((posYEnnemi + hitBoxFaceY + hitBoxEpeeFaceY >= posYJoueur) && (posYEnnemi + hitBoxFaceY + hitBoxEpeeFaceY <= posYJoueur + hitBoxFaceY) && (posXEnnemi + (posXEpeeBas + hitBoxEpeeFaceX) >= posXJoueur) && (posXEnnemi + posXEpeeBas <= posXJoueur + hitBoxFaceX))
           b = true;
       }
       else if (directionEnnemi.equals("OUEST")) {
         if ((posYEnnemi + (posYEpeeCote + hitBoxEpeeCoteY) >= posYJoueur) && (posYEnnemi + posYEpeeCote <= posYJoueur + hitBoxFaceY) && (posXEnnemi - hitBoxEpeeCoteX >= posXJoueur) && (posXEnnemi - hitBoxEpeeCoteX <= posXJoueur + hitBoxFaceX))
           b = true;
       }
       else if ((directionEnnemi.equals("EST")) && 
         (posYEnnemi + (posYEpeeCote + hitBoxEpeeCoteY) >= posYJoueur) && (posYEnnemi + posYEpeeCote <= posYJoueur + hitBoxFaceY) && (posXEnnemi + hitBoxCoteX + hitBoxEpeeCoteX <= posXJoueur + hitBoxFaceX) && (posXEnnemi + hitBoxCoteX + hitBoxEpeeCoteX >= posXJoueur)) {
         b = true;
       }
     }
     else if ((directionJoueur.equals("EST")) || (directionJoueur.equals("OUEST"))) {
       if (directionEnnemi.equals("NORD")) {
         if ((posYEnnemi - hitBoxEpeeFaceY <= posYJoueur + hitBoxCoteY) && (posYEnnemi + hitBoxEpeeFaceY >= posYJoueur) && (posXEnnemi + (posXEpeeHaut + hitBoxEpeeFaceX) >= posXJoueur) && (posXEnnemi + posXEpeeHaut <= posXJoueur + hitBoxCoteX))
           b = true;
       }
       else if (directionEnnemi.equals("SUD")) {
         if ((posYEnnemi + hitBoxFaceY + hitBoxEpeeFaceY >= posYJoueur) && (posYEnnemi + hitBoxFaceY + hitBoxEpeeFaceY <= posYJoueur + hitBoxCoteY) && (posXEnnemi + (posXEpeeBas + hitBoxEpeeFaceX) >= posXJoueur) && (posXEnnemi + posXEpeeBas <= posXJoueur + hitBoxCoteX))
           b = true;
       }
       else if (directionEnnemi.equals("OUEST")) {
         if ((posYEnnemi + (posYEpeeCote + hitBoxEpeeCoteY) >= posYJoueur) && (posYEnnemi + posYEpeeCote <= posYJoueur + hitBoxCoteY) && (posXEnnemi - hitBoxEpeeCoteX >= posXJoueur) && (posXEnnemi - hitBoxEpeeCoteX <= posXJoueur + hitBoxCoteX))
           b = true;
       }
       else if ((directionEnnemi.equals("EST")) && 
         (posYEnnemi + (posYEpeeCote + hitBoxEpeeCoteY) >= posYJoueur) && (posYEnnemi + posYEpeeCote <= posYJoueur + hitBoxCoteY) && (posXEnnemi + hitBoxCoteX + hitBoxEpeeCoteX <= posXJoueur + hitBoxCoteX) && (posXEnnemi + hitBoxCoteX + hitBoxEpeeCoteX >= posXJoueur)) {
         b = true;
       }
     }
 
     return b;
   }
 
   public boolean verifieCollisionMurs(int posXJoueur, int posYJoueur, String direc, int posXObstacle, int posYObstacle, int largeurObstacle, int hauteurObstacle)
   {
     boolean b = false;
     if (((direc.equals("EST")) || (direc.equals("OUEST"))) && 
       (posXJoueur < posXObstacle + largeurObstacle) && 
       (posXJoueur + hitBoxCoteX > posXObstacle) && 
       (posYJoueur < posYObstacle + hauteurObstacle) && 
       (posYJoueur + hitBoxCoteY > posYObstacle)) {
       b = true;
     }
 
     if (((direc.equals("NORD")) || (direc.equals("SUD"))) && 
       (posXJoueur < posXObstacle + largeurObstacle) && 
       (posXJoueur + hitBoxFaceX > posXObstacle) && 
       (posYJoueur < posYObstacle + hauteurObstacle) && 
       (posYJoueur + hitBoxFaceY > posYObstacle)) {
       b = true;
     }
 
     return b;
   }
 
   public void initObstacles()
   {
     this.listeObstacles.add(new Obstacle(0, 0, largeurMurs, this.hauteurPan));
     this.listeObstacles.add(new Obstacle(this.largeurPan - largeurMurs, 0, largeurMurs, this.hauteurPan));
     this.listeObstacles.add(new Obstacle(largeurMurs, this.hauteurPan - hauteurMurs, this.largeurPan - largeurMurs * 2, hauteurMurs));
     this.listeObstacles.add(new Obstacle(largeurMurs, 0, this.largeurPan - largeurMurs * 2, hauteurMurs));
   }
 
   public void initBoutons() {
     this.boutonCommencer.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         Panneau.this.commence = true;
         Panneau.this.respawn = true;
         Panneau.this.relacheAttaque = true;
         Panneau.this.attaque = false;
         Panneau.this.tempsAttaque = 0;
         if(!commence){
	         try {
	           Panneau.this.in = new BufferedReader(new InputStreamReader(Panneau.this.socketRecoi.getInputStream()));
	         }
	         catch (IOException e) {
	           e.printStackTrace();
	         }
         }
       }
     });
     /*this.boutonRespawn.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         Panneau.this.respawn = true;
         Panneau.this.relacheAttaque = true;
         Panneau.this.attaque = false;
         Panneau.this.tempsAttaque = 0;
       }
     });*/
   }
 
   public void initPolice() throws FontFormatException {
     try {
       Font f = Font.createFont(0, new File("police.ttf"));
       this.policeTitre = f.deriveFont(45.0F);
 
       f = Font.createFont(0, new File("police.ttf"));
       this.policeAutre = f.deriveFont(15.0F);
       
       f = Font.createFont(0, new File("police.ttf"));
       policeInGame = f.deriveFont(10.0F);
       
       boutonCommencer.setFont(policeAutre);
      // boutonRespawn.setFont(policeAutre);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   public static void initImages()
   {
     try {
       joueurHaut = ImageIO.read(new File("Images/joueur/joueur_haut.png"));
       joueurHautMarche = ImageIO.read(new File("Images/joueur/joueur_haut_marche.png"));
       joueurHautAttaque = ImageIO.read(new File("Images/joueur/joueur_haut_Attaque.png"));
       joueurBas = ImageIO.read(new File("Images/joueur/joueur_bas.png"));
       joueurBasAttaque = ImageIO.read(new File("Images/joueur/joueur_bas_attaque.png"));
       joueurBasMarche = ImageIO.read(new File("Images/joueur/joueur_bas_marche.png"));
       joueurGauche = ImageIO.read(new File("Images/joueur/joueur_gauche.png"));
       joueurGaucheAttaque = ImageIO.read(new File("Images/joueur/joueur_gauche_attaque.png"));
       joueurGaucheMarche = ImageIO.read(new File("Images/joueur/joueur_gauche_marche.png"));
       joueurDroite = ImageIO.read(new File("Images/joueur/joueur_droite.png"));
       joueurDroiteAttaque = ImageIO.read(new File("Images/joueur/joueur_droite_attaque.png"));
       joueurDroiteMarche = ImageIO.read(new File("Images/joueur/joueur_droite_marche.png"));
 
       personnageHaut = ImageIO.read(new File("Images/personnage/haut.png"));
       personnageHautMarche = ImageIO.read(new File("Images/personnage/haut_marche.png"));
       personnageHautAttaque = ImageIO.read(new File("Images/personnage/haut_Attaque.png"));
       personnageBas = ImageIO.read(new File("Images/personnage/bas.png"));
       personnageBasAttaque = ImageIO.read(new File("Images/personnage/bas_attaque.png"));
       personnageBasMarche = ImageIO.read(new File("Images/personnage/bas_marche.png"));
       personnageGauche = ImageIO.read(new File("Images/personnage/gauche.png"));
       personnageGaucheAttaque = ImageIO.read(new File("Images/personnage/gauche_attaque.png"));
       personnageGaucheMarche = ImageIO.read(new File("Images/personnage/gauche_marche.png"));
       personnageDroite = ImageIO.read(new File("Images/personnage/droite.png"));
       personnageDroiteAttaque = ImageIO.read(new File("Images/personnage/droite_attaque.png"));
       personnageDroiteMarche = ImageIO.read(new File("Images/personnage/droite_marche.png"));
 
       mur = ImageIO.read(new File("Images/autre/mur.png"));
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
   class ClavierListener implements KeyListener {
     ClavierListener() {
     }
 
     public void keyPressed(KeyEvent event) { 
    	 if ((event.getKeyCode() == 32) && (Panneau.this.relacheAttaque)) {
         if (!Panneau.this.mort) {
           if (!Panneau.this.attaque) {
             Panneau.this.attaque = true;
             Panneau.this.relacheAttaque = false;
             Panneau.this.tempsAttaque = Panneau.baseTempsAttaque;
             Panneau.this.envoiePersoServeur();
           }
         } else {
           Panneau.this.respawn = true;
           Panneau.this.envoiePersoServeur();
         }
       }
 
       if (((event.getKeyCode() == 38) || (event.getKeyCode() == 90)) && (
         (!Panneau.this.direction.equals("NORD")) || (!Panneau.this.avance))) {
         Panneau.this.direction = "NORD";
         Panneau.this.avance = true;
         Panneau.this.envoiePersoServeur();
       }
 
       if (((event.getKeyCode() == 40) || (event.getKeyCode() == 83)) && (
         (!Panneau.this.direction.equals("SUD")) || (!Panneau.this.avance))) {
         Panneau.this.direction = "SUD";
         Panneau.this.avance = true;
         Panneau.this.envoiePersoServeur();
       }
 
       if (((event.getKeyCode() == 39) || (event.getKeyCode() == 68)) && (
         (!Panneau.this.direction.equals("EST")) || (!Panneau.this.avance))) {
         Panneau.this.direction = "EST";
         Panneau.this.avance = true;
         Panneau.this.envoiePersoServeur();
       }
 
       if (((event.getKeyCode() == 37) || (event.getKeyCode() == 81)) && (
         (!Panneau.this.direction.equals("OUEST")) || (!Panneau.this.avance))) {
         Panneau.this.direction = "OUEST";
         Panneau.this.avance = true;
         Panneau.this.envoiePersoServeur();
       }
     }
 
     public void keyReleased(KeyEvent event) {
       if ((event.getKeyCode() == 38) || (event.getKeyCode() == 90)) {
         if (Panneau.this.direction.equals("NORD"))
           Panneau.this.avance = false;
         Panneau.this.envoiePersoServeur();
       }
       if ((event.getKeyCode() == 40) || (event.getKeyCode() == 83)) {
         if (Panneau.this.direction.equals("SUD"))
           Panneau.this.avance = false;
         Panneau.this.envoiePersoServeur();
       }
       if ((event.getKeyCode() == 39) || (event.getKeyCode() == 68)) {
         if (Panneau.this.direction.equals("EST"))
           Panneau.this.avance = false;
         Panneau.this.envoiePersoServeur();
       }
       if ((event.getKeyCode() == 37) || (event.getKeyCode() == 81)) {
         if (Panneau.this.direction.equals("OUEST"))
           Panneau.this.avance = false;
         Panneau.this.envoiePersoServeur();
       }
       if (event.getKeyCode() == 32) {
         Panneau.this.relacheAttaque = true;
         Panneau.this.attaque = false;
         Panneau.this.tempsAttaque = 0;
         Panneau.this.envoiePersoServeur();
       }
     }
     public void keyTyped(KeyEvent event) {
     }
   }
   class Temps implements Runnable {
     Temps() {
     }
 
     public void run() { 
    	 while (true) {
    		 Panneau.this.temps += 1;
         try {
           Thread.sleep(1L);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
     }
   }
 }
