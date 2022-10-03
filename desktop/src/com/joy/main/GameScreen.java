package com.joy.main;

import static Helper.Constants.PPM;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import Helper.TileMapHelper;
import objects.player.Player;
import objects.player.ScrollingBackground;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2dDebug;
    private TileMapHelper tMapHelp;
    private OrthogonalTiledMapRenderer oTileRenderer;


    private Audio audio;
    //the moving things
    private Player player;
    public ScrollingBackground scrollingBackground;


    public World getWorld() {
        return world;
    }

    public GameScreen(OrthographicCamera cam){
        this.cam = cam;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,-25.3f), false);
        this.box2dDebug = new Box2DDebugRenderer();
        this.tMapHelp = new TileMapHelper(this );
        this.oTileRenderer = tMapHelp.setUpMap();
        this.audio = Gdx.audio;
        this.scrollingBackground = new ScrollingBackground();

    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //insert object renders
        scrollingBackground.updateAndRender(delta, batch, cam);
        this.update(delta);

        oTileRenderer.render();
        batch.end();
       // box2dDebug.render(world, cam.combined.scl(PPM));

    }
    public void update(float delta){
        world.step(1/60f, 6 , 2);
        cameraUpdate();
        //scrollingBackground.update();
        //scrollingBackground.update( delta, batch)
        batch.setProjectionMatrix(cam.combined);
        oTileRenderer.setView(cam);
        player.update();
        if( Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

    }

    public void cameraUpdate(){
        Vector3 position = cam.position;
        position.x = Math.round(player.getBody().getPosition().x *PPM *10)/10f;
        position.y = Math.round(player.getBody().getPosition().y *PPM *10)/10f;

        cam.position.set(position);
        cam.update();
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public void resize(int width, int height){
        this.scrollingBackground.resize(width, height);
    }
}
