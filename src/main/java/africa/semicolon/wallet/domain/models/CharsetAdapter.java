package africa.semicolon.wallet.domain.models;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.charset.Charset;

public class CharsetAdapter extends TypeAdapter<Charset> {

    @Override
    public void write(JsonWriter out, Charset value) throws IOException {
        out.value(value.name());
    }

    @Override
    public Charset read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Deserialization not supported");
    }
}