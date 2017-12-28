package com.stuk.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuk.game.screens.PlayScreen;

//Tiles credits: http://www.gameart2d.com/free-sci-fi-platformer-tileset.html
//Sprite credits: http://www.gameart2d.com/the-robot---free-sprites.html

/**
 * Created by Abdullah A
 */

/*TODO: Adjust game pad positioning for android res compatibility
  TODO: Update box textures to its position and remove grid lines
  TODO: Add score to winning screen and level selection
 */
public class Stuk extends Game {
	public static final int V_WIDTH = 3250;				//Virtual width and height
	public static final int V_HEIGHT = 3250;			//(Almost) full map height: 3250
	public static final float PPM = 100;				//pixels per meter

	public static final short NOTHING_BIT = 0;
	public static final short DEFAULT_BIT = 1;
	public static final short ROBO_BIT = 2;
	public static final short COIN_BIT = 4;
	public static final short SPIKE_BIT = 8;
	public static final short ACID_BIT = 16;
	public static final short DOOR_BIT = 32;
	public static final short BOX_BIT = 64;
	public static final short USED_BIT = 128;			//for used coins to disappear

	public SpriteBatch batch;

	//AssetManager in static may cause issues with android compatibility
	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("StukMusic.mp3", Music.class);
		manager.load("StukCoinSound.wav", Sound.class);
		manager.load("StukGameOver.mp3", Sound.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
		manager.update();
	}
}
