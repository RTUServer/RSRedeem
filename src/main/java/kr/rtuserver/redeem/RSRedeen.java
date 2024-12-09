package kr.rtuserver.redeem;

import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.redeem.commands.Command;
import kr.rtuserver.redeem.config.CodeConfig;
import kr.rtuserver.redeem.config.RewardConfig;
import kr.rtuserver.redeem.listeners.PlayerJoinQuit;
import kr.rtuserver.redeem.manager.HistoryManager;
import lombok.Getter;

public class RSRedeen extends RSPlugin {

    @Getter
    private static RSRedeen instance;
    @Getter
    private RewardConfig rewardConfig;
    @Getter
    private CodeConfig codeConfig;
    @Getter
    private HistoryManager historyManager;

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        getConfigurations().initStorage("History");

        rewardConfig = new RewardConfig(this);
        codeConfig = new CodeConfig(this);
        historyManager = new HistoryManager(this);

        registerCommand(new Command(this));

        registerEvent(new PlayerJoinQuit(this));
    }

}
