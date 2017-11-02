package br.edu.unicid.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator(value="FileValidator")
public class FileValidator implements Validator {

	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part file = (Part) value;
        
        // Se o conteudo do arquivo nao for igual a pdf...
        if(!(file.getContentType().equals("application/pdf"))) {
        	throw new ValidatorException(new FacesMessage(
        			"Arquivo inválido. Somente arquivos do tipo <a href='https://smallpdf.com/pt' target='_blank'>PDF</a> são aceitos."));
        }
    }
}
