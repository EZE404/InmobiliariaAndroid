package com.albornoz.inmobiliariaandroid.tools;

import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializer implements JsonSerializer<Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";  // Formato compatible con el servidor

    @Override
    public JsonElement serialize(Date src, java.lang.reflect.Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return new JsonPrimitive(format.format(src));  // Retorna la fecha en el formato adecuado
    }
}
