package IHM;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Creation {
	
	// PLAIN = 0
	// BOLD = 1
	// ITALIC = 2
	// cumuler = |
	
	public static JLabel setLabel(String message, int police, int taille, int x, int y, int l, int h) {
		JLabel label = new JLabel(message);
		label.setFont(new Font("Trebuchet MS", police, taille));
		label.setBounds(x, y, l, h);
		
		return label;
	}

	public static JButton setBouton(String message, int x, int y, int l) {
		JButton bouton = new JButton(message);
		bouton.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		bouton.setBounds(x, y, l, 27);
		
		return bouton;
	}

	public static JPasswordField setPassword(int taille, int x, int y, int h) {
		JPasswordField password = new JPasswordField();
		password.setFont(new Font("Trebuchet MS", Font.PLAIN, taille));
		password.setBackground(new Color(255, 255, 255));
		password.setMargin(new Insets(2, 3, 2, 2));
		password.setBounds(x, y, 140, h);
		
		return password;
	}

	public static JTextField setTexte(String message, int x, int y) {
		JTextField texte = new JTextField(message);
		texte.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		texte.setBackground(new Color(255, 255, 255));
		texte.setMargin(new Insets(2, 3, 2, 2));
		texte.setBounds(x, y, 140, 23);
		
		return texte;
	}

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
	
	public static JTextArea setTexteLong(String message, int x, int y, int l, int h) {
		JTextArea texteLong = new JTextArea(message);
		texteLong.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		texteLong.setMargin(new Insets(2, 10, 2, 2));
		texteLong.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		texteLong.setLineWrap(true);
		texteLong.setBounds(x, y, l, h);
		
		return texteLong;
	}
	
	public static JCheckBox setCheck(String message, int police, int taille, int x, int y, int l, int h) {
		JCheckBox check = new JCheckBox(message);
		check.setFont(new Font("Trebuchet MS", police, taille));
		check.setBounds(x, y, l, h);
		
		return check;
	}
	
	public static JTable setTableau(String[] titres, String[][] donnees, int col1, int col2, int col3) {
		@SuppressWarnings("serial")
		JTable tableau = new JTable(donnees,titres) {
			public boolean isCellEditable(int iRowIndex, int iColumnIndex) {
				 return false;
			}
		};		
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.getTableHeader().setReorderingAllowed(false);
		tableau.getTableHeader().setResizingAllowed(false);
		tableau.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		tableau.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
		dtcr.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tableau.getColumnCount(); i++)
			tableau.getColumnModel().getColumn(i).setCellRenderer(dtcr);

		tableau.getColumnModel().getColumn(0).setPreferredWidth(col1);
		tableau.getColumnModel().getColumn(1).setPreferredWidth(col2);
		tableau.getColumnModel().getColumn(2).setPreferredWidth(col3);
		
		return tableau;
	}
	
	public static JScrollPane setScroll(JTable tableau, int x, int y, int l, int h) {
		JScrollPane scroll = new JScrollPane(tableau,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(x, y, l, h);
		
		return scroll;
	}
}