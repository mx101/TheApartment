import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Testing class for Layout and GameStart
 */
public class LayoutTest {

    private Layout gameLayoutTest = new Layout();
    private Gson gson = new Gson();
    GameStart gameStartTest = new GameStart();
    String gsonContents;

    @Before
    public void setup() throws IOException {
        gsonContents = GameStart.readJsonFromUrl("https://courses.grainger.illinois.edu/cs126/fa2019/assignments/siebel.json");
        gameLayoutTest = gson.fromJson(gsonContents, Layout.class);
    }

    @Test
    public void canGetStartingRoom() throws Exception {
        assertEquals("MatthewsStreet", gameLayoutTest.getStartingRoom());
    }

    @Test
    public void canSetGetCurrentRoom() throws Exception {
        gameLayoutTest.setCurrentRoom(gameLayoutTest.returnRoomFromName("Siebel1112"));
        Room roomTest = gameLayoutTest.returnRoomFromName("Siebel1112");
        assertEquals(roomTest, gameLayoutTest.getCurrentRoom());
    }

    @Test
    public void canReturnRoomByName() throws Exception {
        assertEquals("AcmOffice", gameLayoutTest.returnRoomFromName("AcmOffice").getName());
    }

    @Test
    public void badReturnGetRoomByName() throws Exception {
        assertEquals(null, gameLayoutTest.returnRoomFromName("ECEB"));
    }

    @Test
    public void canReturnRoomGivenDirection() throws Exception {
        gameLayoutTest.setCurrentRoom(gameLayoutTest.returnRoomFromName("MatthewsStreet"));
        assertEquals("SiebelEntry", gameLayoutTest.returnRoomFromDirection("East").getName());
    }

    @Test
    public void badDirectionGiven() throws Exception {
        gameLayoutTest.setCurrentRoom(gameLayoutTest.returnRoomFromName("MatthewsStreet"));
        assertEquals(null, gameLayoutTest.returnRoomFromDirection("up"));
    }

    @Test
    public void canGetMovesAtBeginning() throws Exception {
        assertEquals(0, gameLayoutTest.getMoves());
    }

    @Test
    public void trueIsEnd() throws Exception {
        gameLayoutTest.setCurrentRoom(gameLayoutTest.returnRoomFromName("Siebel1314"));
        assertEquals(true, gameLayoutTest.isEnd());
    }

    @Test
    public void falseIsEnd() throws Exception {
        gameLayoutTest.setCurrentRoom(gameLayoutTest.returnRoomFromName("Siebel1112"));
        assertEquals(false, gameLayoutTest.isEnd());
    }

    /**
     * Please note this test will return an error because of differences in line separators,
     * not the actual file contents.
     * reader contents were adapted from code provided by user 0x6C38 on Stack Overflow
     * https://stackoverflow.com/questions/16027229/reading-from-a-text-file-and-storing-in-a-string
     * @throws Exception
     */
    @Test
    public void canReadJsonFromUrl() throws Exception {
        String filepath = new File("").getAbsolutePath();
        boolean error = false;
        BufferedReader br = new BufferedReader(new FileReader(filepath + "/src/test/test_resources/adventure.txt"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");line = br.readLine();
        }

        assertEquals(sb.toString(), gsonContents);
    }

    @Test
    public void canQuitGame() throws Exception {
        boolean boolToCompare = gameStartTest.quitGame("quit");
        assertEquals(true, boolToCompare);
    }

    @Test
    public void doNotQuitGame() throws Exception {
        boolean boolToCompare = gameStartTest.quitGame("Segmentation Fault");
        assertEquals(false, boolToCompare);
    }

}
