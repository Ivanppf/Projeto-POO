package paineis;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import janelas.MinhaJanela;
import ouvintes.OuvinteMouse;
import projetoPOO.Avaliacao;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Corrida;
import projetoPOO.Mototaxista;
import projetoPOO.Passageiro;
import projetoPOO.Persistencia;

public class AvaliarCorridaPanel extends JPanel {
	
	private StarRatingButton avaliacao;
	private JTextArea taComentario;
	private Corrida corrida;
	
	public AvaliarCorridaPanel(Corrida corrida) {
		this.corrida = corrida;
		
		setSize(640,360);
		setLayout(null);
		addLabel();
	
		avaliacao = new StarRatingButton(254, 80);
		add(avaliacao);
		addTextArea();
		addButton();
	}

	private void addButton() {
		JButton btnAvaliar = new JButton("Avaliar");
		btnAvaliar.setBounds(260, 250, 120, 40);
		btnAvaliar.setBackground(Color.BLACK);
		btnAvaliar.setForeground(Color.WHITE);
		btnAvaliar.addActionListener(new OuvinteAvaliarCorrida());
		btnAvaliar.addMouseListener(new OuvinteMouse());
		add(btnAvaliar);
		
	}

	private void addTextArea() {
		taComentario = new JTextArea("Adicione um comentário");
		JScrollPane scroll = new JScrollPane(taComentario);
		scroll.setBounds(195, 130, 250, 100);
		taComentario.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taComentario.setFont(new Font("Arial", Font.BOLD, 12));
		taComentario.setLineWrap(true);
		taComentario.setWrapStyleWord(true);
		
		add(scroll);
		
	}

	private void addLabel() {
		JLabel lblTexto = new JLabel("Avalie a corrida");
		lblTexto.setBounds(260,30,120,30);
		lblTexto.setFont(new Font("Arial", Font.BOLD, 16));
		add(lblTexto);
		
	}
	
	private class OuvinteAvaliarCorrida implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			Persistencia pe = new Persistencia();
			CentralDeInformacoes central = pe.recuperarCentral();
			
			
		
			if (avaliacao.getNota() < 3) {
				int opcao = JOptionPane.showConfirmDialog(MinhaJanela.getInstance(), "Deseja bloquear o mototaxista?", "Avaliação da corrida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(opcao == JOptionPane.YES_OPTION) {
					Mototaxista mototaxista = central.recuperarCorridaPeloId(corrida.getId()).getMototaxistaQueReinvidincou();
					
					central.recuperarCorridaPeloId(corrida.getId()).getPassageiro().setMotoristasBloqueados(mototaxista);
						
				}
			}
			Avaliacao av = new Avaliacao();
			av.setNota(avaliacao.getNota());
			av.setComentario(taComentario.getText());
			av.setPassageiro(corrida.getPassageiro());
			
			
			central.atualizarInfoMototaxista(corrida.getMototaxistaQueReinvidincou()).adicionarAvaliacao(av);
			central.recuperarCorridaPeloId(corrida.getId()).setAvaliada(true);
			pe.salvarCentral(central);
			
			Passageiro passageiro = central.recuperarCorridaPeloId(corrida.getId()).getPassageiro();
			MinhaJanela.setPanel(new PerfilUsuario(passageiro));
				
			}
			
		}

	
	
}
