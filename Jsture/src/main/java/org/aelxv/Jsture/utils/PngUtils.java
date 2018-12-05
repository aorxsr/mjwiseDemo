package org.aelxv.Jsture.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class PngUtils {

	/**
	 * 这个是已经是Base64之后的码转换成SVG图片 注意：关于转换成什么样的文件，需要自己判断base64码是什么样的图片类型
	 *
	 * @param imgStr      : 这个是已经转换成Base64码之后的那个码
	 * @param imgFilePath : 这个是保存的位置
	 * @return 返回是否成功
	 */
	public static boolean base64Toimage(String imgStr, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	

}
