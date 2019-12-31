package tim03we.wallsystem.commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import com.intellectualcrafters.plot.commands.CommandCategory;
import com.intellectualcrafters.plot.commands.MainCommand;
import com.intellectualcrafters.plot.commands.RequiredType;
import com.intellectualcrafters.plot.commands.SubCommand;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.plotsquared.general.commands.CommandDeclaration;
import tim03we.wallsystem.WallSystem;

@CommandDeclaration(
        command = "wall",
        permission = "plots.wall",
        description = "Change your plot wall",
        usage = "/plot wall",
        category = CommandCategory.SETTINGS,
        requiredType = RequiredType.PLAYER)

public class WallCommand extends SubCommand {

    public WallCommand() {
        MainCommand.getInstance().addCommand(this);
    }

    @Override
    public boolean onCommand(PlotPlayer plotPlayer, String[] strings) {
        String name = plotPlayer.getName();
        Player player = Server.getInstance().getPlayer(name);
        if(player != null) {
            FormWindowSimple gui = new FormWindowSimple(WallSystem.getInstance().getConfig().getString("form.title"), " ");
            for (String rands : WallSystem.walls) {
                String[] ex = rands.split(":");
                gui.addButton(new ElementButton(ex[0]));
            }
            player.showFormWindow(gui);
        }
        return false;
    }
}
