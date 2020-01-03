package tim03we.wallsystem.events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.plotsquared.nukkit.util.NukkitUtil;
import tim03we.wallsystem.WallSystem;

public class PlayerFormResponded implements Listener {

    @EventHandler
    public void onFormResponded(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (event.getWindow() instanceof FormWindowSimple) {
            if(event.getResponse() == null) {
                return;
            }
            FormWindowSimple gui = (FormWindowSimple) event.getWindow();
            String responseName = gui.getResponse().getClickedButton().getText();
            if(WallSystem.getInstance().getConfig().getString("form.title").equals(gui.getTitle())) {
                Plot plot = Plot.getPlot(new Location(player.getLevel().getName(), player.getFloorX(), player.getFloorY(), player.getFloorZ()));
                if (plot == null) {
                    player.sendMessage(WallSystem.prefix + WallSystem.getInstance().getConfig().getString("messages.not-on-the-plot"));
                    return;
                }
                if (!plot.isOwner(NukkitUtil.getPlayer(player).getUUID())) {
                    player.sendMessage(WallSystem.prefix + WallSystem.getInstance().getConfig().getString("messages.not-the-owner"));
                    return;
                }
                for (String list : WallSystem.walls) {
                    String[] ex = list.split(":");
                    if(ex[0].equals(responseName)) {
                        if(!ex[3].equals("none")) {
                            if (!player.hasPermission(ex[3])) {
                                player.sendMessage(WallSystem.prefix + WallSystem.getInstance().getConfig().getString("messages.no-perms"));
                                return;
                            }
                        }
                        WallSystem.setWall(player, ex[1] + ":" + ex[2], plot);
                    }
                }
            }
        }
    }
}
