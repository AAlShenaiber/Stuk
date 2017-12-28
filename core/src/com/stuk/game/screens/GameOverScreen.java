package com.stuk.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stuk.game.Stuk;
import com.stuk.game.sprites.Robo;

/**
 * Created by Abdullah A
 */

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(Stuk.V_WIDTH, Stuk.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((Stuk) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("stuk_font.fnt")), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Aw, you died!", font);
        Label playAgainLabel = new Label("Click to play again", font);
        Label hintLabel = new Label("Hint: Always use boxes to get around obstacles.", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(150f);        //Making the text spaced out "padded"
        table.row();
        table.add(hintLabel).expandX().padTop(750f);        //Making the text spaced out "padded"

        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {                           //Play again!
            game.setScreen(new PlayScreen((Stuk) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
