package de.legoshi.chatfilter.checks;

import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.HashMap;


public class SpamCheck {

      //value in seconds of the delay of send messages
      private final double spamDelay = 3.0;
      //hashmap too full after too long time? drop player every min?
      private final HashMap<String, String[]> messageAmountCooldown;
      private final HashMap<String, Long> messageTimeCooldown;

      public SpamCheck() {
            this.messageAmountCooldown = new HashMap<>();
            this.messageTimeCooldown = new HashMap<>();
      }

      //returns true if the message is spammed
      public boolean checkSpamming(Player player, String message) {

            String uuid = player.getUniqueId().toString();

            if(!messageAmountCooldown.containsKey(uuid)) {
                  String[] m = {"",""};
                  messageAmountCooldown.put(uuid, m);
            }

            if(!messageTimeCooldown.containsKey(uuid)) {
                  messageTimeCooldown.put(uuid, 0L);
            }

            if(isMessageSendTooFast(player)) {
                  long currentTime = Calendar.getInstance().getTimeInMillis();
                  long lastMessageTime = messageTimeCooldown.get(uuid);
                  double waitingTime = Math.ceil(((spamDelay*1000)-(currentTime - lastMessageTime))/1000);
                  player.sendMessage(
                          "&8»&m--------------------------------------&r&8« \n" +
                          "&cPlease wait another "+(int)waitingTime+" second(s) to send another message! Purchase a rank at &6store &cto bypass this cooldown. \n" +
                          "&8»&m--------------------------------------&r&8«\n"
                  );
                  return true;
            }

            if(isMessageDuplicate(player, message)) {
                  player.sendMessage(
                          "&8»&m--------------------------------------&r&8« \n" +
                          "&cPlease do not send the same message twice! \n" +
                          "&8»&m--------------------------------------&r&8«\n"
                  );
                  return true;
            }
            return false;

      }

      private boolean isMessageSendTooFast(Player player) {

            long currentTime = Calendar.getInstance().getTimeInMillis();
            String uuid = player.getUniqueId().toString();
            long lastMessageTime = messageTimeCooldown.get(uuid);

            if (currentTime - lastMessageTime < (spamDelay*1000)) return true;
            else {
                  messageTimeCooldown.put(uuid, currentTime);
                  return false;
            }

      }

      //returns true if the last 3 messages are the same (differ in two letters)
      private boolean isMessageDuplicate(Player player, String message) {

            String uuid = player.getUniqueId().toString();
            String[] messageList = messageAmountCooldown.get(uuid);

            String message1 = messageAmountCooldown.get(uuid)[0];
            String message2 = messageAmountCooldown.get(uuid)[1];
            messageList[1] = messageList[0];
            messageList[0] = message;

            if(message.length() <= 7) return (message.equals(message1) && message.equals(message2));

            int diff1 = Math.abs(message.compareToIgnoreCase(message1));
            int diff2 = Math.abs(message.compareToIgnoreCase(message2));

            return (diff1 <= 2) && (diff2 <= 2);

      }

}
