package com.stuk.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stuk.game.Stuk;
import com.stuk.game.scenes.Hud;
import com.stuk.game.sprites.Box;
import com.stuk.game.sprites.Robo;
import com.stuk.game.screens.Controller;
import com.stuk.game.tools.B2WorldCreator;

/**
 * Created by Abdullah A
 */

public class PlayScreen implements Screen{
    //Reference to Game, used to set screen
    public Stuk game;

    //PlayScreen variables
    private OrthographicCamera gameCam;
    private Viewport gameView;
    private Hud hud;

    //TiledMap
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D
    private World world;
    private Box2DDebugRenderer b2dr;

    private Robo player;

    //Music
    private Music music;

    //Gamepad
    private Controller controller;


    public PlayScreen(Stuk game){

        this.game = game;
        //Cam to follow user around the map
        gameCam = new OrthographicCamera();

        //FitViewport, maintains aspect ratio on any screen
        gameView = new FitViewport(Stuk.V_WIDTH / Stuk.PPM, Stuk.V_HEIGHT / Stuk.PPM, gameCam);

        //Game HUD
        hud = new Hud(game.batch);

        //Map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("stuk_level1new.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Stuk.PPM);

        //Center the camera
        gameCam.position.set(gameView.getWorldWidth()/2, gameView.getWorldHeight()/2, 0);

        //Box2D
        world = new World(new Vector2(0, -10), true);    //creating Box2D world, where gravity in x is 0 and in y is -10 and allows bodies to sleep (true)

        b2dr = new Box2DDebugRenderer();                 //debug lines

        new B2WorldCreator(world, map);                 //creates bodies and fixtures

        //Creating Robo
        player = new Robo(world);

        //Contact listener
        world.setContactListener(new MyContactListener());

        //Music
        music = Stuk.manager.get("StukMusic.mp3", Music.class);         //Remember this as static might cause android issues
        music.setLooping(true);
        music.play();

        //Controller
        controller = new Controller();      //NEW

    }

    @Override
    public void show() {

    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);        //60f is 60 times a second

        //Update pos of Robo's texture to its body
        player.update(dt);

        //Updating HUD (for countdown and all)
        hud.update(dt);

        //Move camera with Robo
        if(player.currentState != Robo.State.DEAD)              //Move screen with body only if Robos alive. If dead, freeze screen
            gameCam.position.x = player.boxbody.getPosition().x;

        //Update with corrected coordinates
        gameCam.update();

        //Renderer draws what gameCam sees
        renderer.setView(gameCam);
    }

    public void handleInput(float dt){
        if(player.currentState != Robo.State.DEAD) {            //If hes alive then allow movement input. If hes dead no movement input

            /*For Desktop Version Only
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))                                                //if up is pressed move up Vector2(x,y)
                player.boxbody.applyLinearImpulse(new Vector2(0, 12f), player.boxbody.getWorldCenter(), true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.boxbody.getLinearVelocity().x <= 12)    //can change the 2 if it doesn't work, its a max speed
                player.boxbody.applyLinearImpulse(new Vector2(12f, 0), player.boxbody.getWorldCenter(), true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && player.boxbody.getLinearVelocity().x >= -12)    //can change the -2 as well
                player.boxbody.applyLinearImpulse(new Vector2(-12f, 0), player.boxbody.getWorldCenter(), true);
                */

            //NEW FOR GAMEPAD (Android Compatible):
            if(controller.isUpPressed() && player.boxbody.getLinearVelocity().y==0f)                                                //if up is pressed move up Vector2(x,y)
                player.boxbody.applyLinearImpulse(new Vector2(0, 12f), player.boxbody.getWorldCenter(), true);

            //Right/left speed on ground (linear velocity at y is 0)
            if (controller.isRightPressed() && player.boxbody.getLinearVelocity().x < 6f && player.boxbody.getLinearVelocity().y == 0)
                player.boxbody.applyLinearImpulse(new Vector2(6f, 0), player.boxbody.getWorldCenter(), true);
            if (controller.isLeftPressed() && player.boxbody.getLinearVelocity().x > -6f && player.boxbody.getLinearVelocity().y == 0)
                player.boxbody.applyLinearImpulse(new Vector2(-6f, 0), player.boxbody.getWorldCenter(), true);

            //Right/left speed in air (linear velocity at y is not 0)
            if (controller.isRightPressed() && player.boxbody.getLinearVelocity().x < 4f && player.boxbody.getLinearVelocity().y != 0)
                player.boxbody.applyLinearImpulse(new Vector2(5f, 0), player.boxbody.getWorldCenter(), true);
            if (controller.isLeftPressed() && player.boxbody.getLinearVelocity().x > -4f && player.boxbody.getLinearVelocity().y != 0)
                player.boxbody.applyLinearImpulse(new Vector2(-5f, 0), player.boxbody.getWorldCenter(), true);
        }
    }

    @Override
    public void render(float delta) {
        //Update screen
        update(delta);

        //Clear screen w/ black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render game map (including bodies/fixtures)
        renderer.render();

        //Render debug lines
        b2dr.render(world, gameCam.combined);

        //Draw Robo
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //Set batch to draw HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //Gameover screen
        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        //Win screen
        if(win()){
            game.setScreen(new WinScreen(game));
            dispose();
        }

        if(Gdx.app.getType() == Application.ApplicationType.Android) {      //NEW
            controller.draw();
        }

    }

    public boolean gameOver(){
        if(player.currentState == Robo.State.DEAD && player.getStateTimer() > 2) //if hes dead and dead for at least 2 seconds, start gameOver screen
            return true;
        else
            return false;
    }

    public boolean win(){
        if(player.currentState == Robo.State.WON && player.getStateTimer() > 0) //if he won and won, instantly (>0 seconds go win screen)
            return true;
        else
            return false;
    }

    @Override
    public void resize(int width, int height) {
        gameView.update(width, height);
        controller.resize(width, height);       //NEW
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
