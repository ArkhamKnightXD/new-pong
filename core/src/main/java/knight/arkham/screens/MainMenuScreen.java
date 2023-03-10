package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Pong;

import static knight.arkham.helpers.Constants.*;

public class MainMenuScreen extends ScreenAdapter {

    private final Pong game;

    public MainMenuScreen() {

        game = Pong.INSTANCE;
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);

        game.batch.begin();

        game.font.draw(game.batch, "Welcome to Pong!!! ", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT);

        game.font.draw(game.batch, "Press Enter To Play", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-25);

        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
            game.setScreen(new GameScreen());
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
