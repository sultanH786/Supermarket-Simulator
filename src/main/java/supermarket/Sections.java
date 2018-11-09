package supermarket;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Sections extends JPanel {
	final private static Border border = BorderFactory.createLineBorder(Color.red);
	final private static ImageIcon source = new ImageIcon("cart.png");
	final private JLabel stats = new JLabel("", source, SwingConstants.CENTER);
	int number;

	public Sections() {
		setBackground(Color.white);
		setLayout(new BorderLayout());
		setBorder(border);
	}
	public void addToPanel() {
		removeAll(); // Clear panel
		if (number != 0) {
			add(stats);
			JLabel products = new JLabel(number+ "");
			add(products, BorderLayout.EAST);
		} 
	}
	
	public void setCustomer(int items){
		number=items;
		addToPanel();
	}


	public int getNumberOfItems() {
		return number;
	}

}
