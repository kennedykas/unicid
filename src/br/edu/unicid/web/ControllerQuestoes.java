package br.edu.unicid.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.edu.unicid.bean.Questao;
import br.edu.unicid.dao.QuestaoDAO;

@ManagedBean(name="controllerQuestoes")
@SessionScoped
public class ControllerQuestoes {

	private QuestaoDAO dao;
	private Questao questao;
	private DataModel<Questao> listaQuestoes;
	private static final String FACE_MESSAGES_ID     = "messages";
	private static final String PAGE_LIST_QUESTIONS  = "/list/listaQuestoes";
	private static final String PAGE_NEW_QUESTION    = "/create/novaQuestao";
	private static final String PAGE_UPDATE_QUESTION = "/update/alterarQuestao";
	
	@ManagedProperty(value="#{controllerDisciplinas}")
	private ControllerDisciplinas disciplinaBean;
	
	public ControllerQuestoes() {}

	@PostConstruct
	public void init() {
		this.questao = new Questao();
	}
	
	// SAVE
	public String save(int codProfessor) {
		this.questao.setCodProfessor(codProfessor);
		this.questao.setDisciplina(this.disciplinaBean.getDisciplina().getCodigo());
		
		this.dao = new QuestaoDAO();
		
		return (this.dao.salvar(questao)) ? PAGE_LIST_QUESTIONS : PAGE_NEW_QUESTION;
	}
		
	// CHANGE
	public String alterar() {
		// SETA O CODIGO DA DISCIPLINA CASO OUTRA TENHA SIDO ESCOLHIDA
		this.questao.setDisciplina(this.disciplinaBean.getDisciplina().getCodigo());
		
		dao = new QuestaoDAO();

		return (dao.alterar(questao)) ? PAGE_LIST_QUESTIONS : PAGE_UPDATE_QUESTION;
	}
	
	// DELETE
	public void excluir() {
		dao = new QuestaoDAO();

		if(dao.excluir(questao.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage("Questão excluida!"));				
		}
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		this.questao = listaQuestoes.getRowData();
	}
	
	
	public void findQuestionsByTeachersCode(int codigoProfessor) {
		dao = new QuestaoDAO();

		List<Questao> lista = dao.todasQuestoes(codigoProfessor);
		this.listaQuestoes = new ListDataModel<>(lista);
	}
	
	public DataModel<Questao> findQuestionsByCode(String codigosQuestoes) {
		int[] codigos = Stream.of(codigosQuestoes.split(",")).mapToInt(Integer::parseInt).toArray();
		dao = new QuestaoDAO();
		List<Questao> questoes = new ArrayList<>();
		
		for(int cod : codigos) 
			questoes.add(this.dao.getQuestao(cod));
		
		this.listaQuestoes = new ListDataModel<>(questoes);
		return this.listaQuestoes;
	}

	// GETTERS AND SETTERS
	public Questao getQuestao() {
		return questao;
	}
	public void setQuestao(Questao questao) {
		this.questao = questao;
	}
	public DataModel<Questao> getListaQuestoes() {
		return listaQuestoes;
	}
	public ControllerDisciplinas getDisciplinaBean() {
		return disciplinaBean;
	}
	public void setDisciplinaBean(ControllerDisciplinas disciplinaBean) {
		this.disciplinaBean = disciplinaBean;
	}
}
