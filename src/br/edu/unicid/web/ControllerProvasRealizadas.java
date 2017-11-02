package br.edu.unicid.web;

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

	private ProvasRealizadasDAO dao;
	private ProvasRealizadas provasRealizadas;
	private DataModel<ProvasRealizadas> listaProvasRealizadas;
	private Timer   timer;         // CRONOMETRO
	private long    startTime;     // GET NANOSECONDS ON TIMER START
	private Date    date;          // TO KNOW WHEN THE USER REALIZED THE TEST
	private boolean saveWasCalled; // CHECK SE O TESTE JA FOI SALVO
	private boolean timerAlreadyStarted;
	private Chronometer chronometer = new Chronometer();
	
	// PROVAS BEAN
	@ManagedProperty(value="#{controllerProvas}")
	private ControllerProvas provasBean;
	// ALUNOS BEAN
	@ManagedProperty(value="#{controllerAlunos}")
	private ControllerAlunos alunosBean;
	
	// DATEFORMAT PARA SABER A HORA EM QUE O USER FEZ O TESTE
	private DateFormat data = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	public ControllerProvasRealizadas() {}

	@PostConstruct
	public void init() {
		this.provasRealizadas = new ProvasRealizadas();
		this.timer = new Timer();
		this.saveWasCalled = false;
		this.timerAlreadyStarted = false;
	}
	
	/** SALVA A PROVA DO ALUNO
	 * Seta o código da prova, código do professor, código do aluno,
	 * nota que o aluno pontuol no bean e salva ou atualiza a prova. 
	 * @param Este método não exige parâmetros ele tem todos os beans 
	 * necessários injetados então ele só da um get().
	 * @return Retorna uma String com o nome da página a ser renderizada
	 */
	public String save() {
		chronometer.stop();
		
		this.provasRealizadas.setTempo(
				this.chronometer.getMinutes() >= 1 ? 
				this.chronometer.getMinutes() + "m" : 
				this.chronometer.getSeconds() + "s");
		System.out.println(this.provasRealizadas.getTempo());
		this.provasRealizadas.setCodProva(this.provasBean.getProva().getCodigo());
		this.provasRealizadas.setCodProfessor(this.provasBean.getProva().getCodProfessor());
		this.provasRealizadas.setCodAluno(this.alunosBean.getAluno().getCodigo());
		this.provasRealizadas.setNota(this.provasBean.getProva().getNota());
		this.date = new Date();
		this.provasRealizadas.setData(this.data.format(date));
		
		this.dao = new ProvasRealizadasDAO();

		if (this.dao.salvar(this.provasRealizadas))  
			return "/see/gabaritoAluno";
		else 
			return "/list/listaProvasRealizadasAluno";
	}

	// INICIANDO CRONOMETRO
	public void startTimer() {
		if(!this.timerAlreadyStarted) {
			System.out.println("timer started -----------------------------");
			this.chronometer.start();
			
			this.timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if(!saveWasCalled) {
						save();
						saveWasCalled = true;
						chronometer.stop();
						System.out.println("time after stop RUN" + chronometer.getSeconds());
						
					}
				}
			}, ((this.provasBean.getProva().getTempo() * 60) * 1000));
			
			this.timerAlreadyStarted = true;
		}
	}
		
	// DELETE
	public void excluir() throws Exception {
		this.dao = new ProvasRealizadasDAO();

		if(this.dao.excluir(this.provasRealizadas.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage("messages", new FacesMessage("ProvasRealizadas excluida!"));				
		}
	}
	
	
	// LISTA ALUNOS QUE JA REALIZARAM A AVALIACAO 
	public DataModel<ProvasRealizadas> getListaProvasRealizadas(int codigoProfessor) {
		this.dao = new ProvasRealizadasDAO();

		List<ProvasRealizadas> lista = this.dao.provasRealizadasProfessor(codigoProfessor);
		this.listaProvasRealizadas = new ListDataModel<ProvasRealizadas>(lista);
		
		return this.listaProvasRealizadas;
	}

	// LISTA AVALIACOES REALIZADAS PELO ALUNO
	public DataModel<ProvasRealizadas> getProvasRealizadas(int codigoAluno) {
		this.dao = new ProvasRealizadasDAO();

		List<ProvasRealizadas> lista = this.dao.provasRealizadasAluno(codigoAluno);
		this.listaProvasRealizadas = new ListDataModel<ProvasRealizadas>(lista);
		
		return this.listaProvasRealizadas;
	}
	
	// CHECAGEM SE O ALUNO JA REALIZOU O TESTE
	public boolean checkRealizacao(int codProva) {
		this.dao = new ProvasRealizadasDAO();

		List<Integer> listaCodigos = this.dao.obtemProvas(this.alunosBean.getAluno().getCodigo());
		
		return (listaCodigos.contains(codProva)) ? true : false;  
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		this.provasRealizadas = this.listaProvasRealizadas.getRowData();
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
}
