package me.nykorrin.sucrose.commands;

import me.nykorrin.sucrose.Sucrose;
import me.nykorrin.sucrose.data.ImageManager;
import me.nykorrin.sucrose.LogoRenderer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class ImageCommand implements CommandExecutor {

    private Sucrose plugin;

    public ImageCommand(Sucrose plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "Invalid operation: Not authorized.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Invalid operation: /" + commandLabel + " <url>");
            return true;
        }

        MapView view = Bukkit.createMap(player.getWorld());
        view.getRenderers().clear();
        LogoRenderer renderer = new LogoRenderer();

        if (!renderer.load(args[0])) {
            player.sendMessage(ChatColor.RED + "Invalid operation: Image could not be loaded. Invalid URL?");
            return true;
        }

        view.addRenderer(renderer);

        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) map.getItemMeta();

        meta.setMapView(view);
        map.setItemMeta(meta);
        player.getInventory().addItem(map);
        player.sendMessage(ChatColor.GREEN + "Created custom map. URL " + args[0]);
        ImageManager.getInstance().saveImage(view.getId(), args[0]);
        return true;
    }
}
