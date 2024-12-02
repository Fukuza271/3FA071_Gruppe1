package rest;

import interfaces.IReading;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class KindOfMeterParamConverter implements ParamConverter<IReading.KindOfMeter> {


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
//package rest;
//
//import interfaces.IReading;
//import jakarta.ws.rs.ext.ParamConverter;
//import jakarta.ws.rs.ext.ParamConverterProvider;
//import jakarta.ws.rs.ext.Provider;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//@Provider
//public class KindOfMeterParamConverter implements ParamConverterProvider {
//
//    @Override
//    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
//        if (type.equals(IReading.KindOfMeter.class)) {
//            return new ParamConverter<T>() {
//                @Override
//                public T fromString(String s) {
//                    return s != null ? aClass.cast(IReading.KindOfMeter.valueOf(s)) : null;
//                }
//                @Override
//                public String toString(T t) {
//                    return t != null ? t.toString() : null;
//                }
//            };
//        }
//        return null;
//    }
//}
