package br.edu.unicid.web;

import java.util.ArrayList;
import java.util.List;

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
	private DisciplinaCurso    disciplinaCurso ;
	private int[] codigos; // CODS DOS CURSOS QUE DETERMINADA DISCIPLINA PERTENCE
		
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
		if(this.dao.salvar(this.disciplinaCurso, this.cursosBean.getCurso().getCodigos()))
			return "/list/listaDisciplinas";
		else
			return "/list/novaDisciplina";
	}
	
	// ALTERAR
	public String alterar() { 
		
		this.dao = new DisciplinaCursoDAO();
		
		if (this.dao.alterar(codigos, this.disciplinaCurso.getCodDisciplina()))
			
			return "/list/listaDisciplinas"; 
		else
			return "/list/alterarDisciplina";
	}
	
	// OBTEM TODOS OS CURSOS QUE UMA DISCIPLINA CONTEMPLA
	public int[] findCoursesThatBelongToDisciplineByDisciplineCode(int codigoDisciplina) {
		
		this.dao = new DisciplinaCursoDAO();
		
		return this.dao.findCoursesThatBelongToDisciplineByDisciplineCode(codigoDisciplina);
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
	public int[] getCodigos() {
		return codigos;
	}
	public void setCodigos(int[] codigos) {
		this.codigos = codigos;
	}
}
