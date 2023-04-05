package paineis;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import janelas.MinhaJanela;
import ouvintes.OuvinteMouse;
import projetoPOO.Administrador;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Corrida;
import projetoPOO.Mototaxista;
import projetoPOO.Passageiro;
import projetoPOO.Persistencia;
import projetoPOO.Usuario;

public class listarCorridas extends JPanel {
	
	private DefaultTableModel modelo;
	private JTable tabela;
	private JTextField tfPesquisa;
	private JComboBox<String> cbCorridas;
	private Usuario usuario;
	private ArrayList<Corrida> corridasArray;
	JRadioButton rbReivindicada;
	
	public listarCorridas(Usuario u) {
		corridasArray = new ArrayList<Corrida>();
		
		usuario = u;
		setSize(640,360);
		setLayout(null);
		addTabela();
		
		if (usuario instanceof Administrador || usuario instanceof Passageiro)
			addRadioButton();
		
		if (usuario instanceof Administrador || usuario instanceof Mototaxista)
			addComboBox();
		if (usuario instanceof Administrador)
			addTextField();
		addButton();
	}

	private void addRadioButton() {
		rbReivindicada = new JRadioButton("Reivindicada");
		
		rbReivindicada.setBounds(380, 65, 100, 14);
		rbReivindicada.addActionListener(new OuvinteRadio());
		
		add(rbReivindicada);
		
	}

	private void addButton() {
		JButton btndetalhes = new JButton("Detalhes da corrida");
		btndetalhes.setBounds(120, 265, 170, 40);
		btndetalhes.setBackground(Color.BLACK);
		btndetalhes.setForeground(Color.WHITE);
		btndetalhes.addMouseListener(new OuvinteMouse());
		
		btndetalhes.addActionListener(new OuvinteButton());
		add(btndetalhes);
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(370, 265, 150, 40);
		btnCancelar.setBackground(Color.BLACK);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.addMouseListener(new OuvinteMouse());
		btnCancelar.addActionListener(new OuvinteButton());
		add(btnCancelar);
		
	}
	
	private void addComboBox() {
		String[] opc = {"Mais recentes primeiro","Mais antigas primeiro"};
		cbCorridas = new JComboBox<String>(opc);
		cbCorridas.setBounds(120, 30, 160, 30);
		cbCorridas.addActionListener(new OuvinteComboBox());
		add(cbCorridas);
		
	}


	private void addTextField() {
		tfPesquisa = new JTextField();
		tfPesquisa.setBounds(360, 30, 160, 30);
		tfPesquisa.addKeyListener(new OuvinteTextField());
		add(tfPesquisa);
	}
	
	private void addLinha(DefaultTableModel modelo2, Corrida c) {
		Object[] linha = new Object[3];
		linha[0] = c.getPassageiro().getNome();
		linha[1] = c.getEnderecoPartida();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm   dd/MM/yyyy");
		linha[2] = sdf.format(c.getData());
		modelo2.addRow(linha);
	}
	
	private void addTabela() {
		modelo = new DefaultTableModel();
		modelo.addColumn("Passageiro");
		modelo.addColumn("Partida");
		modelo.addColumn("Data");
		CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
		
		for (Corrida corrida : cdi.getCorridas()) {
			if(usuario.getTipoDeConta().equals("Admin")) {
				corridasArray.add(corrida);
				addLinha(modelo, corrida);
			}
			else if(corrida.getPassageiro().getEmail().equals(usuario.getEmail()) && corrida.getConcluida() == false) {
				corridasArray.add(corrida);
				addLinha(modelo, corrida);
			}
			else if(usuario.getTipoDeConta().equals("Mototaxista") && corrida.getMototaxistaQueReinvidincou() == null) {
				corridasArray.add(corrida);
				addLinha(modelo, corrida);
			}
			else if(usuario.getTipoDeConta().equals("Mototaxista")  && corrida.getReinvidicado() == false && !corrida.getPassageiro().getMotoristasBloqueados().contains(usuario) && corrida.getConcluida() == false) {
				corridasArray.add(corrida);
				addLinha(modelo, corrida);
			}
			
			else if(corrida.getMototaxistaQueReinvidincou() != null && corrida.getMototaxistaQueReinvidincou().getEmail().equals(usuario.getEmail()) && corrida.getConcluida() == false){
				corridasArray.add(corrida);
				addLinha(modelo, corrida);
				
			}
				
			
		}
		tabela = new JTable(modelo);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane painel = new JScrollPane(tabela);
		painel.setBounds(120, 85, 400, 160);
		add(painel);
	}
	
	private class OuvinteButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			Persistencia pe = new Persistencia();
			CentralDeInformacoes cdi = pe.recuperarCentral();
			
			switch (e.getActionCommand()) {
				case "Detalhes da corrida":
					Corrida c = null;
				try {
					c = corridasArray.get(tabela.getSelectedRow());	
				}catch(Exception error) {
					JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Selecione alguma coisa!");
					break;
				}
					
					if(usuario.getTipoDeConta().equals("Mototaxista")) {
						Mototaxista mototaxista = cdi.atualizarInfoMototaxista((Mototaxista)usuario);
						
						if(corridasArray.get(tabela.getSelectedRow()).getReinvidicado() == false) {
							if(cdi.atualizarInfoMototaxista(mototaxista).getCreditos() <= 0) {
								JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Compre mais créditos de reinvindicação primeiro!");
								break;
							}
							int escolha = JOptionPane.showConfirmDialog(MinhaJanela.getInstance(), "Deseja reinvindicar essa corrida?", "Reinvindicar corrida", JOptionPane.YES_NO_OPTION);
							
							if(escolha == JOptionPane.YES_OPTION) {
								
								
								cdi.atualizarInfoMototaxista((Mototaxista)mototaxista).setCreditos(mototaxista.getCreditos() - 1);
								cdi.atualizarInfoCorrida(c).setMototaxistaQueReinvindicou((Mototaxista)usuario);;
								cdi.atualizarInfoCorrida(c).setReinvidicado(true);
								pe.salvarCentral(cdi);
							}
							else {
								break;
							}		
								
						}
					}
					MinhaJanela.setPanel(new DetalhamentoDeCorridaPanel(c, usuario));
					break;
				
					
					
				
				case "Cancelar":
					MinhaJanela.setPanel(new PerfilUsuario(usuario));
					
					break;
			}
			
		}
		
	}
	
	private class OuvinteRadio implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			
			String opc = "Mais antigas primeiro";
			try {
				opc = (String) cbCorridas.getSelectedItem();
			}catch(Exception error) {}
			
			CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
			ArrayList<Corrida> corridas = cdi.getCorridas();
			modelo.setRowCount(0);
			corridasArray.clear();
			
			if(rbReivindicada.isSelected()) {
				if(opc.equals("Mais antigas primeiro")) {
					for (int i = corridas.size()-1; i >= 0; i--) {
						
						
						
						if(usuario.getTipoDeConta().equals("Admin") && corridas.get(i).getReinvidicado() == true) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
						}
						else if(corridas.get(i).getPassageiro().getEmail().equals(usuario.getEmail())  && corridas.get(i).getReinvidicado() == true && corridas.get(i).getConcluida() == false) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
						}		
					
					}
				}else {
					for (int i = 0; i < corridas.size(); i++) {
						if(usuario.getTipoDeConta().equals("Admin") && corridas.get(i).getReinvidicado() == true) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
						}
						else if(corridas.get(i).getPassageiro().getEmail().equals(usuario.getEmail())  && corridas.get(i).getReinvidicado() == true && corridas.get(i).getConcluida() == false) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
								
							}
						}
					}
			}
			
			else {
				if(opc.equals("Mais antigas primeiro")) {
					for (int i = corridas.size()-1; i >= 0; i--) {
						
						if(usuario.getTipoDeConta().equals("Admin")) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
						}
						else if(corridas.get(i).getPassageiro().getEmail().equals(usuario.getEmail())  && corridas.get(i).getConcluida() == false) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
						}		
					
					}
				}else {
					for (int i = 0; i < corridas.size(); i++) {
						if(usuario.getTipoDeConta().equals("Admin")) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
						}
						else if(corridas.get(i).getPassageiro().getEmail().equals(usuario.getEmail())  && corridas.get(i).getConcluida() == false) {
							corridasArray.add(corridas.get(i));
							addLinha(modelo, corridas.get(i));
								
							}
						}
					}
			}
			
		}
		
	}
	
	private class OuvinteComboBox implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
			String opc = (String) comboBox.getSelectedItem();
			CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
			ArrayList<Corrida> corridas = cdi.getCorridas();
			modelo.setRowCount(0);
			corridasArray.clear();
			
			if (opc.equals("Mais antigas primeiro")) {
				for (int i = corridas.size()-1; i >= 0; i--) {
					
					if(usuario.getTipoDeConta().equals("Admin")) {
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
					}
					
					else if(usuario.getTipoDeConta().equals("Mototaxista") && corridas.get(i).getMototaxistaQueReinvidincou() == null) {
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
					}
					else if(usuario.getTipoDeConta().equals("Mototaxista") && corridas.get(i).getReinvidicado() == false && !corridas.get(i).getPassageiro().getMotoristasBloqueados().contains(usuario) && corridas.get(i).getConcluida() == false) {
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
					}
					
					else if(corridas.get(i).getMototaxistaQueReinvidincou() != null && corridas.get(i).getMototaxistaQueReinvidincou().getEmail().equals(usuario.getEmail()) && corridas.get(i).getConcluida() == false){
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
						
					}
					
					
				}
			} else {
				for (int i = 0; i < corridas.size(); i++) {
					if(usuario.getTipoDeConta().equals("Admin")) {
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
					}
					
					else if(usuario.getTipoDeConta().equals("Mototaxista") && corridas.get(i).getMototaxistaQueReinvidincou() == null) {
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
					}
					else if(usuario.getTipoDeConta().equals("Mototaxista") && corridas.get(i).getReinvidicado() == false && !corridas.get(i).getPassageiro().getMotoristasBloqueados().contains(usuario) && corridas.get(i).getConcluida() == false) {
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
					}
					
					else if(corridas.get(i).getMototaxistaQueReinvidincou() != null && corridas.get(i).getMototaxistaQueReinvidincou().getEmail().equals(usuario.getEmail()) && corridas.get(i).getConcluida() == false){
						corridasArray.add(corridas.get(i));
						addLinha(modelo, corridas.get(i));
						
					}
				}
			}
		}
	}
	
	private class OuvinteTextField implements KeyListener {

		public void keyTyped(KeyEvent e) {
			String filtro = tfPesquisa.getText();
			char letra = e.getKeyChar();
			
			if (Character.isLetter(letra) || Character.isSpace(letra))
				filtro = filtro + letra;
			else
				e.consume();
			
			modelo.setRowCount(0);
			corridasArray.clear();
			
			CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
			
			String nome;
			filtro = filtro.toLowerCase();
			
			if (usuario instanceof Administrador) {
			
				for (Corrida corrida : cdi.getCorridas()) {
					nome = corrida.getPassageiro().getNome().toLowerCase();
					if (nome.contains(filtro)) {
						corridasArray.add(corrida);
						addLinha(modelo, corrida);
					}
				}
			}
		}

		public void keyPressed(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent e) {
			
		}
		
	}
}
	
	
	
	
	
	
	
//	public static void main(String[] args) {
//		Persistencia pe = new Persistencia();
//		CentralDeInformacoes cdi = pe.recuperarCentral();
//		
//		MinhaJanela.setPanel(new listarCorridas((Passageiro)cdi.recuperarUsuarioPeloEmail("Joao")));
//		MinhaJanela.setPanel(new listarCorridas((Mototaxista)cdi.recuperarUsuarioPeloEmail("mototaxista")));
//		MinhaJanela.setPanel(new listarCorridas((Administrador)cdi.recuperarUsuarioPeloEmail("admin")));
// }
//
//}