package br.edu.unicid.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.edu.unicid.bean.ProvasRealizadas;
import br.edu.unicid.dao.ProvasRealizadasDAO;
import br.edu.unicid.util.Chronometer;

@ManagedBean(name="controllerProvasRealizadas")
@SessionScoped
public class ControllerProvasRealizadas {

	private Timer               timer;         
	private Date                date;          // TO KNOW WHEN THE USER REALIZED THE TEST
	private boolean             saveWasCalled; // CHECK SE O TESTE JA FOI SALVO
	private boolean             timerAlreadyStarted;
	private Chronometer         chronometer              = new Chronometer();
	private final DateFormat    data                     = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private ProvasRealizadas    provasRealizadas;
	private ProvasRealizadasDAO dao;
	private static final String FACE_MESSAGES_ID         = "messages";
	private static final String PAGE_LIST_MADE_TESTS     = "/list/listaProvasRealizadasAluno";
	private static final String PAGE_SEE_CORRECT_ANSWERS = "/see/gabaritoAluno";
	private static final String MINUTES                  = "m";
	private static final String SECONDS                  = "s";
	private static final int    PRECISION_SCALE          = 2;
	private DataModel<ProvasRealizadas> listaProvasRealizadas;
	
	@ManagedProperty(value="#{controllerProvas}")
	private ControllerProvas provasBean;

	@ManagedProperty(value="#{controllerAlunos}")
	private ControllerAlunos alunosBean;
	
	public ControllerProvasRealizadas() {}

	@PostConstruct
	public void init() {
		
		provasRealizadas    = new ProvasRealizadas();
		timer               = new Timer();
		saveWasCalled       = Boolean.FALSE;
		timerAlreadyStarted = Boolean.FALSE;
	}
	
	// SALVA A PROVA DO ALUNO
	public String save() {
		chronometer.stop();
		this.date = new Date();
		
		this.provasRealizadas.setTempo       (getTempo());
		this.provasRealizadas.setCodProva    (provasBean.getProva().getCodigo());
		this.provasRealizadas.setCodProfessor(provasBean.getProva().getCodProfessor());
		this.provasRealizadas.setCodAluno    (alunosBean.getAluno().getCodigo());
		this.provasRealizadas.setNota        (provasBean.getProva().getNota());
		this.provasRealizadas.setData        (this.data.format(date));
		
		this.dao = new ProvasRealizadasDAO();

		if (this.dao.salvar(this.provasRealizadas))  
			return PAGE_LIST_MADE_TESTS;
		else 
			return PAGE_SEE_CORRECT_ANSWERS;
	}
	
	public String getTempo() {
		
		if (chronometer.getMinutes() >= 1)
			
			return new BigDecimal(
					((Double)(chronometer.getMinutes())).toString()).setScale(PRECISION_SCALE,RoundingMode.HALF_UP).toString() + MINUTES;
		
		else
			return new BigDecimal(
					((Double)(chronometer.getSeconds())).toString()).setScale(PRECISION_SCALE,RoundingMode.HALF_UP).toString() + SECONDS;
	}

	// INICIANDO CRONOMETRO
	public void startTimer() {
		if(!this.timerAlreadyStarted) {
			
			this.chronometer.start();
			
			this.timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if(!saveWasCalled) {
						save();
						saveWasCalled = Boolean.TRUE;
						chronometer.stop();
					}
				}
			}, ((this.provasBean.getProva().getTempo() * 60) * 1000));
			
			this.timerAlreadyStarted = Boolean.TRUE;
		}
	}
		
	// DELETE
	public void excluir() {
		this.dao = new ProvasRealizadasDAO();

		if(this.dao.excluir(this.provasRealizadas.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage("ProvasRealizadas excluida!"));				
		}
	}
	
	public void findTestsMadeByTeachersStudents(int codigoProfessor) {

		dao = new ProvasRealizadasDAO();

		List<ProvasRealizadas> lista = this.dao.provasRealizadasProfessor(codigoProfessor);
		listaProvasRealizadas = new ListDataModel<>(lista);
	}

	public void findTestsByStudentCode(int codigoAluno) {
		
		dao = new ProvasRealizadasDAO();

		List<ProvasRealizadas> lista = dao.provasRealizadasAluno(codigoAluno);
		listaProvasRealizadas = new ListDataModel<>(lista);
	}
	
	// CHECAGEM SE O ALUNO JA REALIZOU O TESTE
	public boolean checkRealizacao(int codProva) {
		this.dao = new ProvasRealizadasDAO();

		List<Integer> listaCodigos = this.dao.obtemProvas(this.alunosBean.getAluno().getCodigo());
		
		return (listaCodigos.contains(codProva)) ? Boolean.TRUE : Boolean.FALSE;  
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		provasRealizadas = listaProvasRealizadas.getRowData();
	}
	
	// GETTERS AND SETTERS
	public ProvasRealizadas getProvasRealizadas() {
		return provasRealizadas;
	}
	public void setProvasRealizadas(ProvasRealizadas provasRealizadas) {
		this.provasRealizadas = provasRealizadas;
	}
	public ControllerProvas getProvasBean() {
		return provasBean;
	}
	public void setProvasBean(ControllerProvas provasBean) {
		this.provasBean = provasBean;
	}
	public ControllerAlunos getAlunosBean() {
		return alunosBean;
	}
	public void setAlunosBean(ControllerAlunos alunosBean) {
		this.alunosBean = alunosBean;
	}
	public DataModel<ProvasRealizadas> getListaProvasRealizadas() {
		return listaProvasRealizadas;
	}
}
