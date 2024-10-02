package de.legoshi.chatfilter;

import de.legoshi.chatfilter.checks.*;
import de.legoshi.chatfilter.utils.FW;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class ChatFilter {

      public static SpamCheck spamCheck;
      public static CapsCheck capsCheck;
      public static LinkCheck linkCheck;
      public static SymbolCheck symbolCheck;
      public static InsultCheck insultCheck;

      public ChatFilter() throws FileNotFoundException {
            spamCheck = new SpamCheck();
            capsCheck = new CapsCheck();
            linkCheck = new LinkCheck();
            symbolCheck = new SymbolCheck();
            insultCheck = new InsultCheck();
      }

      //use this for player messages
      public void checkMessage(Player player, String message) throws FileNotFoundException {
            String adaptedMessage;

            //stops checks if spam or link
            if(spamCheck.checkSpamming(player, message)) return;
            if(linkCheck.checkLink(player, message)) return;

            adaptedMessage = capsCheck.checkCaps(message);
            adaptedMessage = symbolCheck.checkSymbols(player, adaptedMessage);
            adaptedMessage = insultCheck.filter(adaptedMessage);
            for(Player all : Bukkit.getOnlinePlayers()) all.sendMessage(player.getDisplayName() + ": " + adaptedMessage);
      }

      public static String[] getWordList(String path, String yamlFile, String section) {
            FW whiteList = new FW(path, yamlFile);
            String insults = whiteList.getString(section);
            System.out.println(insults);
            return insults.split(", ");
      }

      public static LinkCheck getLinkCheck() {
            return linkCheck;
      }

      public static InsultCheck getInsultCheck() {
            return insultCheck;
      }
}
