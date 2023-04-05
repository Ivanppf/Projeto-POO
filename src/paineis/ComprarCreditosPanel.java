package paineis;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ouvintes.OuvinteComprarCreditos;
import ouvintes.OuvinteMouse;
import personalizados.MeuJButton;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Mototaxista;
import projetoPOO.Persistencia;


public class ComprarCreditosPanel extends JPanel {
	private Mototaxista user;
	
	public ComprarCreditosPanel(Mototaxista user) {
		this.user = user;
		
		setSize(640,360);
		setLayout(null);
		addButton();
	}

	private void addButton() {
		Persistencia pe = new Persistencia();
		CentralDeInformacoes cdi = pe.recuperarCentral();
		float valor = cdi.getValorDoCredito();
		
		MeuJButton cancelar = new MeuJButton("Cancelar", 30, 30, 90, 30);
		cancelar.setBackground(Color.BLACK);
		cancelar.setForeground(Color.WHITE);
		cancelar.addMouseListener(new OuvinteMouse());
		cancelar.addActionListener(new OuvinteComprarCreditos(user));
		add(cancelar);
		
		
		
		 int y = 30;
		String[] precos = {Float.toString(valor)+ "$",Float.toString(valor * 5)+ "$",Float.toString(valor * 10)+ "$",Float.toString(valor * 50)+ "$"};
		String[] quantidade = {"1 crédito", "5 créditos", "10 créditos", "50 créditos"};
		
		for (int i = 0; i < precos.length; i++) {
			JButton botao = new JButton(precos[i]);
			botao.setBounds(290, y, 100, 50);
			botao.setBackground(new Color(0,150,0));
			botao.setForeground(Color.WHITE);
			botao.addActionListener(new OuvinteComprarCreditos(user));
			
			JLabel qnt = new JLabel(quantidade[i]);
			qnt.setForeground(Color.BLACK);
			qnt.setBounds(220, y, 100, 50);
			
			add(qnt);
			add(botao);
			
			
			
			y += 70;
			
		}
		
	}

}
