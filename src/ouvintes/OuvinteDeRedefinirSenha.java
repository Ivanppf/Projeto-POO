package ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import janelas.MinhaJanela;
import paineis.LoginPanel;

import projetoPOO.CentralDeInformacoes;

import projetoPOO.Persistencia;

public class OuvinteDeRedefinirSenha implements ActionListener{
	private JPanel janela;
	
	private JPasswordField pfSenha;
	private JPasswordField pfConfirmarSenha;
	private String email;
	
	public OuvinteDeRedefinirSenha(JPanel janela, JPasswordField pfSenha, JPasswordField pfConfirmarSenha, String email) {
		this.pfSenha = pfSenha;
		this.pfConfirmarSenha = pfConfirmarSenha;
		this.email = email;
		
		this.janela = janela;
	}
	
	public void actionPerformed(ActionEvent clique) {
		Persistencia pe = new Persistencia();
		CentralDeInformacoes central = pe.recuperarCentral();
		
		
		String senha = new String(pfSenha.getPassword());
		String confirmarSenha = new String(pfConfirmarSenha.getPassword());
		
		if(senha.equals(confirmarSenha) == false) {
			JOptionPane.showMessageDialog(janela, "As senhas precisam ser iguais!");
		}
		else if(senha.isBlank() || senha.isBlank()) {
			JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Preencha todos os campos!");
		}
		else {
			central.recuperarUsuarioPeloEmail(email).setSenha(senha);;
			pe.salvarCentral(central);
			
			JOptionPane.showMessageDialog(janela, "Senha alterada com sucesso!");
			SwingUtilities.updateComponentTreeUI(janela);
			MinhaJanela.setPanel(new LoginPanel());
		}
		
		
	}
	

}
