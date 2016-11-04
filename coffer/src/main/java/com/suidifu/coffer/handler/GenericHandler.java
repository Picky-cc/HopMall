package com.suidifu.coffer.handler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.suidifu.coffer.GlobalSpec;
import com.suidifu.coffer.GlobalSpec.BankCorpEps;

public class GenericHandler {

	public String sendHttpRequest(String requestPacket, Map<String, String> workParms) {
		HttpURLConnection conn = null;
		OutputStream os = null;
		BufferedReader br = null;
		try {
			URL url = new URL(workParms.getOrDefault("URL", StringUtils.EMPTY));

			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(GlobalSpec.DEFAULT_TIME_OUT);
			conn.setReadTimeout(GlobalSpec.DEFAULT_TIME_OUT);
			os = conn.getOutputStream();
			os.write(requestPacket.toString().getBytes(workParms.getOrDefault("Encoding", BankCorpEps.DEFAULT_ENCODING).toString()));

			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String responsePacket = StringUtils.EMPTY;
			String line;
			while ((line = br.readLine()) != null) {
				responsePacket += line;
			}
			return responsePacket;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
				if (null != br) {
					br.close();
				}
				if (null != conn) {
					conn.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return StringUtils.EMPTY;
	}
	
	
	public String sendSocketRequest(String requestPacket, Map<String, String> workParms) {
		String responsePacket = StringUtils.EMPTY;
		Socket socket = null;
		InputStream inStream = null;
		ByteArrayOutputStream outSteam = null;
		try {
			socket = new Socket(workParms.getOrDefault("url", StringUtils.EMPTY), Integer.parseInt(workParms.getOrDefault("port", GlobalSpec.DEFAULT_SOCKET_PORT.toString())));
			socket.setSendBufferSize(GlobalSpec.DEFAULT_SOCKET_BUFFER_SIZE);
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(GlobalSpec.DEFAULT_TIME_OUT);
			socket.setKeepAlive(true);
			OutputStream out = socket.getOutputStream();
			
			out.write(requestPacket.getBytes(workParms.getOrDefault("Encoding", BankCorpEps.DEFAULT_ENCODING).toString()));
			out.flush();
			
			inStream = socket.getInputStream();
			outSteam = new ByteArrayOutputStream();  
			byte[] buffer = new byte[1024];  
			int len = -1;  
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);  
			}  
			
			byte[] rebyte = outSteam.toByteArray();
			responsePacket = new String(rebyte);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != outSteam) {
					outSteam.close();
				}
				if (null != inStream) {
					inStream.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return responsePacket;
	}
	
}
