package br.edu.unicid.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.edu.unicid.bean.CursoExtensao;
import br.edu.unicid.constants.Constants;
import br.edu.unicid.dao.CursoExtensaoDAO;
import br.edu.unicid.util.Email;

@ManagedBean(name="controllerCursoExtensao")
@SessionScoped
public class ControllerCursoExtensao {

	private CursoExtensao    cursoExtensao;
	private CursoExtensaoDAO dao;
	private Email            email;
	private Date             date;
	private String           userRecaptchaResponse;
	
	private DataModel<CursoExtensao> listaCursosExtensao;
	
	private final DateFormat DATA = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	public ControllerCursoExtensao() {}
	
	@PostConstruct
	public void init() {
		cursoExtensao = new CursoExtensao();
		email = new Email();
	}
	
	public String save() {
				
		date = new Date();
		cursoExtensao.setData(DATA.format(date));
		
		if (new CursoExtensaoDAO().salvar(cursoExtensao)){
			email.sendEmailCursoExtensao(cursoExtensao);
			return "sucessoCurso";
		}
		return "erroCurso";
	}
	
	public void excluir() {
		dao = new CursoExtensaoDAO();

		if(dao.excluir(cursoExtensao.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage("Prova excluida!"));				
		}
	}
	
	public void findExtensionCourses() {
		
		dao = new CursoExtensaoDAO();
		
		List<CursoExtensao> lista = dao.obterTodosCursosExtensao();
		
		listaCursosExtensao = new ListDataModel<>(lista);
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		cursoExtensao = listaCursosExtensao.getRowData();
	}
	
	// Getters and setters
	public DataModel<CursoExtensao> getListaCursosExtensao() {
		return listaCursosExtensao;
	}
	public CursoExtensao getCursoExtensao() {
		return cursoExtensao;
	}
	public void setCursoExtensao(CursoExtensao cursoExtensao) {
		this.cursoExtensao = cursoExtensao;
	}

}
