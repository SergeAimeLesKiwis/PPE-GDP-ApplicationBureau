package commandLine;

import java.util.ArrayList;
import java.util.List;

import personnel.*;

public class PersonnelConsole
{
	private GestionPersonnel gestionPersonnel;
	private final static char 
		QUITTER = 'q', 
		GERE_COMPTE_ROOT = 'c',
		GERE_LIGUES = 'l', 
		AFFICHER = 'a', 
		MODIFIER = 'm', 
		AJOUTER = 'c', 
		SUPPRIMER = 's', 
		SELECTIONNER_LIGUE = 'm',
		GERER_EMPLOYES = 'e',
		CHANGER_ADMINISTRATEUR = 'x',
		CHANGER_NOM = 'n',
		CHANGER_PRENOM = 'p',
		CHANGER_MAIL = 'm',
		CHANGER_PASSWORD = 'x';
	private final static String 
		MENU_PRINCIPAL = 
			"c : Gérer le compte administrateur\nl : Gérer les ligues\n"
			+ "q : quitter", 
		MENU_LIGUES = "a : afficher\nc : ajouter\ns : supprimer\n"
				+ "m : sélectionner une ligue\nq : revenir",
		MENU_LIGUE = "a : afficher\ne : gérer les employés\nn : changer le nom\n"
				+ "x : changer d'administrateur\nq : revenir",	
		MENU_EMPLOYES = "a : afficher\nc : ajouter\ns : supprimer\n"
				+ "m : modifier\nq : revenir",
		MENU_COMPTE_ROOT = "a : afficher\nn : changer nom\np : changer prénom\n"
					+ "m : changer mail\nx : changer password\n"
					+ "q : revenir\n",
		MENU_COMPTE = MENU_COMPTE_ROOT + "s : supprimer\n",
		DOT_LINE = "---------------------------";
	
	public PersonnelConsole(GestionPersonnel gestionPersonnel)
	{
		this.gestionPersonnel = gestionPersonnel;
	}
	
	private <T> void afficher(List<T> liste)
	{
		int i = 0;
		System.out.println(DOT_LINE);
		for(T item : liste)
			System.out.println(i++ + " : " + item);
		System.out.println(DOT_LINE);
	}
	
	private <T> int selection(List<T> liste)
	{
		afficher(liste);
		try
		{
			return Util.saisieInt("Indice : ");
		}
		catch(Exception e)
		{
			System.out.println("Erreur de saisie");
			return -1;
		}		
	}
	
	private void afficher(Ligue ligue)
	{
		System.out.println(DOT_LINE);
		System.out.println(ligue);
		System.out.println("administrée par " + ligue.getAdministrateur());
		System.out.println(DOT_LINE);
	}

	private void ajouterEmploye(Ligue ligue)
	{
		ligue.addEmploye(Util.saisieString("nom : "), Util.saisieString("prénom : "), 
				Util.saisieString("mail : "), Util.saisieString("password :"));
	}

	private void modifierEmploye(List<Employe> employes)
	{
		menuCompte(employes.get(selection(employes)));
	}
	
	private void supprimerEmploye(List<Employe> employes)
	{
		employes.get(selection(employes)).remove();
	}
	
	private void menuEmployes(Ligue ligue)
	{
		char saisie;
		do
		{
			List<Employe> employes = new ArrayList<>(ligue.getEmployes());
			saisie = Util.saisieChar(MENU_EMPLOYES);
			switch(saisie)
			{
				case QUITTER : break;
				case AFFICHER : afficher(employes) ; break;
				case AJOUTER : ajouterEmploye(ligue) ; break;
				case MODIFIER : modifierEmploye(employes) ; break;
				case SUPPRIMER : supprimerEmploye(employes) ; break;
				default : System.out.println("Erreur de saisie");
			}
		}
		while(saisie != QUITTER);				
	}
	
	private void changerNom(Ligue ligue)
	{
		ligue.setNom(Util.saisieString("Nouveau nom : "));
	}
	
	private void changerAdministrateur(Ligue ligue)
	{
		List<Employe> employes = new ArrayList<>(ligue.getEmployes());
		int indice = selection(employes);
		Employe administrateur = employes.get(indice);
		ligue.setAdministrateur(administrateur);
	}
	
	private void menuLigue(Ligue ligue)
	{
		char saisie;
		do
		{
			saisie = Util.saisieChar(MENU_LIGUE);
			switch(saisie)
			{
				case QUITTER : break;
				case AFFICHER : afficher(ligue) ; break;
				case GERER_EMPLOYES : menuEmployes(ligue) ; break;
				case CHANGER_ADMINISTRATEUR : changerAdministrateur(ligue) ; break;
				case CHANGER_NOM : changerNom(ligue); break;
				default : System.out.println("Erreur de saisie");
			}
		}
		while(saisie != QUITTER && saisie != SUPPRIMER);		
	}

	private void ajouterLigue(List<Ligue> ligues)
	{
		new Ligue (Util.saisieString("nom : "), "");		
	}
	
	private void supprimerLigue(List<Ligue> ligues)
	{
		ligues.get(selection(ligues)).remove();
	}
	
	private void selectionnerLigue(List<Ligue> ligues)
	{
		menuLigue(ligues.get(selection(ligues)));
	}
	
	private void menuLigues()
	{
		char saisie;
		do
		{
			List<Ligue> ligues = new ArrayList<>(gestionPersonnel.getLigues());
			saisie = Util.saisieChar(MENU_LIGUES);
			switch(saisie)
			{
				case QUITTER : break;
				case AFFICHER : afficher(ligues) ; break;
				case AJOUTER : ajouterLigue(ligues) ; break;
				case SELECTIONNER_LIGUE: selectionnerLigue(ligues) ; break;
				case SUPPRIMER : supprimerLigue(ligues) ; break;
				default : System.out.println("Erreur de saisie");
			}
		}
		while(saisie != QUITTER);		
	}
	
	private void afficher(Employe employe)
	{
		System.out.println(DOT_LINE + '\n' + employe + '\n' + DOT_LINE);
	}
	
	private void changerNom(Employe employe)
	{
		employe.setNom(Util.saisieString("Nouveau nom : "));
	}
	
	private void changerPrenom(Employe employe)
	{
		employe.setPrenom(Util.saisieString("Nouveau prénom : "));
	}
	
	private void changerMail(Employe employe)
	{
		employe.setMail(Util.saisieString("Nouveau mail : "));
	}
	
	private void changerPassword(Employe employe)
	{
		employe.setPassword(Util.saisieString("Nouveau password : "));
	}
	
	private void supprimerCompte(Employe employe)
	{
		employe.remove();
	}
	
	private void menuCompte(Employe employe)
	{
		char saisie;
		boolean root = employe.estRoot();
		do
		{
			saisie = Util.saisieChar((root) ? MENU_COMPTE_ROOT : MENU_COMPTE);
			switch(saisie)
			{
				case QUITTER : break;
				case AFFICHER : afficher(employe) ; break;
				case CHANGER_NOM : changerNom(employe); break;
				case CHANGER_PRENOM : changerPrenom(employe); break;
				case CHANGER_MAIL: changerMail(employe); break;
				case CHANGER_PASSWORD : changerPassword(employe); break;
				case SUPPRIMER : 
					if (!root)
					{
						supprimerCompte(employe) ; break;
					}
				default : System.out.println("Erreur de saisie");
			}
		}
		while(saisie != QUITTER && saisie != SUPPRIMER);		
	}
	
	private void menuPrincipal()
	{
		char saisie;
		do
		{
			saisie = Util.saisieChar(MENU_PRINCIPAL);
			switch(saisie)
			{
				case QUITTER : System.out.println("Au revoir !"); break;
				case GERE_COMPTE_ROOT : menuCompte(gestionPersonnel.getRoot()) ; break;
				case GERE_LIGUES : menuLigues() ; break;
				default : System.out.println("Erreur de saisie");
			}
		}
		while(saisie != QUITTER);
		try
		{
			gestionPersonnel.sauvegarder();
		}
		catch (SauvegardeImpossible e) 
		{
			System.out.println("Une erreur s'est produite, le fichier n'a"
					+ "pas été sauvegardé.");
		}
	}
	
	private boolean verifiePassword()
	{
		boolean ok = gestionPersonnel.getRoot().checkPassword(Util.saisieString("password : "));
		if (!ok)
			System.out.println("Password incorrect.");
		return ok;
	}
	
	public static void main(String[] args)
	{
		PersonnelConsole personnelConsole = 
				new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
		if (personnelConsole.verifiePassword())
			personnelConsole.menuPrincipal();
	}
}
