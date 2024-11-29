package rest;

import interfaces.IReading;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class KindOfMeterParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if (aClass.equals(IReading.KindOfMeter.class)) {
            return (ParamConverter<T>) new KindOfMeterParamConverter();
        }
        return null;
    }
}
