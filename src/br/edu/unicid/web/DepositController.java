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
		deposit.setType(depositTypeBean.getDepositType().getCodigo());
		
		return new DepositDAO().save(deposit) ?
			Constants.PAGE_LIST_DEPOSITS_PROFESSOR.concat(Constants.TOAST_SAVED_WITH_SUCCESS) :
			Constants.PAGE_NEW_DEPOSIT.concat(Constants.TOAST_SOMETHING_WENT_WRONG);
	}
			
	public String change() {
		
		deposit.setType(depositTypeBean.getDepositType().getCodigo());
		
		return new DepositDAO().change(deposit) ? 
			Constants.PAGE_LIST_DEPOSITS_PROFESSOR.concat(Constants.TOAST_CHANGES_HAVE_BEEN_SAVED) : 
			Constants.PAGE_UPDATE_DISCIPLINE.concat(Constants.TOAST_GENERIC_ERROR);
	}
	
	public String delete() {
		
		return (new DepositDAO().delete(deposit.getCode())) ?
			Constants.PAGE_LIST_DEPOSITS_PROFESSOR.concat(Constants.TOAST_CHANGES_HAVE_BEEN_SAVED) :
			Constants.PAGE_LIST_DEPOSITS_PROFESSOR.concat(Constants.TOAST_DELETED);
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
