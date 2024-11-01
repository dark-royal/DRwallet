package africa.semicolon.wallet.domain.models;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.Proxy;
public class ProxyTypeAdapter extends TypeAdapter<Proxy> {


    @Override
    public void write(JsonWriter out, Proxy value) throws IOException {
        out.beginObject();
        out.name("type").value(value.type().name());
        out.name("address").jsonValue(value.address().toString());
        out.endObject();
    }

    @Override
    public Proxy read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Deserialization not supported");
    }

}