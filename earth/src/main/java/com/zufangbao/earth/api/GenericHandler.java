package com.zufangbao.earth.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import com.demo2do.core.entity.Result;

/**
 * 
 * @author zjm
 *
 */
public class GenericHandler {

	public Result createFailResult(String message) {
		return new Result().fail().message(message);
	}

	public String sendHttpRequest(String requestPacketData, Map<String, String> config) {
		OutputStream os = null;
		BufferedReader br = null;
		try {
			URL url = new URL(config.get("URL").toString());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			os = conn.getOutputStream();
			os.write(requestPacketData.toString().getBytes(config.getOrDefault("ENCODING", "UTF-8").toString()));

			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String responseData = "";
			String line;
			while ((line = br.readLine()) != null) {
				responseData += line;
			}
			System.out.println("银行返回报文："+responseData);
			return responseData;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
				if (null != br) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
