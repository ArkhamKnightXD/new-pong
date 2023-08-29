package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Pong;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.objects.Ball;
import knight.arkham.objects.Player;
import knight.arkham.objects.Wall;

import static knight.arkham.helpers.Constants.*;

public class GameScreen extends ScreenAdapter {
    private final Pong game;
    private final Player player;
    private final Player enemy;
    private final Ball ball;
    private final Wall bottomWall;
    private final Wall topWall;
    private final OrthographicCamera camera;
    private final World world;
    private final TextureRegion[] scoreNumbers;
    private final Music music;
    private final Sound winSound;

    private float accumulator;
    private final float TIME_STEP;

    public GameScreen(boolean isNewGame) {

        game = Pong.INSTANCE;

        camera = game.camera;

        TIME_STEP = 1/240f;

        world = new World(new Vector2(0, 0), true);

        GameContactListener contactListener = new GameContactListener(this);

        world.setContactListener(contactListener);

        player = new Player(new Rectangle(490, 600, 16, 64), world, true);
        enemy = new Player(new Rectangle(1430,600, 16, 64), world, false);

        if (!isNewGame)
            GameDataHelper.loadGameData(player, enemy);

        ball = new Ball(new Rectangle(1000,600, 20, 20), this);

        topWall = new Wall(new Rectangle(game.screenWidth,930, game.screenWidth, 64), world);
        bottomWall = new Wall(new Rectangle(game.screenWidth,350, game.screenWidth, 64), world);

        scoreNumbers = loadTextureSprite();

        music = AssetsHelper.loadMusic("epic.wav");

        music.play();
        music.setLooping(true);
        music.setVolume(0.3f);

        winSound = AssetsHelper.loadSound("win.wav");
    }

    private TextureRegion[] loadTextureSprite(){

        Texture textureToSplit = new Texture("images/numbers.png");

        return TextureRegion.split(textureToSplit, textureToSplit.getWidth() / 10, textureToSplit.getHeight())[0];
    }


    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    private void update(){

        player.update();
        enemy.update();
        ball.update();

        setGameOverScreen();

        manageGameData();

        game.manageExitTheGame();
    }

    private void manageGameData() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
            GameDataHelper.saveGameData(player.score, enemy.score);
    }

    private void setGameOverScreen() {

        if (player.score > 4 || enemy.score > 4){

            winSound.play();
            game.setScreen(new MainMenuScreen());
        }
    }


    @Override
    public void render(float deltaTime) {

        update();

        draw();

//        It is recommended that you render all your graphics before you do your physics step,
//        otherwise it will be out of sync.
        calculatePhysicsTimeStep(deltaTime);
    }

    private void calculatePhysicsTimeStep(float deltaTime) {

        // max frame time to avoid spiral of death (on slow devices)
        //The minimum frame that my game can have if 0.25.
        // This means that the game is running at 4 fps, and for avoid the spiral of death
        // I have to cap to this minimum fps if the device can't run the game with a performance above 4 fps.
        float frameTime = Math.min(deltaTime, 0.25f);

        accumulator += frameTime;

        // fixed time step. This while loop gives a more stable simulation independent of the frame rate of the screen.
        //And the performance that the devices could have. This loop avoid that the step are calculated every frame.
//      // Instead, the step are going to be calculated while the condition is true.
        while(accumulator >= TIME_STEP) {

            world.step(TIME_STEP, 6,2);
            accumulator -= TIME_STEP;
        }
    }


    private void draw() {

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        topWall.draw(game.batch);

//        El orden importa debido a que draw esta después de top-wall, este se renderizara encima de él y no detrás
        drawScoreNumbers(game.batch, player.score, 500);

        drawScoreNumbers(game.batch, enemy.score, 1380);

        bottomWall.draw(game.batch);

        player.draw(game.batch);
        ball.draw(game.batch);
        enemy.draw(game.batch);

        game.batch.end();
    }

    private void drawScoreNumbers(SpriteBatch batch, int scoreNumber, float x){

        final float width = 48;
        final float height = 64;

        if (scoreNumber < 10)
            batch.draw(scoreNumbers[scoreNumber], x/PIXELS_PER_METER, 900/PIXELS_PER_METER,
                width/PIXELS_PER_METER , height/PIXELS_PER_METER);

        else {

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(0, 1))], x/PIXELS_PER_METER,
                900/PIXELS_PER_METER , width/PIXELS_PER_METER , height/PIXELS_PER_METER);

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(1, 2))], x/PIXELS_PER_METER +20/PIXELS_PER_METER,
                900/PIXELS_PER_METER, width/PIXELS_PER_METER, height/PIXELS_PER_METER);
        }
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        topWall.dispose();
        bottomWall.dispose();
        player.dispose();
        ball.dispose();
        enemy.dispose();
        music.dispose();
    }

    public Ball getBall() {return ball;}

    public Player getPlayer() {return player;}

    public Player getEnemy() {return enemy;}

    public World getWorld() {return world;}
}
