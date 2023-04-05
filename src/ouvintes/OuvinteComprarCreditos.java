package ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;


import janelas.MinhaJanela;

import paineis.PerfilUsuario;
import projetoPOO.Caixa;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.GeradorDeBoletos;
import projetoPOO.Mensageiro;
import projetoPOO.Mototaxista;
import projetoPOO.Persistencia;


public class OuvinteComprarCreditos implements ActionListener {
	private Mototaxista user;
	
	public OuvinteComprarCreditos(Mototaxista user) {
		this.user = user;
		
	}

	public void actionPerformed(ActionEvent e) {
		Persistencia pe = new Persistencia();
		CentralDeInformacoes central = pe.recuperarCentral();
	
		boolean continuar = true;
	
		
	
		int creditos = 0;
		
		if(e.getActionCommand().equals(Float.toString(central.getValorDoCredito())+"$"))
			creditos =  1;
		
		else if(e.getActionCommand().equals(Float.toString(central.getValorDoCredito() * 5)+"$"))
			creditos = 5;
		
		else if(e.getActionCommand().equals(Float.toString(central.getValorDoCredito() * 10)+"$"))
			creditos = 10;
		else if(e.getActionCommand().equals(Float.toString(central.getValorDoCredito() * 50)+"$")) {
			creditos = 50;
		}
		
		else{
			MinhaJanela.setPanel(new PerfilUsuario(user));
			continuar = false;	
		}
		
			
		if(continuar) {
			int resp = JOptionPane.showConfirmDialog(MinhaJanela.getInstance(), "Tem certeza?", "Confirmar compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (resp == 0) {
				
				Caixa caixa = new Caixa();
				caixa.setCreditos(creditos);
				caixa.setData(new Date());
				caixa.setMototaxistaQuePagou(user);
				caixa.setValorDoCredito(central.getValorDoCredito());
				
				GeradorDeBoletos.gerarBoleto(user.getNome(), user.getEmail(), String.valueOf(user.getId()), Float.toString(central.getValorDoCredito() * creditos)+"$");
				Mensageiro.enviarEmail(user.getEmail(), "Você comprou " +Float.toString(creditos)+ " créditos", "Compra de créditos com sucesso");
				
				central.adicionarCaixa(caixa);
				central.atualizarInfoMototaxista(user).setCreditos(user.getCreditos() + creditos);
				
				pe.salvarCentral(central);
				
				JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Créditos comprados", null, JOptionPane.INFORMATION_MESSAGE);
				MinhaJanela.setPanel(new PerfilUsuario(user));
			}
		}
	}

}
