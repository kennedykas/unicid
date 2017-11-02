package br.edu.unicid.web;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.edu.unicid.bean.DisciplinaCurso;
import br.edu.unicid.dao.DisciplinaCursoDAO;

@ManagedBean(name="controllerDisciplinaCurso")
@SessionScoped
public class ControllerDisciplinaCurso {

	private DisciplinaCursoDAO dao;
	private DisciplinaCurso disciplinaCurso ;
	
	// BEAN CURSOS
	@ManagedProperty(value="#{controllerCursos}")
	private ControllerCursos cursosBean;
	
	public ControllerDisciplinaCurso() {}
		
	@PostConstruct
	public void init() {
		this.disciplinaCurso = new DisciplinaCurso();
	}
	
	// SAVE
	public String save() {
		this.dao = new DisciplinaCursoDAO();
		if(this.dao.salvar(this.disciplinaCurso))
			return "/list/listaDisciplinas";
		else
			return "/list/novaDisciplina";
	}
	
	// ALTERAR
	public String alterar() { 
		
		this.dao = new DisciplinaCursoDAO();
		
		if (this.dao.alterar(
				this.cursosBean.getCurso().getCodigos(), 
				this.disciplinaCurso.getCodDisciplina()))
			
			return "/list/listaDisciplinas"; 
		else
			return "/list/alterarDisciplina";
	}
	
	// OBTEM TODOS OS CURSOS QUE UMA DISCIPLINA PERTENCE
	public void obterCursosDisciplinaPertence(int codigoDisciplina) {
		
		this.dao = new DisciplinaCursoDAO();
		
		// SETA ESSES CODIGOS NO BEAN DE CURSOS 
		this.cursosBean.getCurso().setCodigos(
				this.dao.cursosPeloCodigoDisciplina(codigoDisciplina)
		);
		
	}
	
	// RETURN DISCIPLINAS BY COD COURSE
	public ArrayList<Integer> obterCodDisciplinaPeloCodCurso(int codCurso) {
		
		this.dao = new DisciplinaCursoDAO();
		
		return this.dao.obterCodDisciplinaPeloCodCurso(codCurso);
	}
		
	// EXCLUIR
	public boolean excluir(int codigo) {
		this.dao = new DisciplinaCursoDAO();
		return dao.excluir(codigo); 
	}

	// GETTERS AND SETTERS
	public DisciplinaCurso getDisciplinaCurso() {
		return disciplinaCurso;
	}
	public void setDisciplinaCurso(DisciplinaCurso disciplinaCurso) {
		this.disciplinaCurso = disciplinaCurso;
	}
	public ControllerCursos getCursosBean() {
		return cursosBean;
	}
	public void setCursosBean(ControllerCursos cursosBean) {
		this.cursosBean = cursosBean;
	}
}
