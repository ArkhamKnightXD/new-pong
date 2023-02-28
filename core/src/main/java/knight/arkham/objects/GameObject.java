package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public abstract class GameObject {

    protected final Body body;
    private final Rectangle bounds;
    private final TextureRegion actualRegion;

    protected GameObject(Box2DBody gameObjectStructure, TextureRegion region) {
        body = Box2DHelper.createBody(gameObjectStructure).getBody();
        bounds = gameObjectStructure.bounds;
        actualRegion = region;
    }

    private Rectangle getBoundsWithPPMCalculation(){

        return new Rectangle(
                body.getPosition().x - (bounds.width / 2 / PIXELS_PER_METER),
                body.getPosition().y - (bounds.height / 2 / PIXELS_PER_METER),
                bounds.width / PIXELS_PER_METER,
                bounds.height / PIXELS_PER_METER
        );
    }

    public void draw(Batch batch) {

        Rectangle actualBounds = getBoundsWithPPMCalculation();

        batch.draw(actualRegion, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    protected Vector2 getActualPosition() {

        return new Vector2(body.getPosition().x * PIXELS_PER_METER,body.getPosition().y * PIXELS_PER_METER);
    }

    public Texture getSprite() {return actualRegion.getTexture();}
}
