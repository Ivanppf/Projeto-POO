package ouvintes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class OuvinteMenu implements MouseListener {

private JPanel menu;

public OuvinteMenu(JPanel menu) {
	this.menu = menu;
	}
	
	public void mouseClicked(MouseEvent e) {
	
	}
	
	public void mousePressed(MouseEvent e) {
	
	}
	
	public void mouseReleased(MouseEvent e) {
	
	}
	
	public void mouseEntered(MouseEvent e) {
	menu.setVisible(true);
	
	}
	
	public void mouseExited(MouseEvent e) {
	menu.setVisible(false);
	}


}
