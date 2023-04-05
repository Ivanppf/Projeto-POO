package paineis;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import janelas.MinhaJanela;
import ouvintes.OuvinteMouse;
import projetoPOO.Administrador;
import projetoPOO.Mensageiro;

public class EnviarEmailPanel extends JPanel {
	private JTextField campo;
	private JTextArea campoTA;
	private String email;
	private Administrador admin;

	public  EnviarEmailPanel(String email, Administrador admin) {
		this.email = email;
		this.admin = admin;
		
		setSize(640,360);
		setLayout(null);	
		addLabel("Assunto:", 90,20,100,20,15);
		addTextArea();
		addLabel("Mensagem:", 90,70,100,20,15);
		addTextField();
		addButton();
	}
	
	private void addLabel(String nome, int x, int y, int largura, int altura, int tamanho) {
		JLabel lblTexto = new JLabel(nome);
		lblTexto.setFont(new Font(null, Font.BOLD, tamanho));
		lblTexto.setBounds(x, y, largura, altura);
		add(lblTexto);
		
		Icon passaro = new ImageIcon("imagem/passaro.gif");
		JLabel gif = new JLabel(passaro);
		gif.setBounds(535, 45, 20, 20);
		add(gif);
	}
	
	private void addTextArea() {
		campoTA =new JTextArea();
		JScrollPane jsp = new JScrollPane(campoTA);
		jsp.setBounds(90,95,440,150);
		campoTA.setFont(new Font("Arial", Font.BOLD, 13));
		campoTA.setLineWrap(true);
		campoTA.setWrapStyleWord(true);
		add(jsp);
	}
	
	private void addTextField() {
		campo = new JTextField();
		campo.setBounds(90,45,440,20);
		campo.setFont(new Font("Arial", Font.BOLD, 13));
		
		add(campo);
		
	}
	
	private void addButton() {
		JButton bt = new JButton("Enviar");
		bt.setToolTipText("Envia o email para o destinatario");
		bt.setFont(new Font(null, Font.BOLD, 12));
		bt.setBounds(260,270,100,30);
		bt.addMouseListener(new OuvinteMouse());
		bt.setBackground(Color.BLACK);
		bt.setForeground(Color.WHITE);
		OuvinteBt obt = new OuvinteBt();
		bt.addActionListener(obt);
		add(bt);
	}
	
private class OuvinteBt implements ActionListener{
	
		public void actionPerformed(ActionEvent e) {
			
			Mensageiro.enviarEmail(email, campoTA.getText(), campo.getText());
			JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Email enviado com sucesso!");
			MinhaJanela.setPanel(new PerfilUsuario(admin));
		
				
		}
	}






}