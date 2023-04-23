package work.lclpnet.config;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SimpleConfigSerializerTest {

    private static final Logger logger = LoggerFactory.getLogger("test");

    @Test
    void testLoadConfig() throws IOException {
        Path file = Path.of("src", "test", "resources", "test.json");
        assertTrue(Files.isRegularFile(file));

        var serializer = new SimpleConfigSerializer<>(logger, TestConfig.FACTORY);

        TestConfig config = serializer.loadConfig(file);

        assertEquals(10, config.foo);
        assertEquals("Foo bar", config.bar);
    }

    @Test
    void testLoadConfigMissing() throws IOException {
        Path file = Path.of("src", "test", "resources", "test_missing.json");
        assertTrue(Files.isRegularFile(file));

        var serializer = new SimpleConfigSerializer<>(logger, TestConfig.FACTORY);

        TestConfig config = serializer.loadConfig(file);

        assertEquals("Foo bar", config.bar);
        assertEquals(5, config.foo);  // default value
    }

    @Test
    void testLoadConfigEmpty() throws IOException {
        Path file = Files.createTempDirectory("jsoncfg").resolve("empty.json");
        assertFalse(Files.exists(file));

        var serializer = new SimpleConfigSerializer<>(logger, TestConfig.FACTORY);

        TestConfig config = serializer.loadConfig(file);

        // default values
        assertEquals("Hello World!", config.bar);
        assertEquals(5, config.foo);

        assertTrue(Files.isRegularFile(file));
        assertDefaultContent(file);
    }

    @Test
    void testSaveConfig() throws IOException {
        var serializer = new SimpleConfigSerializer<>(logger, TestConfig.FACTORY);

        Path file = Files.createTempDirectory("jsoncfg").resolve("test.json");
        TestConfig config = TestConfig.FACTORY.createDefaultConfig();

        serializer.saveConfig(config, file);

        assertTrue(Files.isRegularFile(file));

        // check saved correctly
        assertDefaultContent(file);
    }

    private static void assertDefaultContent(Path file) throws IOException {
        String content = Files.readString(file, StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(content);

        assertEquals(5, json.getInt("foo"));
        assertEquals("Hello World!", json.getString("bar"));
    }

    private static class TestConfig implements JsonConfig {

        // declare config properties and define default values
        private int foo = 5;
        private String bar = "Hello World!";

        // default and json constructor
        public TestConfig() {}

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
}