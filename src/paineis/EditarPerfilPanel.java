package paineis;

import java.awt.Color;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import janelas.MinhaJanela;
import ouvintes.OuvinteDoEditarPerfil;
import ouvintes.OuvinteMouse;
import personalizados.MeuJButton;
import projetoPOO.Administrador;
import projetoPOO.Usuario;

public class EditarPerfilPanel extends JPanel{
	private JTextField nomeCampo;
	private JTextField emailCampo;
	private JFormattedTextField nascimentoCampo;
	private Usuario user;
	private Administrador admin;

	
	
	public EditarPerfilPanel(Administrador admin, Usuario user) {
		this.admin = admin;
		this.user = user;
		setLayout(null);
		adicionarTextField();
		adicionarCampos();
		adicionarJButton();
	}
	public EditarPerfilPanel(Usuario user) {
		this.user = user;
		setLayout(null);
		adicionarTextField();
		adicionarCampos();
		adicionarJButton();
	}
	
	public void adicionarTextField() {
	
		int y = 50;
		int x = 200;
		
		String[] labels = {"Usuario", "Email", "Data de nascimento"};
		
		for(int i = 0; i < labels.length; i++) {
			JLabel texto = new JLabel(labels[i]);
			texto.setForeground(Color.black);
			
			if(labels[i].equals("Data de nascimento"))
				x -= 80;
			
			texto.setBounds(x, y, 130, 30);
			add(texto);
			y += 50;
		}
		
	}
	public void adicionarCampos() {
	
			nomeCampo = new JTextField(user.getNome());
			emailCampo = new JTextField(user.getEmail());
			emailCampo.setEditable(false);
			try {
				nascimentoCampo = new  JFormattedTextField(new MaskFormatter("##/##/####"));
			} catch (ParseException e) {}
			
			
			nomeCampo.setBounds(250, 50, 200, 30);
			emailCampo.setBounds(250, 100, 200, 30);
			nascimentoCampo.setBounds(250, 150, 70, 30);
			
			add(nomeCampo);
			add(emailCampo);
			add(nascimentoCampo);
			
			
		
	}
	public void adicionarJButton() {
		String[] botoes = {"Salvar", "Cancelar"};
		int x = 180;
		
		for(int i = 0; i < botoes.length; i++) {
			MeuJButton botao = new MeuJButton(botoes[i], x, 210, 100, 50);
			if(admin == null)
				botao.addActionListener(new OuvinteDoEditarPerfil(user, nomeCampo, emailCampo, nascimentoCampo));
			else
				botao.addActionListener(new OuvinteDoEditarPerfil(user, nomeCampo, emailCampo, nascimentoCampo, admin));
			
			botao.setBackground(Color.BLACK);
			botao.setForeground(Color.WHITE);
			botao.addMouseListener(new OuvinteMouse());
			add(botao);
			x += 200;
		}
	}

}
