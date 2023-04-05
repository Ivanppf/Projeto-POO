package paineis;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.ParseException;


import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import janelas.MinhaJanela;
import projetoPOO.Administrador;

import projetoPOO.CentralDeInformacoes;
import projetoPOO.Persistencia;

public class PainelDefValDeReinvindicacao extends JPanel {
	
	private JFormattedTextField tfValor;
	private Administrador admin;
	
	public PainelDefValDeReinvindicacao(Administrador admin) {
		this.admin = admin;
		
		Persistencia pe = new Persistencia();
		CentralDeInformacoes cdi = pe.recuperarCentral();
		
		setSize(640,360);
		setLayout(null);
		addTfValor();
		addLabel("Insira o valor do credito",50,50,150,20,10);
		addLabel("Valor atual dos creditos",50,150,150,20,10);
		addLblBg(Float.toString(cdi.getValorDoCredito()),200,150,60,20,10);//passar Caixa no lugar da string
		addButton("Confirmar",150,280,100,20);
		addButton("Cancelar",360,280,100,20);
	}


	private void addTfValor() {
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("###.##");
			mask.setPlaceholderCharacter('_');
		} catch (ParseException e) {
		}
		tfValor = new JFormattedTextField(mask);
		tfValor.setBounds(200, 50, 100, 20);
		add(tfValor);
	}
	private void addLabel(String nome, int x, int y, int largura, int altura, int tamanho) {
		JLabel lblTexto = new JLabel(nome);
		lblTexto.setFont(new Font(null, Font.BOLD, tamanho));
		lblTexto.setForeground(Color.black);
		lblTexto.setBounds(x, y, largura, altura);
		add(lblTexto);
	}
	//Trocar a string por Caixa;
	private void addLblBg(String c, int x, int y, int largura, int altura, int tamanho) {
		JLabel lblTexto = new JLabel(c);//.getValorDoCredito
		lblTexto.setFont(new Font(null, Font.BOLD, tamanho));
		lblTexto.setBounds(x, y, largura, altura);
		lblTexto.setBackground(Color.WHITE);
		lblTexto.setOpaque(true);
		add(lblTexto);
	}
	private void addButton(String nome, int x, int y, int largura, int altura) {
		JButton bt = new JButton(nome);
		if (nome.equals("Confirmar"))
			bt.setToolTipText("Confirma o valor.");
		else
			bt.setToolTipText("Cancela a ação.");
		bt.setFont(new Font(null, Font.BOLD, 12));
		bt.setBounds(x,y,largura,altura);
		bt.addActionListener(new OuvinteBt());
	
		//bt.addMouseListener(new OuvinteMouse());
		add(bt);
	}
	
	public class OuvinteBt implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			Persistencia pe = new Persistencia();
			CentralDeInformacoes cdi = pe.recuperarCentral();
			
			JButton b = (JButton)e.getSource();
			
			if (b.getText().equals("Confirmar")) {
				try {
				cdi.setValorDoCredito(Float.parseFloat(tfValor.getText()));
				
				pe.salvarCentral(cdi);
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Valor atribuido com sucesso!");
				MinhaJanela.setPanel(new PerfilUsuario(admin));
				
				}catch(Exception error) {	
					JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Digite um valor valido!");
				}
				
				
	
			}
			else if (b.getText().equals("Cancelar")) {
				MinhaJanela.setPanel(new PerfilUsuario(admin));
			}
			
		}
			
		
	}

	
}
		
	
	
