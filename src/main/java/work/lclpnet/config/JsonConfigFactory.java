package work.lclpnet.config;

import org.json.JSONObject;

public interface JsonConfigFactory<T extends JsonConfig> {

    T createDefaultConfig();

    T createConfig(JSONObject json);
}
