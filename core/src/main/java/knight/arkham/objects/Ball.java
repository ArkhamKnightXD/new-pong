package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;
import knight.arkham.screens.GameScreen;

import static knight.arkham.helpers.Constants.*;

public class Ball extends GameObject {
    private final Vector2 velocity;
    private final float speed;

    private final GameScreen actualGameScreen;


    public Ball(Rectangle rectangle, GameScreen gameScreen) {

        super(
//            Le doy poca densidad, pues si le doy 1 esta empujará a mi player y enemy de lugar
            new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody, 0.1f, gameScreen.getWorld(), ContactType.BALL),
            new TextureRegion(new Texture("images/white.png"))
        );

        velocity = new Vector2(getRandomDirection(), getRandomDirection());
        speed = 6;
        actualGameScreen = gameScreen;
    }

    private float getRandomDirection(){

        return (Math.random() < 0.5) ? 1 : -1;
    }

    private void resetBallPosition(){
        velocity.set(getRandomDirection(), getRandomDirection());

        body.setTransform(1000/ PIXELS_PER_METER,600/ PIXELS_PER_METER,0);
    }

    public void update(){

        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);

        if (getActualPosition().x > 1450){
            actualGameScreen.getPlayer().score += 1;
            resetBallPosition();
        }

        if (getActualPosition().x < 470){
            actualGameScreen.getEnemy().score += 1;
            resetBallPosition();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R))
            resetBallPosition();
    }

    public void reverseVelocityX(){
        velocity.x *= -1;
    }

    public void reverseVelocityY(){
        velocity.y *= -1;
    }

    public void incrementXVelocity(){
        velocity.x *= 1.1f;
    }
}
