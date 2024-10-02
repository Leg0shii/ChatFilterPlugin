package de.legoshi.chatfilter.checks;

import de.legoshi.chatfilter.utils.ReplaceObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsultCheck {

    /*
     * From LEGOSHI:
     * Beside that I decided to add two types of insults:
     * type1: insults that doesnt appear in other words: for example "asslicker"
     * type2: insults that are in non insult words: "ass" as in "pass"
     *
     */
    private final List<Pattern> regexBlacklist_strict_type1;
    private final List<Pattern> regexBlacklist_strict_type2;
    private String[] funnyWordsList;

    public InsultCheck() {
        funnyWordsList = new String[]{"flippin' chickens"};
        this.regexBlacklist_strict_type1 = convertWords(new String[]{"fucking", "gay"});
        this.regexBlacklist_strict_type2 = convertWords(new String[]{"ass"});
    }

    public String filter(String message) {

        message = checkContext(message);
        String[] messageArray = message.split(" ");
        StringBuilder newMessage = new StringBuilder();

        for (String m : messageArray) {
            newMessage.append(checkWord(m)).append(" ");
        }
        message = newMessage.toString();
        return message;
    }

    private List<Pattern> convertWords(String[] wordList) {
        List<Pattern> arrayList = new ArrayList<>();
        for (String word : wordList) {
            word = word.replace("a", "[xaA4@]+");
            word = word.replace("b", "[bB8]+");
            word = word.replace("c", "[cC]+");
            word = word.replace("d", "[dD]+");
            word = word.replace("e", "[xeE3]+");
            word = word.replace("f", "[fF]+");
            word = word.replace("g", "[gG9]+");
            word = word.replace("h", "[hH]+");
            word = word.replace("i", "[xiIlL1!]+");
            word = word.replace("j", "[jJ]+");
            word = word.replace("k", "[kK]+");
            word = word.replace("l", "[iIlL1!]+");
            word = word.replace("m", "[mM]+");
            word = word.replace("n", "[nN]+");
            word = word.replace("o", "[xoO0]+");
            word = word.replace("p", "[pP]+");
            word = word.replace("q", "[qQ]+");
            word = word.replace("r", "[|2rR]+");
            word = word.replace("s", "[sS$]+");
            word = word.replace("t", "[tT]+");
            word = word.replace("u", "[uvVU]+");
            word = word.replace("v", "[uUvV]+");
            word = word.replace("w", "[wW]+");
            word = word.replace("x", "[xX]+");
            word = word.replace("y", "[yY]+");
            word = word.replace("z", "[zZ]+");
            arrayList.add(Pattern.compile("(" + word + ")"));
        }
        return arrayList;
    }

    private String checkWord(String word) {
        //checking for matches inside words (type1)
        String oldWord = word;

        // if word consists out of no letters, its fine
        word = stripWord(word);
        if (word.length() == 0) return oldWord;

        for (Pattern reg : regexBlacklist_strict_type1) {
            Matcher match = reg.matcher(word);
            if (match.find()) {
                return wordToStars(word);
            }
        }

        for (Pattern reg : regexBlacklist_strict_type2) {
            Matcher match = reg.matcher(word);
            String matchStr = "";
            while (match.find()) {
                matchStr = match.group();
            }

            //add here replacement with funny word or word to stars
            if (matchStr.equals(word)) {
                return wordToStars(word);
            }
        }

        return oldWord;
    }

    private String checkContext(String sentence) {

        StringBuilder newSentence = new StringBuilder();

        // saves the space positions
        ArrayList<ReplaceObject> arrayList = new ArrayList<>();
        char[] charSentence = sentence.toCharArray();
        int index = 0;

        for (char c : charSentence) {
            if (!((((int) c >= 97) && ((int) c <= 122))
                    || ((int) c >= 65) && ((int) c <= 90)
                    || ((int) c >= 49) && ((int) c <= 57)
                    || ((int) c == 64) || ((int) c == 36))) {
                arrayList.add(new ReplaceObject(c, index));
            }
            index++;
        }

        String sentenceToSend = sentence;
        sentence = stripWord(sentence);

        if (sentence.length() != 0) {

            for (Pattern reg : regexBlacklist_strict_type1) {
                Matcher match = reg.matcher(sentence);
                while (match.find()) {
                    String foundMatch = match.group();
                    sentence = sentence.replace(foundMatch, wordToFixedStars(foundMatch));
                }
            }

            // builds the string with spaces together again
            char[] chars = sentence.toCharArray();
            char before = chars[0];

            for (int i = 0; i < chars.length; i++) {
                int pos = getIndexPos(arrayList, i);
                if (pos != -1) {
                    if ((i - 1) > 0) before = chars[i - 1];
                    if (chars[i] != '*' || before != '*') {
                        newSentence.append((char) pos);
                    }
                    i--;
                    arrayList.replaceAll(ro -> new ReplaceObject(ro.getValue(), (ro.getPosition() - 1)));
                } else {
                    newSentence.append(chars[i]);
                }
            }

            // getting the last remaining things out
            arrayList.sort(Comparator.comparing(ReplaceObject::getPosition));
            for (ReplaceObject ro : arrayList) {
                newSentence.append(ro.getValue());
            }
            sentenceToSend = newSentence.toString();
        }

        return sentenceToSend;
    }

    private int getIndexPos(ArrayList<ReplaceObject> list, int val) {
        int c = -1;
        for (ReplaceObject replaceObject : list) {
            if (replaceObject.getPosition() == val) {
                c = replaceObject.getValue();
                list.remove(replaceObject);
                break;
            }

        }
        return c;
    }

    private String stripWord(String word) {

        char[] charWord = word.toCharArray();
        String w = "";
        for (char c : charWord) {
            if ((((int) c >= 97) && ((int) c <= 122))
                    || (((int) c >= 65) && ((int) c <= 90))
                    || (((int) c >= 49) && ((int) c <= 57))
                    || ((int) c == 64) || ((int) c == 36)) {
                w = w + c;
            }
        }

        return w;
    }

    private String wordToStars(String word) {
        double rand = Math.random();
        rand = rand < 0.5 ? -1 : 1;
        String s = "";
        for (int i = 0; i < word.length() + rand; i++) s = s + "*";
        return s;
    }

    private String wordToFixedStars(String word) {
        String s = "";
        for (int i = 0; i < word.length(); i++) s = s + "*";
        return s;
    }

    private String replaceWithFunnyWord() {
        int rand = (int) Math.floor(Math.random() * funnyWordsList.length);
        return funnyWordsList[rand];
    }

    public void setFunnyWordsList(String[] funnyWordsList) {
        this.funnyWordsList = funnyWordsList;
    }

    public void setFunnyWordsList(List<String> funnyWordsList) {
        setFunnyWordsList(funnyWordsList.toArray(new String[0]));
    }

}