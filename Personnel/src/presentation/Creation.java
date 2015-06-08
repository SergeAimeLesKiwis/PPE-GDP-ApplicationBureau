package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Creation {
	
	/**
	 * Création d'un label.
	 * @param message Le texte à afficher.
	 * @param police La police du texte.
	 * @param taille La taille du texte.
	 * @param x L'abscisse du label.
	 * @param y L'ordonnée du label.
	 * @param l La largeur du label.
	 * @param h La hauteur du label.
	 * @return Le label créé.
	 */
	
	public static JLabel setLabel(String message, int police, int taille, int x, int y, int l, int h) {
		JLabel label = new JLabel(message);
		label.setFont(new Font("Trebuchet MS", police, taille));
		label.setBounds(x, y, l, h);
		
		return label;
	}
	
	/**
	 * Création d'un champ caché.
	 * @param message Le texte du champ caché.
	 * @return Le champ caché créé.
	 */
	
	public static JLabel setHidden(String message) {
		JLabel label = new JLabel(message);
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 1));
		label.setBounds(0, 0, 0, 0);
		label.setVisible(false);
		
		return label;
	}

	/**
	 * Création d'un bouton.
	 * @param message Le texte à afficher.
	 * @param x L'abscisse du bouton.
	 * @param y L'ordonnée du bouton.
	 * @param l La largeur du bouton.
	 * @return Le bouton créé.
	 */
	
	public static JButton setBouton(String message, int x, int y, int l) {
		JButton bouton = new JButton(message);
		bouton.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		bouton.setBounds(x, y, l, 27);
		
		return bouton;
	}
	
	/**
	 * Création d'un bouton d'export.
	 * @return Le bouton d'export créé.
	 */
	
	public static JButton setExport() {
		JButton bouton = new JButton("Exporter");
		bouton.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
		bouton.setBounds(390, 10, 80, 20);
		
		return bouton;
	}

	/**
	 * Création d'un champ de mot de passe.
	 * @param taille La taille du texte.
	 * @param x L'abscisse du champ de mot de passe.
	 * @param y L'ordonnée du champ de mot de passe.
	 * @param h La hauteur du champ de mot de passe.
	 * @return Le champ de mot de passe créé.
	 */
	
	public static JPasswordField setPassword(int taille, int x, int y, int h) {
		JPasswordField password = new JPasswordField("");
		password.setFont(new Font("Trebuchet MS", Font.PLAIN, taille));
		password.setBackground(new Color(255, 255, 255));
		password.setMargin(new Insets(2, 3, 2, 2));
		password.setBounds(x, y, 140, h);
		
		return password;
	}

	/**
	 * Création d'un champ de texte.
	 * @param message Le texte à afficher.
	 * @param x L'abscisse du champ de texte.
	 * @param y L'ordonnée du champ de texte.
	 * @return Le champ de texte créé.
	 */
	
	public static JTextField setTexte(String message, int x, int y) {
		JTextField texte = new JTextField(message);
		texte.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		texte.setBackground(new Color(255, 255, 255));
		texte.setMargin(new Insets(2, 3, 2, 2));
		texte.setBounds(x, y, 140, 23);
		
		return texte;
	}
	
	/**
	 * Création d'un champ de texte long.
	 * @param message Le texte à afficher.
	 * @param x L'abscisse du champ de texte long.
	 * @param y L'ordonnée du champ de texte long.
	 * @param l La largeur du champ de texte long.
	 * @param h La hauteur du champ de texte long.
	 * @return Le champ de texte long créé.
	 */
	
	public static JTextArea setTexteLong(String message, int x, int y, int l, int h) {
		JTextArea texteLong = new JTextArea(message);
		texteLong.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		texteLong.setMargin(new Insets(2, 10, 2, 2));
		texteLong.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		texteLong.setLineWrap(true);
		texteLong.setWrapStyleWord(true);
		texteLong.setBounds(x, y, l, h);
		
		return texteLong;
	}

	/**
	 * Création d'un champ d'aide.
	 * @param message Le texte à afficher.
	 * @param x L'abscisse du champ d'aide.
	 * @param y L'ordonnée du champ d'aide.
	 * @return Le champ d'aide créé.
	 */
	
	public static JTextArea setAide(String message, int x, int y) {
		JTextArea aide = new JTextArea("Champ obligatoire, ne peut être laissé vide. " + message);
		aide.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 13));
		aide.setLineWrap(true);
		aide.setBackground(new Color(255, 235, 205));
		aide.setForeground(new Color(105, 105, 105));
		aide.setVisible(false);
		aide.setBounds(x, y, 284, 38);
		
		return aide;
	}
	
	/**
	 * Création d'une case à cocher.
	 * @param message Le texte à afficher.
	 * @param police La police de la case à cocher.
	 * @param taille La taille de la case à cocher.
	 * @param x L'abscisse de la case à cocher.
	 * @param y L'ordonnée de la case à cocher.
	 * @param l La largeur de la case à cocher.
	 * @param h La hauteur de la case à cocher.
	 * @return La case à cocher créée.
	 */
	
	public static JCheckBox setCheck(String message, int police, int taille, int x, int y, int l, int h) {
		JCheckBox check = new JCheckBox(message);
		check.setFont(new Font("Trebuchet MS", police, taille));
		check.setBounds(x, y, l, h);
		
		return check;
	}
	
	/**
	 * Création d'un champ d'information.
	 * @param message Le texte à afficher.
	 * @param x L'abscisse du champ d'information.
	 * @param y L'ordonnée du champ d'information.
	 * @return Le champ d'information créé.
	 */
	
	public static JEditorPane setInfos(String message, int x, int y) {
		JEditorPane infos = new JEditorPane("text/html", "");
		infos.setText(message);
		infos.setBackground(new Color(238, 238, 238));
		infos.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		infos.setEditable(false);
		infos.setFocusable(false);
		infos.setBounds(x, y, 450, 20);
		
		return infos;
	}
	
	/**
	 * Création d'un tableau.
	 * @param titres La ligne d'entête du tableau.
	 * @param donnees Le contenu du tableau.
	 * @param categorie La catégorie du tableau.
	 * @return Le tableau créé.
	 */
	
	public static JTable setTableau(String[] titres, String[][] donnees, Categorie categorie) {
		@SuppressWarnings("serial")
		JTable tableau = new JTable(donnees,titres) {
			public boolean isCellEditable(int iRowIndex, int iColumnIndex) {
				 return false;
			}
		};		
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.getTableHeader().setReorderingAllowed(false);
		tableau.getTableHeader().setResizingAllowed(false);
		tableau.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		tableau.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
		dtcr.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tableau.getColumnCount(); i++)
			tableau.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		
		if (categorie == Categorie.LIGUE) {
			tableau.getColumnModel().getColumn(0).setMinWidth(0);
			tableau.getColumnModel().getColumn(0).setMaxWidth(0);
			tableau.getColumnModel().getColumn(1).setPreferredWidth(140);
			tableau.getColumnModel().getColumn(2).setPreferredWidth(135);
			tableau.getColumnModel().getColumn(3).setPreferredWidth(120);
			tableau.getColumnModel().getColumn(4).setPreferredWidth(55);
		}

		else if (categorie == Categorie.EMPLOYE) {
			tableau.getColumnModel().getColumn(0).setMinWidth(0);
			tableau.getColumnModel().getColumn(0).setMaxWidth(0);
			tableau.getColumnModel().getColumn(1).setPreferredWidth(120);
			tableau.getColumnModel().getColumn(2).setPreferredWidth(120);
			tableau.getColumnModel().getColumn(3).setPreferredWidth(160);
		}
		
		return tableau;
	}
	
	/**
	 * Création d'un panneau coulissant.
	 * @param composant Le composant à insérer dans le panneau coulissant.
	 * @param x L'abscisse du panneau coulissant.
	 * @param y L'ordonnée du panneau coulissant.
	 * @param l La largeur du panneau coulissant.
	 * @param h La hauteur du panneau coulissant.
	 * @return Le panneau coulissant créé.
	 */
	
	public static JScrollPane setScroll(Component composant, int x, int y, int l, int h) {
		JScrollPane scroll = new JScrollPane(composant,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(x, y, l, h);

		return scroll;
	}
}