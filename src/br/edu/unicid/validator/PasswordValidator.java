package br.edu.unicid.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		UIInput senha1 = (UIInput) component.findComponent("senha1");
		UIInput senha2 = (UIInput) component.findComponent("senha2");
		
		if(!(senha1.getSubmittedValue().toString().equals(senha2.getSubmittedValue().toString()))) {
			senha1.setValid(false);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Por favor verifique sua senha!"));
		}
	}
}
