package com.stuk.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stuk.game.sprites.Acid;
import com.stuk.game.sprites.Box;
import com.stuk.game.sprites.Coin;
import com.stuk.game.sprites.ExitDoor;
import com.stuk.game.sprites.Ground;
import com.stuk.game.sprites.Spikes;

/**
 * Created by Abdullah A
 */

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){

        //Creating body/fixture variables

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Creating objects bodies/fixtures: (All have same loop, just different indexes)

        //Ground
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){    //get(2) is the index of ground object
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Ground(world, map, rect);
        }

        //Spikes
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Spikes(world, map, rect);
        }

        //Acid
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Acid(world, map, rect);
        }

        //Coins
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(world, map, rect);
        }

        //Exit Door
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new ExitDoor(world, map, rect);
        }

        //Box
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Box(world, map, rect);  //Dynamic body, has category and mask bits to not collide with acid
        }

    }
}
