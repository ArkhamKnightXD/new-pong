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

public class Player extends GameObject {

    private float velocityY;

    private final float speed;

    public int score;


    public Player(Rectangle rectangle, World world) {
        super(
            new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody, 10, world, ContactType.PLAYER),
            new TextureRegion(new Texture("images/players.png"))
        );
        speed = 10;
        score = 0;
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            velocityY = 1.5f;

        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            velocityY = -1.5f;

//        indicar velocidad lineal es otra forma de dar movimiento a un objeto o player, dependiendo del contexto,
//        esta forma es mejor que aplicar impulso lineal
        body.setLinearVelocity(0, velocityY * speed);

//        Debo de setear la velocidad en 0 para que el objeto se detenga cuando dejo de presionar los botones.
        velocityY = 0;
    }
}
