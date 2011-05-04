import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
/**
 *
 * @author Andrew
 */
public class Ball {
    private BranchGroup ballBG;
    private static final float GRAVSPEED = 3;
    private static final float MOVESPEED = 1;
    TransformGroup posTG; //A transform to the current position
    TransformGroup rotTG; //A transform to the current direction
    Transform3D t3d;
    Transform3D rot3d;
    Vector3f velocity;  //Current velocity of the ball
    Vector3f position;  //Current position of the ball
    float direction;    //Curent direction the ball is facing
    boolean keyup = false;
    boolean keydown = false;
    boolean keyleft = false;
    boolean keyright = false;

    public Ball(){
        ballBG = new BranchGroup();
        posTG = new TransformGroup();
        rotTG = new TransformGroup();

        // Enable the TRANSFORM_WRITE capability so can modify position
        posTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        posTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        //set appearance
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f red = new Color3f(1.0f, 0.1f, 0.1f);
        Appearance app = new Appearance();
        app.setMaterial(new Material(red, black, red, white, 80.0f));

        //starting position above center of floor

        direction = 0;
        position  = new Vector3f(0.0f, 4.0f, 0.0f);
        velocity  = new Vector3f(0.0f, 0.0f, 0.0f);
        setPositionTG();
        setDirectionTG();

        //create ball
        Sphere s = new Sphere(0.2f, app);
        rotTG.addChild(s);
        posTG.addChild(rotTG);
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
    void setPositionTG()
    {
        t3d = new Transform3D();
        t3d.setTranslation(position);
        posTG.setTransform(t3d);
    }

    void setDirectionTG()
    {
        rot3d = new Transform3D();
        rot3d.rotY(direction);
        rotTG.setTransform(rot3d);
    }
    
    void updateBall(float dt, Vector3f gravDir)
    {
        // dt is seconds elapsed time since previous frame
        if (keyup)
        {
            
        }
        if (keydown)
        {
            
        }
        if (keyleft)
        {
            direction += dt*0.75f;
            if (direction > 2.0f*Math.PI);
                    direction -= 2.0f*Math.PI;
            setDirectionTG();
        }
        if (keyright)
        {
            direction -= dt*0.75f;
            if (direction < 0.0f);
                    direction += 2.0f*Math.PI;
            setDirectionTG();
        }
    }
}
