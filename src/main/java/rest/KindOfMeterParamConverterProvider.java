package rest;

import interfaces.IReading;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class KindOfMeterParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {   // holt den parameter Converter
        if (aClass.equals(IReading.KindOfMeter.class)) {
            return new ParamConverter<T>() {
                @Override
                public T fromString(String value) {
                    return value != null ? aClass.cast(IReading.KindOfMeter.valueOf(value)) : null;
                }
                @Override
                public String toString(T value) {   //gibt den wert als String zurück
                    return value != null ? value.toString() : null;
                }
            };
        }
        return null;
    }
}
