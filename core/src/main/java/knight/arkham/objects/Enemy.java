package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Enemy extends GameObject {

    private float velocityY;

    private final float speed;

    public int score;


    public Enemy(Rectangle rectangle, World world) {
        super(
            new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody, 10, world, ContactType.ENEMY),
            new TextureRegion(new Texture("images/players.png"))
        );
        speed = 8;
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            velocityY = 1.5f;

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velocityY = -1.5f;

        body.setLinearVelocity(0, velocityY * speed);

        velocityY = 0;
    }
}
