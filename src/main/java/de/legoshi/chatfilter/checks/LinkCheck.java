package de.legoshi.chatfilter.checks;

import de.legoshi.chatfilter.ChatFilter;
import de.legoshi.chatfilter.utils.FW;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkCheck {

      private static final Pattern URL_REGEX = Pattern.compile("(https?://w+[.]|https?://|w+[.])([a-zA-Z0-9]+[.][a-zA-Z0-9]+)/?");
      private static final Pattern IP_REGEX = Pattern.compile("([0-9]+[.][0-9]+[.][0-9]+[^ ])");
      private final String path = "./ChatFilter/";
      private final String yamlFile = "Chatfilter.yaml";
      private String[] whitelist;

      public LinkCheck() {
            addFW();
      }

      public void addFW() {
            FW fw = new FW(path, yamlFile);
            fw.setValue("links/ips", "twitch.tv, google.com, 173.343.392");
            fw.save();

            this.whitelist = ChatFilter.getWordList(path, yamlFile, "links/ips");
      }

      //returns true if there is a link in the message
      public boolean checkLink(Player player, String message) {

            Matcher url_matcher = URL_REGEX.matcher(message);
            Matcher ip_matcher = IP_REGEX.matcher(message);
            ArrayList<String> matcher_list = getMatchesURL(url_matcher);
            matcher_list.addAll(getMatchesIP(ip_matcher));

            //iterates through the whitelisted words and checks if there are allowed strings that are the same within two symbols
            //found in the regex group
            int counter;
            for(String match : matcher_list) {
                  counter = 0;
                  for(String words : whitelist) {
                        if(match.equals(words)) counter++;
                  }
                  if (counter == 0) {
                        player.sendMessage(
                                "&8»&m--------------------------------------&r&8« \n" +
                                "&cAdvertising is not allowed! You may be muted/banned if you attempt to advertise. \n" +
                                "&8»&m--------------------------------------&r&8«\n");
                        return true;
                  }
            }
            return false;
      }

      private ArrayList<String> getMatchesURL(Matcher matcher) {
            ArrayList<String> arr = new ArrayList<>();
            while(matcher.find()) {
                  arr.add(matcher.group(2));
            }
            return arr;
      }

      private ArrayList<String> getMatchesIP(Matcher matcher) {
            ArrayList<String> arr = new ArrayList<>();
            while(matcher.find()) {
                  arr.add(matcher.group(0));
            }
            return arr;
      }

      public void setWhitelist(String[] whitelist) {
            this.whitelist = whitelist;
      }
}
