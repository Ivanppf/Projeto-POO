package paineis;

import java.awt.Color;
import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;


import ouvintes.OuvinteDoEnviarCodigoPanel;
import ouvintes.OuvinteMouse;
import personalizados.CampoEmail;
import projetoPOO.Usuario;


public class EnviarCodigoPanel extends JPanel {
	
	private JTextField tfEmail;
	private String janelaDestino;
	private static String codigo;
	private Usuario usuario;
	
	public static String getCodigo() {
		return codigo;
	}
	
	public static void setCodigo(String c) {
		codigo = c;
	}
	
	public EnviarCodigoPanel(Usuario usuario, String janelaDestino) {
		this.usuario = usuario;
		this.janelaDestino = janelaDestino;
		
		setSize(640,360);
		setLayout(null);
		
	
		addLabel();
		
		tfEmail = CampoEmail.addCampoEmail(this, 140, 125);
		
		if(usuario != null) {
			tfEmail.setText(usuario.getEmail());
			tfEmail.setEditable(false);
		}
		addButton();
		addTextField();
	}
	

	private void addTextField() {
		JTextField tfCodigo = new JTextField();
		tfCodigo.setBounds(275, 210, 55, 20);
		tfCodigo.setFont(new Font(null, Font.BOLD, 13));
		tfCodigo.addKeyListener(new OuvinteDoEnviarCodigoPanel(usuario, tfEmail, janelaDestino));
		add(tfCodigo);
		
	}

	private void addButton() {
		String[] botoes = {"Enviar código", "Cancelar"};
		int x = 180;
		
		for(int i = 0; i < botoes.length; i++) {
			JButton btnEnviar = new JButton(botoes[i]);
			btnEnviar.setBounds(x, 160,115,30);
			btnEnviar.setFont(new Font(null, Font.BOLD, 12));
			
			btnEnviar.addActionListener(new OuvinteDoEnviarCodigoPanel(usuario, tfEmail,  janelaDestino));
			btnEnviar.setBackground(new Color(0,0,0));
			btnEnviar.setForeground(Color.WHITE);
			add(btnEnviar);
			
			x += 143;
		}
		
	}

	private void addLabel() {
		String palavra = "Excluir conta";
		if(janelaDestino.equals("redefinirSenha"))
			palavra = "Redefinir senha";
		if(janelaDestino.equals("Criar conta"))
			palavra = "Confirmar conta";
		
		JLabel lblRedefinirSenha = new JLabel(palavra);
		lblRedefinirSenha.setBounds(240, 100, 120, 15);
		lblRedefinirSenha.setFont(new Font("Arial", Font.BOLD, 16));
		add(lblRedefinirSenha);
		
	}
	

}
