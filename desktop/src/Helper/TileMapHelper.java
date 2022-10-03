package Helper;

import static Helper.Constants.PPM;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.joy.main.GameScreen;

import objects.player.Player;

public class TileMapHelper {
    private TiledMap tilemap;
    private GameScreen gScreen;
    public TileMapHelper(GameScreen gScreen){
        this.gScreen = gScreen;


    }
    public OrthogonalTiledMapRenderer setUpMap(){
        tilemap = new TmxMapLoader().load("maps/map01.tmx");
        parseMapObjects(tilemap.getLayers().get("objects").getObjects());
        return  new OrthogonalTiledMapRenderer(tilemap);
    }

    public void parseMapObjects(MapObjects mapObjs){
        for (MapObject mapObj : mapObjs ){
            if(mapObj instanceof PolygonMapObject){
                createStaticBody((PolygonMapObject) mapObj);

            }
            if (mapObj instanceof RectangleMapObject){
                Rectangle rect = ( ((RectangleMapObject) mapObj).getRectangle() );
                String rectName = mapObj.getName();
                if (rectName.equals("player")){
                    Body body = BodyHelperService.createBody(
                            rect.getX() + rect.getWidth()/2,
                            rect.getY()+rect.getHeight()/2,
                            rect.getWidth(),
                            rect.getHeight(),
                            false,
                            gScreen.getWorld()
                            );
                    gScreen.setPlayer(new Player(rect.width, rect.getHeight(), body));
                }
            }
        }

    }

    private void createStaticBody(PolygonMapObject polygonMapObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000); //change Later
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVeritces = new Vector2[vertices.length/2];
        for (int i = 0; i< vertices.length/2 ; i++){
            Vector2 current = new Vector2(vertices[i*2]/PPM, vertices[i*2 +1]/PPM); // need to devide to match the world "scaling"
            worldVeritces[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVeritces);
        return shape;
    }
}
