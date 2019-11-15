import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.io.*;

public class Scrape {

    void scrape(Map<String, String> links) {

        for (String entry : links.keySet()) {
            String url = links.get(entry);

            Document doc = null;

            try {
                doc = Jsoup.connect(url).get();
                Element element = doc.getElementById("game_info");
                String crazyJson = doc.getElementById("all_game_info").toString();
                String[] arr = crazyJson.split("Won OT Toss</th><td class=\"center \" data-stat=\"stat\" >");
                String teamWonToss = arr[1].split("<")[0];
                links.put(entry, teamWonToss);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args)
    {
        try {
            GetLinks linkGetter = new GetLinks();
            Scrape scrape = new Scrape();

            Map<String, String> links = linkGetter.getLinks();
            scrape.scrape(links);
            System.out.println(links.size());


        } catch (Exception e){
            System.out.println("Error Error sad sad");
        }
    }
}
