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
    private static final float RADIUS = .2f;
    TransformGroup posTG; //A transform to the current position
    TransformGroup rotTG; //A transform to the current direction
    //TransformGroup look;
    Transform3D t3d;
    Transform3D rot3d;
    //Transform3D xform;
    Vector3f velocity;  //Current velocity of the ball
    Vector3f gravvelocity;  //Current velocity of the ball due to gravity
    Vector3f position;  //Current position of the ball
    Vector3f gravity;   //gravity vector from main method
    Vector3f pointing;  //vector pointing in balls direction
    float direction;    //Curent direction the ball is facing
    boolean keyup = false;
    boolean keydown = false;
    boolean keyleft = false;
    boolean keyright = false;

    public Ball(){
        ballBG = new BranchGroup();
        posTG = new TransformGroup();
        rotTG = new TransformGroup();
        //look = new TransformGroup();

        // Enable the TRANSFORM_WRITE capability so can modify position
        posTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        posTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        //look.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //look.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        //set appearance
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f red = new Color3f(1.0f, 0.1f, 0.1f);
        Appearance app = new Appearance();
        app.setMaterial(new Material(red, black, red, white, 80.0f));

        //starting position above center of floor

        direction = 0.0f;
        position  = new Vector3f(0.0f, 4.0f, 0.0f);
        velocity  = new Vector3f(0.0f, 0.0f, 0.0f);
        gravvelocity  = new Vector3f(0.0f, 0.0f, 0.0f);
        gravity = new Vector3f(0.0f, -1.0f, 0.0f);
        pointing = new Vector3f(0.0f, 0.0f, -1.0f);
        setPositionTG();
        setDirectionTG();
        //createTransform();


        //create ball
        Sphere s = new Sphere(RADIUS, app);
        rotTG.addChild(s);
        posTG.addChild(rotTG);
        //posTG.addChild(s);
        ballBG.addChild(posTG);
        //look.addChild(s);
        //ballBG.addChild(look);


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

//    void createTransform(){
//        xform = new Transform3D();
//        Point3d eye = new Point3d(position.x, position.y, position.z);
//        Point3d center = new Point3d(pointing.x, pointing.y, pointing.z);
//        Vector3d grav = new Vector3d(gravity);
//        grav.scale(-1.0);
//        xform.lookAt(eye, center, grav);
//        //look.setTransform(xform);
//        setPositionTG();
//    }
    
    void updateBall(float dt, Vector3f gravDir)
    {
        // dt is seconds elapsed time since previous frame
        gravity = gravDir;
        gravity.normalize();

        if (keyup)
        {
            velocity.z = (float) (Math.cos(direction) * -MOVESPEED * dt);
            velocity.x = (float) (Math.sin(direction) * -MOVESPEED * dt);
            velocity.y = 0;
            position.add(velocity);
            //position.scaleAdd(MOVESPEED * dt, pointing, position);
        }

        if (keydown)
        {
            velocity.z = (float) (Math.cos(direction) * MOVESPEED * dt);
            velocity.x = (float) (Math.sin(direction) * MOVESPEED * dt);
            velocity.y = 0;
            position.add(velocity);
            //position.scaleAdd(-MOVESPEED * dt, pointing, position);
        }
        if (keyleft)
        {
            direction += dt*0.75f;
            if (direction > 2.0f*Math.PI);
                    direction -= 2.0f*Math.PI;
            setDirectionTG();
//            Vector3f a = new Vector3f();
//            a.cross(pointing, gravity);
//            pointing.scaleAdd(1.0f*dt, a, pointing);
//            pointing.normalize();
//            direction = (float) Math.asin(pointing.x);
        }
        if (keyright)
        {
            direction -= dt*0.75f;
            if (direction < 0.0f);
                    direction += 2.0f*Math.PI;
            setDirectionTG();
//            Vector3f a = new Vector3f();
//            a.cross(gravity, pointing);
//            pointing.scaleAdd(1.0f*dt, a, pointing);
//            pointing.normalize();
        }
        
        //cumulativly add gravity speed
        //gravvelocity.scaleAdd(dt*GRAVSPEED, gravDir, gravvelocity);
        //position.add(gravvelocity);
        position.scaleAdd(dt*GRAVSPEED, gravity, position);

        //dont fall through walls
        if (position.y < -Cube.BOXDIM + RADIUS){
            position.y = -Cube.BOXDIM + RADIUS;
            gravvelocity.y = 0f;
        }
        if (position.y > Cube.BOXDIM - RADIUS){
            position.y = Cube.BOXDIM - RADIUS;
            gravvelocity.y = 0f;
        }
        if (position.x < -Cube.BOXDIM + RADIUS){
            position.x = -Cube.BOXDIM + RADIUS;
            gravvelocity.x = 0f;
        }
        if (position.x > Cube.BOXDIM - RADIUS){
            position.x = Cube.BOXDIM - RADIUS;
            gravvelocity.x = 0f;
        }
        if (position.z < -Cube.BOXDIM + RADIUS){
            position.z = -Cube.BOXDIM + RADIUS;
            gravvelocity.z = 0f;
        }
        if (position.z > Cube.BOXDIM - RADIUS){
            position.z = Cube.BOXDIM - RADIUS;
            gravvelocity.z = 0f;
        }

        setPositionTG();

        //createTransform();
    }
}
