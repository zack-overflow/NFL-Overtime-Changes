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

  void addWinDiffCol(Scanner reader, BufferedWriter writer, Map<String, Double[]> winsMap) throws IOException{
    reader.nextLine();
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] arr = line.split(",");

      String team1 = arr[1];
      String team2 = arr[2];

      String date = arr[0];
      String[] splitDate = date.split("-");
      int season = Integer.parseInt(splitDate[0]);
      String month = splitDate[1];
      if (month.equals("01")) {
        season--;
      }

      String team1Entry = season + team1;
      String team2Entry = season + team2;

      Double[] team1Data = winsMap.get(team1Entry);
      Double[] team2Data = winsMap.get(team2Entry);

      Double winDiff = team1Data[0] - team2Data[0];
      // Team one's offense compared to team 2's defense
      Double team1Off2Def = team1Data[1] - team2Data[2];
      //Team two's offense compared to team 1's defense
      Double team2Off2Def = team1Data[2] - team2Data[1];

      writer.write(line + "," + winDiff + "," + team1Off2Def + "," + team2Off2Def + "\n");
    }
  }

  public static void main(String[] args)
    {
        AddWinDiff builder = new AddWinDiff();

        try {
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

          for (String entry : winsMap.keySet()) {
            Double[] data = winsMap.get(entry);
            System.out.println(entry + ": " + data[0] + ", " + data[1] + ", " + data[2]);
          }

          Scanner readerWon = new Scanner(new FileInputStream("Data/kickoffWon.csv"));
          BufferedWriter writerWon = new BufferedWriter(new FileWriter("Data/wonAppended.csv"));
          builder.addWinDiffCol(readerWon, writerWon, winsMap);
          writerWon.close();

          Scanner readerLost = new Scanner(new FileInputStream("Data/kickoffLost.csv"));
          BufferedWriter writerLost = new BufferedWriter(new FileWriter("Data/lostAppended.csv"));
          builder.addWinDiffCol(readerLost, writerLost, winsMap);
          writerLost.close();

          Scanner readerWon07 = new Scanner(new FileInputStream("Data/07-11KickoffWon.csv"));
          BufferedWriter writerWon07 = new BufferedWriter(new FileWriter("Data/07-11WonAppended.csv"));
          builder.addWinDiffCol(readerWon07, writerWon07, winsMap);
          writerWon07.close();

          Scanner readerLost07 = new Scanner(new FileInputStream("Data/07-11KickoffLost.csv"));
          BufferedWriter writerLost07 = new BufferedWriter(new FileWriter("Data/07-11LostAppended.csv"));
          builder.addWinDiffCol(readerLost07, writerLost07, winsMap);
          writerLost07.close();

        } catch (IOException e) {
          System.out.println("There was an error reading or writing the files");
        }
    }
}
