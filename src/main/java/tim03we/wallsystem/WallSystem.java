package tim03we.wallsystem;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.config.Configuration;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotBlock;
import tim03we.wallsystem.commands.WallCommand;
import tim03we.wallsystem.events.PlayerFormResponded;

import java.util.ArrayList;

public class WallSystem extends PluginBase {

    private static WallSystem instance;
    public static String prefix;
    public static ArrayList<String> walls = new ArrayList<>();

    public static WallSystem getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerFormResponded(), this);
        new WallCommand();
        prefix = getConfig().getString("prefix");
        walls.addAll(getConfig().getStringList("walls"));
    }

    public static void setWall(Player p, String id, Plot plot) {
        PlotBlock[] pb = Configuration.BLOCKLIST.parseString(id);
        if (plot.getConnectedPlots().size() > 1) {
            for (Plot plots : plot.getConnectedPlots()) {
                PS.get().getPlotManager(new Location(p.getLevel().getName(), p.getFloorX(), p.getFloorY(), p.getFloorZ())).setComponent(plots.getArea(), plots.getId(), "wall", pb);
            }
        } else {
            PS.get().getPlotManager(new Location(p.getLevel().getName(), p.getFloorX(), p.getFloorY(), p.getFloorZ())).setComponent(plot.getArea(), plot.getId(), "wall", pb);
        }
        plot.setSign();
        p.sendMessage(prefix + getInstance().getConfig().getString("messages.success"));
    }
}
