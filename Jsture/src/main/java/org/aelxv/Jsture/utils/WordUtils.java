package org.aelxv.Jsture.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.rtf.RtfWriter2;

public class WordUtils {

	/**
	 * @param image
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void createRtf(String img, String rtfPath) throws DocumentException, IOException {
		// 创建word文档,并设置纸张的大小
		Document document = new Document(PageSize.A4);
		RtfWriter2.getInstance(document, new FileOutputStream(rtfPath));
		// 打开document
		document.open();
		// 读取图片(参数为gif、jpg、png格式的图片都可以)，设置图片大小
		Image image = Image.getInstance(img);
		// Image img = Image.getInstance(new URL("https://xxx.com/logo.jpg)");
		// 设置图片的绝对大小，宽和高
		image.scaleAbsolute(800, 300);
		// 设置图片居中显示
		image.setAlignment(Image.MIDDLE);
		document.add(image);
		// 关闭document
		document.close();
	}

}
