package com.stuk.game.sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stuk.game.Stuk;

/**
 * Created by Abdullah A
 */

public class Spikes extends InteractiveTileObject {

    public Spikes(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Stuk.SPIKE_BIT);
    }

    @Override
    public void onHit() {
        System.out.println("Spike Collision");  //remove later

        Stuk.manager.get("StukMusic.mp3", Music.class).stop();
        Stuk.manager.get("StukGameOver.mp3", Sound.class).play();     //plays sound
    }
}
