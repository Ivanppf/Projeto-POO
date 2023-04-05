package ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import janelas.MinhaJanela;
import paineis.EnviarEmailPanel;
import paineis.PerfilUsuario;
import projetoPOO.Administrador;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Persistencia;
import projetoPOO.Usuario;

public class OuvinteDoEditarPerfil implements ActionListener{
	
	private JTextField nomeCampo;
	private JTextField emailCampo;
	private JFormattedTextField nascimentoCampo;
	private Usuario usuario;
	private Administrador admin;
	
	public OuvinteDoEditarPerfil(Usuario user, JTextField nomeCampo, JTextField emailCampo, JFormattedTextField nascimentoCampo, Administrador admin) {
		this.admin = admin;
	
		this.usuario = user;
		this.nomeCampo = nomeCampo;
		this.emailCampo = emailCampo;
		this.nascimentoCampo = nascimentoCampo;
	}
	public OuvinteDoEditarPerfil(Usuario user, JTextField nomeCampo, JTextField emailCampo, JFormattedTextField nascimentoCampo) {
		this.usuario = user;
		this.nomeCampo = nomeCampo;
		this.emailCampo = emailCampo;
		this.nascimentoCampo = nascimentoCampo;
	}
	
	public void actionPerformed(ActionEvent e) {
		String acao = e.getActionCommand();
		
		Persistencia pe = new Persistencia();
		CentralDeInformacoes central = pe.recuperarCentral();
		
		switch(acao) {
		case"Salvar":			
			
			Date nascimento = new Date();
			try {
				nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(nascimentoCampo.getText());
			
			
			} catch (ParseException error) {
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Data invalida!");
				break;
	
				
			}
			
			central.recuperarUsuarioPeloEmail(usuario.getEmail()).setNome(nomeCampo.getText());
			
			central.recuperarUsuarioPeloEmail(usuario.getEmail()).setNascimento(nascimento);
			
			pe.salvarCentral(central);
			
			if(admin == null) {
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Seus dados foram alterados com sucesso!");
				
				
			
				MinhaJanela.setPanel(new PerfilUsuario(usuario));
				break;
			}
			else {
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Os dados foram alterados com sucesso, por favor, avise ao usuario");
				MinhaJanela.setPanel(new EnviarEmailPanel(usuario.getEmail(), admin));
				break;
			}
			
		case"Cancelar":
			if(admin == null)
				MinhaJanela.setPanel(new PerfilUsuario(usuario));
			else
				MinhaJanela.setPanel(new PerfilUsuario(admin));
		
		}
		
	}

}
