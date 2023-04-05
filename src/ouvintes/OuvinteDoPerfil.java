package ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import janelas.MinhaJanela;
import paineis.ComprarCreditosPanel;
import paineis.EditarPerfilPanel;
import paineis.EnviarCodigoPanel;
import paineis.ListarTodosUsuariosPanel;
import paineis.LoginPanel;
import paineis.ManterCaixaPanel;
import paineis.PainelDefValDeReinvindicacao;
import paineis.SolicitarCorridaPanel;
import paineis.listarCorridas;
import projetoPOO.Administrador;
import projetoPOO.Mototaxista;
import projetoPOO.Usuario;



public class OuvinteDoPerfil implements ActionListener{
	private Usuario user;
	private Administrador admin;

	
	public OuvinteDoPerfil(Usuario user) {	
		this.user = user;
	}
	public OuvinteDoPerfil(Administrador admin, Usuario user) {
		this.user = user;
		this.admin = admin;
	}
	
	public void actionPerformed(ActionEvent clique) {
		String acao = clique.getActionCommand();
		
		
		switch(acao) {
		
		
		  //MAIS DE 1 USUARIO//
		case"Excluir conta":
			MinhaJanela.setPanel(new EnviarCodigoPanel(user, "excluirConta"));
			break;
			
			
		case "Editar perfil":
			if(admin != null) {
				MinhaJanela.setPanel(new EditarPerfilPanel(admin, user));
				break;
			}
			
			else {
				MinhaJanela.setPanel(new EditarPerfilPanel(user));
				break;
			}
			
		case"Sair":
			MinhaJanela.setPanel(new LoginPanel());
			break;
			
		case "<html>Cadastrar nova senha<html>":
			MinhaJanela.setPanel(new EnviarCodigoPanel(user, "redefinirSenha"));
			break;
			
		case "<html>Listar corridas<html>":
			MinhaJanela.setPanel(new listarCorridas(user));
			break;
			
			//MOTOTAXISTA//
		case"<html>Comprar credito<html>":
		
			MinhaJanela.setPanel(new ComprarCreditosPanel((Mototaxista)user));
			break;
			
		
			
		
			
			
			//PASSAGEIRO//
			case "<html>Solicitar Corrida<html>":
				MinhaJanela.setPanel(new SolicitarCorridaPanel(user));
				break;
				
		
			  //ADMIN//
				
				
			case"<html>Manter caixa<html>":
				MinhaJanela.setPanel(new ManterCaixaPanel(user));
				break;
				
			case"<html>Listar os usuários<html>":
				MinhaJanela.setPanel(new ListarTodosUsuariosPanel((Administrador)user));
				break;
				
			case"<html>Valor do crédito de reinvindicação<html>":
				MinhaJanela.setPanel(new PainelDefValDeReinvindicacao((Administrador)user));
				break;
		}
		
			
			
		
	
			
		
			
	
			
	}

}
