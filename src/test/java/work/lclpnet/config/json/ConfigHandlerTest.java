package work.lclpnet.config.json;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger("test");

    @Test
    void testLoadConfig() throws IOException {
        Path file = Files.createTempDirectory("jsoncfg").resolve("config.json");
        assertFalse(Files.isRegularFile(file));

        var serializer = new FileConfigSerializer<>(TestConfig.FACTORY, logger);
        var configHandler = new ConfigHandler<>(file, serializer, logger);

        assertTrue(configHandler.loadConfig());
        assertNotNull(configHandler.getConfig());
        assertTrue(Files.isRegularFile(file));
    }

    @Test
    void testWriteConfig() throws IOException {
        Path file = Files.createTempDirectory("jsoncfg").resolve("config.json");
        assertFalse(Files.isRegularFile(file));

        var serializer = new FileConfigSerializer<>(TestConfig.FACTORY, logger);
        var configHandler = new ConfigHandler<>(file, serializer, logger);

        TestConfig config = TestConfig.FACTORY.createDefaultConfig();
        config.foo = 123;
        configHandler.setConfig(config);

        assertTrue(configHandler.writeConfig());
        assertTrue(Files.isRegularFile(file));

        String content = Files.readString(file, StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(content);

        assertEquals(123, json.getInt("foo"));
        assertEquals("Hello World!", json.getString("bar"));
    }
}