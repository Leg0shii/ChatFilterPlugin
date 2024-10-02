package de.legoshi.chatfilter.commands;

import de.legoshi.chatfilter.checks.InsultCheck;
import de.legoshi.chatfilter.checks.LinkCheck;
import de.legoshi.chatfilter.ChatFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadFiles implements CommandExecutor {

      private final String path = "./ChatFilter/";
      private final String yamlFile = "Chatfilter.yaml";

      @Override
      public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if(args[0].equals("reload")) {
                  InsultCheck iC = ChatFilter.getInsultCheck();
                  LinkCheck lC = ChatFilter.getLinkCheck();

                  iC.setFunnyWordsList(ChatFilter.getWordList(path, yamlFile, "funny"));
                  lC.setWhitelist(ChatFilter.getWordList(path, yamlFile, "links/ips"));
            } else sender.sendMessage("/chatfilter reload");
            return false;
      }

}
