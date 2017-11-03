package br.edu.unicid.web;

import java.io.IOException;
import java.math.BigDecimal;
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
import br.edu.unicid.dao.ProvaDAO;

@ManagedBean(name="controllerProvas")
@SessionScoped
public class ControllerProvas {
	
	private ProvaDAO dao;
	private Prova prova;
	
	// STRING COM A ALTERNATIVA QUE O ALUNO SELECIONOU (A , B, C, D ou E)
	private String selectedAnswer;

	// DATAMODEL DE PROVAS
	private DataModel<Prova> listaProva;
	
	// LIST COM AS QUESTOES SELECIONADAS PARA FORMAR UMA PROVA
	private ArrayList<String> questions = new ArrayList<String>(); 
	
	// LIST COM AS RESPOSTAS CORRETAS QUE O USER SELECIONOU
	private HashMap<Integer, String> rightAnswers = new HashMap<Integer, String>();
	
	// LIST COM AS RESPOSTAS ERRADAS QUE O USER SELECIONOU
	private HashMap<Integer, String> wrongAnswers = new HashMap<Integer, String>();
	
	// DISCIPLINAS BEAN
	@ManagedProperty(value="#{controllerDisciplinas}")
	private ControllerDisciplinas disciplinaBean;
	
	// PROFESSORES BEAN
	@ManagedProperty(value="#{controllerProfessores}")
	private ControllerProfessores professorBean;
	
	// QUESTOES BEAN
	@ManagedProperty(value="#{controllerQuestoes}")
	private ControllerQuestoes questaoBean;
	
	// DATE FORMATS PARA CHECAR SE O ALUNO PODE OU NAO REALIZAR A PROVA
	private SimpleDateFormat year   = new SimpleDateFormat("yyyy");
	private SimpleDateFormat month  = new SimpleDateFormat("MM");
	private SimpleDateFormat day    = new SimpleDateFormat("dd");
	private SimpleDateFormat hour   = new SimpleDateFormat("HH");
	private SimpleDateFormat minute = new SimpleDateFormat("mm");
	
	public ControllerProvas() {}

	@PostConstruct
	public void init() {
		this.prova = new Prova();
		this.questions = new ArrayList<String>();
		this.wrongAnswers.clear();
		this.selectedAnswer = "";
	}
	
	/** SALVA A PROVA 
	 * @param Este método não exige parâmetros
	 * @return Retorna uma String com o nome da página a ser renderizada
	 */
	public String save() {
		this.prova.setCodProfessor(this.professorBean.getProfessor().getCodigo());
		this.prova.setCodDisciplina(this.disciplinaBean.getDisciplina().getCodigo());
		
		calcQuestionValue();
	
		this.dao = new ProvaDAO();
		
		if(this.dao.salvar(this.prova))
			this.questions.clear(); // LIMPA QUESTOES SELECIONADAS

		return "/list/listaProvasProfessor";
	}
		
	/** DELETA DETERMINADA PROVA */
	public void excluir() throws IOException {
		this.dao = new ProvaDAO();

		if(this.dao.excluir(this.prova.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage("messages", new FacesMessage("Prova excluida!"));				
		}
	}
	
	/** GERENCIA AS QUESTOES SELECIONADAS PELO PROFESSOR
	 * Armazena as questões selecionadas em um array, caso a questão já tenha
	 * sido selecionada anteriormente ela é removida do array, do contrario o 
	 * código dela é armazenado no array. 
	 * @param codigoQuestao String - Código da questão. 
	 * @return Este método não tem retorno. 
	 */
	public void addQuestion(String codigoQuestao) {
		// REMOVE
		if(this.questions.contains(codigoQuestao))  			
			this.questions.remove(codigoQuestao);
		// COLOCA NO ARRAY
		else 
			this.questions.add(codigoQuestao);
		
		this.prova.setQuestoes(String.join(",", this.questions));
	}
	
	// ARMAZENA A RESPOSTA SELECIONADA E VERIFICA SE ESTA CORRETA OU INCORRETA
	public void setResposta(int codQuestao) {
		// RESPOSTA CORRETA
		if(this.selectedAnswer.equalsIgnoreCase(this.questaoBean.getQuestao().getAlternativaCorreta())) {
			// REMOVE DO ARRAY DE RESPOSTAS ERRADAS CASO ELA ESTEJA LA
			if(this.wrongAnswers.containsKey(codQuestao)) {  
				//this.wrongAnswers.remove(codQuestao);
				System.out.println(this.wrongAnswers.remove(codQuestao));
			}
			// COLOCA NO ARRAY DE RESPOSTAS CORRETAS
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
	
			// REMOVE O ACERTO DO ARRAY DE ACERTOS CASO ELE TENHA ACERTADO A QUESTAO ANTERIORMENTE
			if(this.rightAnswers.containsKey(codQuestao))
				this.rightAnswers.remove(codQuestao);
		}
			
		
		// CALCULA A NOTA DO ALUNO A CADA RESPOSTA		
		float nota = this.rightAnswers.size() * this.prova.getValorQuestoes();
		if((this.prova.getValorTotal() - nota) < 0.11) 
			this.prova.setNota(this.prova.getValorTotal());
		else
			this.prova.setNota(nota);

		// SETA A NOTA DO ALUNO NO BEAN
		this.prova.setNota(nota);
	}
	
	// RETORNA UM BOOLEAN INDICANDO SE O USUARIO ERROU DETERMINADA QUESTAO
	// UTILIZADO NO GABARITO
	public boolean errou(int codQuestao, String alternativaAtual) {
		boolean retorno = false;
		
		if(this.wrongAnswers.containsKey(codQuestao)) {
			if(this.wrongAnswers.get(codQuestao).equalsIgnoreCase(alternativaAtual)) 
				retorno = true;
		} else 
			retorno = false;
		
		return retorno;
	}
	
	// VALOR INDIVIDUAL DE CADA QUESTAO
	public void calcQuestionValue() {
		BigDecimal bd = new BigDecimal(Float.toString((float) (this.prova.getValorTotal() / this.questions.size())));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);		
		this.prova.setValorQuestoes(bd.floatValue());
	}
	
	// NUMERO DE QUESTOES EM UMA STRING DE QUESTOES
	public int numberQuestions(String questoes) {
		return questoes.split(",").length;
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		this.prova = this.listaProva.getRowData();
	}

	// SET DATE OF THE TEST
	public String alterarInformacoes() {
		this.dao = new ProvaDAO();

		if(this.dao.alterarInformacoes(this.prova))
			return "/list/listaProvasProfessor";
		
		return "/list/listaProvasProfessor";
	}
	
	// CHECA SE O ALUNO PODE REALIZAR A AVALIACAO
	public boolean permissao(String testDate, boolean allowAfterDate, boolean allowMultipleAttempts, boolean jaRealizou) {
		// testDate = dd/mm/yyyy-hh:mm
		
		// CASO O TESTE PERMITA MULTIPLAS TENTATIVAS
		if(allowMultipleAttempts)
			return false; // DISABLED = FALSE
		
		// CASO O ALUNO JA TENHA REALIZADO OU A PROVA AINDA NAO TENHA DATA DEFINIDA ELE E IMPEDIDO
		if(jaRealizou || testDate.equals(""))  
			return true; // DISABLED = TRUE
		
		else {
			// ARRAY COM DATA[0] E HORA[1] 
			String[] separateDateAndTime = testDate.split("-");
			// ANO, MES, DIA PROVA
			String[] dataProva = separateDateAndTime[0].split("/");
			// HORA, MINUTO PROVA
			String[] horaProva = separateDateAndTime[1].split(":");
			
			Calendar calendar = Calendar.getInstance();
					
			// OBTEM DATA ATUAL
			String todayYear   = this.year.format  (calendar.getTime()); // ANO AGORA
			String todayMonth  = this.month.format (calendar.getTime()); // MES AGORA
			String todayDay    = this.day.format   (calendar.getTime()); // DIA AGORA
			String todayHour   = this.hour.format  (calendar.getTime()); // HORA AGORA
			String todayMinute = this.minute.format(calendar.getTime()); // MIN AGORA
						
			// TRANSFORMA DATA PROVA EM LONG YYYY:MM:DD
			long yearMonthDayTest = Integer.parseInt(dataProva[2] + dataProva[1] + dataProva[0]);
			// TRANFORMA DATA ATUAL EM LONG YYYY:MM:DD
			long yearMonthDayNow = Integer.parseInt(todayYear + todayMonth + todayDay);

			if(yearMonthDayTest == yearMonthDayNow) { // IF DIA DA PROVA
				
				// OBTEM DIFERENCA ENTRE HORA PROVA E HORA ATUAL
				int diferencaHoras = (Integer.parseInt(horaProva[0]) - Integer.parseInt(todayHour)); 
				// OBTEM DIFERENCA ENTER MINUTO PROVA E MIN. ATUAL
				int diferencaMinutos = Integer.parseInt(horaProva[1]) - Integer.parseInt(todayMinute); 
				
				if(allowAfterDate) return false; // HORA/MINUTO PROVA JA PASSOU MAS E PERMITIDO
				
				// IF HORA = A DA PROVA E MIN = AO DA PROVA OU 10 MIN. ANTES
				if(diferencaHoras == 0 && (diferencaMinutos >= 0 && diferencaMinutos < 11)) 
					return false; // DISABLED = FALSE 
			
				else // IF HORA PROVA AINDA N CHEGOU (EX: PROVA AS 11:00) MAS SE O ALUNO JA ESTIVER A 10 MIN. ADIANTADO (10:50) ENTAO ELE TEM PERMISSAO
					return (diferencaHoras == 1 && diferencaMinutos < -49) ? false : true;
			
			// DATA JA PASSOU MAS ESTA PERMITIDO
			} else if((yearMonthDayTest < yearMonthDayNow) && allowAfterDate) 
				return false;
			else // SITUACAO NAO PERVEISTA
				return true;
		}
	}
	
	// GET LIST OF PROVAS MONTADAS PELO PROFESSOR
	public DataModel<Prova> getListaProvas() {
		try {
			this.dao = new ProvaDAO();
			List<Prova> lista = this.dao.todasProvas(this.professorBean.getProfessor().getCodigo());
			this.listaProva = new ListDataModel<Prova>(lista);
			init(); // LIMPA O BEAN PARA LIMPAR OS CAMPOS DOS FORMULARIOS (PARA Q OS DADOS DA ULTIMA PROVA NAO APARECAO NOS CAMPOS)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.listaProva;
	}
	
	// GET LIST OF PROVAS BY COD CURSO
	public DataModel<Prova> obterProvasPeloCodDisciplina(ArrayList<Integer> codDisciplina) {
		this.dao = new ProvaDAO();
		
		List<Prova> provas = this.dao.obterProvasPeloCodDisciplina(codDisciplina);
		
		this.listaProva = new ListDataModel<Prova>(provas);
		
		// LIMPA O BEAN PARA LIMPAR PARA Q OS DADOS DA ULTIMA PROVA NAO APARECAO NOS FORMS
		init(); 
		
		return this.listaProva;
	}
	
	// EXIBE UMA PROVA PARA O ALUNO
	public String showProva() {
		this.dao = new ProvaDAO();

		setProva(this.dao.getProva(this.prova.getCodigo()));
		
		return (this.prova != null) ? "/see/verProvaAluno" : "/list/listaProvasAluno";
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
	public int getQuestionsSize() {
		return questions.size();
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
}
