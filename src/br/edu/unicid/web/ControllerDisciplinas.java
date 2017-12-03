package br.edu.unicid.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import br.edu.unicid.bean.Disciplina;
import br.edu.unicid.dao.DisciplinaDAO;

@ManagedBean(name="controllerDisciplinas")
@SessionScoped
public class ControllerDisciplinas {

	private DisciplinaDAO         dao;
	private Disciplina            disciplina;
	private DataModel<Disciplina> listaDisciplinas;
	private static final String   FACE_MESSAGES_ID        = "messages";
	private static final String   PAGE_LIST_DISCIPLINES   = "/list/listaDisciplinas";
	private static final String   PAGE_UPDATE_DISCIPLINES = "/list/alterarDisciplina";
	
	@ManagedProperty(value="#{controllerCursos}")
	private ControllerCursos cursoBean;
	
	@ManagedProperty(value="#{controllerProfessores}")
	private ControllerProfessores professorBean;
	
	@ManagedProperty(value="#{controllerDisciplinaCurso}")
	private ControllerDisciplinaCurso disciplinaCursoBean;
	
	public ControllerDisciplinas() {}

	@PostConstruct
	public void init() {
		this.disciplina = new Disciplina();
	}
	
	// SAVE
	public void saveDisciplineAndPrepareDisciplineCourse() {
		this.disciplina.setCodProfessor(this.professorBean.getProfessor().getCodigo());
		this.dao = new DisciplinaDAO();

		int codigoDisciplina = this.dao.salvar(this.disciplina);
		
		if(codigoDisciplina > 0) {
			this.disciplinaCursoBean.getDisciplinaCurso().setCodDisciplina(codigoDisciplina);
			this.disciplinaCursoBean.getDisciplinaCurso()
				.setCoursesThatDisciplineBelongTo(this.cursoBean.getCurso().getCodigos());
		}
	}
		
	// CHANGE
	public String alterar() {
		
		dao = new DisciplinaDAO();

		disciplinaCursoBean.getDisciplinaCurso().setCodDisciplina(disciplina.getCodigo()); 
		disciplinaCursoBean.getDisciplinaCurso()
			.setCoursesThatDisciplineBelongTo(this.cursoBean.getCurso().getCodigos());
		
		if(dao.alterar(disciplina))
			return PAGE_LIST_DISCIPLINES;
		return PAGE_UPDATE_DISCIPLINES;
	}
	
	// DELETE
	public void excluir() {
		this.dao = new DisciplinaDAO();

		if(this.dao.excluir(this.disciplina.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage("Disciplina Excluida!"));				
		}
	}
	
	// GET NOME DA DISCIPLINA ATRAVES DO CODIGO 
	public String nomeDisciplina(int codigo) {
		this.dao = new DisciplinaDAO(); 
		return this.dao.getNomeDisciplina(codigo);
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		this.disciplina = this.listaDisciplinas.getRowData();
	}

	// GET ALL DISCIPLINAS WHERE COD.PROF. LIKE PROF.
	public void findDisciplinesByTeachersCode(int codProfessor) {
		this.dao = new DisciplinaDAO();
		List<Disciplina> lista = this.dao.todasDisciplinas(codProfessor);
		this.listaDisciplinas = new ListDataModel<>(lista);
	}
	
	// GET COD. AND NAME DISCIPLINA TO FILL SELECTS 	
	public List<SelectItem> disciplinas(int codProfessor) {
		this.dao = new DisciplinaDAO();
		
		List<SelectItem> items = new ArrayList<>();
		List<Disciplina> disciplinaList = this.dao.disciplinasPeloCodProfessor(codProfessor);

		for(Disciplina disciplina: disciplinaList)
	    	items.add(new SelectItem(disciplina.getCodigo(), disciplina.getNomeDisciplina()));

		return items;
	}

	// GETTERS AND SETTERS
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	public DataModel<Disciplina> getListaDisciplinas() {
		return listaDisciplinas;
	}
	public ControllerCursos getCursoBean() {
		return cursoBean;
	}
	public void setCursoBean(ControllerCursos cursoBean) {
		this.cursoBean = cursoBean;
	}
	public ControllerProfessores getProfessorBean() {
		return professorBean;
	}
	public void setProfessorBean(ControllerProfessores professorBean) {
		this.professorBean = professorBean;
	}
	public ControllerDisciplinaCurso getDisciplinaCursoBean() {
		return disciplinaCursoBean;
	}
	public void setDisciplinaCursoBean(ControllerDisciplinaCurso disciplinaCursoBean) {
		this.disciplinaCursoBean = disciplinaCursoBean;
	}
}
