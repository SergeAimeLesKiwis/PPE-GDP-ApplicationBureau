package metiers;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import donnees.*;

/**
 * Représente une ligue. Chaque ligue est reliée à une liste
 * d'employés dont un administrateur. Comme il n'est pas possible
 * de créer un employé sans l'affecter à une ligue, le root est 
 * l'administrateur de la ligue jusqu'à ce qu'un administrateur 
 * lui ait été affecté avec la fonction {@link #setAdministrateur}.
 */

public class Ligue implements Serializable, Comparable<Ligue> {
	private static final long serialVersionUID = 1L;
	private int id;
	private String libelle, description;
	private SortedSet<Employe> employes;
	private Employe administrateur;
	private Sport sport;
	
	/**
	 * Crée une ligue.
	 * @param id L'id de la ligue.
	 * @param libelle Le libellé de la ligue.
	 * @param sport Le sport de la ligue.
	 * @param description La description de la ligue.
	 */
	
	public Ligue(int id, String libelle, Sport sport, String description) {
		this.id = id;
		this.libelle = libelle;
		this.sport = sport;
		this.description = description;
		this.administrateur = BDDEmploye.getRoot();
		employes = new TreeSet<Employe>();
	}
	
	/**
	 * Accesseur de l'id.
	 * @return L'id de la ligue.
	 */

	public int getId() {
		return id;
	}

	/**
	 * Accesseur du libellé.
	 * @return Le libellé de la ligue.
	 */

	public String getLibelle() {
		return libelle;
	}
	
	/**
	 * Accesseur du sport.
	 * @return Le sport de la ligue.
	 */

	public Sport getSport() {
		return sport;
	}
	
	/**
	 * Accesseur de la description.
	 * @return La description de la ligue.
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * Accesseur de l'administrateur.
	 * @return L'administrateur de la ligue.
	 */
	
	public Employe getAdministrateur() {
		return administrateur;
	}

	/**
	 * Fait de administrateur l'administrateur de la ligue.
	 * Révoque les droits de l'ancien administrateur.
	 * @param administrateur Le nouvel administrateur de la ligue.
	 */
	
	public void setAdministrateur(Employe administrateur) {
		this.administrateur = administrateur;
		if (!administrateur.equals(BDDEmploye.getRoot()))
			BDDLigue.changeAdmin(this, administrateur.getId());
	}

	/**
	 * Retourne les employés de la ligue.
	 * @return les employés de la ligue dans l'ordre alphabétique.
	 */
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}
	
	/**
	 * Retourne les employés de la ligue en tableau.
	 * @return les employés de la ligue dans l'ordre alphabétique en tableau.
	 */
	
	public String[][] tableauEmployes(){
		String[][] tab = new String[employes.size()][4];
		int i = 0;

		for (Employe item : employes) {
			tab[i][0] = "" + item.getId();
			tab[i][1] = item.getNom();
			tab[i][2] = item.getPrenom();
			tab[i][3] = item.getMail();
			i++;
		}
		
		return tab;
	}
	
	public Employe[] listeEmployes(){
		Employe[] tab = new Employe[employes.size()];
		int i = 0;

		for (Employe item : employes) {
			tab[i] = item;
			i++;
		}

		return tab;
	}

	/**
	 * Ajoute un employé dans la ligue. Cette méthode 
	 * est le seul moyen de créer un employé.
	 * @param nom le nom de l'employé.
	 * @param prenom le prénom de l'employé.
	 * @param mail l'adresse mail de l'employé.
	 * @param password le password de l'employé.
	 * @return l'employé créé. 
	 */

	public Employe addEmploye(String nom, String prenom, String mail, String password, int type)
	{
		Employe employe = BDDEmploye.insertEmploye(nom, prenom, mail, password, id, type);
		employes.add(employe);
		return employe;
	}
	
	public void ajout(Employe employe)
	{
		employes.add(employe);
	}
	
	public void remove(Employe employe)
	{
		employes.remove(employe);
	}
	
	public boolean insert() {
		return BDDLigue.insertLigue(libelle, description, sport);
	}
	
	/**
	 * Supprime la ligue, entraîne la suppression de tous les employés
	 * de la ligue.
	 */
	
	public void remove()
	{
		GestionPersonnel.getGestionPersonnel().remove(this);
	}

	public String[] tabString()
	{
		return new String[] {
				"" + id, 
				libelle, 
				administrateur.getPrenom() + " " + administrateur.getNom(), 
				sport.getLibelle(), 
				"" + employes.size()};
	}
	
	@Override
	public boolean equals(Object o) {
		Ligue ligue = (Ligue) o;
		return id == ligue.getId();
	}
	
	@Override
	public int compareTo(Ligue autre)
	{
		return getLibelle().compareTo(autre.getLibelle());
	}
}
