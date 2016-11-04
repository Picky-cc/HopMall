package com.zufangbao.sun.entity.financial;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ListSerialize extends JsonSerializer<List<String>>{

	@Override
	public void serialize(List<String> list, JsonGenerator jg,SerializerProvider sp)
			throws IOException, JsonProcessingException {
		jg.writeString(JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
	}

}
