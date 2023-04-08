package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.io.*;
import java.util.Scanner;

public class GameDataHelper {

    public static void savePlayerData(String playerData, String filenameToSave){

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("data/" + filenameToSave + ".txt"));
            writer.write(playerData);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scanner loadFileData(String filenameToLoad) {

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("data/" + filenameToLoad + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return scanner;
    }

    public static Vector2 loadPlayerData(String filePath){

        Scanner playerData = loadFileData(filePath);

        Vector2 savedScore = new Vector2();

        while (playerData.hasNextLine()) {

            String position = playerData.nextLine();

            Gdx.app.log("pos", position);

            int lastCharacter = position.length();

            if (position.contains("Player1:"))
                savedScore.x = Float.parseFloat(position.substring(9, lastCharacter));

            else
                savedScore.y = Float.parseFloat(position.substring(9, lastCharacter));
        }

        playerData.close();

        return savedScore;
    }
}
