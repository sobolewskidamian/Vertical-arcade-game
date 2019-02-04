package game;

import org.jsoup.*;
import org.jsoup.nodes.*;

import java.io.IOException;

public class Ranking implements Runnable {
    String nick;
    int score;
    private String result;

    public Ranking() {
        nick = "";
        result = "";
        score = 0;
    }

    void setNickAndScore(String nick, int score) {
        this.nick = nick;
        this.score = score;
    }

    @Override
    public void run() {
        result = "";
        try {
            Document document = Jsoup.connect("http://przypomnienia.cba.pl/javaAPP/ranking.php?nick=" + nick + "&score=" + score + "&maxscore=" + Game.highScore).get();
            if (document != null) {
                for (Element actualElement : document.select("tr")) {
                    result += actualElement.select("td#score").text();
                    result += " - ";
                    result += actualElement.select("td#nick").text();
                    result += "\n";
                }
            }
        } catch (IOException e) {
            result = "No connection";
        }
        Game.ranking = result;
    }
}
