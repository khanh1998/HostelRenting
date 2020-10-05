package org.avengers.capstone.hostelrenting.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.avengers.capstone.hostelrenting.model.ServiceDetail;

import java.io.IOException;

public class ServiceSerializer extends StdSerializer<ServiceDetail> {
    public ServiceSerializer(Class<ServiceDetail> t) {
        super(t);
    }

    public ServiceSerializer() {
        this(null);
    }

    @Override
    public void serialize(ServiceDetail value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("serviceId",value.getService().getServiceId());
        gen.writeStringField("serviceName", value.getService().getServiceName());
        gen.writeNumberField("price",value.getPrice());
        gen.writeStringField("priceUnit", value.getPriceUnit());
        gen.writeStringField("timeUnit",value.getTimeUnit());
        gen.writeStringField("userUnit", value.getUserUnit());
        gen.writeFieldName("isRequired");
        gen.writeBoolean(value.isRequired());
        gen.writeNumberField("createdAt", value.getCreatedAt());
        gen.writeEndObject();
    }
}