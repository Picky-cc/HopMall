/**
 * 
 */
package com.suidifu.hathaway.amqp.support.convertor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.demo2do.core.utils.JsonUtils;

/**
 * @author wukai
 *
 */
public class FastJson2JsonMessageConvertor extends AbstractJsonMessageConverter {

	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		
		byte[] bytes;
		try {
			String jsonString = JsonUtils.toJsonString(object);
					
			bytes = jsonString.getBytes(getDefaultCharset());
		}
		catch (IOException e) {
			throw new MessageConversionException(
					"Failed to convert Message content", e);
		}
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		messageProperties.setContentEncoding(getDefaultCharset());
		messageProperties.setContentLength(bytes.length);
		

		return new Message(bytes, messageProperties);
		
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
	
		MessageProperties properties = message.getMessageProperties();
		String encoding = properties.getContentEncoding();
		if (encoding == null) {
					encoding = getDefaultCharset();
		}
		String contentAsString;
		try {
			contentAsString = new String(message.getBody(), encoding);
			return contentAsString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
					
	}

}
