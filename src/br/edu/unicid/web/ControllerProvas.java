package br.edu.unicid.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.edu.unicid.bean.Prova;
import br.edu.unicid.constants.Constants;
import br.edu.unicid.dao.ProvaDAO;

@ManagedBean(name="controllerProvas")
@SessionScoped
public class ControllerProvas {
	
	private Prova                      prova;
	private String                     selectedAnswer;
	private ProvaDAO                   dao;
	private DataModel<Prova>           listaProvas;
	private ArrayList<String>          questions    = new ArrayList<>(); 
	private HashMap  <Integer, String> rightAnswers = new HashMap<>();
	private HashMap  <Integer, String> wrongAnswers = new HashMap<>();

	@ManagedProperty(value="#{controllerDisciplinas}")
	private ControllerDisciplinas disciplinaBean;
	
	@ManagedProperty(value="#{controllerProfessores}")
	private ControllerProfessores professorBean;
	
	@ManagedProperty(value="#{controllerQuestoes}")
	private ControllerQuestoes questaoBean;

	public final SimpleDateFormat year   = new SimpleDateFormat("yyyy");
	public final SimpleDateFormat month  = new SimpleDateFormat("MM");
	public final SimpleDateFormat day    = new SimpleDateFormat("dd");
	public final SimpleDateFormat hour   = new SimpleDateFormat("HH");
	public final SimpleDateFormat minute = new SimpleDateFormat("mm");
	
	public static final Double ROUNDING_MARGIN      = 0.11; 
	public static final int    PRECISION_SCALE      = 2;
	public static final int    DEZ_MINUTOS          = 10;
	public static final int    UMA_HORA_ADIANTADO   = 1;
	public static final int    FALTANDO_DEZ_MINUTOS = -50;
	
	public ControllerProvas() {}

	@PostConstruct
	public void init() {
		this.prova = new Prova();
		this.questions = new ArrayList<>();
		this.wrongAnswers.clear();
		this.selectedAnswer = "";
	}
	
	public String save() {
		this.prova.setCodProfessor(this.professorBean.getProfessor().getCodigo());
		this.prova.setCodDisciplina(this.disciplinaBean.getDisciplina().getCodigo());
		
		calcEachQuestionValue();
	
		this.dao = new ProvaDAO();
		
		if(this.dao.salvar(this.prova))
			this.questions.clear();

		return Constants.PAGE_LIST_TESTS_PROF;
	}

	/**
	 * Calcula o valor de cada questao para gerar a prova
	 */
	public void calcEachQuestionValue() {
		
		BigDecimal testValue       = BigDecimal.valueOf(prova.getValorTotal());
		BigDecimal questionsNumber;
		
		if(!questions.isEmpty())
			
			questionsNumber = BigDecimal.valueOf(questions.size());
		
		else // when we are updating a test we dont have the number of questions
			
			questionsNumber = BigDecimal.valueOf((prova.getQuestoes().split(",")).length);
		
		this.prova.setValorQuestoes(
				
				(testValue.divide(questionsNumber, PRECISION_SCALE, RoundingMode.HALF_UP).floatValue()));
	}
		
	public void excluir() {
		this.dao = new ProvaDAO();

		if(this.dao.excluir(this.prova.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage("Prova excluida!"));				
		}
	}
	
	public void manageSelectedQuestions(String codigoQuestao) {
		
		if(this.questions.contains(codigoQuestao))  			
			this.questions.remove(codigoQuestao);
		else 
			this.questions.add(codigoQuestao);
		
		this.prova.setQuestoes(String.join(",", this.questions));
	}
	
	// ARMAZENA A RESPOSTA SELECIONADA E VERIFICA SE ESTA CORRETA OU INCORRETA
	public void setResposta(int codQuestao) {
		// RESPOSTA CORRETA
		if(this.selectedAnswer.equalsIgnoreCase(this.questaoBean.getQuestao().getAlternativaCorreta())) {
	
			if(this.wrongAnswers.containsKey(codQuestao))  
				this.wrongAnswers.remove(codQuestao);
	
			if(!this.rightAnswers.containsKey(codQuestao)) 
				this.rightAnswers.put(codQuestao, this.selectedAnswer);
			
		// RESPOSTA INCORRETA
		} else {
			// CASO O USER TENHA ERRADO NOVAMENTE A MESMA QUESTAO APENAS ATUALIZA A RESPOSTA SELECIONADA
			if(this.wrongAnswers.containsKey(codQuestao))
				this.wrongAnswers.replace(codQuestao, this.selectedAnswer); 
			else
				// PRIMEIRA VEZ QUE ELE ERRA A RESPOSTA ENTAO A RESPOSTA SELECIONADA E ARMAZENADA
				this.wrongAnswers.put(codQuestao, this.selectedAnswer);
	
			// User havia acertado, mas depois selecionou resposta incorreta
			if(this.rightAnswers.containsKey(codQuestao))
				this.rightAnswers.remove(codQuestao);
		}
			
		calcularNota();		
	}
	
	public void calcularNota() {
				
		float nota = this.rightAnswers.size() * this.prova.getValorQuestoes();
		if((this.prova.getValorTotal() - nota) < ROUNDING_MARGIN) 
			this.prova.setNota(this.prova.getValorTotal());
		else
			this.prova.setNota(nota);
		
		this.prova.setNota(nota);
	}
	
	/**
	 * Retorna um boolean indicando se o user errou ou nao a questao
	 * @param codQuestao
	 * @param alternativaAtual
	 * @return Boolean
	 */
	public boolean answeredWrong(int codQuestao, String alternativaAtual) {
		
		if(this.wrongAnswers.containsKey(codQuestao) && 
				this.wrongAnswers.get(codQuestao).equalsIgnoreCase(alternativaAtual)) 
				
			return Boolean.TRUE;
		
		return Boolean.FALSE;
	}
	
	// NUMERO DE QUESTOES EM UMA STRING DE QUESTOES
	public int numberQuestions(String questoes) {
		return questoes.split(",").length;
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		this.prova = this.listaProvas.getRowData();
	}

	public String alterarInformacoesDaProva() {
		
		prova.setCodDisciplina(this.disciplinaBean.getDisciplina().getCodigo());
		
		calcEachQuestionValue();
		
		dao = new ProvaDAO();

		if(dao.alterarInformacoes(prova))
		
			return Constants.PAGE_LIST_TESTS_PROF;
		
		else
			
			return Constants.PAGE_UPDATE_TEST;
	}
	
	public boolean studentCanTakeTestNow(String testDate, boolean allowAfterDate, boolean allowMultipleAttempts, boolean jaRealizou) {
		
		if(allowMultipleAttempts)
			return Boolean.FALSE;
		
		if(jaRealizou || testDate.isEmpty())  
			return Boolean.TRUE;
		
		else {
			// ANO, MES, DIA PROVA
			String[] dataProva = (testDate.split("-"))[0].split("/");
			// HORA, MINUTO PROVA
			String[] horaMinutoProva = (testDate.split("-"))[1].split(":");
			
			Calendar calendar = Calendar.getInstance();
					
			// OBTEM DATA ATUAL
			String todayYear   = this.year.  format(calendar.getTime());
			String todayMonth  = this.month. format(calendar.getTime());
			String todayDay    = this.day.   format(calendar.getTime());
			String todayHour   = this.hour.  format(calendar.getTime());
			String todayMinute = this.minute.format(calendar.getTime());
						
			// TRANSFORMA DATA PROVA EM LONG YYYY:MM:DD
			long yearMonthDayTest = Integer.parseInt(dataProva[2] + dataProva[1] + dataProva[0]);
			// TRANFORMA DATA ATUAL EM LONG YYYY:MM:DD
			long yearMonthDayNow = Integer.parseInt(todayYear + todayMonth + todayDay);

			if(yearMonthDayTest == yearMonthDayNow) {
				
				int diferencaHoras   = Integer.parseInt(horaMinutoProva[0]) - Integer.parseInt(todayHour); 
				int diferencaMinutos = Integer.parseInt(horaMinutoProva[1]) - Integer.parseInt(todayMinute); 
				
				if(allowAfterDate || (diferencaHoras == 0 && (diferencaMinutos >= 0 && diferencaMinutos <= DEZ_MINUTOS))) 
					return Boolean.FALSE;
			
				else
					return (diferencaHoras == UMA_HORA_ADIANTADO && diferencaMinutos <= FALTANDO_DEZ_MINUTOS) ? Boolean.FALSE : Boolean.TRUE;
			
			// DATA JA PASSOU MAS ESTA PERMITIDO
			} else
				return (yearMonthDayTest < yearMonthDayNow) && allowAfterDate ? Boolean.FALSE : Boolean.TRUE;
		}
	}
	
	
	public void findTestsByTeachersCode() {

		this.dao = new ProvaDAO();
		List<Prova> lista = this.dao.todasProvas(this.professorBean.getProfessor().getCodigo());
		this.listaProvas = new ListDataModel<>(lista);
		
		init(); // Impede que os dados da ultima prova encontrada aparecam nos formularios
	}
	
	// GET LIST OF PROVAS BY COD CURSO
	public DataModel<Prova> obterProvasPeloCodDisciplina(ArrayList<Integer> codDisciplina) {
		this.dao = new ProvaDAO();
		
		List<Prova> provas = this.dao.obterProvasPeloCodDisciplina(codDisciplina);
		
		this.listaProvas = new ListDataModel<Prova>(provas);
		
		init(); // Impede que os dados da ultima prova encontrada aparecam nos formularios
		
		return this.listaProvas;
	}
	
	// EXIBE UMA PROVA PARA O ALUNO
	public String showProva() {
		
		this.dao = new ProvaDAO();

		setProva(this.dao.getProva(this.prova.getCodigo()));
		
		return (this.prova != null) ? Constants.PAGE_SHOW_TEST : Constants.PAGE_LIST_TESTS_STUDENT;
	}
	
	// OBTEM TITULO DA PROVA
	public String getTitulo(int codProva) {
		this.dao = new ProvaDAO();
		return this.dao.getTitulo(codProva);
	}
	
	// OBTEM VALOR PROVA
	public double getValorTotal(int codProva) {
		this.dao = new ProvaDAO();
		return this.dao.getValorTotal(codProva);
	}
	
	// GETTERS AND SETTERS
	public Prova getProva() {
		return prova;
	}
	public void setProva(Prova prova) {
		this.prova = prova;
	}
	public String getSelectedAnswer() {
		return selectedAnswer;
	}
	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
	public ControllerDisciplinas getDisciplinaBean() {
		return disciplinaBean;
	}
	public void setDisciplinaBean(ControllerDisciplinas disciplinaBean) {
		this.disciplinaBean = disciplinaBean;
	}
	public ControllerProfessores getProfessorBean() {
		return professorBean;
	}
	public void setProfessorBean(ControllerProfessores professorBean) {
		this.professorBean = professorBean;
	}
	public ControllerQuestoes getQuestaoBean() {
		return questaoBean;
	}
	public void setQuestaoBean(ControllerQuestoes questaoBean) {
		this.questaoBean = questaoBean;
	}
	public DataModel<Prova> getListaProvas() {
		return listaProvas;
	}
}
