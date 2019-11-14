import com.google.gson.*;

import javax.xml.validation.Schema;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLOutput;
import java.util.Scanner;

public class GameStart {

    private static Gson gson = new Gson();

    /**
     * main method for the game.
     * @param args
     */
    public static void main(String[] args) {
        Layout game = new Layout();
        String badMoveInput = "I don't understand '";
        int afterGo = 3;
        boolean validURLGiven = false;

        String badURLInput = "Bad URL input, please input a valid link.";
        //continuously loop through until user inputs a valid URL
        //or exit if user inputs "exit" or "quit"
        while(!validURLGiven) {
            try {
                System.out.println("Enter URL for Game: ");

                String scannedInput = scanInput();

                if (scannedInput.toLowerCase().equals("quit")
                        || scannedInput.toLowerCase().equals("exit")) {
                    return;
                }

                String gsonContents = readJsonFromUrl(scannedInput);
                game = gson.fromJson(gsonContents, Layout.class);
                validURLGiven = true;
            } catch (IOException e) {
                System.out.println(badURLInput);
            } catch (IllegalArgumentException i) {
                System.out.println(badURLInput);
            } catch (JsonSyntaxException j) {
                System.out.println(badURLInput);
            }
        }

        //main game logic block
        while(!game.isEnd()) {
            System.out.println(game.getCurrentRoom().getDescription());

            //updates moves counter, prints beginning message
            incrementMoves(game);

            listAvailableDirections(game);

            String directionInput = scanInput();
            String dirInputLowered = directionInput.toLowerCase();

            if (quitGame(dirInputLowered)) {
                return;
            }

            if (dirInputLowered.startsWith("go ")) {
                String justDir = dirInputLowered.substring(afterGo);
                if (game.returnRoomFromDirection(justDir) == null) {
                    System.out.println("I can't go " + directionInput.substring(afterGo));
                } else {
                    game.setCurrentRoom(game.returnRoomFromDirection(justDir));
                }
            } else {
                System.out.println(badMoveInput + directionInput + "'");
            }
        }

        //this string is declared here in order for game.getMoves()
        //to return the correct number of moves the player has made.
        String endMessage = "Congratulations! You have reached the end. You made "
                + game.getMoves() + " moves. Thank you for playing!";

        System.out.println(game.getCurrentRoom().getDescription());
        System.out.println(endMessage);

        return;
    }

    /**
     * prints out the available directions to move given a game
     * @param game
     */
    public static void listAvailableDirections(Layout game) {
        System.out.print("From here, you can go: ");

        int lastDirectionIndex = game.getCurrentRoom().getDirections().length - 1;

        for (Direction currDir : game.getCurrentRoom().getDirections()) {
            //checks if the current index is the last index in a room's directions array
            if (currDir.equals(game.getCurrentRoom().getDirections()[lastDirectionIndex])) {
                System.out.print(currDir.getDirectionName() + " ");
                break;
            }
            System.out.print(currDir.getDirectionName() + ", ");
        }
        System.out.println("");
    }

    /**
     * increases the moves in each game
     * @param currGame
     */
    public static void incrementMoves(Layout currGame) {
        String beginMessage = "Your journey begins here";

        if (currGame.getMoves() == 0) {
            System.out.println(beginMessage);
            currGame.setMoves(currGame.getMoves() + 1);
        } else {
            currGame.setMoves(currGame.getMoves() + 1);
        }
    }

    /**
     * Adapted from Stack Overflow user: Roland Illig
     * https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java/4308572
     * @param url
     * @return json file as string
     * @throws IOException
     * @throws JsonParseException
     */
    public static String readJsonFromUrl(String url) throws IOException, JsonParseException {
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return jsonText;
        } finally {
            is.close();
        }
    }

    /**
     * helper function for readJsonFromUrl
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;

        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Scanner for user input.
     * Adapted partially from W3Schools.com
     * https://www.w3schools.com/java/java_user_input.asp
     */
    public static String scanInput() {
        Scanner myObj = new Scanner(System.in);
        String userInput;
        userInput = myObj.nextLine();
        return userInput;
    }

    /**
     * helper method for determining if the user wishes to stop playing
     * @param input
     * @return a boolean, true for user input "quit" or "exit"
     */
    public static boolean quitGame(String input) {
        if (input.equals("quit") || input.equals("exit")) {
            return true;
        }
        return false;
    }
}
