package objects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameEntity {
    protected float x, y, velx, vely, speed, width, hieght;
    protected Body body;

    public GameEntity(float width, float height, Body body){
        this.body = body;
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.hieght = height;
        this.width = width;
        this.velx = 0;
        this.speed = 0;

    }
    public abstract void update();
    public abstract void render(SpriteBatch sbatch);
    public Body getBody(){
        return body;
    }
}
