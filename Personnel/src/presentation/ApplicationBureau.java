package presentation;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import metiers.*;
import donnees.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;

@SuppressWarnings("serial")
public class ApplicationBureau extends JFrame {

	/* Gestion des ligues */

	private GestionPersonnel gestionPersonnel;

	/* Différentes étapes de l'application */

	private Mode mode;

	/* Éléments des différentes page de l'application */

	private JLabel jlTitre, jlErreur, jlId, jlNom, jlPrenom, jlMail, jlMDP, jlNewMDP;
	private JTextArea jtAideNom, jtAidePrenom, jtAideMail, jtDescription;
	private JTextField jtNom, jtPrenom, jtMail;
	private JPasswordField jpMDP, jpNewMDP;
	private JCheckBox jcbCheck;
	private JButton jbOk, jbAnnuler, jbMenuLigues, jbListeComplete, jbModif, jbAjout, jbSuppr;
	private JTable jtTableau;
	private JComboBox<Employe> jcbAdmin;
	private JComboBox<Sport> jcbSport;
	
	private String[] ligneTitre;

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
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		changeMode(Mode.CONNEXION, 0);
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

		jpMDP = Creation.setPassword(13, 120, 180, 31);
		panelConnexion.add(jpMDP);

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
		jbModif.addActionListener(getChangeMode(Mode.MODIFIER_ROOT, 0));
		panelMenuPrincipal.add(jbModif);

		jbMenuLigues = Creation.setBouton("Gérer les ligues", 170, 230, 157);
		jbMenuLigues.addActionListener(getChangeMode(Mode.MENU_LIGUES, 0));
		panelMenuPrincipal.add(jbMenuLigues);

		return panelMenuPrincipal;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'un compte.
	 * @param employe L'employé a créé ou modifié.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelModifierCompte(Employe employe) {
		JPanel panelCompte = new JPanel();
		panelCompte.setLayout(null);
				
		String titre = (mode == Mode.MODIFIER_ROOT) ? "Mon compte" : "Modification d'un employé";
		int x = (mode == Mode.MODIFIER_ROOT) ? 200 : 170;

		jlTitre = Creation.setLabel(titre, 1, 13, x, 40, 200, 21);
		panelCompte.add(jlTitre);
		
		jlId = Creation.setHidden("" + employe.getId());
		panelCompte.add(jlId);

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
		
		if (mode == Mode.MODIFIER_ROOT)
			jbAnnuler.addActionListener(getChangeMode(Mode.MENU_PRINCIPAL, 0));
		else
			jbAnnuler.addActionListener(getChangeMode(Mode.GERER_LIGUE, employe.getLigue().getId()));
		
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
		jbListeComplete.addActionListener(getChangeMode(Mode.LISTE_LIGUES, 0));
		panelMenuLigues.add(jbListeComplete);

		jbAjout = Creation.setBouton("Ajouter une ligue", 170, 195, 157);
		jbAjout.addActionListener(getChangeMode(Mode.AJOUTER_LIGUE, 0));
		panelMenuLigues.add(jbAjout);
		
		jbAnnuler = Creation.setBouton("Annuler", 200, 250, 89);
		jbAnnuler.addActionListener(getChangeMode(Mode.MENU_PRINCIPAL, 0));
		panelMenuLigues.add(jbAnnuler);

		return panelMenuLigues;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'une ligue.
	 * @param ligue La ligue a créée ou modifiée.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelAjoutModifLigue(Ligue ligue) {
		JPanel panelAjoutModifLigue = new JPanel();
		panelAjoutModifLigue.setLayout(null);

		String titre = (mode == Mode.AJOUTER_LIGUE) ? "Ajout" : "Modification";
		titre += " d'une ligue";
		
		jlId = Creation.setHidden("" + ligue.getId());
		panelAjoutModifLigue.add(jlId);
		
		jlTitre = Creation.setLabel(titre, 1, 13, 190, 30, 150, 21);
		panelAjoutModifLigue.add(jlTitre);

		jtAideNom = Creation.setAide("Ne doit contenir que des lettres.", 50, 90);
		panelAjoutModifLigue.add(jtAideNom);
		
		jlNom = Creation.setLabel("Libellé *", 1, 12, 145, 80, 70, 21);
		jlNom.addMouseListener(getAide(jtAideNom));
		panelAjoutModifLigue.add(jlNom);

		jtNom = Creation.setTexte(ligue.getLibelle(), 285, 80);
		panelAjoutModifLigue.add(jtNom);
		
		jtAidePrenom = Creation.setAide("Détermine le sport de la ligue.", 50, 130);
		panelAjoutModifLigue.add(jtAidePrenom);
		
		jlPrenom = Creation.setLabel("Sport *", 1, 12, 145, 120, 58, 21);
		jlPrenom.addMouseListener(getAide(jtAidePrenom));
		panelAjoutModifLigue.add(jlPrenom);
		
		jcbSport = new JComboBox<Sport>();
		jcbSport.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		jcbSport.setBounds(285, 120, 150, 26);
		jcbSport.setBackground(new Color(225, 225, 225));
				
		for (Sport sport : BDDSport.getAllSports())
			jcbSport.addItem(sport);

		if (mode == Mode.MODIFIER_LIGUE)
			jcbSport.setSelectedItem(ligue.getSport());

		panelAjoutModifLigue.add(jcbSport);

		jlMail = Creation.setLabel("Description", 1, 12, 145, 160, 100, 21);
		panelAjoutModifLigue.add(jlMail);

		jtDescription = Creation.setTexteLong(ligue.getDescription(), 285, 160, 150, 100);
		panelAjoutModifLigue.add(jtDescription);

		jlErreur = Creation.setLabel("", 1, 12, 190, 270, 224, 14);
		panelAjoutModifLigue.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 115, 300, 89);
		
		if (mode == Mode.AJOUTER_LIGUE)
			jbAnnuler.addActionListener(getChangeMode(Mode.MENU_LIGUES, 0));
		else
			jbAnnuler.addActionListener(getChangeMode(Mode.LISTE_LIGUES, 0));
		
		panelAjoutModifLigue.add(jbAnnuler);

		jbOk = Creation.setBouton("Valider", 300, 300, 89);
		
		if (mode == Mode.AJOUTER_LIGUE)
			jbOk.addActionListener(getValiderAjoutLigue());
		else
			jbOk.addActionListener(getValiderModifLigue());
		
		panelAjoutModifLigue.add(jbOk);

		return panelAjoutModifLigue;
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
		jbAnnuler.addActionListener(getChangeMode(Mode.MENU_LIGUES, 0));
		panelListeLiguesVide.add(jbAnnuler);

		jbOk = Creation.setBouton("Ajouter", 289, 275, 89);
		jbOk.addActionListener(getChangeMode(Mode.AJOUTER_LIGUE, 0));
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
		
		jlTitre = Creation.setLabel("Liste des ligues", 1, 14, 205, 15, 100, 14);
		panelListeLigues.add(jlTitre);

		ligneTitre = new String[] {"ID", "Nom", "Administrateur", "Sport", "Effectif"};
		jtTableau = Creation.setTableau(ligneTitre, BDDLigue.getAllLigues(), Categorie.LIGUE);

		JScrollPane scroll = Creation.setScroll(jtTableau, 25, 40, 450, 220);
		panelListeLigues.add(scroll);
		
		jlErreur = Creation.setLabel("", 1, 13, 150, 295, 200, 14);
		panelListeLigues.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 200, 333, 89);
		jbAnnuler.addActionListener(getChangeMode(Mode.MENU_LIGUES, 0));
		panelListeLigues.add(jbAnnuler);

		jbModif = Creation.setBouton("Modifier", 35, 325, 100);
		jbModif.addActionListener(getModifierLigue());
		panelListeLigues.add(jbModif);
		
		JButton jbGerer = Creation.setBouton("Gérer", 35, 290, 100);
		jbGerer.addActionListener(getGererLigue());
		panelListeLigues.add(jbGerer);

		jbSuppr = Creation.setBouton("Supprimer", 360, 325, 100);
		jbSuppr.addActionListener(getSupprimerLigue());
		panelListeLigues.add(jbSuppr);
		
		JButton jbAfficher = Creation.setBouton("Afficher", 360, 290, 100);
		jbAfficher.addActionListener(getAfficherLigue());
		panelListeLigues.add(jbAfficher);
		
		JButton export = Creation.setExport();
		export.addActionListener(getExport(Categorie.LIGUE));
		panelListeLigues.add(export);

		return panelListeLigues;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'une ligue.
	 * @param ligue La ligue a créée ou modifiée.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelGererLigue(Ligue ligue) {
		JPanel panelGererLigue = new JPanel();
		panelGererLigue.setLayout(null);

		jlTitre = Creation.setLabel("Gérer : " + ligue.getLibelle(), 1, 13, 175, 15, 300, 21);
		panelGererLigue.add(jlTitre);
		
		jlId = Creation.setHidden("" + ligue.getId());
		panelGererLigue.add(jlId);

		jlNom = Creation.setLabel("Administrateur", 1, 12, 60, 50, 100, 21);
		panelGererLigue.add(jlNom);

		jcbAdmin = new JComboBox<Employe>();
		jcbAdmin.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		jcbAdmin.setBounds(175, 50, 265, 27);
		jcbAdmin.setBackground(new Color(225, 225, 225));

		/* Ajout de la liste des employés */

		if (ligue.getEmployes().size() > 0) {
			for (Employe employe : ligue.getEmployes())
				jcbAdmin.addItem(employe);
			
			jcbAdmin.addActionListener(getChangerAdminLigue());
		} else
			jcbAdmin.addItem(BDDEmploye.getRoot());

		/* Sélection de l'administrateur */

		jcbAdmin.setSelectedItem(ligue.getAdministrateur());

		panelGererLigue.add(jcbAdmin);

		ligneTitre = new String[] {"ID", "Nom","Prénom", "Mail"};
		jtTableau = Creation.setTableau(ligneTitre, ligue.tableauEmployes(), Categorie.EMPLOYE);

		JScrollPane scroll = Creation.setScroll(jtTableau, 50, 90, 400, 200);
		panelGererLigue.add(scroll);

		jlErreur = Creation.setLabel("", 1, 12, 145, 325, 224, 14);
		panelGererLigue.add(jlErreur);

		jbAjout = Creation.setBouton("Ajouter", 50, 300, 89);
		jbAjout.addActionListener(getChangeMode(Mode.AJOUTER_EMPLOYE, getIdUse()));
		panelGererLigue.add(jbAjout);

		jbAnnuler = Creation.setBouton("Retour", 50, 333, 89);
		jbAnnuler.addActionListener(getChangeMode(Mode.LISTE_LIGUES, 0));
		panelGererLigue.add(jbAnnuler);

		jbModif = Creation.setBouton("Modifier", 350, 300, 100);
		jbModif.addActionListener(getModifierEmploye());
		panelGererLigue.add(jbModif);

		jbSuppr = Creation.setBouton("Supprimer", 350, 333, 100);
		jbSuppr.addActionListener(getSupprimerEmploye());
		panelGererLigue.add(jbSuppr);
		
		if (ligue.getEmployes().size() > 0) {
			JButton export = Creation.setExport();
			export.addActionListener(getExport(Categorie.EMPLOYE));
			panelGererLigue.add(export);
		}

		return panelGererLigue;
	}
	
	/**
	 * Retourne la disposition des élément de l'écran de modification d'un compte.
	 * @param employe L'employé a créé ou modifié.
	 * @return Le panel de l'écran de modification des informations.
	 */

	private JPanel getPanelAjouterEmploye(Ligue ligue) {
		JPanel panelAjoutEmploye = new JPanel();
		panelAjoutEmploye.setLayout(null);

		jlTitre = Creation.setLabel("Ajout d'un employé à " + ligue.getLibelle(), 1, 13, 150, 40, 300, 21);
		panelAjoutEmploye.add(jlTitre);
		
		jlId = Creation.setHidden("" + ligue.getId());
		panelAjoutEmploye.add(jlId);

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
		
		jcbCheck = Creation.setCheck("Devenir administrateur de la ligue", 1, 13, 115, 210, 250, 27);
		
		/* Le premier employé ajouté devient administrateur */
		
		if (ligue.getEmployes().isEmpty()) {
			jcbCheck.setSelected(true);
			jcbCheck.setEnabled(false);
		}

		panelAjoutEmploye.add(jcbCheck);

		jlErreur = Creation.setLabel("", 1, 12, 190, 265, 224, 14);
		panelAjoutEmploye.add(jlErreur);

		jbAnnuler = Creation.setBouton("Annuler", 115, 300, 89);
		jbAnnuler.addActionListener(getChangeMode(Mode.GERER_LIGUE, getIdUse()));
		panelAjoutEmploye.add(jbAnnuler);

		jbOk = Creation.setBouton("Valider", 300, 300, 89);
		jbOk.addActionListener(getValiderAjoutEmploye());
		panelAjoutEmploye.add(jbOk);

		return panelAjoutEmploye;
	}
	
	private JPanel getPanelAfficherLigue(Ligue ligue) {
		JPanel panelAfficherLigue = new JPanel();
		panelAfficherLigue.setLayout(null);
		
		String titre = "• La ligue <b>" + ligue.getLibelle() + "</b> de <b>" + ligue.getSport() + "</b> est administrée par :";
		JEditorPane jepTitre = Creation.setInfos(titre, 40, 10);
		panelAfficherLigue.add(jepTitre);
		
		final String mail = ligue.getAdministrateur().getMail(),
					prenom = ligue.getAdministrateur().getPrenom(),
					nom = ligue.getAdministrateur().getNom();
		String admin = "<b>" + prenom + " " + nom + "</b> " + "(<a href=\"\">" + mail + "</a>)";
		JEditorPane jepAdmin = Creation.setInfos(admin, 95, 30);
		jepAdmin.addHyperlinkListener(new HyperlinkListener() {
			@Override
		    public void hyperlinkUpdate(HyperlinkEvent e) {
		        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		        	Desktop desktop = Desktop.getDesktop();
		    		String message = "mailto:" + mail + "?subject=Contact%20M2L";
		    		URI uri = URI.create(message);
		    		try {
		    			desktop.mail(uri);
		    			System.out.println("ninja");
		    		} catch (IOException ex) { ex.printStackTrace(); }
		        }
		    }
		});
		panelAfficherLigue.add(jepAdmin);
		
		if (!ligue.getDescription().equals("")) {
			String description = "• Description : " + ligue.getDescription();
			JEditorPane jepDescription = Creation.setInfos(description, 40, 55);
			panelAfficherLigue.add(jepDescription);
		}
		
		if (ligue.getEmployes().size() > 0) {
			String effectif = "• Elle est composée de " + ligue.getEmployes().size() + " employé";
			effectif += (ligue.getEmployes().size() > 1) ? "s :" : " :";
			JEditorPane jepEffectif = Creation.setInfos(effectif, 40, 80);
			panelAfficherLigue.add(jepEffectif);
			
			JList<Employe> liste = new JList<Employe>(ligue.listeEmployes()){
			    @Override
			    public void setSelectionInterval(int index0, int index1) {
			        super.setSelectionInterval(-1, -1);
			    }
			};
			liste.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			liste.setBackground(new Color(238, 238, 238));
			JScrollPane scroll = Creation.setScroll(liste, 75, 110, 350, 190);
			scroll.setBorder(BorderFactory.createEmptyBorder());
			panelAfficherLigue.add(scroll);
		}
		
		jbAnnuler = Creation.setBouton("Retour", 200, 320, 100);
		jbAnnuler.addActionListener(getChangeMode(Mode.LISTE_LIGUES, 0));
		panelAfficherLigue.add(jbAnnuler);

		return panelAfficherLigue;
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
	 * @param mode Le nouveau 
	 */

	private void changeMode(Mode mode, int id) {
		this.mode = mode;
		
		switch (mode) {
		case CONNEXION:
			setContentPane(getPanelConnexion());
			break;

		case MENU_PRINCIPAL:
			setContentPane(getPanelMenuPrincipal());
			break;

		case MODIFIER_ROOT:
			setContentPane(getPanelModifierCompte(gestionPersonnel.getRoot()));
			break;
			
		case MENU_LIGUES:
			setContentPane(getPanelMenuLigues());
			break;
			
		case LISTE_LIGUES:
			if (BDDLigue.getNombreLigues() > 0)
				setContentPane(getPanelListeLigues());
			else
				setContentPane(getPanelListeLiguesVide());
			break;
			
		case AJOUTER_LIGUE:
			setContentPane(getPanelAjoutModifLigue(new Ligue(0, "", null,  "")));
			break;
			
		case MODIFIER_LIGUE:
			setContentPane(getPanelAjoutModifLigue(BDDLigue.getLigueVide(id)));
			break;
		
		case GERER_LIGUE:
			setContentPane(getPanelGererLigue(BDDLigue.getLigue(id)));
		break;
		
		case AJOUTER_EMPLOYE:
			setContentPane(getPanelAjouterEmploye(BDDLigue.getLigue(id)));
		break;
		
		case MODIFIER_EMPLOYE:
			setContentPane(getPanelModifierCompte(BDDEmploye.getEmploye(id)));
		break;
		
		case AFFICHER_LIGUE:
			setContentPane(getPanelAfficherLigue(BDDLigue.getLigue(id)));
			break;
		
		default:
			dispose();
			break;
		}
		
		actualiseFenetre();
	}
	
	/**
	 * Retourne le mot de passe salté.
	 * @param mdp Le mot de passe à salter.
	 * @return Le mot de passe salté.
	 */

	

	/**
	 * Retourne si le mot de passe super-administrateur est bon.
	 * @return Si le mot de passe est bon.
	 */

	private boolean verifConnexion() {
		return gestionPersonnel.getRoot().checkPassword(BDD.getMD5(new String(jpMDP.getPassword())));
	}
	
	private int getIdUse() {
		return Integer.parseInt(jlId.getText());
	}
	
	/**
	 * Affichage du message de succès lors d'une requète réussie.
	 */

	private void erreurOK(String message) {
		jlErreur.setForeground(new Color(0, 128, 0));
		jlErreur.setText(message);
	}

	/**
	 * Affichage du message d'erreur lors d'une requète ratée.
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
		
		if ((text.getText().equals("")) || !text.getText().matches(
				"[- a-zA-ZÀÁÂÆÇÈÉÊËÌÍÎÏÑÒÓÔŒÙÚÛÜÝŸàáâæçèéêëìíîïñòóôœùúûüýÿ]*")) {
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
		
		if ((text.getText().equals("")) || !text.getText().matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			text.setBackground(new Color(205, 92, 92));
			test++;
		} else
			text.setBackground(new Color(255, 255, 255));
		
		return test;
	}
	
	/**
	 * Vérifie l'unicité du mail de l'employé.
	 * @return Si le mail de l'employé est unique ou non.
	 */

	private int verifMailExiste() {
		return (BDDEmploye.verifMailExiste(getIdUse(), jtMail.getText())) ? 1000 : 0;
	}
	
	/**
	 * Vérifie l'unicité du nom de la ligue.
	 * @return Si le nom de la ligue est unique ou non.
	 */

	private int verifLigueExiste() {
		return (BDDLigue.verifLigueExiste(getIdUse(), jtNom.getText())) ? 1000 : 0;
	}

	/**
	 * Vérifie que les deux champs de mot de passe sont vides ou renseignés.
	 * @return Si les champs sont vides ou renseignés.
	 */

	private int verifNewMDP() {
		int test = 0;
		boolean vide = false;
		
		String MDP = new String(jpMDP.getPassword()),
				newMDP = new String(jpNewMDP.getPassword());

		if ((MDP.equals("")) && !(newMDP.equals("")))
			vide = true;
		else if ((newMDP.equals("")) && !(MDP.equals("")))
			vide = true;
		
		if (vide || !(MDP.equals(newMDP))) {
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
	 * Sauvegarde des nouvelles données de l'employé.
	 */
	
	private void save(int id) {
		BDDEmploye.updateEmploye(id, jtNom.getText(), jtPrenom.getText(), jtMail.getText(),new String(jpMDP.getPassword()));
	}
	
	/**
	 * Retourne un mot de passe aléatoire.
	 * @return Mot de passe.
	 */
	
	private String MDPAleatoire() {
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

	private ActionListener getChangeMode(final Mode mode, final int id) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeMode(mode, id);
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
					jpMDP.setEchoChar((char) 0);
				else
					jpMDP.setEchoChar('•');
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
				if (verifConnexion())
					changeMode(Mode.MENU_PRINCIPAL, 0);
				else {
					jlErreur.setVisible(true);
					jpMDP.setText("");
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
				
				/* Vérification de l'unicité du mail */
				
				test += verifMailExiste();

				/* Test d'intégrité des mots de passe */
				
				test += verifNewMDP();

				/* Sauvegarde du résultat, si les tests sont conformes */

				if (test == 0) {
					erreurOK("Modification réussie !");
					save(getIdUse());
				} else if (test >= 1000)
					erreurNOK("Mail déjà existant !");
				else
					erreurNOK("Modification ratée !");

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

				/* Vérification de l'unicité du nom */

				test += verifLigueExiste();

				/* Sauvegarde du résultat, si les tests sont conformes */

				if (test == 0) {
					Ligue ligue = new Ligue(0, jtNom.getText(), (Sport) jcbSport.getSelectedItem(), jtDescription.getText());
					ligue.insert();
					erreurOK("Ajout réussi !");
					jlErreur.setBounds(200, 130, 200, 14);
					jbAnnuler.setText("Retour");
					jbAnnuler.setBounds(200, 230, 84, 27);
					jlNom.setVisible(false);
					jtNom.setVisible(false);
					jlPrenom.setVisible(false);
					jcbSport.setVisible(false);
					jlMail.setVisible(false);
					jtDescription.setVisible(false);
					jbOk.setVisible(false);
				} else if (test >= 1000)
					erreurNOK("Nom déjà existant !");
				else
					erreurNOK("Nom non conforme !");
			}
		};
	}
	
	/**
	 * Retourne la fonction de vérification de modification d'une ligue.
	 * @return Modification d'une ligue ou non.
	 */

	private ActionListener getModifierLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTableau.getSelectedRow() > -1) {
					int id = Integer.parseInt((String) jtTableau.getValueAt(jtTableau.getSelectedRow(), 0));
					changeMode(Mode.MODIFIER_LIGUE, id);
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
					int id = Integer.parseInt((String) jtTableau.getValueAt(jtTableau.getSelectedRow(), 0));
					changeMode(Mode.GERER_LIGUE, id);
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
					int id = Integer.parseInt((String) jtTableau.getValueAt(jtTableau.getSelectedRow(), 0));
					jlErreur.setText("");
					int choix = JOptionPane.showConfirmDialog(
							null, "Êtes-vous sûr(e) de vouloir supprimer la ligue ?", "Vérification", JOptionPane.YES_NO_OPTION);
					
					if (choix == JOptionPane.YES_OPTION) {
						BDDLigue.deleteLigue(id);
						changeMode(Mode.LISTE_LIGUES, 0);
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
					int id = Integer.parseInt((String) jtTableau.getValueAt(jtTableau.getSelectedRow(), 0));
					changeMode(Mode.AFFICHER_LIGUE, id);
				} else
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
								
				/* Vérification de l'unicité du nom */
				
				test += verifLigueExiste();
								
				/* Sauvegarde du résultat, si les tests sont conformes */
				
				if (test == 0) {
					BDDLigue.updateLigue(getIdUse(), jtNom.getText(), jtDescription.getText(), (Sport) jcbSport.getSelectedItem());
					erreurOK("Modification réussie !");
				} else if (test >= 1000)
					erreurNOK("Nom déjà existant !");
				else
				erreurNOK("Nom non conforme !");
			}
		};
	}

	/**
	 * Retourne la fonction de changement d'administrateur d'une ligue.
	 * @return Changement d'administrateur d'une ligue.
	 */

	private ActionListener getChangerAdminLigue() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BDDLigue.getLigue(getIdUse()).setAdministrateur((Employe) jcbAdmin.getSelectedItem());
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
					int id = Integer.parseInt((String) jtTableau.getValueAt(jtTableau.getSelectedRow(), 0));
					jlErreur.setText("");
					
					int choix = JOptionPane.showConfirmDialog(
							null, "Êtes-vous sûr(e) de vouloir supprimer l'employé ?", "Vérification", JOptionPane.YES_NO_OPTION);
					
					if (choix == JOptionPane.YES_OPTION) {
						if (BDDLigue.getAdmin(getIdUse()).getId() != id) {
							BDDEmploye.deleteEmploye(id);
							changeMode(Mode.GERER_LIGUE, getIdUse());
						}
						else
							erreurNOK("Veuillez changer l'administrateur !");
					}
				} else
					erreurNOK("Veuillez sélectionnez un employé !");
			}
		};
	}
	
	/**
	 * Retourne la fonction de vérification de suppresion d'un employé.
	 * @return Suppression un employé ou non.
	 */

	private ActionListener getModifierEmploye() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jtTableau.getSelectedRow() > -1) {
					int id = Integer.parseInt((String) jtTableau.getValueAt(jtTableau.getSelectedRow(), 0));
						changeMode(Mode.MODIFIER_EMPLOYE, id);
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
				
				/* Vérification de l'unicité du mail */
				
				test += verifMailExiste();

				/* Sauvegarde du résultat, si les tests sont conformes */

				if (test == 0) {

					/* Ajout de l'employé et changement d'administrateur si nécessaire */
					
					Ligue ligue = BDDLigue.getLigue(getIdUse());
					
					if (jcbCheck.isSelected())
						ligue.setAdministrateur(ligue.addEmploye(jtNom.getText(), jtPrenom.getText(), jtMail.getText(), MDPAleatoire(), 2));
					else
						ligue.addEmploye(jtNom.getText(), jtPrenom.getText(), jtMail.getText(), MDPAleatoire(), 3);
					
					// ENVOIE MAIL DES INFOS
					
					changeMode(Mode.GERER_LIGUE, getIdUse());
				} else if (test >= 1000)
					erreurNOK("Mail déjà existant !");
				else
				erreurNOK("Ajout raté !");
			}
		};
	}
	
	private ActionListener getExport(final Categorie categorie) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					String chemin = "";
					if (categorie == Categorie.LIGUE)
						chemin = "listeLigues.csv";
					else if (categorie == Categorie.EMPLOYE)
						chemin = "listeEmployes_ligue" + getIdUse() + ".csv";
					
					File fichier = new File(chemin);
					fichier.createNewFile();
					
					FileWriter ecrire =new FileWriter(fichier);
					String ligne = "", admin = "";
					
					for (String titre : ligneTitre)
						ligne += titre + ",";
					
					ligne = ligne.substring(0, ligne.length() - 1);
					
					ecrire.write(ligne);
					ecrire.write("\n");
					
					int idAdmin = BDDLigue.getLigue(getIdUse()).getAdministrateur().getId();
					
					for (int i = 0; i < jtTableau.getRowCount(); i++) {
						ligne = "";
						for (int j = 0; j < jtTableau.getColumnCount(); j++) {
							if (Integer.parseInt((String) jtTableau.getValueAt(i, 0)) != idAdmin) {
								if (j != 0)
									ligne += ",";
								ligne += jtTableau.getValueAt(i, j);
							} else {
								if (j != 0)
									admin += ",";
								admin += jtTableau.getValueAt(i, j);
							}
						}
						if (!ligne.equals(""))
							ecrire.write(ligne + "\n");
					}
					
					if (categorie == Categorie.EMPLOYE) {
						ecrire.write("\nAdministrateur\n");
						ecrire.write(admin);
					}

					ecrire.close();
				} 
				catch (IOException e) { e.printStackTrace(); }
			}
		};
	}

	public static void main(String[] args) {
		new ApplicationBureau(GestionPersonnel.getGestionPersonnel());
	}
}