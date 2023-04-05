package ouvintes;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import janelas.MinhaJanela;
import paineis.PerfilUsuario;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Corrida;
import projetoPOO.Passageiro;
import projetoPOO.Persistencia;
import projetoPOO.Usuario;

public class OuvinteDoSolicitarCorrida implements ActionListener {

	private JTextField tfPartida;
	private JTextField tfDestino;
	private JTextField tfDistancia;
	private Usuario user;
	private JFormattedTextField tfData;
	

	
	public OuvinteDoSolicitarCorrida(JTextField tfPartida, JTextField tfDestino,
									JTextField tfDistancia, JFormattedTextField tfData, Usuario user) {
		
		this.user = user;
		this.tfPartida = tfPartida;
		this.tfDestino = tfDestino;
		this.tfDistancia = tfDistancia;
		this.tfData = tfData;
	}

	public OuvinteDoSolicitarCorrida(JFormattedTextField tfData) {
		this.tfData = tfData;
	}
	
	public void actionPerformed(ActionEvent e) {
		Persistencia pe = new Persistencia();
		CentralDeInformacoes central = pe.recuperarCentral();
		
		
		switch (e.getActionCommand()) {
			case "Agendar corrida":
				JRadioButton botao =  (JRadioButton) e.getSource();
				if (botao.isSelected()) 
					tfData.enable();
				else 
					tfData.disable();
				tfData.repaint();
				break;
				
			case "Enviar":
				boolean marcado = false;
				JRadioButton radioButton;
				
				try {
						radioButton = (JRadioButton)e.getSource();
						marcado = true;
				}catch(Exception error) {
					marcado = false;
				}
					
				if(tfPartida.getText().isEmpty() || tfDestino.getText().isEmpty() || tfDistancia.getText().isEmpty()) {
					JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Preencha todos os campos!");
				}
				
				else {
					Corrida corrida = new Corrida();
					corrida.setEnderecoPartida(tfPartida.getText());
					corrida.setEnderecoDestino(tfDestino.getText());
					corrida.setDistancia(tfDistancia.getText());
					String data = tfData.getText();
					
					Date dataMarcada = new Date();
					
					if(marcado == true) {
						try {
							
							dataMarcada = new SimpleDateFormat("dd/MM/yyyy").parse(data);
							corrida.setData(dataMarcada);
						
						
						} catch (ParseException error) {
							JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Data invalida!");
						}
					}
					else {
						dataMarcada = new Date(System.currentTimeMillis());
						corrida.setData(dataMarcada);
					}
					corrida.setReinvidicado(false);
					corrida.setPassageiro((Passageiro)user);
					
					if(central.adicionarCorrida(corrida) == true) {
					
						pe.salvarCentral(central);
						JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Corrida solicitada com sucesso!");
						MinhaJanela.setPanel(new PerfilUsuario(user));
					}
				}
				break;
				
			case"Cancelar":
				MinhaJanela.setPanel(new PerfilUsuario(user));
		
		}
		
		
	}

}
