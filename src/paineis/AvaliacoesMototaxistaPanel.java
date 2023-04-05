package paineis;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import janelas.MinhaJanela;
import ouvintes.OuvinteMouse;
import personalizados.MeuJButton;
import projetoPOO.Avaliacao;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Corrida;
import projetoPOO.Mototaxista;
import projetoPOO.Persistencia;
import projetoPOO.Usuario;

public class AvaliacoesMototaxistaPanel extends JPanel{
	private JTable tabela;
	private DefaultTableModel modelo;
	
	private Corrida corrida;
	private Usuario user;
	
	public AvaliacoesMototaxistaPanel(Corrida corrida, Usuario user) {
		setSize(640,360);
		setLayout(null);
		
		
		this.user = user;
		this.corrida = corrida;
		
		MeuJButton voltar = new MeuJButton("Voltar" ,370, 265, 150, 40);
		voltar.setBackground(Color.BLACK);
		voltar.setForeground(Color.WHITE);
		voltar.addMouseListener(new OuvinteMouse());
		voltar.addActionListener(new OuvinteLocal());
		add(voltar);
		
		addTabela();
	}
	
	private void addTabela() {
		modelo = new DefaultTableModel();
		
		modelo.addColumn("Passageiro");
		modelo.addColumn("Nota");
		modelo.addColumn("Comentario");
		
		
		
		
		Persistencia pe = new Persistencia();
		CentralDeInformacoes cdi = pe.recuperarCentral();
		Mototaxista mototaxi = (Mototaxista)cdi.recuperarUsuarioPeloEmail(corrida.getMototaxistaQueReinvidincou().getEmail());
		
		if(mototaxi.getAvaliacoes() != null) {
			for (Avaliacao avaliacao : mototaxi.getAvaliacoes()) {
				addLinha(modelo, avaliacao);
		}
			
		}
		
		tabela = new JTable(modelo);
	
		
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabela.getColumnModel().getColumn(1).setPreferredWidth(5);
		
		JScrollPane painel = new JScrollPane(tabela);
		
		painel.setBounds(110, 10, 370, 250);
		
		
		add(painel);
		
	}
	private void addLinha(DefaultTableModel modelo2, Avaliacao avaliacao) {
		Object[] linha = new Object[3];
		
		linha[0] = avaliacao.getPassageiro().getNome();
		
		linha[1] = avaliacao.getNota();
	
		linha[2] = avaliacao.getComentario();
		
		modelo2.addRow(linha);
		
	}
	
	public class OuvinteLocal implements ActionListener{

	
		public void actionPerformed(ActionEvent e) {
			MinhaJanela.setPanel(new DetalhamentoDeCorridaPanel(corrida, user));
	
			
		}
		
	}

}
