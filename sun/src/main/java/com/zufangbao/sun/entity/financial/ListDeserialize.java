package com.zufangbao.sun.entity.financial;

import java.io.IOException;
import java.util.List;

import com.demo2do.core.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ListDeserialize extends JsonDeserializer<List<String>>{

	@Override
	public List<String> deserialize(JsonParser jp,DeserializationContext dc) throws IOException,
			JsonProcessingException {
		return JsonUtils.parseArray(jp.getText(),String.class);
	}

}
