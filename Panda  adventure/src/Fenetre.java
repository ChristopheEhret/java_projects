import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
	boolean pose = false, descendre = true, droite = false, gauche = false,
			monter = false, versLaDroite = true, arme = false, armeVue = false,
			espaceRelachee = true, jeuCommence = false, jeuRecommence = false, monterVite =  false, peutRecommencer = false;
	boolean doitSupprimer = false; // boolean qui indique si on doit supprimer
									// le projectile
	int monte = 0, tempsArme = 15, tempsArmeVue = 10, monteVite = 0, nbMort = 0;
	ArrayList<Obstacle> listeObstacles = new ArrayList<Obstacle>();
	ArrayList<Ennemi> listeEnnemis = new ArrayList<Ennemi>();
	ArrayList<ObstaclePiege> listeObstaclePieges = new ArrayList<ObstaclePiege>();
	ArrayList<CheckPoint> listeCheckPoint = new ArrayList<CheckPoint>();
	ArrayList<EnnemiQuiTire> listeEnnemisQuiTirent = new ArrayList<EnnemiQuiTire>();
	ArrayList<Projectile> listeProjectiles = new ArrayList<Projectile>();
	ArrayList<Pic> listePics = new ArrayList<Pic>();
	ArrayList<Pic> listePicsPlafond = new ArrayList<Pic>();
	ArrayList<Obstacle> listeObstaclesProjettent = new ArrayList<Obstacle>();
	boolean[] listeCheckPointPasses;
	Panneau pan = new Panneau();
	JPanel panelTitre = new JPanel();
	JPanel panelTitre2 = new JPanel();
	JPanel panelFond = new JPanel();
	JPanel panelPerdu = new JPanel();
	JPanel panelPerdu2 = new JPanel();
	JPanel panelCommandes = new JPanel();
	JPanel panelCommandes2 = new JPanel();
	JPanel panelBouttons = new JPanel();
	JPanel panelBouttons2 = new JPanel();
	JPanel panelBouttonsTotal = new JPanel();
	JPanel panelFin = new JPanel();
	JPanel panelHistoire = new JPanel();
	JPanel credits = new JPanel();
	JPanel panelCredits = new JPanel();
	JButton nouveauJeu = new JButton("Commencer");
	JButton recommencer = new JButton("Recommencer");
	JButton commandes = new JButton("Commandes");
	JButton retour = new JButton("Retour");
	JButton retourHistoire = new JButton("Retour");
	JButton retourCredits = new JButton("Retour");
	JButton afficherHistoire = new JButton("Histoire");
	JButton afficherCredits = new JButton("Cr�dits");
	JLabel titre = new JLabel("Panda's Adventure");
	JLabel sousTitre = new JLabel("Bienvenue à toi, jeune pandawan");
	JLabel controle1 = new JLabel("Droite : D/Flèche Droite");
	JLabel controle2 = new JLabel("Gauche : Q/Flèche Gauche");
	JLabel controle3 = new JLabel("Sauter : Z/Flèche Haut");
	JLabel controle4 = new JLabel("Attaquer : Barre d'espace");
	JLabel mort = new JLabel("Tu es mort, tu as échoué à ta mission");
	JLabel titreFin = new JLabel("Bravo tu as fini mon jeu!"); 
	JLabel sousTitreFin = new JLabel("Tu est mort " + nbMort + " fois !"); 
	JLabel histoire = new JLabel("Hérissons = méchants ! ==> Tuer les hérisons !");
	JLabel creditProg = new JLabel("Prorammation : Christophe Ehret");
	JLabel creditIdeeOrignale = new JLabel("Id�e originale : Christophe Ehret");
	JLabel creditDessins = new JLabel("Dessins : Christophe Ehret");
	JLabel creditFutursDessins = new JLabel("Dessins v2 (qui arriveront prochainement) : Simon Luciano");
	JLabel creditExplications = new JLabel("'Panda's Adventure' est le tout premier jeu de Chirstophe Ehret (15 ans).");
	String saute = "Images/Autre/saute.wav";
	String meurt = "Images/Autre/mort.wav";
	String tire = "Images/Autre/ennemi qui tire.wav";
	CardLayout cl = new CardLayout();
	Thread t;
	Font titres = new Font("Tahoma", Font.BOLD, 20);
	ObjectInputStream lire;
	ObjectOutputStream ecrire;

	public Fenetre() {
		this.setTitle("Panda's adventure");
		this.setSize(700, 550);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setUndecorated(false);

		/*
		 * try { File soundFile = new File("mort.wav"); AudioInputStream
		 * inputStream = AudioSystem .getAudioInputStream(soundFile);
		 * meurt.open(inputStream);
		 * 
		 * } catch (Exception e) { System.out.println(e.getMessage()); }
		 * 
		 * try { File soundFile = new File("saute.wav"); AudioInputStream
		 * inputStream = AudioSystem .getAudioInputStream(soundFile);
		 * saute.open(inputStream);
		 * 
		 * } catch (Exception e) { System.out.println(e.getMessage()); }
		 * 
		 * try { File soundFile = new File("ennemi qui tire.wav");
		 * AudioInputStream inputStream = AudioSystem
		 * .getAudioInputStream(soundFile); tire.open(inputStream);
		 * 
		 * } catch (Exception e) { System.out.println(e.getMessage()); }
		 */

		nouveauJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetListes();
				t = new Thread(new Jeu());
				t.start();
				pan.reset();
				cl.show(panelFond, "jeu");
				jeuCommence = true;
				peutRecommencer = false;
			}
		});
		recommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pose = false;
				descendre = true;
				droite = false;
				gauche = false;
				monter = false;
				versLaDroite = true;
				arme = false;
				espaceRelachee = true;
				monterVite = false;
				resetListes();
				monte = 0;
				tempsArme = 20;
				monteVite = 0;
				pan.setListeObstacles(listeObstacles);
				pan.setListeEnnemi(listeEnnemis);
				pan.reset();			
				goCheckPoint();
				cl.show(panelFond, "jeu");
				jeuCommence = true;
				jeuCommence = true;
				nbMort ++;
				peutRecommencer = false;
			}
		});

		commandes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelFond, "commandes");
			}
		});
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelFond, "titre");
			}
		});
		retourHistoire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelFond, "titre");
			}
		});
		retourCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelFond, "titre");
			}
		});
		afficherHistoire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelFond, "histoire");
			}
		});
		afficherCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelFond, "credits");
			}
		});

		titre.setFont(titres);
		titre.setHorizontalAlignment(JLabel.CENTER);
		sousTitre.setHorizontalAlignment(JLabel.CENTER);
		mort.setFont(titres);
		mort.setHorizontalAlignment(JLabel.CENTER);
		titreFin.setFont(titres);
		titreFin.setHorizontalAlignment(JLabel.CENTER);
		sousTitreFin.setHorizontalAlignment(JLabel.CENTER);
		histoire.setFont(titres);
		histoire.setHorizontalAlignment(JLabel.CENTER);
		
		creditProg.setHorizontalAlignment(JLabel.CENTER);
		creditIdeeOrignale.setHorizontalAlignment(JLabel.CENTER);		
		creditDessins.setHorizontalAlignment(JLabel.CENTER);
		creditExplications.setHorizontalAlignment(JLabel.CENTER);
		creditFutursDessins.setHorizontalAlignment(JLabel.CENTER);

		panelTitre2.setLayout(new GridLayout(2, 1));
		panelTitre2.add(titre);
		panelTitre2.add(sousTitre);

		panelBouttons.setLayout(new GridLayout(1, 2));
		panelBouttons.add(nouveauJeu);
		panelBouttons.add(commandes);
		
		panelBouttons2.setLayout(new GridLayout(1, 2));
		panelBouttons2.add(afficherHistoire);
		panelBouttons2.add(afficherCredits);
		
		panelBouttonsTotal.setLayout(new GridLayout(2, 1));
		panelBouttonsTotal.add(panelBouttons);
		panelBouttonsTotal.add(panelBouttons2);

		panelTitre.setLayout(new BorderLayout());
		panelTitre.add(panelTitre2, BorderLayout.CENTER);
		panelTitre.add(panelBouttonsTotal, BorderLayout.SOUTH);

		panelPerdu2.setLayout(new GridLayout(1, 1));
		panelPerdu2.add(mort);

		panelPerdu.setLayout(new BorderLayout());
		panelPerdu.add(panelPerdu2, BorderLayout.CENTER);
		panelPerdu.add(recommencer, BorderLayout.SOUTH);

		panelCommandes2.setLayout(new GridLayout(4, 1));
		panelCommandes2.add(controle1);
		panelCommandes2.add(controle2);
		panelCommandes2.add(controle3);
		panelCommandes2.add(controle4);

		panelCommandes.setLayout(new BorderLayout());
		panelCommandes.add(panelCommandes2, BorderLayout.CENTER);
		panelCommandes.add(retour, BorderLayout.SOUTH);
		
		panelFin.setLayout(new GridLayout(2, 1));
		panelFin.add(titreFin);
		panelFin.add(sousTitreFin);
		
		panelHistoire.setLayout(new BorderLayout());
		panelHistoire.add(histoire, BorderLayout.CENTER);
		panelHistoire.add(retourHistoire, BorderLayout.SOUTH);
		
		panelCredits.setLayout(new GridLayout(5, 1));
		panelCredits.add(creditExplications);
		panelCredits.add(creditIdeeOrignale);
		panelCredits.add(creditProg);
		panelCredits.add(creditDessins);
		panelCredits.add(creditFutursDessins);
		
		credits.setLayout(new BorderLayout());
		credits.add(panelCredits,BorderLayout.CENTER);
		credits.add(retourCredits, BorderLayout.SOUTH);

		panelFond.setLayout(cl);
		panelFond.add(panelTitre, "titre");
		panelFond.add(panelPerdu, "perdu");
		panelFond.add(panelCommandes, "commandes");
		panelFond.add(pan, "jeu");
		panelFond.add(panelFin, "fin");
		panelFond.add(panelHistoire, "histoire");
		panelFond.add(credits, "credits");		

		this.addKeyListener(new ClavierListener());

		this.setContentPane(panelFond);
		this.setVisible(true);

	}

	class Jeu implements Runnable {
		public void run() {
			go();
		}
	}

	public void go() {
		for (;;) {
			if (jeuCommence == true) {
				verifieObstables();
				verifieObstaclePieges();
				verifieObstaclesProjettent();

				if (pose == false && descendre == true) {
					pan.descend();
				}

				if (droite == true) {
					pan.goDroite();
					pan.setDirection(true);
					for (int i = 0; i < listeObstacles.size(); i++) {
						listeObstacles.get(i).enlevePosX();
					}
					for (int i = 0; i < listeObstaclePieges.size(); i++) {
						listeObstaclePieges.get(i).enlevePosX();
					}
					for (int i = 0; i < listeEnnemis.size(); i++) {
						listeEnnemis.get(i).goDroite();
					}
					for (int i = 0; i < listeCheckPoint.size(); i++) {
						listeCheckPoint.get(i).enlevePosX();
					}
					for (int i = 0; i < listeEnnemisQuiTirent.size(); i++) {
						listeEnnemisQuiTirent.get(i).enlevePosX();
					}
					for (int i = 0; i < listeProjectiles.size(); i++) {
						listeProjectiles.get(i).enlevePosX();
					}
					for (int i = 0; i < listePics.size(); i++) {
						listePics.get(i).enlevePosX();
					}
					for (int i = 0; i < listePicsPlafond.size(); i++) {
						listePicsPlafond.get(i).enlevePosX();
					}
					for (int i = 0; i < listeObstaclesProjettent.size(); i++) {
						listeObstaclesProjettent.get(i).enlevePosX();
					}
					for (int i = 0; i < listeObstacles.size(); i++) {
						if ((pan.getPosX() + 47 > listeObstacles.get(i)
								.getPosX()
								&& pan.getPosX() < listeObstacles.get(i)
										.getPosX()
										+ listeObstacles.get(i).getLargeur()
								&& pan.getPosY() + 30 > listeObstacles.get(i)
										.getPosY() && pan.getPosY() < listeObstacles
								.get(i).getPosY()
								+ listeObstacles.get(i).getHauteur())) {
							pan.goGauche();
							for (int j = 0; j < listeObstacles.size(); j++) {
								listeObstacles.get(j).ajoutePosX();
							}
							for (int j = 0; j < listeObstaclePieges.size(); j++) {
								listeObstaclePieges.get(j).ajoutePosX();
							}
							for (int h = 0; h < listeEnnemis.size(); h++) {
								listeEnnemis.get(h).goGauche();
							}
							for (int j = 0; j < listeCheckPoint.size(); j++) {
								listeCheckPoint.get(j).ajoutePosX();
							}
							for (int j = 0; j < listeEnnemisQuiTirent.size(); j++) {
								listeEnnemisQuiTirent.get(j).ajoutePosX();
							}
							for (int j = 0; j < listeProjectiles.size(); j++) {
								listeProjectiles.get(j).ajoutePosX();
							}
							for (int j = 0; j < listePics.size(); j++) {
								listePics.get(j).ajoutePosX();
							}
							for (int j = 0; j < listePicsPlafond.size(); j++) {
								listePicsPlafond.get(j).ajoutePosX();
							}
							for (int j = 0; j < listeObstaclesProjettent.size(); j++) {
								listeObstaclesProjettent.get(j).ajoutePosX();
							}
						}
					}
					versLaDroite = true;
				}
				if (gauche == true) {
					pan.goGauche();
					pan.setDirection(false);
					for (int i = 0; i < listeObstacles.size(); i++) {
						listeObstacles.get(i).ajoutePosX();
					}
					for (int j = 0; j < listeObstaclePieges.size(); j++) {
						listeObstaclePieges.get(j).ajoutePosX();
					}
					for (int i = 0; i < listeEnnemis.size(); i++) {
						listeEnnemis.get(i).goGauche();
					}
					for (int i = 0; i < listeCheckPoint.size(); i++) {
						listeCheckPoint.get(i).ajoutePosX();
					}
					for (int i = 0; i < listeEnnemisQuiTirent.size(); i++) {
						listeEnnemisQuiTirent.get(i).ajoutePosX();
					}
					for (int i = 0; i < listeProjectiles.size(); i++) {
						listeProjectiles.get(i).ajoutePosX();
					}
					for (int i = 0; i < listePics.size(); i++) {
						listePics.get(i).ajoutePosX();
					}
					for (int i = 0; i < listeObstaclesProjettent.size(); i++) {
						listeObstaclesProjettent.get(i).ajoutePosX();
					}
					for (int i = 0; i < listePicsPlafond.size(); i++) {
						listePicsPlafond.get(i).ajoutePosX();
					}
					for (int i = 0; i < listeObstacles.size(); i++) {
						if (pan.getPosX() + 47 > listeObstacles.get(i)
								.getPosX()
								&& pan.getPosX() < listeObstacles.get(i)
										.getPosX()
										+ listeObstacles.get(i).getLargeur()
								&& pan.getPosY() + 30 > listeObstacles.get(i)
										.getPosY()
								&& pan.getPosY() < listeObstacles.get(i)
										.getPosY()
										+ listeObstacles.get(i).getHauteur()) {
							pan.goDroite();
							for (int j = 0; j < listeObstacles.size(); j++) {
								listeObstacles.get(j).enlevePosX();
							}
							for (int j = 0; j < listeObstaclePieges.size(); j++) {
								listeObstaclePieges.get(j).enlevePosX();
							}
							for (int h = 0; h < listeEnnemis.size(); h++) {
								listeEnnemis.get(h).goDroite();
							}
							for (int j = 0; j < listeCheckPoint.size(); j++) {
								listeCheckPoint.get(j).enlevePosX();
							}
							for (int j = 0; j < listeEnnemisQuiTirent.size(); j++) {
								listeEnnemisQuiTirent.get(j).enlevePosX();
							}
							for (int j = 0; j < listeProjectiles.size(); j++) {
								listeProjectiles.get(j).enlevePosX();
							}
							for (int j = 0; j < listePics.size(); j++) {
								listePics.get(j).enlevePosX();
							}
							for (int j = 0; j < listeObstaclesProjettent.size(); j++) {
								listeObstaclesProjettent.get(j).enlevePosX();
							}
							for (int j = 0; j < listePicsPlafond.size(); j++) {
								listePicsPlafond.get(j).enlevePosX();
							}
						}

					}
					versLaDroite = false;
				}

				if (monter) {
					pan.monte();
					monte -= 2;
					pan.setSaute();

					if (monte <= 0) {
						monter = false;
						descendre = true;
					}
					if (pose == false && descendre == true) {
						pan.descend();
					}
				}
				
				if (monterVite) {
					pan.monteVite();
					monteVite -= 2;
					pan.setSaute();

					if (monteVite <= 0) {
						monterVite = false;
						descendre = true;
					}
					if (pose == false && descendre == true) {
						pan.descend();
					}
				}
				for (int i = 0; i < listeObstacles.size(); i++) {
					if (verifieColision(listeObstacles.get(i).getPosX(),
							listeObstacles.get(i).getPosY(), listeObstacles
									.get(i).getLargeur(), listeObstacles.get(i)
									.getHauteur())) {
						monter = false;
						descendre = true;
					}
				}
				for (int i = 0; i < listeObstaclePieges.size(); i++) {
					if (verifieColision(listeObstaclePieges.get(i).getPosX(),
							listeObstaclePieges.get(i).getPosY(),
							listeObstaclePieges.get(i).getLargeur(),
							listeObstaclePieges.get(i).getHauteur())) {
						monter = false;
						descendre = true;
					}
				}
				for (int i = 0; i < listeObstaclesProjettent.size(); i++) {
					if (verifieColision(listeObstaclesProjettent.get(i).getPosX(),
							listeObstaclesProjettent.get(i).getPosY(),
							listeObstaclesProjettent.get(i).getLargeur(),
							listeObstaclesProjettent.get(i).getHauteur())) {
						monter = false;
						descendre = true;
					}
				}
				if (arme) {
					tempsArme -= 2;
					if (tempsArme <= 0) {
						arme = false;
					}

				}
				if (armeVue) {
					tempsArmeVue -= 2;
					if (tempsArmeVue <= 0) {
						armeVue = false;
					}
				}

				if (pan.getPosY() >= 500) {
					jeuCommence = false;
					faitSon(meurt);
					cl.show(panelFond, "perdu");
					peutRecommencer = true;
				}

				for (int i = 0; i < listeObstaclePieges.size(); i++) {
					listeObstaclePieges.get(i).actualise();
				}

				for (int i = 0; i < listeEnnemisQuiTirent.size(); i++) {
					if (listeEnnemisQuiTirent.get(i).getPosX() + 20 > -50
							&& listeEnnemisQuiTirent.get(i).getPosX() < 750) {
						if (!listeEnnemisQuiTirent.get(i).getDirection()) {
							listeEnnemisQuiTirent.get(i).setModeRepos();
							if (pan.getPosY() < listeEnnemisQuiTirent.get(i)
									.getPosY() + 20
									&& pan.getPosY() + 30 > listeEnnemisQuiTirent
											.get(i).getPosY()
									&& pan.getPosX() + 47 < listeEnnemisQuiTirent
											.get(i).getPosX()) {
								listeEnnemisQuiTirent.get(i).setModeTire();
								for (int j = 0; j < listeObstacles.size(); j++) {
									if (listeObstacles.get(j).getPosX() < listeEnnemisQuiTirent
											.get(i).getPosX()
											&& listeObstacles.get(j).getPosX()
													+ listeObstacles.get(j)
															.getLargeur() > pan
														.getPosX()
											&& listeObstacles.get(j).getPosY() < listeEnnemisQuiTirent
													.get(i).getPosY() + 20
											&& listeObstacles.get(j).getPosY()
													+ listeObstacles.get(j)
															.getHauteur() > listeEnnemisQuiTirent
													.get(i).getPosY()) {
										listeEnnemisQuiTirent.get(i)
												.setModeRepos();
									}
								}
							}

						}
						if (listeEnnemisQuiTirent.get(i).getDirection()) {
							listeEnnemisQuiTirent.get(i).setModeRepos();
							if (pan.getPosY() < listeEnnemisQuiTirent.get(i)
									.getPosY() + 20
									&& pan.getPosY() + 30 > listeEnnemisQuiTirent
											.get(i).getPosY()
									&& pan.getPosX() + 47 > listeEnnemisQuiTirent
											.get(i).getPosX()) {
								listeEnnemisQuiTirent.get(i).setModeTire();
								for (int j = 0; j < listeObstacles.size(); j++) {
									if (listeObstacles.get(j).getPosX() > listeEnnemisQuiTirent
											.get(i).getPosX()
											&& listeObstacles.get(j).getPosX()
													+ listeObstacles.get(j)
															.getLargeur() < pan
														.getPosX()
											&& listeObstacles.get(j).getPosY() < listeEnnemisQuiTirent
													.get(i).getPosY() + 20
											&& listeObstacles.get(j).getPosY()
													+ listeObstacles.get(j)
															.getHauteur() > listeEnnemisQuiTirent
													.get(i).getPosY()) {
										listeEnnemisQuiTirent.get(i)
												.setModeRepos();
									}
								}
							}
						}
						listeEnnemisQuiTirent.get(i).actualise();
						if (listeEnnemisQuiTirent.get(i).getDoitTirer()) {
							if (listeEnnemisQuiTirent.get(i).getDirection()) {
								listeProjectiles
										.add(new Projectile(
												listeEnnemisQuiTirent.get(i)
														.getPosX() + 20,
												listeEnnemisQuiTirent.get(i)
														.getPosY() + 5, true));
							}
							if (!listeEnnemisQuiTirent.get(i).getDirection()) {
								listeProjectiles
										.add(new Projectile(
												listeEnnemisQuiTirent.get(i)
														.getPosX() - 20,
												listeEnnemisQuiTirent.get(i)
														.getPosY() + 5, false));
							}
							faitSon(tire);
							listeEnnemisQuiTirent.get(i).arreteDeTirer();
						}

					}
				}
				for (int i = 0; i < listeProjectiles.size(); i++) {
					listeProjectiles.get(i).avance();
					doitSupprimer = false;
					for (int j = 0; j < listeObstacles.size(); j++) {
						if (listeProjectiles.get(i).getPosX() + 20 > listeObstacles
								.get(j).getPosX()
								&& listeProjectiles.get(i).getPosX() < listeObstacles
										.get(j).getPosX()
										+ listeObstacles.get(j).getLargeur()
								&& listeProjectiles.get(i).getPosY() < listeObstacles
										.get(j).getPosY()
										+ listeObstacles.get(j).getHauteur()
								&& listeProjectiles.get(i).getPosY() + 10 > listeObstacles
										.get(j).getPosY()) {
							doitSupprimer = true;
						}
					}
					if (doitSupprimer) {
						listeProjectiles.remove(i);
					}
				}

				verifieCheckPoint();
				ennemiAvance();
				verifieEnnemi();
				verifiePics();
				verifiePicsPlafond();
				pan.setArme(armeVue);
				pan.setListeObstacles(listeObstacles);
				pan.setListeEnnemi(listeEnnemis);
				pan.setListeObstaclePiege(listeObstaclePieges);
				pan.setListeEnnemisQuiTirent(listeEnnemisQuiTirent);
				pan.setListeProjectiles(listeProjectiles);
				pan.setListeCheckPoints(listeCheckPoint);
				pan.setListePics(listePics);
				pan.setListePicsPlafond(listePicsPlafond);
				pan.setListeObstaclesProjettent(listeObstaclesProjettent);
				pan.repaint();

				this.requestFocusInWindow();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class ClavierListener implements KeyListener {

		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == 37 || event.getKeyCode() == 81) {
				gauche = true;
				droite = false;
			}
			if (event.getKeyCode() == 39 || event.getKeyCode() == 68) {
				gauche = false;
				droite = true;
			}
			if ((event.getKeyChar() == 'z' || event.getKeyCode() == 38)
					&& monter == false && pose == true) {
				monter = true;
				descendre = false;
				monte = 50;
				pose = false;
				faitSon(saute);
			}
			if (event.getKeyCode() == 32 && espaceRelachee == true) {
				if (jeuCommence) {
					espaceRelachee = false;
					arme = true;
					armeVue = true;
					tempsArme = 15;
					tempsArmeVue = 10;
				}
				if (!jeuCommence && peutRecommencer) {
					pose = false;
					descendre = true;
					droite = false;
					gauche = false;
					monter = false;
					versLaDroite = true;
					arme = false;
					espaceRelachee = true;
					monterVite = false;
					resetListes();
					monte = 0;
					tempsArme = 20;
					monteVite = 0;
					pan.setListeObstacles(listeObstacles);
					pan.setListeEnnemi(listeEnnemis);
					pan.reset();					
					goCheckPoint();
					cl.show(panelFond, "jeu");
					jeuCommence = true;
					jeuCommence = true;
					nbMort ++;
					peutRecommencer = false;
				}
			}

		}

		public void keyReleased(KeyEvent event) {
			if (event.getKeyCode() == 37 || event.getKeyCode() == 81) {
				gauche = false;
			}
			if (event.getKeyCode() == 39 || event.getKeyCode() == 68) {
				droite = false;
			}
			if (event.getKeyCode() == 32) {
				espaceRelachee = true;
			}
		}

		public void keyTyped(KeyEvent event) {

		}
	}

	public void verifieObstables() {
		pose = false;
		for (int i = 0; i < listeObstacles.size(); i++) {
			if (verifieColision(listeObstacles.get(i).getPosX(), listeObstacles
					.get(i).getPosY(), listeObstacles.get(i).getLargeur(), 1)
					&& monter == false) {
				pose = true;
				pan.setPosY(listeObstacles.get(i).getPosY() - 30);
				descendre = true;
			}
		}
	}

	public void verifieObstaclePieges() {
		for (int i = 0; i < listeObstaclePieges.size(); i++) {
			if (verifieColision(listeObstaclePieges.get(i).getPosX(),
					listeObstaclePieges.get(i).getPosY(), listeObstaclePieges
							.get(i).getLargeur(), 1)
					&& monter == false) {
				pose = true;
				pan.setPosY(listeObstaclePieges.get(i).getPosY() - 30);
				descendre = true;
				listeObstaclePieges.get(i).setActive();
			}
		}
	}
	
	public void verifieObstaclesProjettent(){
		for (int i = 0; i < listeObstaclesProjettent.size(); i++) {
			if (verifieColision(listeObstaclesProjettent.get(i).getPosX(), listeObstaclesProjettent
					.get(i).getPosY(), listeObstaclesProjettent.get(i).getLargeur(), 1)
					&& monter == false) {
				pose = true;
				pan.setPosY(listeObstaclesProjettent.get(i).getPosY() - 30);
				monterVite = true;
				descendre = false;
				monteVite = 75;
				pose = false;
				faitSon(saute);
			}
		}
	}

	public void verifiePics() {
		for (int i = 0; i < listePics.size(); i++) {
			if (verifieColision(listePics.get(i).getPosX(), listePics.get(i)
					.getPosY(), 45, 25)) {
				jeuCommence = false;
				cl.show(panelFond, "perdu");
				faitSon(meurt);
				peutRecommencer = true;
			}
		}
	}
	
	public void verifiePicsPlafond() {
		for (int i = 0; i < listePicsPlafond.size(); i++) {
			if (verifieColision(listePicsPlafond.get(i).getPosX(), listePicsPlafond.get(i)
					.getPosY(), 45, 25)) {
				jeuCommence = false;
				cl.show(panelFond, "perdu");
				faitSon(meurt);
				peutRecommencer = true;
			}
		}
	}

	public void ennemiAvance() {
		for (int i = 0; i < listeEnnemis.size(); i++) {
			listeEnnemis.get(i).avancer();
		}
	}

	public void verifieEnnemi() {
		if (arme) {
			if (versLaDroite) {
				for (int i = 0; i < listeEnnemis.size(); i++) {
					if (pan.getPosX() + 60 >= listeEnnemis.get(i).getPosX()
							&& pan.getPosX() <= listeEnnemis.get(i).getPosX() + 40
							&& pan.getPosY() + 30 >= listeEnnemis.get(i)
									.getPosY()
							&& pan.getPosY() <= listeEnnemis.get(i).getPosY() + 20) {
						pan.ajouteTache(new Tache(
								listeEnnemis.get(i).getPosX(), listeEnnemis
										.get(i).getPosY()));
						listeEnnemis.remove(listeEnnemis.get(i).getID());
						for (int j = i; j < listeEnnemis.size(); j++) {
							listeEnnemis.get(j).setPlace(
									listeEnnemis.get(j).getPlace() - 1);

						}
						faitSon(meurt);
					}

				}
			}
			if (!versLaDroite) {
				for (int i = 0; i < listeEnnemis.size(); i++) {
					if (pan.getPosX() - 13 <= listeEnnemis.get(i).getPosX() + 40
							&& pan.getPosX() >= listeEnnemis.get(i).getPosX()
							&& pan.getPosY() + 30 >= listeEnnemis.get(i)
									.getPosY()
							&& pan.getPosY() <= listeEnnemis.get(i).getPosY() + 20) {
						pan.ajouteTache(new Tache(
								listeEnnemis.get(i).getPosX(), listeEnnemis
										.get(i).getPosY()));
						listeEnnemis.remove(listeEnnemis.get(i).getID());
						for (int j = i; j < listeEnnemis.size(); j++) {
							listeEnnemis.get(j).setPlace(
									listeEnnemis.get(j).getPlace() - 1);
						}
						faitSon(meurt);
					}

				}
			}
		} else {
			for (int i = 0; i < listeEnnemis.size(); i++) {
				if (verifieColision(listeEnnemis.get(i).getPosX(), listeEnnemis
						.get(i).getPosY(), 40, 20)) {
					jeuCommence = false;
					cl.show(panelFond, "perdu");
					faitSon(meurt);
					peutRecommencer = true;
				}
			}
		}
		for (int i = 0; i < listeProjectiles.size(); i++) {
			if (verifieColision(listeProjectiles.get(i).getPosX(),
					listeProjectiles.get(i).getPosY(), 20, 10)) {
				jeuCommence = false;
				cl.show(panelFond, "perdu");
				faitSon(meurt);
				peutRecommencer = true;

			}
		}
	}

	public void goCheckPoint() {
		for (int i = 0; i < listeCheckPoint.size(); i++) {
			if (listeCheckPoint.get(i).getPasse() == true) {

				int decalage = listeCheckPoint.get(i).getPosX() - 336;

				pan.setPosY(listeCheckPoint.get(i).getPosY() - 30);
				for (int j = 0; j < listeObstacles.size(); j++) {
					listeObstacles.get(j).ajoutePosX(-decalage);
				}
				for (int j = 0; j < listeObstaclePieges.size(); j++) {
					listeObstaclePieges.get(j).ajoutePosX(-decalage);
				}
				for (int j = 0; j < listeEnnemis.size(); j++) {
					listeEnnemis.get(j).ajoutePosX(-decalage);
				}
				for (int j = 0; j < listeCheckPoint.size(); j++) {
					listeCheckPoint.get(j).ajoutePosX(-decalage);
				}
				for (int j = 0; j < listeEnnemisQuiTirent.size(); j++) {
					listeEnnemisQuiTirent.get(j).ajoutePosX(-decalage);
				}
				for (int j = 0; j < listePics.size(); j++) {
					listePics.get(j).ajoutePosX(-decalage);
				}
				for (int j = 0; j < listePicsPlafond.size(); j++) {
					listePicsPlafond.get(j).ajoutePosX(-decalage);
				}
				for (int j = 0; j < listeObstaclesProjettent.size(); j++) {
					listeObstaclesProjettent.get(j).ajoutePosX(-decalage);
				}
			}
		}
	}

	public void verifieCheckPoint() {
		for (int i = 0; i < listeCheckPoint.size(); i++) {
			if (listeCheckPoint.get(i).getPosX() > pan.getPosX()
					&& listeCheckPoint.get(i).getPosX() < pan.getPosX() + 30
					&& pan.getPosY() <= listeCheckPoint.get(i).getPosY()) {
				for (int j = 0; j < listeCheckPointPasses.length; j++) {
					listeCheckPointPasses[j] = false;
				}
				listeCheckPointPasses[i] = true;
				listeCheckPoint.get(i).setPasse();
				if(i == 4){
					cl.show(panelFond, "fin");
					jeuCommence = false;
					peutRecommencer = false;
					sousTitreFin.setText("Tu est mort " + nbMort + " fois !");
				}
			}
		}
	}

	public void resetListes() {

		try {
			lire = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(new File("Images/terrain.txt"))));
			listeObstacles = (ArrayList<Obstacle>) lire.readObject();
			listeObstaclePieges = (ArrayList<ObstaclePiege>) lire.readObject();
			listeEnnemis = (ArrayList<Ennemi>) lire.readObject();
			listeCheckPoint = (ArrayList<CheckPoint>) lire.readObject();
			listeEnnemisQuiTirent = (ArrayList<EnnemiQuiTire>) lire
					.readObject();
			listePics = (ArrayList<Pic>) lire.readObject();
			listeObstaclesProjettent = (ArrayList<Obstacle>) lire.readObject();
			listePicsPlafond = (ArrayList<Pic>) lire.readObject();
			if (jeuRecommence == false) {
				listeCheckPointPasses = new boolean[listeCheckPoint.size()];
				for (int i = 0; i < listeCheckPointPasses.length; i++) {
					listeCheckPointPasses[i] = false;
				}
				jeuRecommence = true;
			}
			lire.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		 listeCheckPointPasses[2] = true;
		for (int i = 0; i < listeCheckPoint.size(); i++) {
			if (listeCheckPointPasses[i] == true) {
				listeCheckPoint.get(i).setPasse();
			}
		}
		
/*		listeObstaclesProjettent = new ArrayList<Obstacle>();		
		listeObstaclesProjettent.add(new Obstacle( 600, 350, 10 ,50));
		
		listePicsPlafond = new ArrayList<Pic>();		
		listePicsPlafond.add(new Pic( 600, 200));	*/

		listeProjectiles = new ArrayList<Projectile>();

	}

	public void faitSon(String str) {
		try {
			Clip clip = AudioSystem.getClip();
			File soundFile = new File(str);

			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(soundFile);

			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean verifieColision(int posX, int posY, int l, int h) {
		boolean retourne = false;
		if (pan.getPosY() + 30 >= posY && pan.getPosY() <= posY + h
				&& pan.getPosX() + 45 >= posX && pan.getPosX() <= posX + l) {
			retourne = true;
		}
		return retourne;
	}
}
