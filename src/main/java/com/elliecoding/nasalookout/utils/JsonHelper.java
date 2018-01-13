package com.elliecoding.nasalookout.utils;

import com.elliecoding.nasalookout.entities.NasaData;
import com.google.gson.*;
import org.joda.time.LocalDate;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Helper class for all json access. Makes use of Google Gson project
 */
public class JsonHelper {

    private JsonHelper() {
        throw new AssertionError("Static util class, do not instantiate");
    }

    public static NasaData parseJsonToData(String response) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateDeserializer()).create();
        return gson.fromJson(response, NasaData.class);
    }

    public static void streamDataToJson(NasaData data, Writer writer) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();
        gson.toJson(data, writer);
    }

    public static NasaData streamDataFromJson(Reader reader, Class<NasaData> type) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateDeserializer()).create();
        return gson.fromJson(reader, type);
    }


    private static class LocalDateSerializer implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate source, Type typeOfSrc, JsonSerializationContext context) {
            // No date formatter needed here since, again, nasa has the lovely ISO format for dates by default
            return new JsonPrimitive(source.toString());
        }
    }

    private static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
                JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
    }
}
