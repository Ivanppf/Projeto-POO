package ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import janelas.MinhaJanela;
import paineis.PerfilUsuario;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Corrida;
import projetoPOO.Mototaxista;
import projetoPOO.Persistencia;

public class OuvinteDetalhamentoDeCorrida implements ActionListener{

	private Corrida corrida;
	private Mototaxista mototaxista;
	
	public OuvinteDetalhamentoDeCorrida(Corrida corrida, Mototaxista mototaxista) {
		this.mototaxista = mototaxista;
		this.corrida = corrida;
	}
	
	public void actionPerformed(ActionEvent e) {
		Persistencia pe = new Persistencia();
		CentralDeInformacoes central = pe.recuperarCentral();
		
		central.recuperarCorridaPeloId(corrida.getId()).setConcluida(true);
		central.recuperarCorridaPeloId(corrida.getId()).setMototaxistaQueReinvindicou(mototaxista);
		pe.salvarCentral(central);
		
		JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Corrida concluida com sucesso!");
		MinhaJanela.setPanel(new PerfilUsuario(mototaxista));
	}

}
