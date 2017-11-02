package br.edu.unicid.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.edu.unicid.dao.AlunoDAO;

@FacesValidator("AlunoValidator")
public class AlunoValidator implements Validator{

	private AlunoDAO dao;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try { 
			dao = new AlunoDAO();
			if(dao.existe(Integer.parseInt((value.toString())))) {
//				System.out.println("existe");
			} else {
				UIInput uiInputRepresentante = (UIInput) component.findComponent("representante");
				uiInputRepresentante.setValid(false);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("O CA: " + value.toString() + " n�o est� cadastrado. Volte ao portal e cadastre-se."));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
