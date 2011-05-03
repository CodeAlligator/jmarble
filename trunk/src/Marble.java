
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

    // creates main box
    public class Box{
        private BranchGroup boxBG;

        public Box(){
            boxBG = new BranchGroup();

            //set appearance
            Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
            Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
            Color3f green = new Color3f(0.1f, 1.0f, 0.1f);
            Appearance appB = new Appearance();
            appB.setMaterial(new Material(green, black, green, white, 80.0f));

            /*TODO
             * create faces of box here
             * center box on origin
             * these pieces never move
             * initially create without holes?
             *
             */
           
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
        objRoot.addChild(new Box().getBG());
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

