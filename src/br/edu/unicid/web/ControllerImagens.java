package br.edu.unicid.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.edu.unicid.bean.Imagem;
import br.edu.unicid.dao.ImagemDAO;

@ManagedBean(name="controllerImagens")
@SessionScoped
public class ControllerImagens {
	
	private ImagemDAO dao;
	private Imagem    imagem ;
	private Part      selectedImage; // Imagem que o user selecionou
	
	public ControllerImagens() {}
		
	@PostConstruct
	public void init() {
		this.imagem = new Imagem();
	}
	
	// SAVE
	public int save() throws IOException {
		try {
			// OBTEM A IMG QUE ESTA TEMPORARIAMENTE ARMAZANADA NO SERVIDOR
			InputStream input = this.selectedImage.getInputStream(); 
			this.imagem.setImagem(IOUtils.toByteArray(input)); // OBTEM OS BYTES DA IMG
			this.imagem.setNomeImagem(this.selectedImage.getSubmittedFileName());
		
		} catch (IOException e) { e.printStackTrace(); }
		
		this.dao = new ImagemDAO();
		
		// SALVA E RETORNA A PK DA IMAGEM
		int codImagem = this.dao.salvar(this.imagem);
			
		// CASO OBJETO TENHA SIDO SALVO SEU COD E SETADO NO BEAN E ELE 
		// ESTA PRONTO PARA SER UTILIZADO POR OUTRA CLASSE QUALQUER
		if(codImagem > 0) { 
			this.imagem.setCodigo(codImagem);
			return codImagem;
		}
		return 0;
	}
		
	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageCode = context.getExternalContext().getRequestParameterMap().get("imageCode");
            // Student student = studentService.find(Long.valueOf(studentId));
        	this.dao = new ImagemDAO();
        	this.imagem = this.dao.obtemImagem(Integer.parseInt(imageCode));
            return new DefaultStreamedContent(new ByteArrayInputStream(this.imagem.getImagem()));
        }
    }
	
	// ALTERAR
	public boolean alterar() { 
		
		this.dao = new ImagemDAO();
		
		return (this.dao.alterar(this.imagem)) ? true : false;
	}
		
	// EXCLUIR
	public boolean excluir(int codigo) {
		this.dao = new ImagemDAO();
		return dao.excluir(codigo); 
	}

	// GETTERS AND SETTERS
	public Imagem getImagem() {
		return imagem;
	}
	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}
	public Part getSelectedImage() {
		return selectedImage;
	}
	public void setSelectedImage(Part selectedImage) {
		this.selectedImage = selectedImage;
	}
}
