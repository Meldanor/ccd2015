package jchess.gamelogic;

import jchess.Moves;
import jchess.Player;
import jchess.Settings;
import jchess.exceptions.ReadGameException;

import java.io.*;
import java.util.Calendar;

/**
 * Created by stephan on 10.11.2015.
 */
public class GameLoader {

    /*
    * todo: safe GameStateHistory
     */
    static public void saveGame(File path, Settings settings, Moves moves) throws IOException{
        File file = path;
        FileWriter fileW = null;
       try {
            fileW = new FileWriter(file);
       } catch (java.io.IOException exc) {
                throw new IOException("error_writing_to_file");
        }
        Calendar cal = Calendar.getInstance();
        String str = new String("");
        String info = new String("[Event \"Game\"]\n[Date \"" + cal.get(Calendar.YEAR) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.DAY_OF_MONTH) + "\"]\n"
                + "[White \"" + settings.playerWhite.getName() + "\"]\n[Black \"" + settings.playerBlack.getName() + "\"]\n\n");
        str += info;
        str += moves.getMovesInString();
        try {
            fileW.write(str);
            fileW.flush();
            fileW.close();
         } catch (java.io.IOException exc) {
            throw new IOException("error_writing_to_file");
        }
    }
    /*
    * todo: load GameStateHistory into new GameLogic
    * */
    static public Settings loadGame(File file) throws ReadGameException, FileNotFoundException {
        FileReader fileR = null;
        fileR = new FileReader(file);

        BufferedReader br = new BufferedReader(fileR);
        String tempStr = new String();
        String blackName, whiteName;

        tempStr = getLineWithVar(br, new String("[White"));
        whiteName = getValue(tempStr);
        tempStr = getLineWithVar(br, new String("[Black"));
        blackName = getValue(tempStr);
        tempStr = getLineWithVar(br, new String("1."));

        Settings locSetts = new Settings();
        locSetts.playerBlack.setName(blackName);
        locSetts.playerWhite.setName(whiteName);
        locSetts.playerBlack.setType(Player.playerTypes.localUser);
        locSetts.playerWhite.setType(Player.playerTypes.localUser);
        locSetts.gameMode = Settings.gameModes.loadGame;
        locSetts.gameType = Settings.gameTypes.local;
        locSetts.movesString = tempStr;
        return locSetts;

    }

    /**
     * Method checking in with of line there is an error
     *
     * @param br     BufferedReader class object to operate on
     * @param srcStr String class object with text which variable you want to get in file
     * @return String with searched variable in file (whole line)
     * @throws ReadGameException class object when something goes wrong when reading file
     */
    static public String getLineWithVar(BufferedReader br, String srcStr) throws ReadGameException {
        String str = new String();
        while (true) {
            try {
                str = br.readLine();
            } catch (java.io.IOException exc) {
                System.out.println("Something wrong reading file: " + exc);
            }
            if (str == null) {
                throw new ReadGameException();
            }
            if (str.startsWith(srcStr)) {
                return str;
            }
        }
    }

    /**
     * Method to get value from loaded txt line
     *
     * @param line Line which is readed
     * @return result String with loaded value
     * @throws ReadGameException object class when something goes wrong
     */
    static public String getValue(String line) throws ReadGameException {
        //System.out.println("getValue called with: "+line);
        int from = line.indexOf("\"");
        int to = line.lastIndexOf("\"");
        int size = line.length() - 1;
        String result = new String();
        if (to < from || from > size || to > size || to < 0 || from < 0) {
            throw new ReadGameException();
        }
        try {
            result = line.substring(from + 1, to);
        } catch (StringIndexOutOfBoundsException exc) {
            System.out.println("error getting value: " + exc);
            return "none";
        }
        return result;
    }
}
