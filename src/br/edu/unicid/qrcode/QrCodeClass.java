package br.edu.unicid.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QrCodeClass {

	public void gerarQrcode(String cpf) {

		int size = 300;

		File f = new File("C:\\Users\\davis\\Pictures\\qrcode.jpg");

		try {
			FileOutputStream arquivo = new FileOutputStream(f);
			ByteArrayOutputStream out = QRCode.from(cpf).to(ImageType.JPG)
					.withSize(size, size).stream();
			arquivo.write(out.toByteArray());
			arquivo.flush();
			arquivo.close();
		} catch (IOException ex) {
			Logger.getLogger(QrCodeClass.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

}
