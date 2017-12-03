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

	// CODS DOS CURSOS QUE DETERMINADA DISCIPLINA PERTENCE
	private int[]               codigos;
	private DisciplinaCursoDAO  dao;
	private DisciplinaCurso     disciplinaCurso ;
	private static final String PAGE_LIST_DISCIPLINES  = "/list/listaDisciplinas";
	private static final String PAGE_NEW_DISCIPLINE    = "/list/novaDisciplina";
	private static final String PAGE_UPDATE_DISCIPLINE = "/list/alterarDisciplina";
	
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
			return PAGE_LIST_DISCIPLINES;
		else
			return PAGE_NEW_DISCIPLINE;
	}
	
	// ALTERAR
	public String alterar() { 
		
		dao = new DisciplinaCursoDAO();
		
		if (dao.alterar(cursosBean.getCurso().getCodigos(), disciplinaCurso.getCodDisciplina()))
			
			return PAGE_LIST_DISCIPLINES; 
		else
			return PAGE_UPDATE_DISCIPLINE;
	}
	
	// OBTEM TODOS OS CURSOS QUE UMA DISCIPLINA CONTEMPLA
	public int[] findCoursesThatBelongToDisciplineByDisciplineCode(int codigoDisciplina) {
		
		dao = new DisciplinaCursoDAO();
		
		cursosBean.getCurso().setCodigos(dao.findCoursesThatBelongToDisciplineByDisciplineCode(codigoDisciplina));
		
		return cursosBean.getCurso().getCodigos();
	}
	
	// OBTEM TODOS OS CURSOS QUE UMA DISCIPLINA CONTEMPLA
	public void justFindCoursesThatBelongToDisciplineByDisciplineCode(int codigoDisciplina) {
		
		dao = new DisciplinaCursoDAO();
		
		cursosBean.getCurso().setCodigos(dao.findCoursesThatBelongToDisciplineByDisciplineCode(codigoDisciplina));
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
