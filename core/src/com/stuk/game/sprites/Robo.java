package com.stuk.game.sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.stuk.game.Stuk;
import com.stuk.game.screens.MyContactListener;

/**
 * Created by Abdullah A
 */

public class Robo extends Sprite{
    public enum State{ IDLE, RUNNING, JUMPING, FALLING, DEAD, WON };
    public State currentState;
    public State previousState;
    public World world;
    public Body boxbody;
    public TextureRegion roboIdle;
    private Animation<TextureRegion> roboRun;
    private Animation<TextureRegion> roboJump;
    private float stateTimer;
    private boolean runningRight;
    public boolean roboIsDead;
    public boolean roboWon;

    public Robo(World world){
        this.world = world;
        defineRobo();

        Texture mytexture = new Texture("l.png");

        //Setting Robo's texture
        roboIdle = new TextureRegion(mytexture, 0, 0, 567, 556);
        setBounds(0, 0, 567 / Stuk.PPM, 556 / Stuk.PPM);
        setRegion(roboIdle);

        //Initializing states
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        runningRight = true;

        //Setting animation frames
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i<=9; i++)
            frames.add(new TextureRegion(mytexture, i * 567, 0, 567, 556));
        roboJump = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        for(int i = 10; i<=17; i++)
            frames.add(new TextureRegion(mytexture, i * 567, 0, 567, 556));
        roboRun = new Animation<TextureRegion>(0.1f, frames);

    }

    public void update(float dt){
        //Updating sprite position to body position
        setPosition(boxbody.getPosition().x - getWidth() / 2, boxbody.getPosition().y - getHeight() / 2);

        setRegion(getFrame(dt));
    }

    public void defineRobo(){
        //Creating body and fixture
        BodyDef bdef = new BodyDef();
        bdef.position.set(1950 / Stuk.PPM, 1024 /  Stuk.PPM);     //Starting Position
        bdef.type = BodyDef.BodyType.DynamicBody;                 //Dynamic = moving body
        boxbody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(125 / Stuk.PPM, 225 / Stuk.PPM);

        fdef.filter.categoryBits = Stuk.ROBO_BIT;           //category bit
        fdef.filter.maskBits = Stuk.DEFAULT_BIT | Stuk.ACID_BIT | Stuk.COIN_BIT | Stuk.DOOR_BIT | Stuk.SPIKE_BIT | Stuk.BOX_BIT; //everything but used bit (for used coins)
        fdef.shape = shape;
        boxbody.createFixture(fdef).setUserData(this);

    }

    //Rotating his texture based on left/right
    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = roboJump.getKeyFrame(stateTimer);
                break;

            case RUNNING:
                region = roboRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case IDLE:
            default:
                region = roboIdle;
                break;
        }

        //Flipping texture based on direction (right or left)
        if((boxbody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) { //if running left and region isnt facing left
            region.flip(true, false);                                                   //flip x, don't flip y
            runningRight = false;
        }
        else if((boxbody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;      //if currentstate == previous state stateTimer is stateTimer else (:) its 0
        previousState = currentState;
        return region;
    }

    //State of movement
    public State getState(){
        if(roboIsDead)
            return State.DEAD;
        else if(roboWon)
            return State.WON;
        else if(boxbody.getLinearVelocity().y > 0 || (boxbody.getLinearVelocity().y < 0 && previousState == State.JUMPING))   //if jumping (and not just falling)
            return State.JUMPING;
        else if(boxbody.getLinearVelocity().y < 0)   //if falling
            return State.FALLING;
        else if(boxbody.getLinearVelocity().x != 0)   //if running
            return State.RUNNING;
        else
            return State.IDLE;
    }


    public void hit(){                                                  //Death hit (called when collided w spikes or acid)
        Stuk.manager.get("StukMusic.mp3", Music.class).stop();        //stop music
        Stuk.manager.get("StukGameOver.mp3", Sound.class).play();     //plays game over sound
        roboIsDead = true;
        Filter filter = new Filter();                           //Filter to set filter of fixtures (collisions)
        filter.maskBits = Stuk.NOTHING_BIT;
        for(Fixture fixture : boxbody.getFixtureList())         //Loop through all Robo fixtures, set their bits to dead_bit so they dont collide with anything "For every fixture go through Robo's fixture"
            fixture.setFilterData(filter);
    }

    public void winHit(){
        Stuk.manager.get("StukMusic.mp3", Music.class).stop();        //stop music
        Stuk.manager.get("StukGameOver.mp3", Sound.class).play();     //TODO add winning sound
        roboWon = true;
        Filter filter = new Filter();                           //Filter to set filter of fixtures (collisions)
        filter.maskBits = Stuk.NOTHING_BIT;
        for(Fixture fixture : boxbody.getFixtureList())         //Loop through all Robo fixtures, set their bits to dead_bit so they dont collide with anything "For every fixture go through Robo's fixture"
            fixture.setFilterData(filter);
    }

    public boolean isDead(){
        return roboIsDead;
    }

    public boolean isWon(){
        return roboWon;
    }

    public float getStateTimer(){
        return stateTimer;
    }
}
