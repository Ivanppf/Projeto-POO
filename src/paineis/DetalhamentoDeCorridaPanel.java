package paineis;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import janelas.MinhaJanela;
import ouvintes.OuvinteDetalhamentoDeCorrida;
import ouvintes.OuvinteMouse;
import personalizados.MeuJButton;
import projetoPOO.Corrida;
import projetoPOO.Mototaxista;

import projetoPOO.Usuario;

public class DetalhamentoDeCorridaPanel extends JPanel{
	private Corrida corrida;
	private Usuario user;
	private JButton mototaxista;
	private Mototaxista mototaxistaQueReinvindicou;
	
	public DetalhamentoDeCorridaPanel(Corrida corrida, Usuario user) {
		this.user = user;
		this.corrida = corrida;
		
		adicionarJLabel();
		adicionarJButton();
		setLayout(null);
	}
	
	public void adicionarJLabel() {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("HH:mm   dd/MM/yyyy");
		
		
		String nomeMototaxista;
		if(corrida.getMototaxistaQueReinvidincou() == null) {
			nomeMototaxista = "Nenhum";
		}
		else {
			nomeMototaxista = corrida.getMototaxistaQueReinvidincou().getNome();
			mototaxistaQueReinvindicou = corrida.getMototaxistaQueReinvidincou();
		}
		
		mototaxista = new JButton(nomeMototaxista);
		mototaxista.setOpaque(false);
		mototaxista.setContentAreaFilled(false);
		mototaxista.setBorderPainted(false);
		mototaxista.addActionListener(new OuvinteLocal());
		
		String situacao;
		if(corrida.getConcluida() == true)
			situacao = "Concluida";
		else if(corrida.getReinvidicado() == true)
			situacao = "Reinvindicada";
		else {
			situacao = "Sem reinvindicação";
		}
		
		String[] botoes = {"Endereço de partida", "Endereço de destino", "Data e horario da corrida", "Passageiro", "Mototaxista", "Situação atual"};
		String[] informacoes = {corrida.getEnderecoPartida(), corrida.getEnderecoDestino(), dataFormatada.format(corrida.getData()), corrida.getPassageiro().getNome(), "", situacao};
		
		int x = 50;
		int y = 60;
		
		int quebraLinha = 2;
		
		for(int i = 0; i < botoes.length; i++) {
			JLabel tipo = new JLabel(botoes[i]);
			JLabel info = new JLabel(informacoes[i]);
			
			tipo.setBounds(x, y, 150, 30);
			info.setBounds(x, y+30, 200, 30);
			tipo.setForeground(Color.BLACK);
			info.setForeground(Color.black);
			
			if(i == 4) {
				mototaxista.setBounds(x - 40, y+30, 150, 30);
				add(mototaxista);
			}
			x += 170;
			
			if(i == quebraLinha) {
				y += 110;
				x = 50;
				quebraLinha += 3;
			}
			
			
			add(tipo);
			add(info);
		}
	}
	public void adicionarJButton() {
		if(user.getTipoDeConta().equals("Mototaxista")) {
			MeuJButton botao  = new MeuJButton("Marcar que foi concluida", 50, 270, 200, 30);
			botao.addActionListener(new OuvinteDetalhamentoDeCorrida(corrida, (Mototaxista)user));
			botao.setBackground(Color.BLACK);
			botao.setForeground(Color.WHITE);
			botao.addMouseListener(new OuvinteMouse());
			
			add(botao);
		}
		MeuJButton voltar = new MeuJButton("Voltar", 350, 270, 200, 30);
		voltar.setBackground(Color.BLACK);
		voltar.setForeground(Color.WHITE);
		voltar.addMouseListener(new OuvinteMouse());
		voltar.addActionListener(new OuvinteLocal());
		add(voltar);
	}
	
	public class OuvinteLocal implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Voltar"))
				MinhaJanela.setPanel(new PerfilUsuario(user));
			
			else if(!mototaxista.getText().equals("Nenhum"))
				MinhaJanela.setPanel(new AvaliacoesMototaxistaPanel(corrida, user));
				
		}
		
		
	}

}


