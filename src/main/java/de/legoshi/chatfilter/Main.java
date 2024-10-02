package de.legoshi.chatfilter;

import de.legoshi.chatfilter.listener.PlayerChatEvent;
import de.legoshi.chatfilter.commands.ReloadFiles;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Main extends JavaPlugin {

      public ChatFilter chatFilter;
      public static Main instance;

      @Override
      public void onEnable() {
            instance = this;

            try { createFiles(); } catch (IOException e) { e.printStackTrace(); }
            try {this.chatFilter = new ChatFilter(); } catch (FileNotFoundException e) { e.printStackTrace(); }

            Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(), this);
            getCommand("chatfilter").setExecutor(new ReloadFiles());
      }

      @Override
      public void onDisable() {
            // Plugin shutdown logic
      }

      public static Main getInstance() {
            return instance;
      }

      public static void createFiles() throws IOException {
            //ChatFilter folder
            File chatFilterFile = new File("./ChatFilter");
            if(chatFilterFile.mkdir()) System.out.println("ChatFilter Folder created!");
            else System.out.println("ChatFilter Folder already exists!");
      }

      public ChatFilter getChatFilter() {
            return chatFilter;
      }
}
