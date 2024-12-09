package kr.rtuserver.redeem.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import kr.rtuserver.framework.bukkit.api.storage.Storage;
import kr.rtuserver.framework.bukkit.api.utility.platform.JSON;
import kr.rtuserver.redeem.RSRedeen;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@RequiredArgsConstructor
public class HistoryManager {

    private final RSRedeen plugin;
    @Getter
    private final Map<UUID, Set<String>> map = new HashMap<>();

    public void addPlayer(UUID uuid) {
        Storage storage = plugin.getStorage();
        storage.get("History", Pair.of("uuid", uuid.toString())).thenAccept(result -> {
            if (result == null || result.isEmpty()) {
                storage.add("History", JSON.of("uuid", uuid.toString()).append("history", false));
                map.put(uuid, Set.of());
            } else {
                Set<String> list = new HashSet<>();
                JsonArray array = result.get(0).get("history").getAsJsonArray();
                for (JsonElement element : array.asList()) list.add(element.getAsString());
                map.put(uuid, list);
            }
        });
    }

    public void removePlayer(UUID uuid) {
        map.remove(uuid);
    }

    public void add(UUID uuid, String code) {
        Set<String> list = new HashSet<>(get(uuid));
        list.add(code);
        JsonArray array = new JsonArray();
        for (String str : list) array.add(str);
        Storage storage = plugin.getStorage();
        storage.set("History", Pair.of("uuid", uuid.toString()), Pair.of("history", array));
        map.put(uuid, list);
    }

    public Set<String> get(UUID uuid) {
        return map.getOrDefault(uuid, Set.of());
    }

}
