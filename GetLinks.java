import java.util.*;
import java.io.*;

public class GetLinks {

   Map<String, String> getLinks() throws IOException{
       Map<String, String> links = new HashMap<>();
       Scanner reader = new Scanner(new FileInputStream("Data/playData.html"));
       reader.nextLine();
       String baseUrl = "https://www.pro-football-reference.com/boxscores/";
       while (reader.hasNextLine()) {
           String line = reader.nextLine();
           String[] arr = line.split(">");
           String aTag = arr[2];
           String urlAdd = aTag.substring(20, aTag.length() - 1);
           String url = baseUrl + urlAdd;

           String date = arr[3].substring(0, 10);
           String team1 = arr[7].substring(0, arr[7].length() - 3);
           String team2 = arr[11].substring(0, arr[11].length() - 3);
           String entry = date + "/" + team1 + "/" + team2;

           links.put(entry, url);
       }

       return links;

   }

    public static void main(String[] args)
    {
        try {
            GetLinks solution = new GetLinks();
            Map<String, String> links = solution.getLinks();

            for (String link : links.keySet()) {
                System.out.println(link + ": " + links.get(link));
            }
        } catch (Exception e){
            System.out.println("Error in getting links");
        }

    }
}
