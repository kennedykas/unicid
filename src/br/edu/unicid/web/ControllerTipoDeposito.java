package br.edu.unicid.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.edu.unicid.bean.TipoDeposito;
import br.edu.unicid.dao.TipoDepositoDAO;

@ManagedBean(name="controllerTipoDeposito")
@SessionScoped
public class ControllerTipoDeposito {

	private TipoDepositoDAO dao;
	private TipoDeposito tipoDeposito;
		
	public ControllerTipoDeposito() {}

	@PostConstruct
	public void init() {
		this.tipoDeposito = new TipoDeposito();
	}
	
	public String getDepositTypeNameByCode(int code) {
		 
		return new TipoDepositoDAO().getDepositTypeNameByCode(code);
	}
	 	
	public List<SelectItem> getDepositTypes() {
		
		dao = new TipoDepositoDAO();
		
		List<SelectItem> items = new ArrayList<>();
		List<TipoDeposito> depositTypesList = dao.getDepositTypes();

		for(TipoDeposito tipoDeposito: depositTypesList)
	    	items.add(new SelectItem(tipoDeposito.getCodigo(), tipoDeposito.getNome()));

		return items;
	}
	
	// GETTERS AND SETTERS
	public TipoDeposito getTipoDeposito() {
		return tipoDeposito;
	}
	public void setTipoDeposito(TipoDeposito tipoDeposito) {
		this.tipoDeposito = tipoDeposito;
	}	
}
