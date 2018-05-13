package br.edu.unicid.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.edu.unicid.bean.Deposit;
import br.edu.unicid.constants.Constants;
import br.edu.unicid.dao.DepositDAO;

@ManagedBean(name="depositController")
@SessionScoped
public class DepositController {

	private DepositDAO         dao;
	private Deposit            deposit;
	private DataModel<Deposit> depositsList;
	
	@ManagedProperty(value="#{controllerTipoDeposito}")
	private ControllerTipoDeposito depositTypeBean;
	
	@ManagedProperty(value="#{controllerProfessores}")
	private ControllerProfessores professorBean;
	
	@ManagedProperty(value="#{controllerAlunos}")
	private ControllerAlunos studentBean;
	
	public DepositController() {}

	@PostConstruct
	public void init() {
		deposit = new Deposit();
	}

	public String save() {
		
		deposit.setProfessor(professorBean.getProfessor().getCodigo());
		deposit.setStudent(studentBean.getAluno().getRgm());
		deposit.setType(depositTypeBean.getTipoDeposito().getCodigo());
		
		return new DepositDAO().save(deposit) ?
			Constants.PAGE_LIST_DEPOSITS_PROFESSOR + Constants.SAVED_WITH_SUCCESS :
			Constants.PAGE_NEW_DEPOSIT;
	}
			
	public String change() {
		
		deposit.setType(depositTypeBean.getTipoDeposito().getCodigo());
		
		dao = new DepositDAO();
		
		return dao.change(deposit) ? 
				Constants.PAGE_LIST_DEPOSITS_PROFESSOR + Constants.CHANGES_HAVE_BEEN_SAVED : 
				Constants.PAGE_UPDATE_DISCIPLINE;
	}
	
	public void delete() {
		
		new DepositDAO().delete(deposit.getCode());
	}
	
	public void findDepositsByStudentRgm() {
				
		depositsList = 
				new ListDataModel<>(new DepositDAO().getDeposits(studentBean.getAluno().getRgm()));
	}
	
	public void findDepositsByProfessorCode() {
		
		depositsList = 
			new ListDataModel<>(new DepositDAO()
				.getDepositsByProfessorCode(professorBean.getProfessor().getCodigo()));
	}
	
	public void getRowData() {
		deposit = depositsList.getRowData();
	}
	
	// Getters and Setters
	
	public Deposit getDeposit() {
		return deposit;
	}
	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}
	public DataModel<Deposit> getDepositsList() {
		return depositsList;
	}
	public ControllerTipoDeposito getDepositTypeBean() {
		return depositTypeBean;
	}
	public void setDepositTypeBean(ControllerTipoDeposito depositTypeBean) {
		this.depositTypeBean = depositTypeBean;
	}
	public ControllerProfessores getProfessorBean() {
		return professorBean;
	}
	public void setProfessorBean(ControllerProfessores professorBean) {
		this.professorBean = professorBean;
	}
	public ControllerAlunos getStudentBean() {
		return studentBean;
	}
	public void setStudentBean(ControllerAlunos studentBean) {
		this.studentBean = studentBean;
	}
}
