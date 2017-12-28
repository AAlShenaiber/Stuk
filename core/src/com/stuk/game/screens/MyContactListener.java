package com.stuk.game.screens;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.stuk.game.Stuk;
import com.stuk.game.sprites.Box;
import com.stuk.game.sprites.Coin;
import com.stuk.game.sprites.InteractiveTileObject;
import com.stuk.game.sprites.Robo;

/**
 * Created by Abdullah A
 */

public class MyContactListener implements ContactListener {

    //Called when 2 fixtures collide
    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            case Stuk.ROBO_BIT | Stuk.COIN_BIT:                                 //COIN HIT
                if (fixA.getFilterData().categoryBits == Stuk.ROBO_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHit();
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHit();
                break;

            case Stuk.ROBO_BIT | Stuk.BOX_BIT:                                 //Box HIT
                if (fixA.getFilterData().categoryBits == Stuk.ROBO_BIT)
                    ((Box) fixB.getUserData()).onHit();
                else
                    ((Box) fixA.getUserData()).onHit();
                break;


            case Stuk.ROBO_BIT | Stuk.ACID_BIT:                             //Hit acid
                if (fixA.getFilterData().categoryBits == Stuk.ROBO_BIT)
                    ((Robo) fixA.getUserData()).hit();
                else
                    ((Robo) fixB.getUserData()).hit();
                break;

            case Stuk.ROBO_BIT | Stuk.SPIKE_BIT:                            //Hit spikes
                if (fixA.getFilterData().categoryBits == Stuk.ROBO_BIT)
                    ((Robo) fixA.getUserData()).hit();
                else
                     ((Robo) fixB.getUserData()).hit();
                break;

            case Stuk.ROBO_BIT | Stuk.DOOR_BIT:                            //Hit Door (win)
                if (fixA.getFilterData().categoryBits == Stuk.ROBO_BIT)
                    ((Robo) fixA.getUserData()).winHit();
                else
                    ((Robo) fixB.getUserData()).winHit();
                break;

        }

    }

    //Called when 2 fixtures stop colliding with each other
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
