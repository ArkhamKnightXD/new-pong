package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.screens.MainMenuScreen;

import static knight.arkham.helpers.Constants.*;

public class Pong extends Game {
    public static Pong INSTANCE;
    public SpriteBatch batch;
    public int screenWidth;
    public int screenHeight;

    public OrthographicCamera camera;
    public Viewport viewport;

    public Pong() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();

        viewport = new FitViewport(screenWidth / PIXELS_PER_METER, screenHeight / PIXELS_PER_METER, camera);

        camera.position.set(screenWidth / PIXELS_PER_METER, screenHeight / PIXELS_PER_METER, 0);

        batch = new SpriteBatch();

        setScreen(new MainMenuScreen());
    }


    public void manageExitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }
}
