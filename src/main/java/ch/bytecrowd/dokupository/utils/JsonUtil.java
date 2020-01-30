package ch.bytecrowd.dokupository.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtil {

    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

    private JsonUtil() {}

    public static <T> T getFromJsonUrl(String url, Class<T> returnType) throws RuntimeException {

        final Gson gson = GSON_BUILDER.create();
            try (final InputStream inputStream = getHttpInputStream(url);
                 final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);) {
                return gson.fromJson(inputStreamReader, returnType);
            } catch (IOException | JsonSyntaxException | JsonIOException e) {
                throw new RuntimeException(e);
            }
    }

    /**
     * @param url
     * @return
     * @throws RuntimeException instead of IOException so we can use it in lambda expressions.
     */
    public static InputStream getHttpInputStream(String url) throws RuntimeException {

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.connect();
            return urlConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
