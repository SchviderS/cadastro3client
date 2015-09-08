package br.univel.tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.univel.model.Disciplina;

public class ModeloDisciplina extends AbstractTableModel{

	private List<Disciplina> lista;
	
	public ModeloDisciplina(List<?> lista){
		this.lista = (List<Disciplina>) lista;
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return lista.size();
	}

	public Object getValueAt(int row, int col) {
		switch(col){
		case 0:
			return lista.get(row).getId();
		case 1:
			return lista.get(row).getNome();
		default:
			return "Erro curso";
		}
	}
	
	@Override
	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "ID";
			
		case 1:
			return "Disciplina";

		default:
			return "Erro";
		}
	}
}
