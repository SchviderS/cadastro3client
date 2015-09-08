package br.univel.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import br.univel.dao.CursoDAO;
import br.univel.dao.DisciplinaDAO;
import br.univel.model.Curso;
import br.univel.model.Disciplina;
import br.univel.tableModel.ModeloTabela;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tela extends JFrame {

	private JPanel contentPane;
	private JTextField txtCurso;
	private JTable tableCurso;
	private JTextField txtDisciplina;
	private JTable tableDisciplina;
	
	private Curso cursoSelecionado;
	private Disciplina disciplinaSelecionada;
	
	private boolean cursoSelected = false;
	private boolean discSelected = false;
	private JButton btnSalvarDisciplina;
	private JButton btnSalvarCurso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if(tabbedPane.getSelectedIndex() == 0)
					atualizarTableCurso();
				if(tabbedPane.getSelectedIndex() == 1)
					atualizarTableDisciplina();
			}
		});
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Cursos", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		
		tableCurso = new JTable();
		tableCurso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int id = (Integer) tableCurso.getModel().getValueAt(tableCurso.getSelectedRow(), 0);
				selecionarCurso(id);
			}
		});
		scrollPane.setViewportView(tableCurso);
		
		txtCurso = new JTextField();
		GridBagConstraints gbc_txtCurso = new GridBagConstraints();
		gbc_txtCurso.insets = new Insets(0, 0, 5, 5);
		gbc_txtCurso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurso.gridx = 1;
		gbc_txtCurso.gridy = 1;
		panel.add(txtCurso, gbc_txtCurso);
		txtCurso.setColumns(10);
		
		JButton btnNovoCurso = new JButton("Novo Curso");
		btnNovoCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				novoCurso();
			}
		});
		GridBagConstraints gbc_btnNovoCurso = new GridBagConstraints();
		gbc_btnNovoCurso.insets = new Insets(0, 0, 0, 5);
		gbc_btnNovoCurso.gridx = 0;
		gbc_btnNovoCurso.gridy = 2;
		panel.add(btnNovoCurso, gbc_btnNovoCurso);
		
		btnSalvarCurso = new JButton("Salvar Curso");
		btnSalvarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cursoSelected)
					atualizarCurso(cursoSelecionado);
				else
					cadastrarCurso();
			}
		});
		GridBagConstraints gbc_btnSalvarCurso = new GridBagConstraints();
		gbc_btnSalvarCurso.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalvarCurso.gridx = 1;
		gbc_btnSalvarCurso.gridy = 2;
		panel.add(btnSalvarCurso, gbc_btnSalvarCurso);
		
		JButton btnRemoverCurso = new JButton("Remover Curso");
		btnRemoverCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removerCurso(cursoSelecionado);
			}
		});
		GridBagConstraints gbc_btnRemoverCurso = new GridBagConstraints();
		gbc_btnRemoverCurso.gridx = 2;
		gbc_btnRemoverCurso.gridy = 2;
		panel.add(btnRemoverCurso, gbc_btnRemoverCurso);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Disciplinas", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 3;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		
		tableDisciplina = new JTable();
		tableDisciplina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int id = (Integer) tableDisciplina.getModel().getValueAt(tableDisciplina.getSelectedRow(), 0);
				selecionarDisciplina(id);
			}
		});
		scrollPane_1.setViewportView(tableDisciplina);
		
		txtDisciplina = new JTextField();
		GridBagConstraints gbc_txtDisciplina = new GridBagConstraints();
		gbc_txtDisciplina.insets = new Insets(0, 0, 5, 5);
		gbc_txtDisciplina.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDisciplina.gridx = 1;
		gbc_txtDisciplina.gridy = 1;
		panel_1.add(txtDisciplina, gbc_txtDisciplina);
		txtDisciplina.setColumns(10);
		
		JButton btnNovaDisciplina = new JButton("Nova Disciplina");
		btnNovaDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				novaDisciplina();
			}
		});
		GridBagConstraints gbc_btnNovaDisciplina = new GridBagConstraints();
		gbc_btnNovaDisciplina.insets = new Insets(0, 0, 0, 5);
		gbc_btnNovaDisciplina.gridx = 0;
		gbc_btnNovaDisciplina.gridy = 2;
		panel_1.add(btnNovaDisciplina, gbc_btnNovaDisciplina);
		
		btnSalvarDisciplina = new JButton("Salvar Disciplina");
		btnSalvarDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(discSelected)
					atualizarDisciplina(disciplinaSelecionada);
				else
					cadastrarDisciplina();
			}
		});
		GridBagConstraints gbc_btnSalvarDisciplina = new GridBagConstraints();
		gbc_btnSalvarDisciplina.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalvarDisciplina.gridx = 1;
		gbc_btnSalvarDisciplina.gridy = 2;
		panel_1.add(btnSalvarDisciplina, gbc_btnSalvarDisciplina);
		
		JButton btnRemoverDisciplina = new JButton("Remover Disciplina");
		btnRemoverDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerDisciplina(disciplinaSelecionada);
			}
		});
		GridBagConstraints gbc_btnRemoverDisciplina = new GridBagConstraints();
		gbc_btnRemoverDisciplina.gridx = 2;
		gbc_btnRemoverDisciplina.gridy = 2;
		panel_1.add(btnRemoverDisciplina, gbc_btnRemoverDisciplina);
		
		carregarTabelas();
	}
	
	protected void removerCurso(Curso cursoSelecionado) {
		CursoDAO dao = new CursoDAO();
		if(dao.remover(cursoSelecionado))
			JOptionPane.showMessageDialog(this, "Removido com sucesso!");
		else
			JOptionPane.showMessageDialog(this, "Falha ao remover...");
		novoCurso();
		atualizarTableCurso();
	}

	protected void atualizarCurso(Curso cursoSelecionado) {
		CursoDAO dao = new CursoDAO();
		cursoSelecionado.setNome(txtCurso.getText().trim());
		if(dao.atualizar(cursoSelecionado))
			JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
		else
			JOptionPane.showMessageDialog(this, "Falha ao atualizar...");
		novoCurso();
		atualizarTableCurso();
	}

	protected void cadastrarCurso() {
		Curso c = new Curso();
		c.setNome(txtCurso.getText().trim());
		CursoDAO dao = new CursoDAO(); 
		if(dao.cadastrar(c))
			JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!");
		else
			JOptionPane.showMessageDialog(this, "Falha ao cadastrar...");
		novoCurso();
		atualizarTableCurso();
	}

	protected void selecionarDisciplina(int id) {
		disciplinaSelecionada = new DisciplinaDAO().getDisciplina(id);
		txtDisciplina.setText(disciplinaSelecionada.getNome());
		discSelected = true;
		btnSalvarDisciplina.setText("Atualizar Disciplina");
	}

	protected void selecionarCurso(int id) {
		cursoSelecionado = new CursoDAO().getCurso(id);
		txtCurso.setText(cursoSelecionado.getNome());
		cursoSelected = true;
		btnSalvarCurso.setText("Atualizar Curso");
	}

	protected void carregarTabelas(){
		atualizarTableCurso();
		atualizarTableDisciplina();
	}

	protected void atualizarTableDisciplina() {
		TableModel modelo = new ModeloTabela(Tipos.DISCIPLINA, new DisciplinaDAO().getDisciplinas()).create();
		tableDisciplina.setModel((TableModel) modelo);
	}

	protected void atualizarTableCurso() {
		TableModel modelo = new ModeloTabela(Tipos.CURSO, new CursoDAO().getCursos()).create();
		tableCurso.setModel((TableModel) modelo);
	}
	
	protected void limparCampos(){
		novoCurso();
		novaDisciplina();
	}
	
	protected void novoCurso(){
		cursoSelected = false;
		btnSalvarCurso.setText("Salvar Curso");
		txtCurso.setText("");
		cursoSelecionado = null;
	}
	
	protected void novaDisciplina(){
		discSelected = false;
		btnSalvarDisciplina.setText("Salvar Disciplina");
		disciplinaSelecionada = null;
		txtDisciplina.setText("");
	}
	
	protected void removerDisciplina(Disciplina disciplinaSelecionada) {
		DisciplinaDAO dao = new DisciplinaDAO();
		if(dao.remover(disciplinaSelecionada))
			JOptionPane.showMessageDialog(this, "Removida com sucesso!");
		else
			JOptionPane.showMessageDialog(this, "Falha ao remover...");
		novaDisciplina();
		atualizarTableDisciplina();
	}

	protected void atualizarDisciplina(Disciplina disciplinaSelecionada2) {
		DisciplinaDAO dao = new DisciplinaDAO();
		disciplinaSelecionada2.setNome(txtDisciplina.getText().trim());
		if(dao.atualizar(disciplinaSelecionada2))
			JOptionPane.showMessageDialog(this, "Atualizada com sucesso!");
		else
			JOptionPane.showMessageDialog(this, "Falha ao atualizar...");
		novaDisciplina();
		atualizarTableDisciplina();
	}

	protected void cadastrarDisciplina() {
		Disciplina d = new Disciplina();
		d.setNome(txtDisciplina.getText().trim());
		DisciplinaDAO dao = new DisciplinaDAO(); 
		if(dao.cadastrar(d))
			JOptionPane.showMessageDialog(this, "Cadastrada com sucesso!");
		else
			JOptionPane.showMessageDialog(this, "Falha ao cadastrar...");
		novaDisciplina();
		atualizarTableDisciplina();
	}
}
