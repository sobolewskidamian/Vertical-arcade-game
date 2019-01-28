package game;

import org.jsoup.*;
import org.jsoup.nodes.*;

import java.io.IOException;

public class Ranking {
    static String parse(String nick, int score) {
        Document document = null;
        try {
            document = Jsoup.connect("http://przypomnienia.cba.pl/javaAPP/ranking.php?nick=" + nick + "&score=" + score).timeout(2000).get();
        } catch (IOException e) {
            System.out.println("Brak internetu");
        }

        String result = "";
        if (document != null) {
            for (Element actualElement : document.select("tr")) {
                result += actualElement.select("td#score").text();
                result += " - ";
                result += actualElement.select("td#nick").text();
                result += "\n";
            }
        }
        return result;
    }
}
