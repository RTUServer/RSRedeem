package kr.rtuserver.redeem.config;

import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import kr.rtuserver.redeem.data.Reward;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RewardConfig extends RSConfiguration {

    private final Map<String, Reward> map = new HashMap<>();

    public RewardConfig(RSPlugin plugin) {
        super(plugin, "Reward.yml", 1);
        setup(this);
    }

    private void init() {
        map.clear();
        for (String key : getConfig().getKeys(false)) {
            List<String> items = getStringList(key+ ".items", List.of());
            Reward reward = new Reward(items);
            map.put(key, reward);
        }
    }
}
