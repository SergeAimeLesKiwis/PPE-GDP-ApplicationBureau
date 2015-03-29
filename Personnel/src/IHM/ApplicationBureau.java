package IHM;

import javax.swing.*;

import personnel.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings({ "serial", "unused" })
public class ApplicationBureau extends JFrame {

	/* Gestion des ligues */

	private GestionPersonnel gestionPersonnel;

	/* Différentes étapes de l'application */

	private int mode;
	final static int CONNEXION = 1;
	final static int MENU_PRINCIPAL = 2;
	final static int MODIF_COMPTE = 3;
	final static int MENU_LIGUES = 4;
	final static int LISTE_LIGUES = 5;
	final static int AJOUTER_LIGUE = 6;
	final static int MODIF_LIGUE = 7;

	/* Éléments des différentes page de l'application */

	private JLabel jlTitre, jlErreur, jlNom, jlPrenom, jlMail, jlMDP, jlNewMDP;
	private JTextArea jtAideNom, jtAidePrenom, jtAideMail, jtDescription;
	private JTextField jtNom, jtPrenom, jtMail;
	private JPasswordField jpConnexion, jpMDP, jpNewMDP;
	private JCheckBox jcbConnexion;
	private JButton jbOk, jbAnnuler, jbMenuLigues, jbModifCompte, jbListeComplete, jbAjoutLigue, jbModifLigue, jbSupprLigue;

	/**
	 * Crée une application de gestion des ligues.
	 * @param gestionPersonnel La gestion des ligues.
	 */

	public ApplicationBureau(GestionPersonnel gestionPersonnel) {
		super("Gestion du personnel");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("logoM2L.png"));
		setVisible(true);

		this.gestionPersonnel = gestionPersonnel;

		setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		setBounds(100, 100, 500, 400);
		setLocationRelativeTo(null);
		
		gestionPersonnel.add(new Ligue("ninja",""));

		changeMode(CONNEXION);
	}

	/**
	 * Retourne la disposition des élément de l'écran de connexion.
	 * @return Le panel de connexion.
	 */

	private JPanel getPanelConnexion() {
		JPanel panelConnexion = new JPanel();
		panelConnexion.setLayout(null);
		
		jlTitre = Creation.setLabel("Mot de passe super-administrateur", 1, 14, 131, 124, 224, 14);
		panelConnexion.add(jlTitre);

		jpConnexion = Creation.setPassword(13, 120, 180, 31);
		panelConnexion.add(jpConnexion);

		jcbConnexion = new JCheckBox("Afficher le mot de passe");
		jcbConnexion.setFont(new Font("Trebuchet MS", Font.PLAIN, 10));
		jcbConnexion.setBounds(140, 220, 141, 27);
		jcbConnexion.addActionListener(getAfficheMDP());
		panelConnexion.add(jcbConnexion);

		jbOk = Creation.setBouton("OK", 290, 181, 67);
		jbOk.addActionListener(getVerifConnexion());
		panelConnexion.add(jbOk);

		jlErreur = Creation.setLabel("Mot de passe incorrect !", 1, 13, 142, 260, 224, 14);
		jlErreur.setForeground(new Color(205, 92, 92));
		jlErreur.setVisible(false);
		panelConnexion.add(jlErreur);

		return panelConnexion;
	}

	/**
	 * Retourne la disposition des élément de l'écran de menu principal.
	 * @return Le panel du menu principal.
	 */

	private JPanel getPanelMenuPrincipal() {
		JPanel panelMenuPrincipal = new JPanel();
		panelMenuPrincipal.setLayout(null);

		jlTitre = Creation.setLabel("Menu principal", 1, 14, 205, 110, 100, 14);
		panelMenuPrincipal.add(jlTitre);

		jbModifCompte = Creation.setBouton("Modifier mon compte", 157, 167, 183);
		jbModifCompte.addActionListener(getChangeMode(MODIF_COMPTE));
		panelMenuPrincipal.add(jbModifCompte);

		jbMenuLigues = Creation.setBouton("Gérer les ligues", 170, 230, 157);
		jbMenuLigues.addActionListener(getChangeMode(MENU_LIGUES));
		panelMenuPrincipal.add(jbMenuLigues);

		return panelMenuPrincipal;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'un compte.
	 * @param employe L'employé a créé ou modifié.
	 * @return Le panel de "mon compte".
	 */

	private JPanel getPanelCompte(Employe employe) {
		JPanel panelCompte = new JPanel();
		panelCompte.setLayout(null);

		jlTitre = Creation.setLabel("Mon compte", 1, 13, 200, 40, 74, 21);
		panelCompte.add(jlTitre);

		jlNom = Creation.setLabel("Nom *", 1, 12, 100, 97, 58, 21);
		jlNom.addMouseListener(getHoverNom());
		panelCompte.add(jlNom);

		jtAideNom = Creation.setAide("Ne doit contenir que des lettres.", 57, 114);
		panelCompte.add(jtAideNom);

		jtNom = Creation.setTexte(employe.getNom(), 283, 96);
		panelCompte.add(jtNom);

		jlPrenom = Creation.setLabel("Prénom *", 1, 12, 100, 131, 58, 21);
		jlPrenom.addMouseListener(getHoverPrenom());
		panelCompte.add(jlPrenom);

		jtAidePrenom = Creation.setAide("Ne doit contenir que des lettres.", 57, 148);
		panelCompte.add(jtAidePrenom);

		jtPrenom = Creation.setTexte(employe.getPrenom(), 283, 130);
		panelCompte.add(jtPrenom);

		jlMail = Creation.setLabel("Mail *", 1, 12, 100, 163, 58, 21);
		jlMail.addMouseListener(getHoverMail());
		panelCompte.add(jlMail);

		jtAideMail = Creation.setAide("De plus, l'adresse renseignée doit être valide.", 57, 182);
		panelCompte.add(jtAideMail);

		jtMail = Creation.setTexte(employe.getMail(), 283, 162);
		panelCompte.add(jtMail);

		jlMDP = Creation.setLabel("Nouveau mot de passe", 1, 12, 100, 195, 126, 21);
		panelCompte.add(jlMDP);

		jpMDP = Creation.setPassword(12, 283, 195, 22);
		panelCompte.add(jpMDP);

		jlNewMDP = Creation.setLabel("Confirmer mot de passe", 1, 12, 100, 227, 134, 21);
		panelCompte.add(jlNewMDP);

		jpNewMDP = Creation.setPassword(12, 283, 227, 22);
		panelCompte.add(jpNewMDP);

		jlErreur = Creation.setLabel("", 1, 12, 190, 265, 224, 14);
		jlErreur.setVisible(false);
		panelCompte.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 115, 300, 89);
		jbAnnuler.addActionListener(getChangeMode(MENU_PRINCIPAL));
		panelCompte.add(jbAnnuler);

		jbOk = Creation.setBouton("Valider", 300, 300, 89);
		jbOk.addActionListener(getValiderModif());
		panelCompte.add(jbOk);

		return panelCompte;
	}

	/**
	 * Retourne la disposition des élément de l'écran de menu de gestion des ligues.
	 * @return Le panel du menu de gestion des ligues.
	 */

	private JPanel getPanelMenuLigues() {
		JPanel panelMenuLigues = new JPanel();
		panelMenuLigues.setLayout(null);

		jlTitre = Creation.setLabel("Gestion des ligues", 1, 14, 190, 59, 120, 14);
		panelMenuLigues.add(jlTitre);

		jbListeComplete = Creation.setBouton("Voir la liste complète", 157, 140, 183);
		jbListeComplete.addActionListener(getChangeMode(LISTE_LIGUES));
		panelMenuLigues.add(jbListeComplete);

		jbAjoutLigue = Creation.setBouton("Ajouter une ligue", 170, 195, 157);
		jbAjoutLigue.addActionListener(getChangeMode(AJOUTER_LIGUE));
		panelMenuLigues.add(jbAjoutLigue);
		
		jbAnnuler = Creation.setBouton("Annuler", 200, 250, 89);
		jbAnnuler.addActionListener(getChangeMode(MENU_PRINCIPAL));
		panelMenuLigues.add(jbAnnuler);

		return panelMenuLigues;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de menu d'ajout d'une ligue.
	 * @return Le panel du menu d'ajout d'une ligue.
	 */

	private JPanel getPanelAjouterLigue() {
		JPanel panelAjouterLigue = new JPanel();
		panelAjouterLigue.setLayout(null);

		jlTitre = Creation.setLabel("Ajout d'une ligue", 1, 14, 190, 40, 120, 14);
		panelAjouterLigue.add(jlTitre);

		jlNom = Creation.setLabel("Nom *", 1, 12, 145, 110, 58, 21);
		jlNom.addMouseListener(getHoverNom());
		panelAjouterLigue.add(jlNom);

		jtAideNom = Creation.setAide("Ne doit contenir que des lettres.", 80, 130);
		panelAjouterLigue.add(jtAideNom);

		jtNom = Creation.setTexte("", 285, 110);
		panelAjouterLigue.add(jtNom);
		
		jlPrenom = Creation.setLabel("Description", 1, 12, 145, 150, 80, 21);
		panelAjouterLigue.add(jlPrenom);

		jtDescription = new JTextArea();
		jtDescription.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 13));
		jtDescription.setMargin(new Insets(2, 10, 2, 2));
		jtDescription.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		jtDescription.setLineWrap(true);
		jtDescription.setBounds(285, 150, 150, 100);
		panelAjouterLigue.add(jtDescription);
		
		jlErreur = Creation.setLabel("", 1, 13, 165, 230, 200, 14);
		jlErreur.setVisible(false);
		panelAjouterLigue.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 125, 300, 89);
		jbAnnuler.addActionListener(getChangeMode(MENU_LIGUES));
		panelAjouterLigue.add(jbAnnuler);

		jbOk = Creation.setBouton("Ajouter", 289, 300, 89);
		jbOk.addActionListener(getAjouterLigue());
		panelAjouterLigue.add(jbOk);

		return panelAjouterLigue;
	}

	/**
	 * Retourne la disposition des élément de l'écran d'affichage de la liste des ligues vide.
	 * @return Le panel d'affichage de la liste vide.
	 */

	private JPanel getPanelListeLiguesVide() {
		JPanel panelListeLiguesVide = new JPanel();
		panelListeLiguesVide.setLayout(null);

		jlTitre = Creation.setLabel("Liste des ligues", 1, 14, 205, 50, 100, 14);
		panelListeLiguesVide.add(jlTitre);
		
		jlNom = Creation.setLabel("Aucune ligue... Ajoutez-en !", 0, 13, 175, 150, 200, 22);
		panelListeLiguesVide.add(jlNom);

		jbAnnuler = Creation.setBouton("Annuler", 125, 275, 89);
		jbAnnuler.addActionListener(getChangeMode(MENU_LIGUES));
		panelListeLiguesVide.add(jbAnnuler);

		jbOk = Creation.setBouton("Ajouter", 289, 275, 89);
		jbOk.addActionListener(getChangeMode(AJOUTER_LIGUE));
		panelListeLiguesVide.add(jbOk);

		return panelListeLiguesVide;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran d'affichage des ligues.
	 * @return Le panel d'affichage des ligues.
	 */

	private JPanel getPanelListeLigues() {
		JPanel panelListeLigues = new JPanel();
		panelListeLigues.setLayout(null);

		jlTitre = new JLabel("Liste des ligues");
		jlTitre.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		jlTitre.setBounds(205, 30, 100, 14);
		panelListeLigues.add(jlTitre);
		
		jlNom = new JLabel("Nom");
		jlNom.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		jlNom.setBounds(80, 65, 100, 14);
		panelListeLigues.add(jlNom);
		
		jlPrenom = new JLabel("Administrateur");
		jlPrenom.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		jlPrenom.setBounds(240, 65, 100, 14);
		panelListeLigues.add(jlPrenom);
		
		jlMail = new JLabel("Effectif");
		jlMail.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		jlMail.setBounds(375, 65, 100, 14);
		panelListeLigues.add(jlMail);
		
		JSeparator separateur = new JSeparator();
		separateur.setBounds(90, 80, 320, 2);
		separateur.setEnabled(false);
		panelListeLigues.add(separateur);
		
		String[] data = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
		JList<String> myList = new JList<String>(data);
		//JList<Ligue> myList = new JList<Ligue>((Ligue[]) gestionPersonnel.getLigues().toArray());
		myList.setFont(new Font("Trebuchet MS", Font.ITALIC, 16));
		
		JScrollPane scrollPane = new JScrollPane (myList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(70, 90, 350, 200);
		panelListeLigues.add(scrollPane);
		
		jbAnnuler = new JButton("Annuler");
		jbAnnuler.setBounds(50, 333, 89, 27);
		jbAnnuler.addActionListener(getChangeMode(MENU_LIGUES));
		panelListeLigues.add(jbAnnuler);

		jbModifLigue = new JButton("Modifier");
		jbModifLigue.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		jbModifLigue.setBounds(195, 333, 100, 27);
		//jbModifLigue.addActionListener(getChangeMode(MODIF_COMPTE));
		panelListeLigues.add(jbModifLigue);

		jbSupprLigue = new JButton("Supprimer");
		jbSupprLigue.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		jbSupprLigue.setBounds(350, 333, 100, 27);
		//jbSupprLigue.addActionListener(getChangeMode(MENU_LIGUES));
		panelListeLigues.add(jbSupprLigue);

		return panelListeLigues;
	}
	
	/**
	 * Actualise la fenêtre de l'application.
	 */

	private void actualiseFenetre() {
		validate();
		repaint();
	}

	/**
	 * Change le mode et le panel de l'application.
	 * @param mode Le nouveau mode.
	 */

	private void changeMode(int mode) {
		switch (mode) {
		case CONNEXION:
			setContentPane(getPanelConnexion());
			break;

		case MENU_PRINCIPAL:
			setContentPane(getPanelMenuPrincipal());
			break;

		case MODIF_COMPTE:
			setContentPane(getPanelCompte(gestionPersonnel.getRoot()));
			break;
			
		case MENU_LIGUES:
			setContentPane(getPanelMenuLigues());
			break;
			
		case LISTE_LIGUES:
			if (gestionPersonnel.getLigues().isEmpty())
				setContentPane(getPanelListeLiguesVide());
			else
				setContentPane(getPanelListeLigues());
			break;
			
		case AJOUTER_LIGUE:
			setContentPane(getPanelAjouterLigue());
			break;
			
		case MODIF_LIGUE:
			
			break;
		}

		this.mode = mode;
		actualiseFenetre();
	}

	/**
	 * Retourne si le mot de passe super-administrateur est bon.
	 * @return Si le mot de passe est bon.
	 */

	private boolean verifConnexion() {
		return gestionPersonnel.getRoot().checkPassword(new String(jpConnexion.getPassword()));
	}
	
	/**
	 * Affichage d'une sauvegarde réussie.
	 */

	private void erreurOK(String message) {
		jlErreur.setForeground(new Color(0, 128, 0));
		jlErreur.setText(message);
		jlErreur.setVisible(true);
	}

	/**
	 * Affichage d'une sauvegarde ratée.
	 */

	private void erreurNOK(String message) {
		jlErreur.setForeground(new Color(205, 92, 92));
		jlErreur.setText(message);
		jlErreur.setVisible(true);
	}

	/**
	 * Vérifie l'intégrité du champ.
	 * @param text Le champ texte à vérifier.
	 * @return Si le champ ne contient que des lettres.
	 */

	private int verifLettres(JTextField text) {
		int test = 0;
		
		if ((text.getText().equals("")) || !text.getText().matches("[a-zA-Z]*")) {
			text.setBackground(new Color(205, 92, 92));
			test++;
		} else
			text.setBackground(new Color(255, 255, 255));
		
		return test;
	}

	/**
	 * Vérifie la validité du champ.
	 * @param text Le champ texte à vérifier.
	 * @return Si le champ est une adresse mail valide.
	 */

	private int verifMail(JTextField text) {
		int test = 0;
		
		if ((text.equals("")) || !text.getText().matches(
						"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			text.setBackground(new Color(205, 92, 92));
			test++;
		} else
			text.setBackground(new Color(255, 255, 255));
		
		return test;
	}

	/**
	 * Vérifie que les deux champs de mot de passe sont vides ou renseignés.
	 * @return Si les champs sont vides ou renseignés.
	 */

	private int verifNewMDP() {
		int test = 0;
		boolean vide = true;

		if (jpMDP.getPassword().equals("") && !jpNewMDP.getPassword().equals(""))
			vide = false;
		else if (jpNewMDP.getPassword().equals("") && !jpMDP.getPassword().equals(""))
			vide = false;
		
		if ((!vide) || (!new String(jpMDP.getPassword()).equals(new String(jpNewMDP.getPassword())))) {
			jpMDP.setBackground(new Color(205, 92, 92));
			jpNewMDP.setBackground(new Color(205, 92, 92));
			test++;
		} else {
			jpMDP.setBackground(new Color(255, 255, 255));
			jpNewMDP.setBackground(new Color(255, 255, 255));
		}

		return test;
	}
	
	/**
	 * Sauvegarde des nouvelles données du super-administrateur.
	 */

	private void saveRoot() {
		gestionPersonnel.getRoot().setNom(jtNom.getText());
		gestionPersonnel.getRoot().setPrenom(jtPrenom.getText());
		gestionPersonnel.getRoot().setMail(jtMail.getText());
		gestionPersonnel.getRoot().setPassword(new String(jpMDP.getPassword()));
	}
	
	/**
	 * Retourne la fonction d'affichage de l'aide (nom).
	 * @return Aide du nom.
	 */

	private MouseAdapter getHoverNom() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jtAideNom.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jtAideNom.setVisible(false);
			}
		};
	}

	/**
	 * Retourne la fonction d'affichage de l'aide (prénom).
	 * @return Aide du prénom.
	 */

	private MouseAdapter getHoverPrenom() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jtAidePrenom.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jtAidePrenom.setVisible(false);
			}
		};
	}

	/**
	 * Retourne la fonction d'affichage de l'aide (mail).
	 * @return Aide du mail.
	 */

	private MouseAdapter getHoverMail() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jtAideMail.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jtAideMail.setVisible(false);
			}
		};
	}
	
	/**
	 * Retourne la fonction de changement d'affichage.
	 * @return Nouvel affichage.
	 */

	private ActionListener getChangeMode(final int mode) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeMode(mode);
			}
		};
	}
	
	/**
	 * Retourne la fonction d'affichage du mot de passe.
	 * @return Affichage du mot de passe ou non.
	 */

	private ActionListener getAfficheMDP() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jcbConnexion.isSelected())
					jpConnexion.setEchoChar((char) 0);
				else
					jpConnexion.setEchoChar('•');
			}
		};
	}

	/**
	 * Retourne la fonction de vérification du mot de passe afin de se connecter.
	 * @return Connexion ou non.
	 */

	private ActionListener getVerifConnexion() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (verifConnexion()) {
					changeMode(MENU_PRINCIPAL);
				} else {
					jlErreur.setVisible(true);
					jpConnexion.setText("");
				}
			}
		};
	}

	/**
	 * Retourne la fonction de vérification des données et sauvegarde si données conformes.
	 * @return Vérification et sauvegarde des données.
	 */

	private ActionListener getValiderModif() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int test = 0;

				/* Test d'intégrité du nom */
				
				test += verifLettres(jtNom);

				/* Test d'intégrité du prénom */

				test += verifLettres(jtPrenom);

				/* Test d'intégrité du mail */
				
				test += verifMail(jtMail);

				/* Test d'intégrité des mots de passe */
				
				test += verifNewMDP();

				/* Sauvegarde du résultat, si les tests sont conformes */

				if (test == 0) {
					erreurOK("Sauvegarde réussie !");
					saveRoot();
				} else
					erreurNOK("Sauvegarde ratée !");

				/* Réinitialisation des champs de mots de passe */
				
				jpMDP.setText("");
				jpNewMDP.setText("");
			}
		};
	}

	/**
	 * Retourne la fonction de vérification d'ajout d'une ligue.
	 * @return Ajout d'une ligue ou non.
	 */

	private ActionListener getAjouterLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int test = 0;

				/* Test d'intégrité du nom */
				
				test += verifLettres(jtNom);
				
				/* Sauvegarde du résultat, si les tests sont conformes */
				
				if (test == 0) {
					erreurOK("Ajout réussi !");
					jlErreur.setBounds(180, 130, 200, 14);
					jbAnnuler.setText("Retour");
					jbAnnuler.setBounds(200, 230, 84, 27);
					jlNom.setVisible(false);
					jtNom.setVisible(false);
					jlPrenom.setVisible(false);
					jtDescription.setVisible(false);
					jbOk.setVisible(false);
					gestionPersonnel.add(new Ligue(jtNom.getText(), jtDescription.getText()));
					
				} else
					erreurNOK("Ajout raté !");
			}
		};
	}
	
	public static void main(String[] args) {
		new ApplicationBureau(GestionPersonnel.getGestionPersonnel());
	}

}