package mc.iaiao.me;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Me extends JavaPlugin {
  public void onEnable() {
    saveDefaultConfig();
    String local = ChatColor.translateAlternateColorCodes('&', getConfig().getString("local-message"));
    String global = ChatColor.translateAlternateColorCodes('&', getConfig().getString("global-message"));
    int radius = getConfig().getInt("local-radius");
    getCommand("me").setExecutor((sender, cmd, label, args) -> {
      if (sender instanceof Player) {
        Player p = (Player) sender;
        String message = createMessage(local, args, sender.getName());
        for (Entity e : p.getNearbyEntities(radius, 10000, radius)) {
          if (e instanceof Player) {
            e.sendMessage(message);
          }
        }
        sender.sendMessage(message);
      } else {
        sender.sendMessage("Use /eme from console");
      }
      return true;
    });
    getCommand("eme").setExecutor((sender, cmd, label, args) -> {
      String message = createMessage(global, args, sender.getName());
      getServer().broadcastMessage(message);
      return true;
    });
  }

  private String createMessage(String template, String[] words, String playerName) {
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word).append(' ');
    }
    String message = sb.toString();
    return template
            .replace("<player>", playerName)
            .replace("<message>", message);
  }
}
