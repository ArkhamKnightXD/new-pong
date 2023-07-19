package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Player extends GameObject {
    private float velocityY;
    public int score;

    public Player(Rectangle rectangle, World world) {
        super(
            new Box2DBody(rectangle, 10, world, ContactType.PLAYER),
            "images/players.png", 10
        );
        score = 0;
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            velocityY = 1.5f;

        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            velocityY = -1.5f;

//        indicar velocidad lineal es otra forma de dar movimiento a un objeto o player, dependiendo del contexto,
//        esta forma es mejor que aplicar impulso lineal
        body.setLinearVelocity(0, velocityY * actualSpeed);

//        Debo de setear la velocidad en 0 para que el objeto se detenga cuando dejo de presionar los botones.
        velocityY = 0;
    }
}
