package objects.player;

import static Helper.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends GameEntity {

    private int jumpCount;
    private int attackCount;

    public Player(float width, float hieght, Body body) {
        super(width, hieght, body);
        this.speed = 10f;
        this.jumpCount =0;
    }

    @Override
    public void update() {
        x =body.getPosition().x*PPM;
        y= body.getPosition().y*PPM;

        checkUserInput();
    }

    private void checkUserInput() {
        velx = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            velx = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            velx = -1;
        }
         if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.BUTTON_A))&& jumpCount <2){
            float force = body.getMass() *18;
            body.setLinearVelocity(body.getLinearVelocity().x, 0); // might need a debounce
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCount++;
        }

        if (body.getLinearVelocity().y ==0){
            jumpCount =0;
        }
        body.setLinearVelocity(velx * speed, body.getLinearVelocity().y <25 ? body.getLinearVelocity().y :25);
    }

    @Override
    public void render(SpriteBatch sbatch) {

    }
}
