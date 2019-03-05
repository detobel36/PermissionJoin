/*
 * Copyright (C) 2019 Detobel36.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package be.detobel36.permissionjoin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventPriority;


/**
 *
 * @author remy
 */
public class PermissionJoin extends JavaPlugin implements Listener {
    
    private String permissionLogin;
    private String denyLoginMessage;
    
    @Override
    public void onEnable() {
        getLogger().info("Lancement du plugin: PermissionJoin ");
        
        loadConfiguration();
        permissionLogin = getConfig().getString("Permission");
        denyLoginMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Message"));
        
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);

        getConfig().options().header("Plugin fait par Detobel36. Ce plugin permet de limiter l'accès à un serveur\n"
                + "Permission:\n"
                + "    Permission à avoir pour venir sur le serveur\n" 
                + "Message:\n"
                + "    Message que reçoit le joueur s'il ne peut pas rentrer");
        
        getConfig().addDefault("Permission", "permissionjoin.login");
        getConfig().addDefault("Message", "&cVous n'êtes pas autoriser à "
                + "rentrer sur ce serveur\n&7Contactez un admin si vous "
                + "pensez que c'est un bug");
        
        saveConfig();
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if(!event.getPlayer().hasPermission(permissionLogin)) {
            event.setKickMessage(denyLoginMessage);
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
    
}
