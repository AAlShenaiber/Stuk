package com.stuk.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stuk.game.Stuk;

/**
 * Created by Abdullah A
 */

public class ExitDoor extends InteractiveTileObject {
    public ExitDoor(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Stuk.DOOR_BIT);
    }

    @Override
    public void onHit() {
        System.out.println("Door Collision");  //remove later
    }
}
