import java.util.*;
import java.io.*;

public class AddWinDiff {

  Map<String, Double[]> buildResultsMap(Scanner[] scanners) {
    Map<String, Double[]> winsMap = new HashMap<>();

    try {
      int season = 2007;
      for (Scanner scanner : scanners) {
        processScanner(scanner, winsMap, season);
        season++;
      }
    } catch (Exception e) {
      System.out.println("There was an error on the play");
      System.out.println(e);
    }

    return winsMap;
  }

  void processScanner(Scanner reader, Map<String, Double[]> winsMap, int season) throws FileNotFoundException{
    reader.nextLine();
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] arr = line.split(",");

      String teamName = arr[0];
      String[] teamArr = teamName.split(" ");
      String teamNameNoLoc = teamArr[(teamArr.length - 1)];

      Double offRating = Double.parseDouble(arr[arr.length - 2]);
      Double defRating = Double.parseDouble(arr[arr.length - 1]);

      Double wins = (Integer.parseInt(arr[1]) * 1.0);
      String entry = season + teamNameNoLoc;

      Double[] teamData = new Double[]{wins, offRating, defRating};

      winsMap.put(entry, teamData);
    }
  }

  Map<String, String> getTeamAbb() {
      Map<String, String> teamAbbreviations = new HashMap<>();
      teamAbbreviations.put("ARI", "Cardinals");
      teamAbbreviations.put("ATL", "Falcons");
      teamAbbreviations.put("BAL", "Ravens");
      teamAbbreviations.put("BUF", "Bills");
      teamAbbreviations.put("CAR", "Panthers");
      teamAbbreviations.put("CHI", "Bears");
      teamAbbreviations.put("CIN", "Bengals");
      teamAbbreviations.put("CLE", "Browns");
      teamAbbreviations.put("DAL", "Cowboys");
      teamAbbreviations.put("DEN", "Broncos");
      teamAbbreviations.put("DET", "Lions");
      teamAbbreviations.put("GNB", "Packers");
      teamAbbreviations.put("HOU", "Texans");
      teamAbbreviations.put("IND", "Colts");
      teamAbbreviations.put("JAX", "Jaguars");
      teamAbbreviations.put("KAN", "Chiefs");
      teamAbbreviations.put("SDG", "Chargers");
      teamAbbreviations.put("STL", "Rams");
      teamAbbreviations.put("MIA", "Dolphins");
      teamAbbreviations.put("MIN", "Vikings");
      teamAbbreviations.put("NWE", "Patriots");
      teamAbbreviations.put("NOR", "Saints");
      teamAbbreviations.put("NYG", "Giants");
      teamAbbreviations.put("NYJ", "Jets");
      teamAbbreviations.put("OAK", "Raiders");
      teamAbbreviations.put("PHI", "Eagles");
      teamAbbreviations.put("PIT", "Steelers");
      teamAbbreviations.put("SEA", "Seahawks");
      teamAbbreviations.put("SFO", "49ers");
      teamAbbreviations.put("TAM", "Buccaneers");
      teamAbbreviations.put("TEN", "Titans");
      teamAbbreviations.put("WAS", "Redskins");

      return teamAbbreviations;
  }

  void addToData(Scanner reader, BufferedWriter writer, Map<String, String> tossMap, Map<String, Double[]> winsMap) throws IOException{

    Map<String, String> teamAbb = getTeamAbb();
    writer.write("Rk,Tm,Year,Date,Time,LTime,,Opp,Week,G#,Day,Result,OT" + ",WinDiff,CombinedRating,OTToss" + "\n");

    reader.nextLine();
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] arr = line.split(",");

      String team1 = teamAbb.get(arr[1]);
      String team2 = teamAbb.get(arr[7]);

      String season = arr[2];
      String date = arr[3];

      String team1Entry = season + team1;
      String team2Entry = season + team2;

      Double[] team1Data = winsMap.get(team1Entry);
      Double[] team2Data = winsMap.get(team2Entry);

      String gameEntry = date + "/" + team1 + "/" + team2;
      String wonToss = tossMap.get(gameEntry);
      if (wonToss == null) {
          String gameEntryRev = date + "/" + team2 + "/" + team1;
          wonToss = tossMap.get(gameEntryRev);
          if (wonToss == null) {
              System.out.println("Didn't find:" + gameEntry + "or " + gameEntryRev);
          }
      }

      Double winDiff = team1Data[0] - team2Data[0];

      // Team one's offense compared to team 2's defense
      Double team1Off2Def = team1Data[1] - team2Data[2];
      //Team two's offense compared to team 1's defense
      Double team2Off1Def = team2Data[1] - team1Data[2];
      Double combinedRating = team1Off2Def - team2Off1Def;

      writer.write(line + "," + winDiff + "," + combinedRating + "," + wonToss + "\n");
    }
  }

  public static void main(String[] args)
    {
        AddWinDiff builder = new AddWinDiff();

        GetLinks linkGetter = new GetLinks();
        Scrape scrape = new Scrape();

        try {
          Map<String, String> links = linkGetter.getLinks();
          scrape.scrape(links);

          Scanner reader7 = new Scanner(new FileInputStream("Data/2007res.csv"));
          Scanner reader8 = new Scanner(new FileInputStream("Data/2008res.csv"));
          Scanner reader9 = new Scanner(new FileInputStream("Data/2009res.csv"));
          Scanner reader10 = new Scanner(new FileInputStream("Data/2010res.csv"));
          Scanner reader11 = new Scanner(new FileInputStream("Data/2011res.csv"));
          Scanner reader12 = new Scanner(new FileInputStream("Data/2012res.csv"));
          Scanner reader13 = new Scanner(new FileInputStream("Data/2013res.csv"));
          Scanner reader14 = new Scanner(new FileInputStream("Data/2014res.csv"));
          Scanner reader15 = new Scanner(new FileInputStream("Data/2015res.csv"));
          Scanner reader16 = new Scanner(new FileInputStream("Data/2016res.csv"));
          Scanner[] scanners = new Scanner[]{reader7, reader8, reader9, reader10, reader11, reader12, reader13, reader14, reader15, reader16};
          Map<String, Double[]> winsMap = builder.buildResultsMap(scanners);

          // coin toss
          for (String entry : links.keySet()) {
            System.out.println(entry + ": " + links.get(entry));
          }
          System.out.println(links.size());

          // wins, offRating, defRating
          for (String entry : winsMap.keySet()) {
            Double[] data = winsMap.get(entry);
            System.out.println(entry + ": " + data[0] + ", " + data[1] + ", " + data[2]);
          }

          Scanner readerNew = new Scanner(new FileInputStream("Data/07-11.csv"));
          BufferedWriter writerPre = new BufferedWriter(new FileWriter("Data/07-11Combined.csv"));
          builder.addToData(readerNew, writerPre, links, winsMap);
          writerPre.close();

          Scanner readerNewPost = new Scanner(new FileInputStream("Data/12-16.csv"));
          BufferedWriter writerPost = new BufferedWriter(new FileWriter("Data/12-16Combined.csv"));
          builder.addToData(readerNewPost, writerPost, links, winsMap);
          writerPost.close();

        } catch (IOException e) {
          System.out.println("There was an error reading or writing the files");
        }
    }
}
