package paineis;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import janelas.MinhaJanela;
import ouvintes.OuvinteMouse;
import projetoPOO.Caixa;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.GeradorDeRelatorio;
import projetoPOO.Persistencia;
import projetoPOO.Usuario;

public class ManterCaixaPanel extends JPanel {
	
	private DefaultTableModel modelo;
	private JTable tabela;
	private JFormattedTextField tfInicio;
	private JFormattedTextField tfFim;
	private Usuario usuario;
	
	public ManterCaixaPanel(Usuario usuario) {
		this.usuario = usuario;
		setSize(640,360);
		setLayout(null);
		addTabela();
		addTextFIeld();
		addButton();
		addComboBox();
		addLabel();
	}

	private void addLabel() {
		String[] nomes = {"Inicio","Fim"};
		int y = 35;
		for (String n : nomes) {
			JLabel nome = new JLabel(n);
			nome.setBounds(80, y, 30, 40);
			add(nome);
			y += 40;
		}
		
	}

	private void addComboBox() {
		CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
		
		
		
		
	
		
		ArrayList<Caixa> caixas = cdi.getCaixas();
		

		
		
		ArrayList<String> emails = new ArrayList<String>();
		int contador = 1;
		
		String[] opc = new String[caixas.size()+1];
		opc[0] = "Todos";
		for (int i = 0; i < caixas.size(); i++) {
			
			if(!emails.contains(caixas.get(i).getMototaxistaQuePagou().getEmail())) {
				opc[contador++] = caixas.get(i).getMototaxistaQuePagou().getNome();
				emails.add(caixas.get(i).getMototaxistaQuePagou().getEmail());
			}
		}
		String[] users = new String[contador];
		for(int i = 0; i < contador; i++) {
			users[i] = opc[i];
		}
		
		JComboBox<String> comboBox = new JComboBox<String>(users);
		comboBox.setBounds(320, 40, 200, 30);
		comboBox.addActionListener(new OuvinteComboBox());
		add(comboBox);
		
	}

	private void addButton() {
		String[] nomes = {"Gerar relatório PDF", "Cancelar"};
		int x = 140;
		for (String n : nomes) {
			JButton botao = new JButton(n);
			botao.setBounds(x, 265, 150, 40);
			botao.setBackground(Color.BLACK);
			botao.setForeground(Color.WHITE);
			botao.addActionListener(new OuvinteButton());
			botao.addMouseListener(new OuvinteMouse());
			add(botao);
			x += 210;
		}
		
	}

	private void addTextFIeld() {
		try {
			MaskFormatter mask = new MaskFormatter("##/##/#### ##:##");
			tfInicio = new JFormattedTextField(mask);
			tfInicio.setBounds(120, 40, 110, 30);
			tfInicio.addKeyListener(new OuvinteTextField());
			add(tfInicio);
			
			tfFim = new JFormattedTextField(mask);
			tfFim.setBounds(120, 80, 110, 30);
			tfFim.addKeyListener(new OuvinteTextField());
			add(tfFim);
		} catch (ParseException e) {
			
		}
	}

	private void addTabela() {
		modelo = new DefaultTableModel();
		
		modelo.addColumn("Data");
		modelo.addColumn("Nome do cliente");
		modelo.addColumn("Créditos");
		modelo.addColumn("Valor pago");
		
		CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
		
		for (Caixa c : cdi.getCaixas()) {
			addLinha(modelo, c);
			
		}
		
		tabela = new JTable(modelo);
		
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane painel = new JScrollPane(tabela);
		
		painel.setBounds(120, 120, 400, 130);
		
		add(painel);
		
	}
	
	private void addLinha(DefaultTableModel modelo2, Caixa c) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH/mm");
		
		Object[] linha = new Object[4];
		
		linha[0] = sdf.format(c.getData());
		
		linha[1] = c.getMototaxistaQuePagou().getNome();
		
		linha[2] = c.getCreditos();
		
		linha[3] = c.getValorDoCredito();
		
		modelo2.addRow(linha);
		
	}
	
	private class OuvinteButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			switch (e.getActionCommand()) {
				case "Gerar relatório PDF":
					GeradorDeRelatorio.gerarRelatorio(MinhaJanela.getInstance(), tabela);
					break;
				case "Cancelar":
					MinhaJanela.setPanel(new PerfilUsuario(usuario));
					// daqui ele volta
					break;
			}
			
		}
		
	}
	
	private class OuvinteComboBox implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
			String opc = (String) comboBox.getSelectedItem();
			CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
			modelo.setRowCount(0);
			
			for (Caixa c : cdi.getCaixas()) {
				if (opc.equals(c.getMototaxistaQuePagou().getNome()))
					addLinha(modelo, c);
				else if (opc.equals("Todos"))
					addLinha(modelo, c);
			}
		}
	}
	
	private class OuvinteTextField implements KeyListener {

		public void keyTyped(KeyEvent e) {
			
		}

		public void keyPressed(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent e) {
			int tamInicio = tfInicio.getText().strip().length();
			int tamFim = tfFim.getText().strip().length();
			
			if (!Character.isDigit(e.getKeyChar()))
				e.consume();
			
			if (tamInicio == 16 && tamFim == 16) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				try {
					Date dataInicio = sdf.parse(tfInicio.getText());
					Date dataFim = sdf.parse(tfFim.getText());
					modelo.setRowCount(0);
					
					CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
					
					for (Caixa c : cdi.getCaixas()) {
						if (c.getData().after(dataInicio) && c.getData().before(dataFim));
							addLinha(modelo, c);
					}
				} catch (ParseException er) {
					System.out.println("ParseException");
				}
				
			}
			
		}
		
	}
	
}