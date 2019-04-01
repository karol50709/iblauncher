package sample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class JSONConfig {




    Gson gson = new Gson();



    Type type = new TypeToken<List<JSONProject>>(){}.getType();
    protected List<JSONProject> fromJSONFile() throws FileNotFoundException {

            JsonReader reader = new JsonReader(new FileReader("config.json"));
           return gson.fromJson(reader, type);



    }


}
