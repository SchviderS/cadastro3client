package br.univel.tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.univel.main.Tipos;
import br.univel.model.Curso;
import br.univel.model.Disciplina;

public class ModeloTabela {
	
	private List<?> lista;
	private Tipos tipo;

	public ModeloTabela(Tipos tipo, List<?> lista){
		this.lista = lista;
		this.tipo = tipo;
	}

	public TableModel create(){
		if(this.tipo == Tipos.CURSO)
			return new ModeloCurso(this.lista);
		else
			return new ModeloDisciplina(this.lista);
	}
}
