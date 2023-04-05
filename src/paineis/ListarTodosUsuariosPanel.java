package paineis;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import janelas.MinhaJanela;
import ouvintes.OuvinteMouse;
import projetoPOO.Administrador;
import projetoPOO.CentralDeInformacoes;
import projetoPOO.Persistencia;
import projetoPOO.Usuario;


public class ListarTodosUsuariosPanel extends JPanel {
	
	private DefaultTableModel modelo;
	private JTable tabela;
	private JTextField tfCampo;
	private Administrador admin;
	private ArrayList<Usuario> usuariosArray;
	private JComboBox<String> comboBox;
	
	public ListarTodosUsuariosPanel(Administrador admin) {
		comboBox = new JComboBox<String>();
		usuariosArray = new ArrayList<Usuario>();
		
		this.admin = admin;
		setSize(640,360);
		setLayout(null);
		addTabela();
		addTextFIeld();
		addButton();
		addComboBox();
	}

	private void addComboBox() {
		String[] opc = {"Todos", "Passageiro", "Mototaxista"};
		comboBox = new JComboBox<String>(opc);
		comboBox.setBounds(270, 20, 100, 30);
		comboBox.addActionListener(new OuvinteComboBox());
		add(comboBox);
		
	}

	private void addButton() {
		JButton btndetalhes = new JButton("Detalhes do usuário");
		btndetalhes.setBounds(120, 265, 170, 40);
		btndetalhes.addActionListener(new OuvinteButton());
		btndetalhes.setBounds(120, 265, 170, 40);
		btndetalhes.setBackground(Color.BLACK);
		btndetalhes.setForeground(Color.WHITE);
		btndetalhes.addMouseListener(new OuvinteMouse());
		add(btndetalhes);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(370, 265, 150, 40);
		btnCancelar.addActionListener(new OuvinteButton());
		btnCancelar.setBackground(Color.BLACK);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.addMouseListener(new OuvinteMouse());
		
		add(btnCancelar);
		
	}

	private void addTextFIeld() {
		tfCampo = new JTextField();
		
		tfCampo.setBounds(120, 70, 400, 30);
		tfCampo.addKeyListener(new OuvinteTextField());
		
		add(tfCampo);
		
	}

	private void addTabela() {
		modelo = new DefaultTableModel();
		
		modelo.addColumn("Nome");
		modelo.addColumn("Email");
		modelo.addColumn("Perfil");
		
		CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
		
		for (Usuario usuario : cdi.getTodosOsUsuarios()) {
			usuariosArray.add(usuario);
			addLinha(modelo, usuario);
			
		}
		
		tabela = new JTable(modelo);
		
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane painel = new JScrollPane(tabela);
		
		painel.setBounds(120, 120, 400, 130);
		
		add(painel);
		
	}
	
	private void addLinha(DefaultTableModel modelo2, Usuario u) {
		Object[] linha = new Object[3];
		
		linha[0] = u.getNome();
		
		linha[1] = u.getEmail();
		
		linha[2] = u.getTipoDeConta();
		
		modelo2.addRow(linha);
		
	}
	
	private class OuvinteButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Persistencia pe = new Persistencia();
			CentralDeInformacoes cdi = pe.recuperarCentral();
			
			Usuario user = null;
			
			String opc = (String)comboBox.getSelectedItem();
			
	
			
			switch (e.getActionCommand()) {
				case "Detalhes do usuário":
					if(tabela.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(MinhaJanela.getInstance(), "Selecione alguma coisa!");
						break;
					}
						
					
					if(opc.equals("Todos"))
						user = usuariosArray.get(tabela.getSelectedRow());
					
					if(opc.equals("Passageiro"))
						user = usuariosArray.get(tabela.getSelectedRow());
					else if(opc.equals("Mototaxista"))
						user = usuariosArray.get(tabela.getSelectedRow());
		
					
					MinhaJanela.setPanel(new EditarPerfilPanel(admin, user));
				
					
					//o tabela.getSelectedRow() pega o numero da linha selecionada e retorna -1 se nenhuma for selecionada
					break;
				case "Cancelar":
					MinhaJanela.setPanel(new PerfilUsuario(admin));
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
			usuariosArray.clear();
			
			for (Usuario usuario : cdi.getTodosOsUsuarios()) {
				if (opc.equals(usuario.getTipoDeConta())) {
					usuariosArray.add(usuario);
					addLinha(modelo, usuario);
				}
				else if (opc.equals("Todos")) {
					usuariosArray.add(usuario);
					addLinha(modelo, usuario);
				}
			}
		}
	}
	
	private class OuvinteTextField implements KeyListener {

		public void keyTyped(KeyEvent e) {
			String filtro = tfCampo.getText();
			
			char letra = e.getKeyChar();
			
			if (Character.isLetter(letra) || Character.isSpace(letra))
				filtro = filtro + letra;
			else
				e.consume();
			
			modelo.setRowCount(0);
			usuariosArray.clear();
			
			CentralDeInformacoes cdi = new Persistencia().recuperarCentral();
			
			String nome;
			filtro = filtro.toLowerCase();
			
			for (Usuario usuario : cdi.getTodosOsUsuarios()) {
				nome = usuario.getNome().toLowerCase();
				if (nome.contains(filtro)) {
					usuariosArray.add(usuario);
					addLinha(modelo, usuario);
				}
			}
			
		}

		public void keyPressed(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent e) {
			
		}
		
	}

}
