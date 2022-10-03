package objects.player;

import static Helper.Constants.DEFAULTSCROLLSPEED;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {
    private Texture img;
    private float x1, x2, imgScale;
    private int speed, maxSpeed;



    public ScrollingBackground(){
        this.img = new Texture("maps/sky.png");
        this.x1 = 0;
        this.x2 = img.getWidth();
        this.imgScale = 0;
        this.speed = DEFAULTSCROLLSPEED;


    }
    public void updateAndRender(float deltaTime, SpriteBatch batch, Camera cam){
        x1 -= speed *deltaTime;
        x2 -=speed *deltaTime;
        //
        if (x1 + img.getWidth() <= cam.position.x - (Gdx.graphics.getWidth())){
            x1 = x2 +(img.getWidth() *imgScale);
        }
        if (x2 + img.getWidth() <= cam.position.x - (Gdx.graphics.getWidth())){
            x2 = x1 +(img.getWidth()*imgScale);
        }

        batch.draw(img, x1,0, img.getWidth() *  imgScale,Gdx.graphics.getHeight());
        batch.draw(img, x2,0, img.getWidth() *imgScale,Gdx.graphics.getHeight());
    }
    public void resize(int width, int height){
        imgScale = 2*height/img.getHeight();
    }
}
