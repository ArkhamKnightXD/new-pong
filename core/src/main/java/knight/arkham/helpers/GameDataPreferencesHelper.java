package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;

public class GameDataPreferencesHelper {

    public static void saveGameData(int player1Score, int player2Score){

        Preferences preferences = Gdx.app.getPreferences("pong-data");

        preferences.putInteger("player1Score", player1Score);
        preferences.putInteger("player2Score", player2Score);

        preferences.flush();
    }

    public static void loadGameData(Player player, Enemy enemy){

        Preferences preferences = Gdx.app.getPreferences("pong-data");

        player.score = preferences.getInteger("player1Score");
        enemy.score = preferences.getInteger("player2Score");
    }
}
