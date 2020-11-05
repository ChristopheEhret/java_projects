import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panneau extends JPanel {
	Image checkPoint = getToolkit().getImage("Images/Autre/checkPoint.png");
	Image checkPointPasse = getToolkit().getImage("Images/Autre/checkPoint passe.png");
	Image panda = getToolkit().getImage("Images/Panda/panda.png");
	Image pandaRetourne = getToolkit().getImage("Images/Panda/panda retourne.png");
	Image pandaAttaque = getToolkit().getImage("Images/Panda/panda attaque.png");
	Image pandaRetourneAttaque = getToolkit().getImage(
			"Images/Panda/panda retourne attaque.png");
	Image pandaSaute = getToolkit().getImage("Images/Panda/panda saute.png");
	Image pandaRetourneSaute = getToolkit()
			.getImage("Images/Panda/panda retourne saute.png");
	Image pandaMarche = getToolkit().getImage("Images/Panda/panda marche.png");
	Image pandaRetourneMarche = getToolkit().getImage(
			"Images/Panda/panda retourne marche.png");
	Image herisson = getToolkit().getImage("Images/Ennemis/herisson.png");
	Image herissonRetourne = getToolkit().getImage("Images/Ennemis/herisson retourne.png");
	Image herissonMarche = getToolkit().getImage("Images/Ennemis/herisson marche.png");
	Image herissonRetourneMarche = getToolkit().getImage(
			"Images/Ennemis/herisson retourne marche.png");
	Image tache = getToolkit().getImage("Images/Autre/tache.png");
	Image ennemiQuiTire = getToolkit().getImage("Images/Ennemis/ennemi qui tire droite.png");
	Image ennemiQuiTireRetourne = getToolkit().getImage(
			"Images/Ennemis/ennemi qui tire gauche.png");
	Image pics = getToolkit().getImage("Images/Ennemis/pics.png");
	Image picsPlafond = getToolkit().getImage("Images/Ennemis/pics plafond.png");
	Image projectile = getToolkit().getImage("Images/Ennemis/projectile.png");
	int posX = 336, posY = 430;
	ArrayList<Obstacle> listeObstacles = new ArrayList<Obstacle>();
	ArrayList<Ennemi> listeEnnemis = new ArrayList<Ennemi>();
	ArrayList<Tache> listeTaches = new ArrayList<Tache>();
	ArrayList<ObstaclePiege> listeObstaclePieges = new ArrayList<ObstaclePiege>();
	ArrayList<EnnemiQuiTire> listeEnnemisQuiTirent = new ArrayList<EnnemiQuiTire>();
	ArrayList<Projectile> listeProjectiles = new ArrayList<Projectile>();
	ArrayList<CheckPoint> listeCheckPoints = new ArrayList<CheckPoint>();
	ArrayList<Pic> listePics = new ArrayList<Pic>();
	ArrayList<Pic> listePicsPlafond = new ArrayList<Pic>();
	ArrayList<Obstacle> listeObstaclesProjettent = new ArrayList<Obstacle>();
	boolean versLaDroite = true;
	boolean arme = false, avance = false, ennemiAvance = false, saute = false;
	int etapeMarche = 0, timer = 25, timerEnnemis = 25;

	public Panneau() {

	}

	protected void paintComponent(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for (int i = 0; i < listeTaches.size(); i++) {
			g.drawImage(tache, listeTaches.get(i).getPosX(), listeTaches.get(i)
					.getPosY(), this);
		}
		
		for (int i = 0; i < listeCheckPoints.size(); i++) {
			if (listeCheckPoints.get(i).getPosX() > -50
					&& listeCheckPoints.get(i).getPosX() < 750) {
				if (!listeCheckPoints.get(i).getPasse()) {
					g.drawImage(checkPoint, listeCheckPoints.get(i).getPosX(),
							listeCheckPoints.get(i).getPosY() - 40, this);
				}
				if (listeCheckPoints.get(i).getPasse()) {
					g.drawImage(checkPointPasse, listeCheckPoints.get(i)
							.getPosX(), listeCheckPoints.get(i).getPosY() - 40,
							this);
				}
			}
		}
		if (versLaDroite == true) {
			switch (etapeMarche) {
			case 0:
				if (saute == false) {
					if (arme == false) {
						g.drawImage(pandaRetourne, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaRetourneAttaque, posX, posY, this);
						timer -= 2;
					}
				}
				if (saute == true) {
					if (arme == false) {
						g.drawImage(pandaRetourneSaute, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaRetourneAttaque, posX, posY, this);
						timer -= 2;
					}
				}
				break;
			case 1:
				if (saute == false) {
					if (arme == false) {
						g.drawImage(pandaRetourneMarche, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaRetourneAttaque, posX, posY, this);
						timer -= 2;
					}
				}
				if (saute == true) {
					if (arme == false) {
						g.drawImage(pandaRetourneSaute, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaRetourneAttaque, posX, posY, this);
						timer -= 2;
					}
				}

				break;
			}

			switch (etapeMarche) {
			case 0:
				if (avance == true && timer <= 0) {
					etapeMarche = 1;
					avance = false;
					timer = 25;
				}
				break;
			case 1:
				if (avance == true && timer <= 0) {
					etapeMarche = 0;
					avance = false;
					timer = 25;
				}
				break;
			}
		}
		if (versLaDroite == false) {
			switch (etapeMarche) {
			case 0:
				if (saute == false) {
					if (arme == false) {
						g.drawImage(panda, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaAttaque, posX - 13, posY, this);
						timer -= 2;
					}
				}
				if (saute == true) {
					if (arme == false) {
						g.drawImage(pandaSaute, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaAttaque, posX - 13, posY, this);
						timer -= 2;
					}
				}
				break;
			case 1:
				if (saute == false) {
					if (arme == false) {
						g.drawImage(pandaMarche, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaAttaque, posX - 13, posY, this);
						timer -= 2;
					}
				}
				if (saute == true) {
					if (arme == false) {
						g.drawImage(pandaSaute, posX, posY, this);
						timer -= 2;
					}
					if (arme == true) {
						g.drawImage(pandaAttaque, posX - 13, posY, this);
						timer -= 2;
					}
				}
				break;
			}
			switch (etapeMarche) {
			case 0:
				if (avance == true && timer <= 0) {
					etapeMarche = 1;
					avance = false;
					timer = 25;
				}
				break;
			case 1:
				if (avance == true && timer <= 0) {
					etapeMarche = 0;
					avance = false;
					timer = 25;
				}
				break;
			}
		}

		for (int i = 0; i < listeEnnemis.size(); i++) {
			if (listeEnnemis.get(i).getPosX() > -50
					&& listeEnnemis.get(i).getPosX() < 750) {
				if (listeEnnemis.get(i).getDirection() == true) {
					if (ennemiAvance == false) {
						g.drawImage(herisson, listeEnnemis.get(i).getPosX(),
								listeEnnemis.get(i).getPosY(), this);
					}
					if (ennemiAvance == true) {
						g.drawImage(herissonMarche, listeEnnemis.get(i)
								.getPosX(), listeEnnemis.get(i).getPosY(), this);
					}
				}
				if (listeEnnemis.get(i).getDirection() == false) {
					if (ennemiAvance == false) {
						g.drawImage(herissonRetourne, listeEnnemis.get(i)
								.getPosX(), listeEnnemis.get(i).getPosY(), this);
					}
					if (ennemiAvance == true) {
						g.drawImage(herissonRetourneMarche, listeEnnemis.get(i)
								.getPosX(), listeEnnemis.get(i).getPosY(), this);
					}
				}
			}
		}

		timerEnnemis -= 2;

		if (timerEnnemis <= 0 && ennemiAvance) {
			timerEnnemis = 25;
			ennemiAvance = false;
		}
		if (timerEnnemis <= 0 && !ennemiAvance) {
			timerEnnemis = 25;
			ennemiAvance = true;
		}

		g.setColor(Color.green);
		for (int i = 0; i < listeObstacles.size(); i++) {
			if (listeObstacles.get(i).getPosX()
					+ listeObstacles.get(i).getLargeur() > -50
					&& listeObstacles.get(i).getPosX() < 750) {
				g.fillRect(listeObstacles.get(i).getPosX(),
						listeObstacles.get(i).getPosY(), listeObstacles.get(i)
								.getLargeur(), listeObstacles.get(i)
								.getHauteur());
			}
		}

		for (int i = 0; i < listeEnnemisQuiTirent.size(); i++) {
			if (listeEnnemisQuiTirent.get(i).getPosX() + 20 > -50
					&& listeEnnemisQuiTirent.get(i).getPosX() < 750) {
				if (listeEnnemisQuiTirent.get(i).getDirection()) {
					g.drawImage(ennemiQuiTire, listeEnnemisQuiTirent.get(i)
							.getPosX(), listeEnnemisQuiTirent.get(i).getPosY(),
							this);

				}
				if (!listeEnnemisQuiTirent.get(i).getDirection()) {
					g.drawImage(ennemiQuiTireRetourne, listeEnnemisQuiTirent
							.get(i).getPosX() - 20, listeEnnemisQuiTirent
							.get(i).getPosY(), this);

				}
			}
		}
		for (int i = 0; i < listePics.size(); i++) {
			if (listePics.get(i).getPosX() + 20 > -50
					&& listePics.get(i).getPosX() < 750) {
				g.drawImage(pics, listePics.get(i).getPosX(), listePics.get(i)
						.getPosY(), this);
			}
		}
		for (int i = 0; i < listePicsPlafond.size(); i++) {
			if (listePicsPlafond.get(i).getPosX() + 20 > -50
					&& listePicsPlafond.get(i).getPosX() < 750) {
				g.drawImage(picsPlafond, listePicsPlafond.get(i).getPosX(), listePicsPlafond.get(i)
						.getPosY(), this);
			}
		}

		for (int i = 0; i < listeProjectiles.size(); i++) {
			if (listeProjectiles.get(i).getDirection()) {
				g.drawImage(projectile, listeProjectiles.get(i).getPosX(),
						listeProjectiles.get(i).getPosY(), this);
			}
			if (!listeProjectiles.get(i).getDirection()) {
				g.drawImage(projectile, listeProjectiles.get(i).getPosX() - 20,
						listeProjectiles.get(i).getPosY(), this);
			}
		}

		g.setColor(Color.blue);
		for (int i = 0; i < listeObstaclePieges.size(); i++) {
			if (listeObstaclePieges.get(i).getPosX()
					+ listeObstaclePieges.get(i).getLargeur() > -50
					&& listeObstaclePieges.get(i).getPosX() < 750) {
				g.fillRect(listeObstaclePieges.get(i).getPosX(),
						listeObstaclePieges.get(i).getPosY(),
						listeObstaclePieges.get(i).getLargeur(),
						listeObstaclePieges.get(i).getHauteur());
			}
		}
		g.setColor(Color.yellow);
		for (int i = 0; i < listeObstaclesProjettent.size(); i++) {
			if (listeObstaclesProjettent.get(i).getPosX()
					+ listeObstaclesProjettent.get(i).getLargeur() > -50
					&& listeObstaclesProjettent.get(i).getPosX() < 750) {
				g.fillRect(listeObstaclesProjettent.get(i).getPosX(),
						listeObstaclesProjettent.get(i).getPosY(),
						listeObstaclesProjettent.get(i).getLargeur(),
						listeObstaclesProjettent.get(i).getHauteur());
			}
		}
		saute = false;
	}

	public void reset() {
		posX = 330;
		posY = 430;
		versLaDroite = true;
		arme = false;
		avance = false;
		ennemiAvance = false;
		saute = false;
		etapeMarche = 0;
		timer = 25;
		timerEnnemis = 25;
		listeTaches = new ArrayList<Tache>();
	}

	public int getPosY() {
		return posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosY(int y) {
		posY = y;
	}

	public void setPosX(int x) {
		posX = x;
	}

	public void setDroite() {
		versLaDroite = true;
	}

	public void setGauche() {
		versLaDroite = false;
	}

	public void monte() {
		posY -= 4;
		this.repaint();
	}
	
	public void monteVite() {
		posY -= 8;
		this.repaint();
	}

	public void descend() {
		posY += 4;
		this.repaint();
	}

	public void setDirection(boolean droite) {
		versLaDroite = droite;
	}

	public void goDroite() {
		for (int i = 0; i < listeTaches.size(); i++) {
			listeTaches.get(i).enlevePosX();
		}
		avance = true;
		this.repaint();
	}

	public void goGauche() {
		for (int i = 0; i < listeTaches.size(); i++) {
			listeTaches.get(i).ajoutePosX();
		}
		avance = true;
		this.repaint();
	}

	public void setSaute() {
		saute = true;
	}

	public void setArme(boolean b) {
		arme = b;
	}

	public void setListeObstacles(ArrayList<Obstacle> obs) {
		listeObstacles = obs;
	}

	public void setListeEnnemi(ArrayList<Ennemi> enn) {
		listeEnnemis = enn;
	}

	public void setListeObstaclePiege(ArrayList<ObstaclePiege> obp) {
		listeObstaclePieges = obp;
	}

	public void setListeEnnemisQuiTirent(ArrayList<EnnemiQuiTire> eqt) {
		listeEnnemisQuiTirent = eqt;
	}

	public void setListeProjectiles(ArrayList<Projectile> proj) {
		listeProjectiles = proj;
	}

	public void setListeCheckPoints(ArrayList<CheckPoint> CC) {
		listeCheckPoints = CC;
	}

	public void setListePics(ArrayList<Pic> pc) {
		listePics = pc;
	}
	
	public void setListePicsPlafond(ArrayList<Pic> pcp) {
		listePicsPlafond = pcp;
	}
	
	public void setListeObstaclesProjettent(ArrayList<Obstacle> obp) {
		listeObstaclesProjettent = obp;
	}

	public void ajouteTache(Tache t) {
		listeTaches.add(t);
	}
}
