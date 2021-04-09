package com.melexis.test.eventconsumer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MachineEventProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String msg = exchange.getIn().getBody(String.class);
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {

			@Override
			public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				JsonObject jo = json.getAsJsonObject();
                return LocalDateTime.of(jo.get("year").getAsInt(),
                        jo.get("monthValue").getAsInt(),
                        jo.get("dayOfMonth").getAsInt(),
                        jo.get("hour").getAsInt(),
                        jo.get("minute").getAsInt(),
                        jo.get("second").getAsInt(),
                        jo.get("nano").getAsInt());
			}
		}).create();

		MachineEvent machineEvent = gson.fromJson(msg, MachineEvent.class);
		exchange.getIn().setBody(machineEvent);
	}
}
