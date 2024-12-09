package kr.rtuserver.redeem.commands;

import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.utility.player.PlayerChat;
import kr.rtuserver.redeem.RSRedeen;
import kr.rtuserver.redeem.config.CodeConfig;
import kr.rtuserver.redeem.config.RewardConfig;
import kr.rtuserver.redeem.data.Code;
import kr.rtuserver.redeem.data.Reward;

import java.util.Date;

public class Command extends RSCommand {

    private final RewardConfig rewardConfig;
    private final CodeConfig codeConfig;

    public Command(RSRedeen plugin) {
        super(plugin, "redeem", true);
        this.rewardConfig = plugin.getRewardConfig();
        this.codeConfig = plugin.getCodeConfig();
    }

    @Override
    public boolean execute(RSCommandData data) {
        PlayerChat chat = PlayerChat.of(getPlugin());
        if (data.length(1)) {
            String codeStr = data.args(0);
            Code code = codeConfig.getMap().get(codeStr);
            if (code != null) {
                if (code.expire().after(new Date())) {
                    Reward reward = rewardConfig.getMap().get(code.reward());
                    if (reward != null) {
                        chat.announce(getSender(), String.join("/", rewardConfig.getMap().values().stream().map(reward1 -> String.join(",", reward1.items())).toList()));
                        chat.announce(getSender(), String.join("/", codeConfig.getMap().values().stream().map(Record::toString).toList()));
                        chat.announce(getSender(), "---------------------");
                    }
                } else {

                }
            }
        } else {

        }
        return true;
    }

    @Override
    public void reload(RSCommandData data) {
        rewardConfig.reload();
        codeConfig.reload();
    }

}
