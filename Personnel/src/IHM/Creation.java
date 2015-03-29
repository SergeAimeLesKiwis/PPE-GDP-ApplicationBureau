package IHM;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Creation {
	
	// PLAIN = 0
	// BOLD = 1
	// ITALIC = 2
	// cumuler = |
	
	public static JLabel setLabel(String message,int police, int taille, int x, int y, int l, int h) {
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
}