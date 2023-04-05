      package ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import javax.swing.JTextField;

import janelas.MinhaJanela;
import paineis.EnviarCodigoPanel;
import paineis.LoginPanel;
import paineis.PerfilUsuario;
import paineis.RedefinirSenhaPanel;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Mensageiro;

import projetoPOO.Persistencia;
import projetoPOO.Usuario;

public class OuvinteDoEnviarCodigoPanel implements ActionListener, KeyListener{
	private JTextField tfEmail;
	private String janelaDestino;
	private Usuario usuario;
	
	String codigo = "";
	JTextField tfCodigo = null;
	
	public OuvinteDoEnviarCodigoPanel(Usuario usuario, JTextField tfEmail, String janelaDestino) {
		this.tfEmail = tfEmail;
		this.janelaDestino = janelaDestino;
		this.usuario = usuario;
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		String acao = e.getActionCommand();
		Persistencia pe = new Persistencia();
		CentralDeInformacoes central = pe.recuperarCentral();
		
		String codigo = "";
		String email = tfEmail.getText();

		
		switch(acao) {
		case"Enviar código":
			if(email.isBlank()) {
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Digite alguma coisa!");
				break;
			}
			for (int i = 0; i < 6; i++) {
				codigo += (int) (Math.random()*10);
			}
				EnviarCodigoPanel.setCodigo(codigo);
				Mensageiro.enviarCodigoDeRecuperacao(email, codigo);
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Codigo enviado!");
	
	
			
			break;
			
		case"Cancelar":
			try {
			if(central.recuperarUsuarioPeloEmail(usuario.getEmail()) == null) {
				MinhaJanela.setPanel(new LoginPanel());
				break;
			}
				
			}catch(Exception error) {
				MinhaJanela.setPanel(new LoginPanel());
				break;
			}
			MinhaJanela.setPanel(new PerfilUsuario(usuario));
			
			}
		
			
		}
	
	
	public void keyTyped(KeyEvent e) {
		
		if (!Character.isDigit(e.getKeyChar()))
			e.consume();
		
	}

	public void keyPressed(KeyEvent e) {
		tfCodigo = (JTextField) e.getSource();
				
		codigo = tfCodigo.getText();
		if (codigo.length() == 6) {
			tfCodigo.disable();
			tfCodigo.repaint();
			if (codigo.equals(EnviarCodigoPanel.getCodigo())) {
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Código confirmado", "Verificar código", JOptionPane.INFORMATION_MESSAGE);
				
				if(janelaDestino.equals("redefinirSenha"))
					MinhaJanela.setPanel(new RedefinirSenhaPanel(tfEmail.getText()));
				
				if(janelaDestino.equals("excluirConta")) {
					Persistencia pe = new Persistencia();
					CentralDeInformacoes central = pe.recuperarCentral();
					
					central.removerPassageiro(central.recuperarUsuarioPeloEmail(tfEmail.getText()));
					JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Sua conta foi deletada com sucesso!");
					pe.salvarCentral(central);
					
					
					MinhaJanela.setPanel(new LoginPanel());
				}
				else if(janelaDestino.equals("Criar conta")) {
					Persistencia pe = new Persistencia();
					CentralDeInformacoes central = pe.recuperarCentral();
					central.adicionarUsuario(usuario);
					pe.salvarCentral(central);
					JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Conta ativada com sucesso! Por favor faça login");
					
					MinhaJanela.setPanel(new LoginPanel());
						
					
				}
			}
			else {
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Código incorreto!", "Verificar código", JOptionPane.ERROR_MESSAGE);
				tfCodigo.enable();
				tfCodigo.setText("");
				tfCodigo.repaint();
			}
		}
		
	}

	public void keyReleased(KeyEvent e) {
		keyPressed(e);

		
	}
	

}
