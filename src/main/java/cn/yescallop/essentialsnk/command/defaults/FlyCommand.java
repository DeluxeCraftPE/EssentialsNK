package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class FlyCommand extends CommandBase {

    public FlyCommand(EssentialsAPI api) {
        super("fly", api);

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                new CommandParameter("player", CommandParamType.TARGET, true)
        });
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length > 1) {
            this.sendUsage(sender);
            return true;
        }
        Player player;
        if (args.length == 0) {
            if (!this.testIngame(sender)) {
                return true;
            }
            player = (Player) sender;
        } else {
            if (!sender.hasPermission("essentialsnk.fly.others")) {
                this.sendPermissionMessage(sender);
                return true;
            }
            player = api.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", args[0]));
                return true;
            }
        }
        String enabled = Language.translate(api.switchCanFly(player) ? "commands.generic.enabled" : "commands.generic.disabled");
        player.sendMessage(Language.translate("commands.fly.success", enabled));
        if (sender != player) {
            sender.sendMessage(Language.translate("commands.fly.success.other", player.getDisplayName(), enabled));
        }
        return true;
    }
}
