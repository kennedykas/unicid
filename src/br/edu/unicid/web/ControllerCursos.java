package br.edu.unicid.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.edu.unicid.bean.Curso;
import br.edu.unicid.dao.CursoDAO;

@ManagedBean(name="controllerCursos")
@SessionScoped
public class ControllerCursos {

	private Curso curso;
	private CursoDAO dao;

	public ControllerCursos() {}
	
	@PostConstruct
	public void init() {
		this.curso = new Curso();
	}
	
	// LISTA DE CURSOS DO TIPO SELECTITEM
	public List<SelectItem> cursos() {
		List<SelectItem> itens = new ArrayList<SelectItem>();
		this.dao = new CursoDAO();
		List<Curso> cursoList = this.dao.cursos();

		for(Curso curso: cursoList)
	    	itens.add(new SelectItem(curso.getCodigo(), curso.getNome()));
	    
		return itens;
	}
	
	// NOMES CURSOS
	public String nomesCursos(String codsCursos) throws Exception {
		try {
			this.dao = new CursoDAO(); 
			return this.dao.nomesCursos(codsCursos);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "Nenhum curso selecionado :(";
	}
	
	// GETTERS AND SETTERS
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}
