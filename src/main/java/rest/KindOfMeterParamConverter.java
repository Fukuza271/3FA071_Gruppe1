package rest;

import interfaces.IReading;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class KindOfMeterParamConverter implements ParamConverter {


    @Override
    public IReading.KindOfMeter fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return IReading.KindOfMeter.valueOf(value.toUpperCase());
    }

    @Override
    public String toString(IReading.KindOfMeter value) {
        return value == null ? null : value.name();
    }
}
