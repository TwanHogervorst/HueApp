package student.avansti.hueapp.parts;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import student.avansti.hueapp.annotation.GsonExclude;

public class PartGson {

    private static Gson instance;

    public static Gson getGsonInstance() {
        if(PartGson.instance == null) {
            PartGson.instance = new GsonBuilder()
                    .setExclusionStrategies(new GsonExclusionStrategy())
                    .addSerializationExclusionStrategy(new GsonSerializationExclusionStrategy())
                    .addDeserializationExclusionStrategy(new GsonDeserializationExclusionStrategy())
                    .create();
        }

        return PartGson.instance;
    }

    private static class GsonSerializationExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            boolean result = false;

            // add exclusions rules

            return result;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            boolean result = false;

            // add exclusions rules

            return result;
        }
    }

    private static class GsonDeserializationExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            boolean result = false;

            // add exclusions rules

            return result;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            boolean result = false;

            // add exclusions rules

            return result;
        }
    }

    private static class GsonExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            boolean result = false;

            // has exclude annotation
            result = result || f.getAnnotation(GsonExclude.class) != null;

            return result;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            boolean result = false;

            // add exclusions rules

            return result;
        }
    }

}
