package org.avengers.capstone.hostelrenting.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.avengers.capstone.hostelrenting.model.StreetWard;

import java.io.IOException;

public class AddressSerializer extends StdSerializer<StreetWard> {

    public AddressSerializer() {
        this(null);
    }

    public AddressSerializer(Class<StreetWard> t) {
        super(t);
    }

    @Override
    public void serialize(StreetWard value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("provinceId", value.getWard().getDistrict().getProvince().getProvinceId());
        gen.writeStringField("provinceName", value.getWard().getDistrict().getProvince().getProvinceName());
        gen.writeNumberField("districtId", value.getWard().getDistrict().getDistrictId());
        gen.writeStringField("districtName", value.getWard().getDistrict().getDistrictName());
        gen.writeNumberField("wardId", value.getWard().getWardId());
        gen.writeStringField("wardName", value.getWard().getWardName());
        gen.writeNumberField("streetId", value.getStreet().getStreetId());
        gen.writeStringField("streetName", value.getStreet().getStreetName());;
        gen.writeEndObject();
    }
}
