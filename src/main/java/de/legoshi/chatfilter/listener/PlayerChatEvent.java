package de.legoshi.chatfilter.listener;

import de.legoshi.chatfilter.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.FileNotFoundException;

public class PlayerChatEvent implements Listener {

      @EventHandler
      public void onChatEvent(AsyncPlayerChatEvent asyncPlayerChatEvent) throws FileNotFoundException {

            asyncPlayerChatEvent.setCancelled(true);
            String message = asyncPlayerChatEvent.getMessage();
            Main.getInstance().chatFilter.checkMessage(asyncPlayerChatEvent.getPlayer(), message);

      }

}
