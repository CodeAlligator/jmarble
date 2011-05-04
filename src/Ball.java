import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
/**
 *
 * @author Andrew
 */
public class Ball {
    private BranchGroup ballBG;
    TransformGroup posTG; //A transform to the current position
    Vector3f velocity;  //Current velocity of the ball
    Vector3f position;  //Current position of the ball
    boolean keyup = false;
    boolean keydown = false;
    boolean keyleft = false;
    boolean keyright = false;

    public Ball(){
        ballBG = new BranchGroup();
        posTG = new TransformGroup();

        // Enable the TRANSFORM_WRITE capability so can modify position
        posTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        posTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        //set appearance
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f red = new Color3f(1.0f, 0.1f, 0.1f);
        Appearance app = new Appearance();
        app.setMaterial(new Material(red, black, red, white, 80.0f));

        //starting position above center of floor
        setPosition(new Vector3f(0.0f, 4.0f, 0.0f));

        //create ball
        Sphere s = new Sphere(0.2f, app);
        posTG.addChild(s);
        ballBG.addChild(posTG);

        /*TODO
         *
         * add rotational transform based on velocity
         *      -add texture so rotation is noticible
         * add set/get position/velocity methods
         */

        ballBG.compile();
    }

    public BranchGroup getBG(){
        return ballBG;
    }

    // Move the Ball to the given position
    void setPosition(Vector3f loc)
    {
        Transform3D t = new Transform3D();
        t.setTranslation(loc);
        posTG.setTransform(t);
    }

    /* not ready
    void updateBall(float dt)
    {
        // dt is seconds elapsed time since previous frame
        if (keyup)
        {
                rel_pos.scaleAdd(dt, velocity, rel_pos);
                goflag = true;
        }
        if (keyleft)
        {
                rotation += dt*0.75f;
                if (rotation > twopi);
                        rotation -= twopi;
                rot3d.rotY(rotation);
                rot3d.transform(vel_prime, velocity);
                tg.setTransform(rot3d);
        }
        if (keyright)
        {
                rotation -= dt*0.75f;
                if (rotation < 0.0f);
                        rotation += twopi;
                rot3d.rotY(rotation);
                rot3d.transform(vel_prime, velocity);
                tg.setTransform(rot3d);
        }
    }*/
}
