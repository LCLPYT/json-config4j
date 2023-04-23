package work.lclpnet.config.json;

import org.json.JSONObject;

class TestConfig implements JsonConfig {

    // declare config properties and define default values
    public int foo = 5;
    public String bar = "Hello World!";

    // default and json constructor
    public TestConfig() {
    }

    public TestConfig(JSONObject json) {
        if (json.has("foo")) {
            this.foo = json.getInt("foo");
        }

        if (json.has("bar")) {
            this.bar = json.getString("bar");
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("foo", foo);
        json.put("bar", bar);

        return json;
    }

    public static final JsonConfigFactory<TestConfig> FACTORY = new JsonConfigFactory<>() {
        @Override
        public TestConfig createDefaultConfig() {
            return new TestConfig();
        }

        @Override
        public TestConfig createConfig(JSONObject json) {
            return new TestConfig(json);
        }
    };
}
