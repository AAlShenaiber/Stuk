package com.stuk.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stuk.game.Stuk;

/**
 * Created by Abdullah A
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer coins;

    private Label COINSLabel;
    private Label TIMELabel;
    private static Label coinsLabel;
    private Label timeLabel;

    public Hud(SpriteBatch sb){
        //Initialization
        worldTimer = 180;
        timeCount = 0;
        coins = 0;

        viewport = new FitViewport(Stuk.V_WIDTH, Stuk.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //Organizing labels in a table
        Table table = new Table();
        table.top();                            //place at the top
        table.setFillParent(true);              //set table to size of stage

        COINSLabel = new Label("COINS", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("stuk_font.fnt")), Color.WHITE));
        TIMELabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("stuk_font.fnt")), Color.WHITE));

        coinsLabel = new Label(String.format("%01d", coins), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("stuk_font.fnt")), Color.WHITE));      //%01d = 1 digit long
        timeLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("stuk_font.fnt")), Color.WHITE));

        table.add(COINSLabel).expandX().padTop(10);
        table.add(TIMELabel).expandX().padTop(10);
        table.row();
        table.add(coinsLabel).expandX();
        table.add(timeLabel).expandX();

        stage.addActor(table);          //Add table (with all labels) to the stage

    }

    //Setting up countdown timer

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            timeLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addCoins(int number){
        coins += number;
        coinsLabel.setText(String.format("%01d", coins));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
