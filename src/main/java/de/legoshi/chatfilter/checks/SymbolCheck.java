package de.legoshi.chatfilter.checks;

import org.bukkit.entity.Player;

public class SymbolCheck {

      public SymbolCheck() { }

      public String checkSymbols(Player player, String message) {

            String newMessage = message.replaceAll("[\\u007F-\\uFFFF]+", "");

            if(newMessage.trim().length() == 0) {
                  player.sendMessage(
                          "&8»&m--------------------------------------&r&8« \n" +
                                  "&cPlease do not use unrecognized symbols! If you think this is an error, message a staff member. \n" +
                                  "&8»&m--------------------------------------&r&8«\n");
                  return "";
            }

            return newMessage;

      }

      // for outside use
      public boolean checkSymbols(String message) {
            String newMessage = message.replaceAll("[\\u007F-\\uFFFF]+", "");
            return !(message.equals(newMessage));
      }

}
