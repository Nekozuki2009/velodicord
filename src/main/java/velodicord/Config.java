package velodicord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Config {

    public static Map<String, String> dic;

    public static List<String> detectbot;

    public static Map<String, Integer> disspeaker;

    public static Map<String, Integer> minespeaker;

    public static Map<String, String> config;

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Type type = new TypeToken<Map<String, String>>() {}.getType();

    @DataDirectory
    public static Path dataDirectory;

    public static Path dicjson;

    public static Path detectbotjson;

    public static Path disspeakerjson;

    public static Path minespeakerjson;

    public static Path configjson;

    public static void init() throws IOException, InterruptedException {
        if (Files.notExists(dataDirectory))
            try {
                Files.createDirectory(dataDirectory);
            } catch (IOException e) {
                throw new RuntimeException("Velodicordのconfigディレクトリを作れませんでした");
            }

        if (Files.notExists(dicjson))
            Files.copy(Objects.requireNonNull(Velodicord.class.getResourceAsStream("/object.json")), dicjson);

        if (Files.notExists(detectbotjson))
            Files.copy(Objects.requireNonNull(Velodicord.class.getResourceAsStream("/array.json")), detectbotjson);

        if (Files.notExists(disspeakerjson))
            Files.copy(Objects.requireNonNull(Velodicord.class.getResourceAsStream("/object.json")), disspeakerjson);

        if (Files.notExists(minespeakerjson))
            Files.copy(Objects.requireNonNull(Velodicord.class.getResourceAsStream("/object.json")), minespeakerjson);

        if (Files.notExists(configjson)) {
            Files.copy(Objects.requireNonNull(Velodicord.class.getResourceAsStream("/config.json")), configjson);
            Velodicord.velodicord.logger.info("Velodicordのconfigを設定してください");
            System.exit(0);
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(dicjson)), StandardCharsets.UTF_8))) {
            dic = gson.fromJson(reader, type);
        }
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(detectbotjson)), StandardCharsets.UTF_8))) {
            detectbot = gson.fromJson(reader, new TypeToken<List<String>>() {}.getType());
        }
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(disspeakerjson)), StandardCharsets.UTF_8))) {
            disspeaker = gson.fromJson(reader, new TypeToken<Map<String, Integer>>() {}.getType());
        }
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(minespeakerjson)), StandardCharsets.UTF_8))) {
            minespeaker = gson.fromJson(reader, new TypeToken<Map<String, Integer>>() {}.getType());
        }
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(configjson)), StandardCharsets.UTF_8))) {
            config = gson.fromJson(reader, type);
        }

        VOICEVOX.init();
    }
}