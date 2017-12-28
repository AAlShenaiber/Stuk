package com.stuk.game.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stuk.game.Stuk;


/**
 * Created by Abdullah A
 */


public class Box extends Sprite{

    private static Body body;
    private Fixture fixture;
    private TextureRegion boxregion;

    public Box(World world, TiledMap map, Rectangle bounds){

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.DynamicBody;   //DYNAMIC body
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Stuk.PPM, (bounds.getY() + bounds.getHeight() / 2) / Stuk.PPM);
        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / Stuk.PPM, (bounds.getHeight() / 2) / Stuk.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = Stuk.BOX_BIT;                                                                    //"Is a" statement
        fdef.filter.maskBits = Stuk.DEFAULT_BIT | Stuk.ROBO_BIT | Stuk.SPIKE_BIT | Stuk.COIN_BIT | Stuk.DOOR_BIT;   //collides with everything but acid
        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    }

    public void onHit(){
        System.out.println("Box collision");
        //play a sound here, and try updating texture OR don't do it here and update it constantly w/out collision
    }


}
