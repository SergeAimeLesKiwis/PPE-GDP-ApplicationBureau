package IHM;

import javax.swing.*;

import personnel.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
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
	final static int MODIFIER_LIGUE = 7;
	final static int GERER_LIGUE = 8;
	final static int AJOUTER_EMPLOYE = 9;
	
	private int indiceLigue, indiceEmploye;

	/* Éléments des différentes page de l'application */

	private JLabel jlTitre, jlErreur, jlNom, jlPrenom, jlMail, jlMDP, jlNewMDP;
	private JTextArea jtAideNom, jtAidePrenom, jtAideMail, jtDescription;
	private JTextField jtNom, jtPrenom, jtMail;
	private JPasswordField jpConnexion, jpMDP, jpNewMDP;
	private JCheckBox jcbCheck;
	private JButton jbOk, jbAnnuler, jbMenuLigues, jbListeComplete, jbModif, jbAjout, jbSuppr;
	private JTable jtTableau;

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
		
		Ligue l = new Ligue("RocknRoll","Oh yeah !");
		l.addEmploye("Moreira", "Diego", "kikoo@jesuisunnoob.com", "aiiightsussumgl");
		l.addEmploye("Flégeau", "Yoann", "emo@jaipasdetesticules.net", "jaimelespapillons");
		l.addEmploye("111", "11", "1", "");
		l.addEmploye("222", "22", "2", "");
		l.addEmploye("333", "33", "3", "");
		l.addEmploye("444", "44", "4", "");
		gestionPersonnel.add(l);
		gestionPersonnel.add(new Ligue("ligue test","description de ninja"));

		changeMode(MENU_PRINCIPAL);
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

		jcbCheck = Creation.setCheck("Afficher le mot de passe", 0, 10, 140, 220, 141, 27);
		jcbCheck.addActionListener(getAfficheMDP());
		panelConnexion.add(jcbCheck);

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

		jbModif = Creation.setBouton("Modifier mon compte", 157, 167, 183);
		jbModif.addActionListener(getChangeMode(MODIF_COMPTE));
		panelMenuPrincipal.add(jbModif);

		jbMenuLigues = Creation.setBouton("Gérer les ligues", 170, 230, 157);
		jbMenuLigues.addActionListener(getChangeMode(MENU_LIGUES));
		panelMenuPrincipal.add(jbMenuLigues);

		return panelMenuPrincipal;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'un compte.
	 * @param employe L'employé a créé ou modifié.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelModifCompte(Employe employe) {
		JPanel panelCompte = new JPanel();
		panelCompte.setLayout(null);

		jlTitre = Creation.setLabel("Mon compte", 1, 13, 200, 40, 74, 21);
		panelCompte.add(jlTitre);

		jtAideNom = Creation.setAide("Ne doit contenir que des lettres.", 57, 114);
		panelCompte.add(jtAideNom);
		
		jlNom = Creation.setLabel("Nom *", 1, 12, 100, 97, 58, 21);
		jlNom.addMouseListener(getAide(jtAideNom));
		panelCompte.add(jlNom);

		jtNom = Creation.setTexte(employe.getNom(), 283, 96);
		panelCompte.add(jtNom);

		jtAidePrenom = Creation.setAide("Ne doit contenir que des lettres.", 57, 148);
		panelCompte.add(jtAidePrenom);
		
		jlPrenom = Creation.setLabel("Prénom *", 1, 12, 100, 131, 58, 21);
		jlPrenom.addMouseListener(getAide(jtAidePrenom));
		panelCompte.add(jlPrenom);

		jtPrenom = Creation.setTexte(employe.getPrenom(), 283, 130);
		panelCompte.add(jtPrenom);

		jtAideMail = Creation.setAide("De plus, l'adresse renseignée doit être valide.", 57, 182);
		panelCompte.add(jtAideMail);
		
		jlMail = Creation.setLabel("Mail *", 1, 12, 100, 163, 58, 21);
		jlMail.addMouseListener(getAide(jtAideMail));
		panelCompte.add(jlMail);

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
		panelCompte.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 115, 300, 89);
		jbAnnuler.addActionListener(getChangeMode(MENU_PRINCIPAL));
		panelCompte.add(jbAnnuler);

		jbOk = Creation.setBouton("Valider", 300, 300, 89);
		jbOk.addActionListener(getValiderModifCompte());
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

		jbAjout = Creation.setBouton("Ajouter une ligue", 170, 195, 157);
		jbAjout.addActionListener(getChangeMode(AJOUTER_LIGUE));
		panelMenuLigues.add(jbAjout);
		
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

		jtAideNom = Creation.setAide("Ne doit contenir que des lettres.", 80, 130);
		panelAjouterLigue.add(jtAideNom);
		
		jlNom = Creation.setLabel("Nom *", 1, 12, 145, 110, 58, 21);
		jlNom.addMouseListener(getAide(jtAideNom));
		panelAjouterLigue.add(jlNom);

		jtNom = Creation.setTexte("", 285, 110);
		panelAjouterLigue.add(jtNom);
		
		jlPrenom = Creation.setLabel("Description", 1, 12, 145, 150, 80, 21);
		panelAjouterLigue.add(jlPrenom);

		jtDescription = Creation.setTexteLong("", 285, 150, 150, 100);
		panelAjouterLigue.add(jtDescription);
		
		jlErreur = Creation.setLabel("", 1, 13, 165, 230, 200, 14);
		panelAjouterLigue.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 125, 300, 89);
		jbAnnuler.addActionListener(getChangeMode(MENU_LIGUES));
		panelAjouterLigue.add(jbAnnuler);

		jbOk = Creation.setBouton("Ajouter", 289, 300, 89);
		jbOk.addActionListener(getValiderAjoutLigue());
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

		jlTitre = Creation.setLabel("Liste des ligues", 1, 14, 205, 20, 100, 14);
		panelListeLigues.add(jlTitre);

		String[] ligneTitre = new String[] {"Nom","Administrateur","Effectif"};
		
		jtTableau = Creation.setTableau(ligneTitre, gestionPersonnel.tabLigues(), 220, 125, 55);		
//		jtabLigues.addMouseListener(getAfficherBoutonTable(jbOk, jbAnnuler));
//		jtabLigues.getSelectionModel().addListSelectionListener(getAfficherBoutonTable(jbOk, jbAnnuler));

		JScrollPane scroll = Creation.setScroll(jtTableau, 50, 40, 400, 220);
		panelListeLigues.add(scroll);
		
		jlErreur = Creation.setLabel("", 1, 13, 150, 295, 200, 14);
		panelListeLigues.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 200, 333, 89);
		jbAnnuler.addActionListener(getChangeMode(MENU_LIGUES));
		panelListeLigues.add(jbAnnuler);

		jbModif = Creation.setBouton("Modifier", 35, 325, 100);
		jbModif.addActionListener(getModifierLigue());
//		jbModif.setEnabled(false);
		panelListeLigues.add(jbModif);
		
		JButton jbGerer = Creation.setBouton("Gérer", 35, 290, 100);
		jbGerer.addActionListener(getGererLigue());
//		jbGerer.setEnabled(false);
		panelListeLigues.add(jbGerer);

		jbSuppr = Creation.setBouton("Supprimer", 360, 325, 100);
		jbSuppr.addActionListener(getSupprimerLigue());
//		jbSuppr.setEnabled(false);
		panelListeLigues.add(jbSuppr);
		
		JButton jbAfficher = Creation.setBouton("Afficher", 360, 290, 100);
		jbAfficher.addActionListener(getAfficherLigue());
//		jbAfficher.setEnabled(false);
		panelListeLigues.add(jbAfficher);

		return panelListeLigues;
	}

	/**
	 * Retourne la disposition des élément de l'écran de modification d'une ligue.
	 * @param ligue La ligue a créée ou modifiée.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelModifierLigue(Ligue ligue) {
		JPanel panelModifLigue = new JPanel();
		panelModifLigue.setLayout(null);

		jlTitre = Creation.setLabel("Modification d'une ligue", 1, 13, 190, 40, 150, 21);
		panelModifLigue.add(jlTitre);

		jtAideNom = Creation.setAide("Ne doit contenir que des lettres.", 57, 114);
		panelModifLigue.add(jtAideNom);
		
		jlNom = Creation.setLabel("Nom *", 1, 12, 100, 97, 58, 21);
		jlNom.addMouseListener(getAide(jtAideNom));
		panelModifLigue.add(jlNom);

		jtNom = Creation.setTexte(ligue.getNom(), 285, 96);
		panelModifLigue.add(jtNom);

		jlPrenom = Creation.setLabel("Description", 1, 12, 100, 131, 100, 21);
		panelModifLigue.add(jlPrenom);

		jtDescription = Creation.setTexteLong(ligue.getDescription(), 285, 150, 150, 100);
		panelModifLigue.add(jtDescription);

		jlErreur = Creation.setLabel("", 1, 12, 190, 265, 224, 14);
		panelModifLigue.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 115, 300, 89);
		jbAnnuler.addActionListener(getChangeMode(LISTE_LIGUES));
		panelModifLigue.add(jbAnnuler);

		jbOk = Creation.setBouton("Valider", 300, 300, 89);
		jbOk.addActionListener(getValiderModifLigue());
		panelModifLigue.add(jbOk);

		return panelModifLigue;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'une ligue.
	 * @param ligue La ligue a créée ou modifiée.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelGererLigue(Ligue ligue) {
		JPanel panelGererLigue = new JPanel();
		panelGererLigue.setLayout(null);

		jlTitre = Creation.setLabel("Gérer : " + ligue.getNom(), 1, 13, 190, 25, 150, 21);
		panelGererLigue.add(jlTitre);

		jlNom = Creation.setLabel("Administrateur", 1, 12, 130, 50, 100, 21);
		panelGererLigue.add(jlNom);
		
		JComboBox<Employe> jcbAdmin = new JComboBox<Employe>();
		jcbAdmin.setBounds(250, 50, 130, 26);
		jcbAdmin.setBackground(new Color(204, 204, 204));
		
		/* Ajout de la liste des employés */

		if (ligue.getEmployes().size() == 0)
			jcbAdmin.addItem(gestionPersonnel.getRoot());
		else 
			for (Employe employe : ligue.getEmployes())
				jcbAdmin.addItem(employe);

		/* Sélection de l'administrateur */
		
		jcbAdmin.setSelectedItem(ligue.getAdministrateur());
		jcbAdmin.addActionListener(getChangerAdminLigue(jcbAdmin));
		
		panelGererLigue.add(jcbAdmin);
		
		String[] ligneTitre = new String[] {"Nom","Prénom", "Mail"};
		
		jtTableau = Creation.setTableau(ligneTitre, ligue.tabEmployes(), 120, 120, 160);		
//		jtTableau.addMouseListener(getAfficherBoutonTable(jbOk, jbAnnuler));
//		jtTableau.getSelectionModel().addListSelectionListener(getAfficherBoutonTable(jbOk, jbAnnuler));

		JScrollPane scroll = Creation.setScroll(jtTableau, 50, 90, 400, 200);
		panelGererLigue.add(scroll);

		jlErreur = Creation.setLabel("", 1, 12, 175, 305, 224, 14);
		panelGererLigue.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 50, 333, 89);
		jbAnnuler.addActionListener(getChangeMode(LISTE_LIGUES));
		panelGererLigue.add(jbAnnuler);

		jbAjout = Creation.setBouton("Ajouter", 190, 333, 89);
		jbAjout.addActionListener(getChangeMode(AJOUTER_EMPLOYE));
		panelGererLigue.add(jbAjout);
		
		jbSuppr = Creation.setBouton("Supprimer", 335, 333, 100);
		jbSuppr.addActionListener(getSupprimerEmploye());
		panelGererLigue.add(jbSuppr);

		return panelGererLigue;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'un compte.
	 * @param employe L'employé a créé ou modifié.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelAjouterEmploye() {
		JPanel panelAjoutEmploye = new JPanel();
		panelAjoutEmploye.setLayout(null);

		jlTitre = Creation.setLabel("Ajout d'un employé", 1, 13, 175, 40, 150, 21);
		panelAjoutEmploye.add(jlTitre);

		jtAideNom = Creation.setAide("Ne doit contenir que des lettres.", 57, 114);
		panelAjoutEmploye.add(jtAideNom);
		
		jlNom = Creation.setLabel("Nom *", 1, 12, 100, 97, 58, 21);
		jlNom.addMouseListener(getAide(jtAideNom));
		panelAjoutEmploye.add(jlNom);

		jtNom = Creation.setTexte("", 283, 96);
		panelAjoutEmploye.add(jtNom);

		jtAidePrenom = Creation.setAide("Ne doit contenir que des lettres.", 57, 148);
		panelAjoutEmploye.add(jtAidePrenom);
		
		jlPrenom = Creation.setLabel("Prénom *", 1, 12, 100, 131, 58, 21);
		jlPrenom.addMouseListener(getAide(jtAidePrenom));
		panelAjoutEmploye.add(jlPrenom);

		jtPrenom = Creation.setTexte("", 283, 130);
		panelAjoutEmploye.add(jtPrenom);

		jtAideMail = Creation.setAide("De plus, l'adresse renseignée doit être valide.", 57, 182);
		panelAjoutEmploye.add(jtAideMail);
		
		jlMail = Creation.setLabel("Mail *", 1, 12, 100, 163, 58, 21);
		jlMail.addMouseListener(getAide(jtAideMail));
		panelAjoutEmploye.add(jlMail);

		jtMail = Creation.setTexte("", 283, 162);
		panelAjoutEmploye.add(jtMail);

		List<Ligue> ligues = new ArrayList<>(gestionPersonnel.getLigues());
		
		jcbCheck = Creation.setCheck("Devenir administrateur de la ligue", 1, 13, 115, 210, 250, 27);
		
		/* Le premier employé ajouté devient administrateur */
		
		if (ligues.get(indiceLigue).getEmployes().isEmpty()) {
			jcbCheck.setSelected(true);
			jcbCheck.setEnabled(false);
		}

		panelAjoutEmploye.add(jcbCheck);

		jlErreur = Creation.setLabel("", 1, 12, 190, 265, 224, 14);
		panelAjoutEmploye.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 115, 300, 89);
		jbAnnuler.addActionListener(getChangeMode(GERER_LIGUE));
		panelAjoutEmploye.add(jbAnnuler);

		jbOk = Creation.setBouton("Valider", 300, 300, 89);
		jbOk.addActionListener(getValiderAjoutEmploye());
		panelAjoutEmploye.add(jbOk);

		return panelAjoutEmploye;
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
		List<Ligue> ligues = new ArrayList<>(gestionPersonnel.getLigues());
		
		switch (mode) {
		case CONNEXION:
			setContentPane(getPanelConnexion());
			break;

		case MENU_PRINCIPAL:
			setContentPane(getPanelMenuPrincipal());
			break;

		case MODIF_COMPTE:
			setContentPane(getPanelModifCompte(gestionPersonnel.getRoot()));
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
			
		case MODIFIER_LIGUE:
			setContentPane(getPanelModifierLigue(ligues.get(indiceLigue)));
			break;
		
		case GERER_LIGUE:
			setContentPane(getPanelGererLigue(ligues.get(indiceLigue)));
		break;
		
		case AJOUTER_EMPLOYE:
			setContentPane(getPanelAjouterEmploye());
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
	}


	/**
	 * Affichage d'une sauvegarde ratée.
	 */

	private void erreurNOK(String message) {
		jlErreur.setForeground(new Color(205, 92, 92));
		jlErreur.setText(message);
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
	 * Supprime la ligue sélectionnée dans la liste.
	 * @param ligues La ligues des ligues.
	 */
	
	private void supprimerLigue(List<Ligue> ligues) {
		ligues.get(indiceLigue).remove();
	}
	
	/**
	 * Supprime la ligue sélectionnée dans la liste.
	 * @param employes La ligues des ligues.
	 */
	
	private void supprimerEmploye(List<Employe> employes) {
		employes.get(indiceEmploye).remove();
	}
	
	/**
	 * Retourne un mot de passe aléatoire.
	 * @return Mot de passe.
	 */
	
	private String newMDP() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
		
	/**
	 * Retourne la fonction d'affichage de l'aide.
	 * @return Affichage de l'aide.
	 */

	private MouseAdapter getAide(final JTextArea aide) {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				aide.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				aide.setVisible(false);
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
				if (jcbCheck.isSelected())
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

	private ActionListener getValiderModifCompte() {
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
					if (mode == MODIF_COMPTE)
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

	private ActionListener getValiderAjoutLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int test = 0;
				
				/* Test d'intégrité du nom */
				
				test += verifLettres(jtNom);
				
				/* Sauvegarde du résultat, si les tests sont conformes */
				
				if (test == 0) {
					erreurOK("Ajout réussi !");
					jlErreur.setBounds(192, 130, 200, 14);
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

	/**
	 * Retourne la fonction d'affichage des boutons lorsqu'une ligue a été choisie.
	 * @return Affichage des boutons ou non.
	 */

//	private ListSelectionListener getAfficherBoutonTable(final JButton jb1, final JButton jb2) {
//		return new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				if(!e.getValueIsAdjusting()) {
//					try {
//						jb1.setEnabled(jtabLigues.getSelectedRow() > -1);
//						jb2.setEnabled(jtabLigues.getSelectedRow() > -1);
//					} catch (Exception e2) {
//						jbOk.setEnabled(true);
//					}
////					actualiseFenetre();
//				}
//		    }
//		};
//	}
//
//	private MouseAdapter getAfficherBoutonTable(final JButton jb1, final JButton jb2) {
//		return new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				//jb1.setEnabled(jtabLigues.getSelectedRow() > -1);
//				//jb2.setEnabled(jtabLigues.getSelectedRow() > -1);
////				actualiseFenetre();
//			}
//		};
//	}
	
	/**
	 * Retourne la fonction de vérification de modification d'une ligue.
	 * @return Modification d'une ligue ou non.
	 */

	private ActionListener getModifierLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTableau.getSelectedRow() > -1) {
					indiceLigue = jtTableau.getSelectedRow();
					changeMode(MODIFIER_LIGUE);
				} else
					erreurNOK("Veuillez sélectionnez une ligue !");
			}
		};
	}
	
	/**
	 * Retourne la fonction de vérification de modification d'une ligue.
	 * @return Modification d'une ligue ou non.
	 */

	private ActionListener getGererLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTableau.getSelectedRow() > -1) {
					indiceLigue = jtTableau.getSelectedRow();
					changeMode(GERER_LIGUE);
				} else
					erreurNOK("Veuillez sélectionnez une ligue !");
			}
		};
	}

	/**
	 * Retourne la fonction de vérification de suppresion d'une ligue.
	 * @return Suppression d'une ligue ou non.
	 */

	private ActionListener getSupprimerLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTableau.getSelectedRow() > -1) {
					indiceLigue = jtTableau.getSelectedRow();
					jlErreur.setText("");
					int n = JOptionPane.showConfirmDialog(
							null, "Êtes-vous sûr(e) de vouloir supprimer la ligue ?", "Vérification", JOptionPane.YES_NO_OPTION);
					
					if (n == JOptionPane.YES_OPTION) {
						supprimerLigue(new ArrayList<>(gestionPersonnel.getLigues()));
						changeMode(LISTE_LIGUES);
					}
				} else
					erreurNOK("Veuillez sélectionnez une ligue !");
			}
		};
	}
	
	/**
	 * Retourne la fonction de vérification de modification d'une ligue.
	 * @return Modification d'une ligue ou non.
	 */

	private ActionListener getAfficherLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTableau.getSelectedRow() > -1) {
					indiceLigue = jtTableau.getSelectedRow();
					System.out.println("afficher");
				}
				else
					erreurNOK("Veuillez sélectionnez une ligue !");
			}
		};
	}
	
	/**
	 * Retourne la fonction de vérification d'ajout d'une ligue.
	 * @return Ajout d'une ligue ou non.
	 */

	private ActionListener getValiderModifLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int test = 0;
				
				/* Test d'intégrité du nom */
				
				test += verifLettres(jtNom);
				
				/* Sauvegarde du résultat, si les tests sont conformes */
				
				if (test == 0) {
					erreurOK("Modification réussie !");
					List<Ligue> ligues = new ArrayList<>(gestionPersonnel.getLigues());
					ligues.get(indiceLigue).setNom(jtNom.getText());
					ligues.get(indiceLigue).setDescription(jtDescription.getText());
				} else
					erreurNOK("Modification ratée !");
			}
		};
	}

	/**
	 * Retourne la fonction de changement d'administrateur d'une ligue.
	 * @return Changement d'administrateur d'une ligue.
	 */

	private ActionListener getChangerAdminLigue(final JComboBox<Employe> jcb) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Ligue> ligues = new ArrayList<>(gestionPersonnel.getLigues());
				ligues.get(indiceLigue).setAdministrateur((Employe) jcb.getSelectedItem());
			}
		};
	}
	
	/**
	 * Retourne la fonction de vérification de suppresion d'un employé.
	 * @return Suppression un employé ou non.
	 */

	private ActionListener getSupprimerEmploye() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTableau.getSelectedRow() > -1) {
					indiceEmploye = jtTableau.getSelectedRow();
					jlErreur.setText("");
					
					int n = JOptionPane.showConfirmDialog(
							null, "Êtes-vous sûr(e) de vouloir supprimer l'employé ?", "Vérification", JOptionPane.YES_NO_OPTION);
					
					if (n == JOptionPane.YES_OPTION) {
						List<Ligue> ligues = new ArrayList<>(gestionPersonnel.getLigues());
						supprimerEmploye(new ArrayList<>(ligues.get(indiceLigue).getEmployes()));
						changeMode(GERER_LIGUE);
					}
				} else
					erreurNOK("Veuillez sélectionnez un employé !");
			}
		};
	}
	
	/**
	 * Retourne la fonction de vérification de d'ajout d'un employé.
	 * @return Ajout d'un employé.
	 */

	private ActionListener getValiderAjoutEmploye() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int test = 0;

				/* Test d'intégrité du nom */
				
				test += verifLettres(jtNom);

				/* Test d'intégrité du prénom */

				test += verifLettres(jtPrenom);

				/* Test d'intégrité du mail */
				
				test += verifMail(jtMail);

				/* Sauvegarde du résultat, si les tests sont conformes */

				if (test == 0) {
					List<Ligue> ligues = new ArrayList<>(gestionPersonnel.getLigues());
					
					/* Ajout de l'employé et changement d'administrateur si nécessaire */
					
					if (jcbCheck.isSelected())
						ligues.get(indiceLigue).setAdministrateur(ligues.get(indiceLigue).addEmploye(jtNom.getText(), jtPrenom.getText(), jtMail.getText(), newMDP()));
					else
						ligues.get(indiceLigue).addEmploye(jtNom.getText(), jtPrenom.getText(), jtMail.getText(), newMDP());
					
					// ENVOIE MAIL DES INFOS
					
					changeMode(GERER_LIGUE);
				} else
					erreurNOK("Ajout raté !");
			}
		};
	}

	public static void main(String[] args) {
		new ApplicationBureau(GestionPersonnel.getGestionPersonnel());
	}

}