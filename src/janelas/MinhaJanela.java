package janelas;


import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class MinhaJanela extends JFrame {
	
	private static MinhaJanela janela;
	
	private MinhaJanela() {
		setSize(640,360);
		setLayout(null);
		
		Image icone = Toolkit.getDefaultToolkit().getImage("imagem/calango.png"); 
		
		
		
		this.setIconImage(icone);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Simbora");
		setVisible(true);
		
	}
	
	public static void setPanel(JPanel painel) {
		SwingUtilities.updateComponentTreeUI(MinhaJanela.getInstance());
		Icon fundo = new ImageIcon("imagem/Simbora.jpg");
		JLabel imagem = new JLabel(fundo);
		imagem.setBounds(0, 0, 630, 330);
	
		painel.add(imagem);
		
		getInstance().setContentPane(painel);
	}
	
	public static MinhaJanela getInstance() {
		if (janela == null) 
			janela = new MinhaJanela();
		return janela;
		
	}

}
