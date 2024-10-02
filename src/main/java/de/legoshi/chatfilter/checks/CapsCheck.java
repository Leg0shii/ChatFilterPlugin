package de.legoshi.chatfilter.checks;

public class CapsCheck {

      private final double capsPercent = 0.70;

      public String checkCaps(String message) {

            long stringLength = message.trim().length();
            long uppercaseCount = message.codePoints().filter(c-> c>='A' && c<='Z').count();
            double percentage = (double) (uppercaseCount/stringLength);

            if (percentage > capsPercent)  return message.toLowerCase();
            return message;

      }

}
