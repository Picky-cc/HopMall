package com.zufangbao.earth.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.zufangbao.gluon.resolver.JsonViewResolver;
import com.zufangbao.gluon.resolver.PageViewResolver;
import com.zufangbao.gluon.spec.global.GlobalSpec;

public class BaseController {
	
	@Autowired
	public PageViewResolver pageViewResolver;
	
	@Autowired
	public JsonViewResolver jsonViewResolver;
	
	public void exportExcelToClient(HttpServletResponse response, String fileName, String charset, HSSFWorkbook workBook) throws IOException {
		String encodeFileName = getEncodeFileName(fileName, charset);
		response.setContentType(GlobalSpec.Response.EXCEL_CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName + "; filename*="+ charset +"''"+ encodeFileName);
		
		OutputStream out = response.getOutputStream();
		out.flush();
		workBook.write(out);
		workBook.close();
		out.close();
	}
	
	public void exportCSVToClient(HttpServletResponse response, String fileName, String charset, List<String> csvData) throws IOException {
		String encodeFileName = getEncodeFileName(fileName, charset);
		response.setContentType(GlobalSpec.Response.CSV_CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName + "; filename*="+ charset +"''"+ encodeFileName);
		
		OutputStream out = response.getOutputStream();
		writeCSVToOutputStream(csvData, out);
		out.close();
	}
	
	/**
	 * 导出zip包到客户端
	 * @param response
	 * @param fileName zip文件名
	 * @param charset 字符编码
	 * @param csvs key为csv文件名（不含.csv），value为csvData
	 * @throws IOException
	 */
	public void exportZipToClient(HttpServletResponse response, String fileName, String charset, Map<String, List<String>> csvs) throws IOException {
		String encodeFileName = getEncodeFileName(fileName, charset);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName + "; filename*="+ charset +"''"+ encodeFileName);
		OutputStream out = response.getOutputStream();
		
		ZipOutputStream zip = new ZipOutputStream(out);

		for (String csvName : csvs.keySet()) {
			ZipEntry entry = new ZipEntry(csvName + ".csv");
			// 将压缩实体放入压缩包
			zip.putNextEntry(entry);
			// 将csv内容写进压缩实体
			
			writeCSVToOutputStream(csvs.get(csvName), zip);
		}
		zip.flush();
		zip.close();
		response.flushBuffer();
	}
	
	private void writeCSVToOutputStream(List<String> csvData, OutputStream outputStream) throws IOException {
		BufferedOutputStream bufStream = new BufferedOutputStream(outputStream);
		PrintWriter outPutStream = new PrintWriter(bufStream);

		bufStream.flush();
		bufStream.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });

		for (String str : csvData) {
			outPutStream.println(str);
		}
		outPutStream.flush();
	}
	
	public void zipFilesToClient(HttpServletResponse response, String zipFileName, String charset, List<String> sourceFilePath) throws IOException {
		String encodeFileName = getEncodeFileName(zipFileName, charset);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName + "; filename*="+ charset +"''"+ encodeFileName);
		OutputStream out = response.getOutputStream();
		ZipOutputStream zip = new ZipOutputStream(out);
		for (String path : sourceFilePath) {
			writeFileToZipOutputStream(zip, path);
		}
		zip.flush();
		zip.close();
		response.flushBuffer();
	}

	private void writeFileToZipOutputStream(ZipOutputStream zip, String path) throws IOException {
		FileInputStream input = null;
		try {
			File file = new File(path);
			input = new FileInputStream(file);
			zip.putNextEntry(new ZipEntry(file.getName()));
			int temp = 0;
		    while ((temp = input.read()) != -1) {
		        zip.write(temp);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			input.close();
		}
	}

	private String getEncodeFileName(String zipFileName, String charset) throws UnsupportedEncodingException {
		String encodeFileName = java.net.URLEncoder.encode(zipFileName, charset);
		encodeFileName = encodeFileName.replaceAll("\\+", "%20");
		return encodeFileName;
	}
}