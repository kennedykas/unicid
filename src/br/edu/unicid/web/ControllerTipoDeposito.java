package br.edu.unicid.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.edu.unicid.bean.DepositType;
import br.edu.unicid.dao.TipoDepositoDAO;

@ManagedBean(name="controllerTipoDeposito")
@SessionScoped
public class ControllerTipoDeposito {

	private DepositType depositType;
		
	public ControllerTipoDeposito() {}

	@PostConstruct
	public void init() {}
	
	public String getDepositTypeNameByCode(int code) {
		 
		return new TipoDepositoDAO().getDepositTypeNameByCode(code);
	}
	 	
	public List<SelectItem> getDepositTypes() {
		
		List<SelectItem> items = new ArrayList<>();
		
		try {
			List<DepositType> depositTypesList = new TipoDepositoDAO().getDepositTypes();
			
			for (DepositType tipoDeposito: depositTypesList)
				items.add(new SelectItem(tipoDeposito.getCodigo(), tipoDeposito.getNome()));
			
			return items;
		} catch (Exception e) { return items; }
	}
	
	// GETTERS AND SETTERS
	public DepositType getDepositType() {
		return depositType;
	}
	public void setDepositType(DepositType depositType) {
		this.depositType = depositType;
	}	
}
