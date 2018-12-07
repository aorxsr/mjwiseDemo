package sample.chapter05;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.bstek.dorado.uploader.DownloadFile;
import com.bstek.dorado.uploader.UploadFile;
import com.bstek.dorado.uploader.annotation.FileProvider;
import com.bstek.dorado.uploader.annotation.FileResolver;

@Component
public class MyUploadProcessor {

	@Resource
	private HttpServletRequest request;

	@FileResolver
	public String process1(UploadFile file, Map<String, Object> parmeter) {
		try {

			System.out.println(request.getSession().getServletContext().getRealPath("/"));
			file.transferTo(new File(new String(file.getFileName().getBytes("ISO-8859-1"),"UTF-8")));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file.getFileName();
	}

	@FileProvider
	public DownloadFile download(Map<String, Object> parameter) {
		System.out.println(parameter.get("fileName"));
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File((String) parameter.get("fileName")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new DownloadFile((String) parameter.get("fileName"), inputStream);
	}
}
