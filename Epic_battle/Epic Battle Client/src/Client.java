 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.PrintWriter;
 import java.net.Socket;
 import javax.swing.JFrame;
 import javax.swing.JMenuBar;
 import javax.swing.JMenuItem;
 import javax.swing.JOptionPane;
 
 public class Client extends JFrame
 {
   Socket socketEnvoie;
   Socket socketRecoi;
   BufferedReader in;
   PrintWriter out;
   String login;
   String version = "1.0";
   
 //  String adresseServeur = "185.10.19.142";
   String adresseServeur = "localhost";
   JOptionPane jop = new JOptionPane();
 
   JMenuBar bar = new JMenuBar();
   JMenuItem ferme = new JMenuItem("Fermer");
   Panneau pan;
 
   public Client(String adresse)
   {
	 if(adresse != null)
		 this.adresseServeur = adresse;
	 
     try
     {
       this.socketRecoi = new Socket(this.adresseServeur, 85);
       this.socketEnvoie = new Socket(this.adresseServeur, 86);
     } catch (IOException e) {
       JOptionPane.showMessageDialog(
         null, 
         "Le serveur n'est pas disponible ! Veuillez réessayer plus tard.", 
         "Erreur!", 0);
       System.exit(0);
     }
     
     this.login = JOptionPane.showInputDialog(null, "Veuillez entrer votre pseudo ! (Pas plus de 12 caractères)", 
       "Identification", 3);
     if (this.login == null) {
       this.login = "Âne au Nyme";
     }
 
     while (this.login.length() > 12) {
       this.login = JOptionPane.showInputDialog(null, "Votre pseudo est trop long ! Veuillez re-entrer votre pseudo !", 
         "Identification", 3);
       if (this.login == null) {
         this.login = "Âne au Nymes";
       }
     }
     
     Connection();
 
     this.ferme.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         Client.this.pan.ferme = true;
         Client.this.pan.envoiePersoServeur();
         
         try {
           Thread.sleep(1000L);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
         try {
           Client.this.socketEnvoie.close();
           Client.this.socketRecoi.close();
         }
         catch (IOException e) {
           e.printStackTrace();
         }
         System.exit(0);
       }
     });
     this.bar.add(this.ferme);
 
     Thread r = new Thread(new Client.Rafraichit(16));
     r.start();
     Thread a = new Thread(new Client.Actualise(1));
     a.start();
 
     setTitle("Epic Fight / " + this.login);
     setSize(664, 686);
     setLocationRelativeTo(null);
     setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
     setResizable(false);
     setJMenuBar(this.bar);
     setContentPane(this.pan);
     setVisible(true);
 
     this.pan.repaint();
     repaint();
   }
 
   public static void main(String[] args)
   {
	 String adresse = JOptionPane.showInputDialog(null, "Veuillez entrer l'adresse du serveur", 
		       "Adresse", 3);
     Client c = new Client(adresse);
   }
   public void Connection() {
     try {
       this.out = new PrintWriter(this.socketRecoi.getOutputStream());
       this.out.println(this.version);
       this.out.flush();
 
       this.in = new BufferedReader(new InputStreamReader(this.socketRecoi.getInputStream()));
       if (!this.in.readLine().equals("NON VERSION"))
       {
         try {
           Thread.sleep(50L);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
 
         this.out = new PrintWriter(this.socketRecoi.getOutputStream());
         this.out.println(this.login);
         this.out.flush();
 
         this.in = new BufferedReader(new InputStreamReader(this.socketRecoi.getInputStream()));
         if (!this.in.readLine().equals("NON DEJA UTILISE"))
         {
           this.in = new BufferedReader(new InputStreamReader(this.socketRecoi.getInputStream()));
           String msgId = this.in.readLine();
           int id = 0;
           for (int i = 0; i < msgId.length(); i++)
             id = (int)(id + (msgId.charAt(msgId.length() - 1 - i) - '0') * Math.pow(10.0D, i));
           this.pan = new Panneau(this.socketRecoi, this.socketEnvoie, this.login, id);
         }
         else
         {
           JOptionPane.showMessageDialog(
             null, 
             "Le pseudo que vous avez entré déjà  utilisé !", 
             "Erreur!", 0);
         }
       } else {
         JOptionPane.showMessageDialog(
           null, 
           "Vous n'utilisez pas la bonne version ! Téléchargez la dernière version sur ma page facebook (Negeko-san)", 
           "Erreur!", 0);
       }
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   public void repaindre() { this.pan.actualiseJoueurs();
     this.pan.calculsPositionEtMort();
     this.pan.repaint();
     repaint();
   }
 
   class Actualise
     implements Runnable
   {
     int temps = 0;
 
     public Actualise(int t) { this.temps = t; }
 
     public void run()
     {
       while (true)
         try {
           Thread.sleep(0L);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
     }
   }
 
   class Rafraichit
     implements Runnable
   {
     int temps = 0;
 
     public Rafraichit(int t) { this.temps = t; }
 
     public void run() {
       while (true) {
         Client.this.repaindre();
         try {
           Thread.sleep(this.temps);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       }
     }
   }
 }