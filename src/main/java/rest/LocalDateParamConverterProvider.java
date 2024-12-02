package rest;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Provider
public class LocalDateParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(LocalDate.class)) {
            return new ParamConverter<>() {
                @Override
                public T fromString(String value) {
                    return value != null ? rawType.cast(LocalDate.parse(value, DateTimeFormatter.ISO_DATE)) : null;
                }

                @Override
                public String toString(T value) {
                    return value != null ? value.toString() : null;
                }
            };
        }
        return null;
    }
}
