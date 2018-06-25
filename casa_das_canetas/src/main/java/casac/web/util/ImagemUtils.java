package casac.web.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImagemUtils {
	public static void salvarImagem(String caminho, MultipartFile imagem) {
		// TODO Auto-generated method stub
		File file = new File(caminho);
		
		if(!imagem.isEmpty()) {
		try {
			FileUtils.writeByteArrayToFile(file, imagem.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public static void removeImagem(String caminho) {
		File file = new File(caminho);
		file.delete();
	}
}
