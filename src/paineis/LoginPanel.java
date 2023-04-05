package paineis;



import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import ouvintes.OuvinteDoLogin;

import ouvintes.OuvinteMouse;
import personalizados.CampoEmail;

public class LoginPanel extends JPanel{
	
	private  JTextField tfEmail;
	private JTextField pfSenha;
	
	public LoginPanel() {
	
		
		setSize(640,360);
		setLayout(null);
		
		addLabel("LOGIN", 293, 75, 55, 15, 16);
		tfEmail = CampoEmail.addCampoEmail(this, 160, 100);
		addLabel("Senha", 155, 130, 40, 30, 12);
		addTextField();
		addButton("Entrar", 280, 200, 80, 30);
		addButton("Esqueci minha senha", 243, 160, 155, 16);
		addButton("Criar uma conta", 258, 180, 125, 12);
	}
	
	private void addTextField() {
		pfSenha = new JPasswordField();
		pfSenha.setBounds(200, 135, 250, 20);
		pfSenha.setFont(new Font(null, Font.BOLD, 13));
		add(pfSenha);
		
	}

	private void addButton(String nome, int x, int y, int largura, int altura) {
		JButton botao = new JButton(nome);
		
		botao.setFont(new Font(null, Font.BOLD, 12));
		botao.setBounds(x,y,largura,altura);
		botao.addMouseListener(new OuvinteMouse());

		if (nome.equals("Entrar")) {
			botao.setBackground(Color.BLACK);
			botao.setToolTipText("Entra no aplicativo");
			botao.setForeground(Color.WHITE);
			botao.addActionListener(new OuvinteDoLogin(this, tfEmail, pfSenha));
		} else {
			botao.setOpaque(false);
			botao.setContentAreaFilled(false);
			botao.setBorderPainted(false);
			botao.addActionListener(new OuvinteDoLogin(this, tfEmail, pfSenha));
		}
		add(botao);
			
	}
	
	private void addLabel(String nome, int x, int y, int largura, int altura, int tamanho) {
		JLabel lblTexto = new JLabel(nome);
		lblTexto.setForeground(Color.black);
		lblTexto.setFont(new Font(null, Font.BOLD, tamanho));
		lblTexto.setBounds(x, y, largura, altura);
		add(lblTexto);
	}
	
}
