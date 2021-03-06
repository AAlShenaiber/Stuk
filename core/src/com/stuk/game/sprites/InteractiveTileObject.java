package com.stuk.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stuk.game.Stuk;

/**
 * Created by Abdullah A
 */

public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Body body;
    protected Rectangle bounds;
    protected Fixture fixture;

    //Creates static body and fixture
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Stuk.PPM, (bounds.getY() + bounds.getHeight() / 2) / Stuk.PPM);
        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / Stuk.PPM, (bounds.getHeight() / 2) / Stuk.PPM);    //center
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onHit();

    public void setCategoryFilter(short catBits){
        Filter filter = new Filter();
        filter.categoryBits = catBits;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);   //layer 1 is graphics layer (where coins and all are)
        return layer.getCell((int)(body.getPosition().x * Stuk.PPM / 256), (int)(body.getPosition().y * Stuk.PPM / 256));
    }
}
