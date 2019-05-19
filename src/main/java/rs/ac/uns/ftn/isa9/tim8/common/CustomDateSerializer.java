package rs.ac.uns.ftn.isa9.tim8.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(dateFormatter.format(value));
        }
		
	}

}
