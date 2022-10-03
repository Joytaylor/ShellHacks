package com.joy.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game {
    public static Boot INSTANCE;
    public int wScreen, hScreen;
    private OrthographicCamera oCam;

    public Boot(){
        INSTANCE = this;
    }
    @Override
    public void create() {
        this.wScreen = Gdx.graphics.getWidth();
        this.hScreen = Gdx.graphics.getHeight();
        this.oCam = new OrthographicCamera();
        this.oCam.setToOrtho(false, wScreen,hScreen);
        setScreen(new GameScreen(oCam));
    }
}

