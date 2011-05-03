
/*
 * Marble.java
 * Authors: Andrew Johnson & Paul Schrauder
 * Programming Computer Games
 * Assignment 8
 * 5/6/2011
 */
import java.applet.Applet;
import java.awt.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.keyboard.*;

public class Marble extends Applet{

    SimpleUniverse simpleU=null;
    Vector3f gravity;  //direction of marble acceleration and world position
    private static final float BOXDIM = 10.0f;  //actually have of side length

    // creates main box
    public class Cube{
        private BranchGroup boxBG;

        public Cube(){
            boxBG = new BranchGroup();

            //set appearance
            Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
            Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
            Color3f green = new Color3f(0.1f, 1.0f, 0.1f);
            Appearance appB = new Appearance();
            appB.setMaterial(new Material(green, black, green, white, 80.0f));

            //cube sides

            Box bottom = new Box(BOXDIM,.1f,BOXDIM,appB);
            Transform3D t1 = new Transform3D();
            t1.set(new Vector3d(0, -BOXDIM, 0));
            TransformGroup tg1 = new TransformGroup(t1);
            boxBG.addChild(tg1);
            tg1.addChild(bottom);

            Box top = new Box(BOXDIM,.1f,BOXDIM,appB);
            Transform3D t2 = new Transform3D();
            t2.set(new Vector3d(0, BOXDIM, 0));
            TransformGroup tg2 = new TransformGroup(t2);
            boxBG.addChild(tg2);
            tg2.addChild(top);

            Box front = new Box(BOXDIM,BOXDIM,0.1f,appB);
            Transform3D t3 = new Transform3D();
            t3.set(new Vector3d(0, 0, BOXDIM));
            TransformGroup tg3 = new TransformGroup(t3);
            boxBG.addChild(tg3);
            tg3.addChild(front);

            Box back = new Box(BOXDIM,BOXDIM,0.1f,appB);
            Transform3D t4 = new Transform3D();
            t4.set(new Vector3d(0, 0, -BOXDIM));
            TransformGroup tg4 = new TransformGroup(t4);
            boxBG.addChild(tg4);
            tg4.addChild(back);

            Box left = new Box(.1f,BOXDIM,BOXDIM,appB);
            Transform3D t5 = new Transform3D();
            t5.set(new Vector3d(-BOXDIM, 0, 0));
            TransformGroup tg5 = new TransformGroup(t5);
            boxBG.addChild(tg5);
            tg5.addChild(left);

            Box right = new Box(.1f,BOXDIM,BOXDIM,appB);
            Transform3D t6 = new Transform3D();
            t6.set(new Vector3d(BOXDIM, 0, 0));
            TransformGroup tg6 = new TransformGroup(t6);
            boxBG.addChild(tg6);
            tg6.addChild(right);

            boxBG.compile();
        }

        public BranchGroup getBG(){
            return boxBG;
        }
    }

    //creates player controlled marble
    public class Ball{
        private BranchGroup ballBG;
        Vector3f velocity;  //Current velocity of the ball
        Vector3f position;  //Current position of the ball

        public Ball(){
            ballBG = new BranchGroup();

            //set appearance, either white or black
            Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
            Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
            Color3f red = new Color3f(1.0f, 0.1f, 0.1f);
            Appearance app = new Appearance();
            app.setMaterial(new Material(red, black, red, white, 80.0f));

            /*TODO
             * create ball
             * add transform based on position
             * add rotational transform based on velocity
             *      -add texture so rotation is noticible
             * add set/get position/velocity methods
             */

            ballBG.compile();
        }

        public BranchGroup getBG(){
            return ballBG;
        }
    }

    //TODO: add coin class
    public class Coin{

    }

    // main drawing section
    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        //Ambient light
        AmbientLight lightA = new AmbientLight();
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),1000.0);
        lightA.setInfluencingBounds(bs);
        objRoot.addChild(lightA);

        // Directional light
        DirectionalLight lightD = new DirectionalLight();
        lightD.setInfluencingBounds(bs);
        Vector3f direction = new Vector3f(-1.0f, -1.0f, -1.0f);
        direction.normalize();
        lightD.setDirection(direction);
        lightD.setColor(new Color3f(0.9f, 0.9f, 0.9f));
        objRoot.addChild(lightD);

        // Place Box and Marble
        objRoot.addChild(new Cube().getBG());
        objRoot.addChild(new Ball().getBG());

        /*TODO
         * draw sky and ground based on gravity vector
         * draw reference ojects outside box (ex. trees) based on gravity vector
         * add fixed moving camera based on gravity vector and ball's position
         * add keyboard controls to move ball
         * add update method and/or transform group to slowly change gravity vector
         */


	return objRoot;
    } // end of CreateSceneGraph method

    public Marble() {
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);

        // SimpleUniverse is a Convenience Utility class
        simpleU = new SimpleUniverse(canvas3D);

        BranchGroup scene = createSceneGraph();

        //set up a KeyNavigator to let us move about
        TransformGroup vpTrans = simpleU.getViewingPlatform().getViewPlatformTransform();

        KeyNavigatorBehavior keyNav = new KeyNavigatorBehavior(vpTrans);
        keyNav.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
        scene.addChild(keyNav);

	scene.compile();

        simpleU.addBranchGraph(scene);
    }

    //  The following allows this to be run as an application
    //  as well as an applet
    public static void main(String[] args) {
        Frame frame = new MainFrame(new Marble(), 256, 256);
    } // end of main method

} // end of class

