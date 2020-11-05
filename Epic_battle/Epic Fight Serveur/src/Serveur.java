import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;


/*public class Serveur extends JFrame{
	ArrayList<Combattant> listeJoueurs = new ArrayList<Combattant>();
	String version = "1.0";
	int temps = 0;
	
	boolean maj = false;
	boolean envoie = false;
	ArrayList listeMaj = new ArrayList();
	public Serveur(){
		ServerSocket socket;
		
		try {
			socket = new ServerSocket(85);	
			
			Thread t = new Thread(new Identification(socket, this));
			t.start();
			
			Thread r = new Thread(new Temps());
			r.start();
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		this.setTitle("Epic Battle / serveur");
		this.setSize(700, 550);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(true);
		JButton boutton = new JButton("Stopper le serveur");
		boutton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent arg0) { 	        	
	        	System.exit(0);
	        }
	    });
		this.setContentPane(boutton);
		this.setVisible(true);
	}*/

public class Serveur extends JFrame{
	ArrayList<Combattant> listeJoueurs = new ArrayList<Combattant>();
	String version = "1.0";
	int temps = 0;
	
	boolean maj = false;
	boolean envoie = false;
	boolean enJeu = true;
	ArrayList listeMaj = new ArrayList();
	public Serveur(){
		this.setTitle("Epic Battle / serveur");
		this.setSize(700, 550);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(true);
		JButton boutton = new JButton("Stopper le serveur");
		boutton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent arg0) { 	        	
	        	System.exit(0);
	        }
	    });
		this.setContentPane(boutton);
		this.setVisible(true);
		
		ServerSocket socketEnvoie;
		ServerSocket socketRecoi;
		
		try {
			socketEnvoie = new ServerSocket(85);
			socketRecoi = new ServerSocket(86);
			
			Thread t = new Thread(new Identification(socketEnvoie, socketRecoi, this));
			t.start();
			
			Thread r = new Thread(new Temps());
			r.start();
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Serveur srv = new Serveur();
	}
	
	
	
	class Identification implements Runnable{
		ServerSocket socketServerEnvoie;
		ServerSocket socketServerRecoi;
		Socket socketEnvoie;
		Socket socketRecoi;
		BufferedReader in;
		PrintWriter out;
		Serveur serveur;
		public Identification(ServerSocket se, ServerSocket sr, Serveur srv){
			socketServerEnvoie = se;
			socketServerRecoi = sr;
			serveur = srv;
		}
		public void run(){
			try{
				while(enJeu){					
					socketEnvoie = socketServerEnvoie.accept();
					socketRecoi = socketServerRecoi.accept();
					in = new BufferedReader(new InputStreamReader(socketEnvoie.getInputStream()));
					if(in.readLine().equals(version)){
						out = new PrintWriter(socketEnvoie.getOutputStream());
						out.println("BON");
						out.flush();
						
						in = new BufferedReader(new InputStreamReader(socketEnvoie.getInputStream()));
						String log = in.readLine();
						System.out.println(log);
						boolean b = false;
						for(int i = 0; i < listeJoueurs.size(); i++){
							
							if(listeJoueurs.get(i).getNom().equals(log)){
								b = true;
							}
						}
						if(!b){
							out = new PrintWriter(socketEnvoie.getOutputStream());
							out.println("BON");
							out.flush();
							int id = 0;
							
							if(listeJoueurs.size() > 0){
								id = listeJoueurs.get(listeJoueurs.size() - 1).getId() + 1;
							}
							
							try {
				        		Thread.sleep(50);
				        	} catch (InterruptedException e) {
				        		e.printStackTrace();
				        	}
							
							out = new PrintWriter(socketEnvoie.getOutputStream());
							out.println(id);
							out.flush();
							envoiePremier();
							listeJoueurs.add(new Combattant(-100, -100, false, false, log, id, "NORD", false, 0, 0));
							
							Thread r = new Thread(new Recoi(socketRecoi, listeJoueurs.get(listeJoueurs.size() - 1).getId(), serveur));
							r.start();
							Thread e = new Thread(new Envoie(socketEnvoie, listeJoueurs.get(listeJoueurs.size() - 1).getId(), serveur));
							e.start();
						}else{
							out = new PrintWriter(socketEnvoie.getOutputStream());
							out.println("NON DEJA UTILISE");
							out.flush();
						}
					}else{
						out = new PrintWriter(socketEnvoie.getOutputStream());
						out.println("NON VERSION");
						out.flush();
						
						socketEnvoie.close();
						socketRecoi.close();
					}
//					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void envoiePremier(){
			try {
				in = new BufferedReader(new InputStreamReader(socketRecoi.getInputStream()));
				out = new PrintWriter(socketRecoi.getOutputStream());		
				
				String msg = in.readLine();
				System.out.println(msg);
										
				String msgEnvoie = serveur.listeJoueurs.size() + "/";
				
				for(int i = 0; i < serveur.listeJoueurs.size(); i++){
					
					String msgAttaque;
					if(serveur.listeJoueurs.get(i).getAttaque())
						msgAttaque = "OUI";
					else
						msgAttaque = "NON";
					
					String msgVivant;
					if(serveur.listeJoueurs.get(i).getVivant())
						msgVivant = "OUI";
					else
						msgVivant = "NON";
					
					String msgAvance;
					if(serveur.listeJoueurs.get(i).getAvance())
						msgAvance = "OUI";
					else
						msgAvance = "NON";
					
					String msgContinue;
					if(i == serveur.listeJoueurs.size() - 1)
						msgContinue = "NON";
					else
						msgContinue = "OUI";
					
					msgEnvoie += serveur.listeJoueurs.get(i).getPosX() + "/" + serveur.listeJoueurs.get(i).getPosY() + "/" + serveur.listeJoueurs.get(i).getNom() + "/" + serveur.listeJoueurs.get(i).getId() + "/" + msgAttaque + "/" + msgVivant + "/" + serveur.listeJoueurs.get(i).getDirection() + "/" + msgAvance + "/" + msgContinue + "/" + serveur.listeJoueurs.get(i).getNbMorts() + "/" + serveur.listeJoueurs.get(i).getNbKills();
					System.out.println(msgEnvoie);
					
					
					if(msgContinue.equals("OUI")){
						/*in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						in.readLine();*/
						msgEnvoie += "/";
					}										
				}
				
				out.println(msgEnvoie);
				System.out.println(msgEnvoie);
				out.flush();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	class Temps implements Runnable{
		public void run(){
			while (true){
				temps ++;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}	
	}	
}
class Recoi implements Runnable{
	
	boolean quit = false;
	int id;
	int temps = 0;
	
	Socket socketRe;
	BufferedReader in;
	PrintWriter out;
	String msg;	
	Serveur serveur;
	
	public Recoi(Socket s, int ID,  Serveur srv){
		socketRe = s;
		id = ID;
		serveur = srv;
	}
	public void run(){
		try {
			in = new BufferedReader(new InputStreamReader(socketRe.getInputStream()));
			out = new PrintWriter(socketRe.getOutputStream());		
			
			while(!quit && !socketRe.isClosed()){
//				temps = 0;
				//System.out.println("-----" + id);
				msg = "";
//				System.out.println(temps);
				msg = in.readLine();
//				System.out.println(temps);
				
				out.println("");
				out.flush();
						
				if(!msg.equals("QUIT")){
					
					int posX = 0, posY = 0;
					int nbMorts = 0, nbKills = 0;
					boolean attaque, vivant, avance;
					String direction;
					
					String tableInfos[] = new String[8];
									
					System.out.println(id+ ":recoi:" + msg);
			
					if(msg.length() > 10 && !serveur.envoie){
						int index = 0;
						tableInfos[0] = "";
						for(int j = 0; j < msg.length(); j++){
							if(msg.charAt(j) != '/'){
								tableInfos[index] += msg.charAt(j);
							} else {
								index ++;
								tableInfos[index] = "";
							}
						}
	
						for(int i = 0; i < tableInfos[0].length(); i++)
							posX += ((((int) tableInfos[0].charAt((tableInfos[0].length()-1) - i)) - 48) * Math.pow(10, i));
						
						for(int i = 0; i < tableInfos[1].length(); i++)
							posY += ((((int) tableInfos[1].charAt((tableInfos[1].length()-1) - i)) - 48) * Math.pow(10, i));
						
						System.out.println(posX + "/" + posY);
						
						if(tableInfos[2].equals("OUI"))	
						
							vivant = true;
						else 
							vivant = false;
						
						if(tableInfos[3].equals("OUI"))
							attaque = true;
						else 
							attaque = false;
						
						direction = tableInfos[4];
						
						if(tableInfos[5].equals("OUI"))
							avance = true;
						else 
							avance = false;
						
						for(int i = 0; i < tableInfos[6].length(); i++)
							nbMorts += ((((int) tableInfos[6].charAt((tableInfos[6].length()-1) - i)) - 48) * Math.pow(10, i));
						
						for(int i = 0; i < tableInfos[7].length(); i++)
							nbKills += ((((int) tableInfos[7].charAt((tableInfos[7].length()-1) - i)) - 48) * Math.pow(10, i));
						
						System.out.println(nbMorts+"/"+nbKills);
						
						for(int i = 0; i < serveur.listeJoueurs.size(); i++){
							if(serveur.listeJoueurs.get(i).getId() == id){
								serveur.listeJoueurs.get(i).setPos(posX, posY);
								serveur.listeJoueurs.get(i).setAttaque(attaque);
								serveur.listeJoueurs.get(i).setDirection(direction);
								serveur.listeJoueurs.get(i).setVivant(vivant);
								serveur.listeJoueurs.get(i).setAvance(avance);
								serveur.listeJoueurs.get(i).setMorts(nbMorts);
								serveur.listeJoueurs.get(i).setKills(nbKills);
							}
						}
						
						serveur.listeMaj.add(id);
						System.out.println(temps);
					}
				}else{
					quit = true;
				}
			}
			socketRe.close();			
			for(int i = 0; i < serveur.listeJoueurs.size(); i++){
				if(serveur.listeJoueurs.get(i).getId() == id)
					serveur.listeJoueurs.remove(i);
			}
			serveur.listeMaj.add(id);
		} catch (IOException e) {
			//try {
				/*for(int i = 0; i < serveur.listeJoueurs.size(); i++){
					if(id == serveur.listeJoueurs.get(i).getId()){
						serveur.listeJoueurs.remove(i);
					}
				}
				System.out.println("fini!");*/
				//socketRe.close();
			//} catch (IOException e1) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			//}
			for(int i = 0; i < serveur.listeJoueurs.size(); i++){
				if(serveur.listeJoueurs.get(i).getId() == id)
					serveur.listeJoueurs.remove(i);
			}
			serveur.listeMaj.add(id);
		}
	}
}

class Envoie implements Runnable{
	
	boolean quit = false;
	int id;
	int temps = 0;
	
	Socket socketRe;
	BufferedReader in;
	PrintWriter out;
	String msg;
	
	Serveur serveur;
	
	int nbMaj;
	
	public Envoie(Socket s, int ID, Serveur srv){
		socketRe = s;
		id = ID;
		serveur = srv;
		nbMaj = serveur.listeMaj.size();
	}
	public void run(){
		try {
			in = new BufferedReader(new InputStreamReader(socketRe.getInputStream()));
			out = new PrintWriter(socketRe.getOutputStream());		
			
			while(!quit && !socketRe.isClosed()){
				if(nbMaj < serveur.listeMaj.size()){
					System.out.println("oui");			
						
							System.out.println("oui");
							String msgAvance, msgAttaque, msgVivant;
							
							int placeMaj = -1;
							for(int i = 0; i < serveur.listeJoueurs.size(); i++){
								if(serveur.listeJoueurs.get(i).getId() == (int) serveur.listeMaj.get(nbMaj)){
									placeMaj = i;
								}
							}
							
							if(placeMaj > -1){
								if((int)serveur.listeMaj.get(nbMaj) != id){
									if(serveur.listeJoueurs.get(placeMaj).getAttaque())
										msgAttaque = "OUI";
									else 
										msgAttaque = "NON";
									
									if(serveur.listeJoueurs.get(placeMaj).getAvance())
										msgAvance = "OUI";
									else 
										msgAvance = "NON";
									
									if(serveur.listeJoueurs.get(placeMaj).getVivant())
										msgVivant = "OUI";
									else 
										msgVivant = "NON";
														
									String msgEnvoie = serveur.listeJoueurs.get(placeMaj).getPosX() + "/" + serveur.listeJoueurs.get(placeMaj).getPosY() + "/" + msgAttaque + "/" + msgVivant + "/" + serveur.listeJoueurs.get(placeMaj).getNom() + "/" + serveur.listeJoueurs.get(placeMaj).getId() + "/" + serveur.listeJoueurs.get(placeMaj).getDirection() + "/" + msgAvance + "/" + serveur.listeJoueurs.get(placeMaj).getNbMorts() + "/" + serveur.listeJoueurs.get(placeMaj).getNbKills();
									
									serveur.envoie = true;
									System.out.println(id+ " :envoie:" + msgEnvoie);
									out.println(msgEnvoie);
									out.flush();
									
									in.readLine();
									serveur.envoie = false;
								}
							} else {
								if((int)serveur.listeMaj.get(nbMaj) != id){
									String msgEnvoie = "-100/-100/NON/NON/a/" + serveur.listeMaj.get(nbMaj) + "/SUD/NON/0/0";
									
									out.println(msgEnvoie);
									out.flush();
									
									in.readLine();
								} else {
									quit = true;
								}
							}
							
						
					nbMaj ++;
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			socketRe.close();
			for(int i = 0; i < serveur.listeJoueurs.size(); i++){
				if(serveur.listeJoueurs.get(i).getId() == id)
					serveur.listeJoueurs.remove(i);
			}
		} catch (IOException e) {
			//try {
			System.out.println("fini!" + id);
			e.printStackTrace();
				//socketRe.close();
			//} catch (IOException e1) {
				// TODO Auto-generated catch block
			//}
			for(int i = 0; i < serveur.listeJoueurs.size(); i++){
				if(serveur.listeJoueurs.get(i).getId() == id)
					serveur.listeJoueurs.remove(i);
			}
		}
	}
}