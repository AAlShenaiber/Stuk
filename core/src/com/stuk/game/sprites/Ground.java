package com.stuk.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stuk.game.Stuk;

/**
 * Created by Abdullah A
 */

public class Ground {

    private Body body;

    public Ground(World world, TiledMap map, Rectangle bounds){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Stuk.PPM, (bounds.getY() + bounds.getHeight() / 2) / Stuk.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / Stuk.PPM, (bounds.getHeight() / 2) / Stuk.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
