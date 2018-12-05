package org.aelxv.Jsture.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aelxv.Jsture.dao.JutureJpaRepository;
import org.aelxv.Jsture.entity.Juture;
import org.aelxv.Jsture.utils.PngUtils;
import org.aelxv.Jsture.utils.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

@RestController
public class IndexController {

	@Autowired
	private JutureJpaRepository jutureJpaRepository;

	@RequestMapping(value = "/getBase64FromatImg")
	public String getBase64FromatImg(@RequestParam("name") String name, @RequestParam("base") String base,
			HttpServletRequest request) {

		// 上传的base码 并不能被存到数据库中，所以这里采用保存成文件的方式。
		String realPath = request.getSession().getServletContext().getRealPath("/");
		long currentTimeMillis = System.currentTimeMillis();
		String imagePath = realPath + currentTimeMillis + ".png";
		boolean b = PngUtils.base64Toimage(base, imagePath);

		try {
			WordUtils.createRtf(imagePath, realPath + currentTimeMillis + ".rtf");
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (b) {
			Juture juture = new Juture();
			juture.setName(name);
			juture.setPngPath(imagePath);
			juture.setWordPath(realPath + currentTimeMillis + ".rtf");
			jutureJpaRepository.save(juture);
		}

		return "OK";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param          name：按名称查找图片，然后下载
	 * @return
	 */
	@RequestMapping("/downloadPng")
	public String downloadPng(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("name") String name) {
		Juture juture = jutureJpaRepository.findByName(name);
		System.out.println(juture);
		String fileName = juture.getName() + ".png";// 文件名
		if (fileName != null) {
			// 设置文件路径
			File file = new File(juture.getPngPath());
			// File file = new File(realPath , fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					return "下载成功";
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return "下载失败";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param          name：按名称查找图片，然后下载
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/downloadRTF")
	public String downloadRTF(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("name") String name) throws UnsupportedEncodingException {
		Juture juture = jutureJpaRepository.findByName(name);
		System.out.println(juture);
		String fileName = juture.getName() + ".rtf";// 文件名
		if (fileName != null) {
			// 设置文件路径
			File file = new File(juture.getWordPath());
			// File file = new File(realPath , fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" +new String("付飞虎.rtf".getBytes("GB2312"),"ISO-8859-1"));// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					return "下载成功";
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return "下载失败";
	}

}
