package ouvintes;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import paineis.EnviarCodigoPanel;

import paineis.PerfilUsuario;
import janelas.MinhaJanela;
import paineis.Registro;

import projetoPOO.CentralDeInformacoes;

import projetoPOO.Persistencia;
import projetoPOO.Usuario;



public class OuvinteDoLogin implements ActionListener{
	private JPanel janela;
	private JTextField tfEmail;
	private JTextField tfSenha;
	
	
	
	public OuvinteDoLogin(JPanel janela, JTextField email, JTextField senha) {
		
		
		tfEmail = email;
		tfSenha = senha;
		this.janela = janela;
		
	}
	
	public void actionPerformed(ActionEvent clique) {
		String acao = clique.getActionCommand();
		Persistencia pe = new Persistencia();
		CentralDeInformacoes central = pe.recuperarCentral();
		
		Usuario user = central.recuperarUsuarioPeloEmail(tfEmail.getText());
		
		boolean possui = false;
		
		
		switch(acao) {
		case"Entrar":			
				if(user != null && user.getSenha().equals(tfSenha.getText())) {
					SwingUtilities.updateComponentTreeUI(janela);
					MinhaJanela.setPanel(new PerfilUsuario(user));
					possui = true;
					break;
					
					
			}
			if(!possui)
				JOptionPane.showMessageDialog(janela, "E-mail ou senha invalidos!");
				break;
			
		
		case"Criar uma conta":
			MinhaJanela.setPanel(new Registro(false));
			break;
			
		case"Esqueci minha senha":
			MinhaJanela.setPanel(new EnviarCodigoPanel(null, "redefinirSenha"));
		}
		
		
		
	}

}
