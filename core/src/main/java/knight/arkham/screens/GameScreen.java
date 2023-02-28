package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.Pong;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.objects.Ball;
import knight.arkham.objects.Enemy;
import knight.arkham.objects.Player;
import knight.arkham.objects.StaticStructure;

import static knight.arkham.helpers.Constants.*;
import static knight.arkham.helpers.Constants.BOX2D_FULL_SCREEN_HEIGHT;

public class GameScreen extends ScreenAdapter {

    private final Pong game;
    private final Player player;
    private final Enemy enemy;

    private final Ball ball;
    private final StaticStructure bottomWall;
    private final StaticStructure topWall;
    private final OrthographicCamera camera;

    private final Viewport viewport;
    private final World world;
    private final TextureRegion[] scoreNumbers;


    public GameScreen() {

        game = Pong.INSTANCE;

        world = new World(new Vector2(0, 0), true);

        GameContactListener contactListener = new GameContactListener(this);

        world.setContactListener(contactListener);

        player = new Player(new Rectangle(490, 600, 16, 64), world);
        enemy = new Enemy(new Rectangle(1430,600, 16, 64), world);
        ball = new Ball(new Rectangle(1000,600, 32, 32), this);

        topWall = new StaticStructure(new Rectangle(FULL_SCREEN_WIDTH,930, FULL_SCREEN_WIDTH, 64), world);
        bottomWall = new StaticStructure(new Rectangle(FULL_SCREEN_WIDTH,350, FULL_SCREEN_WIDTH, 64), world);

        camera = new OrthographicCamera();

        viewport = new FitViewport(BOX2D_FULL_SCREEN_WIDTH, BOX2D_FULL_SCREEN_HEIGHT, camera);

        camera.position.set(BOX2D_FULL_SCREEN_WIDTH, BOX2D_FULL_SCREEN_HEIGHT, 0);

        scoreNumbers = loadTextureSprite();
    }

    private TextureRegion[] loadTextureSprite(){

        Texture textureToSplit = new Texture("images/numbers.png");

        return TextureRegion.split(textureToSplit, textureToSplit.getWidth() / 10, textureToSplit.getHeight())[0];
    }


    private void drawScoreNumbers(SpriteBatch batch, int scoreNumber, float x, float y, float width, float heigth){

        if (scoreNumber < 10)
            batch.draw(scoreNumbers[scoreNumber], x, y, width, heigth);

        else {

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(0, 1))], x, y, width, heigth);

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(1, 2))], x+20/PIXELS_PER_METER, y, width, heigth);
        }
    }


    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
    }

    private void update(){

        world.step(1 / 60f, 6, 2);

        player.update();
        enemy.update();
        ball.update();

        game.manageExitTheGame();
    }


    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        topWall.draw(game.batch);

//        El orden importa debido a que draw esta después de top-wall, este se renderizara encima de él y no detrás
        drawScoreNumbers(game.batch, player.score, 500/PIXELS_PER_METER , 900/PIXELS_PER_METER,
            48/PIXELS_PER_METER, 64/PIXELS_PER_METER);

        drawScoreNumbers(game.batch, enemy.score, 1380/PIXELS_PER_METER , 900/PIXELS_PER_METER,
            48/PIXELS_PER_METER, 64/PIXELS_PER_METER);


        bottomWall.draw(game.batch);

        player.draw(game.batch);
        ball.draw(game.batch);
        enemy.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        topWall.getSprite().dispose();
        bottomWall.getSprite().dispose();
        player.getSprite().dispose();
        ball.getSprite().dispose();
        enemy.getSprite().dispose();
    }

    public Ball getBall() {return ball;}

    public Player getPlayer() {return player;}

    public Enemy getEnemy() {return enemy;}

    public World getWorld() {return world;}
}
