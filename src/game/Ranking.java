package game;

import org.jsoup.*;
import org.jsoup.nodes.*;

import java.io.IOException;

public class Ranking implements Runnable {
    private String nick;
    private int score;
    private int highScore;
    private String result;

    Ranking() {
        nick = "";
        result = "";
        score = 0;
    }

    void setNickAndScore(String nick, int score, int highScore) {
        this.nick = nick;
        this.score = score;
        this.highScore = highScore;
    }

    @Override
    public void run() {
        result = "";
        try {
            Document document = Jsoup.connect("http://przypomnienia.cba.pl/javaAPP/ranking.php?nick=" + nick + "&score=" + score + "&maxscore=" + highScore).get();
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
