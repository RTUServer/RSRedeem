package kr.rtuserver.redeem.config;

import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import kr.rtuserver.redeem.data.Code;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CodeConfig extends RSConfiguration {

    private final Map<String, Code> map = new HashMap<>();

    public CodeConfig(RSPlugin plugin) {
        super(plugin, "Code.yml", 1);
        setup(this);
    }

    private void init() {
        map.clear();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (String key : getConfig().getKeys(false)) {
            String reward = getString(key + ".reword", "");
            if (reward.isEmpty()) {
                // TODO alert
                continue;
            }
            Date date;
            try {
                date = format.parse(getString(key + ".expire", "2099-12-31"));
            } catch (Exception e) {
                continue;
            }
            Code code = new Code(reward, date);
            map.put(key, code);
        }
    }
}
