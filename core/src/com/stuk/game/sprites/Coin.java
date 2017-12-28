package com.stuk.game.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stuk.game.Stuk;
import com.stuk.game.scenes.Hud;

/**
 * Created by Abdullah A
 */

public class Coin extends InteractiveTileObject {

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Stuk.COIN_BIT);
    }

    @Override
    public void onHit() {
        System.out.println("Coin Collision");  //remove later
        setCategoryFilter(Stuk.USED_BIT);      //when collided with, its used up
        getCell().setTile(null);               //null = cell texture disappears
        Hud.addCoins(1);                       //add 1 to coin HUD
        Stuk.manager.get("StukCoinSound.wav", Sound.class).play();     //plays sound
    }
}
