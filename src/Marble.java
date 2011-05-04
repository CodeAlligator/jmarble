
/*
 * Marble.java
 * Authors: Andrew Johnson & Paul Schrauder
 * Programming Computer Games
 * Assignment 8
 * 5/6/2011
 *
 * Uses code from Mike Slattery's MBall
 */

import java.applet.Applet;
import java.awt.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.keyboard.*;
import java.awt.event.*;
import java.util.Enumeration;

public class Marble extends Applet{

    SimpleUniverse simpleU=null;
    Vector3f gravity;  //direction of marble acceleration and world position
    Ball player;
    Transform3D followT3D;

    // main drawing section
    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        //Ambient light
        AmbientLight lightA = new AmbientLight();
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
        lightA.setInfluencingBounds(bs);
        objRoot.addChild(lightA);

        // Directional light
        DirectionalLight lightD = new DirectionalLight();
        lightD.setInfluencingBounds(bs);
        Vector3f direction = new Vector3f(-1.0f, -1.0f, -1.0f);
        direction.normalize();
        lightD.setDirection(direction);
        lightD.setColor(new Color3f(1f, 1f, 1f));
        objRoot.addChild(lightD);

        // Set the background sky to blue
        Background bg = new Background();
        bg.setColor(0.0f, 0.0f, 1.0f); // yellow
        bg.setApplicationBounds(new BoundingSphere());
        objRoot.addChild(bg);

        // Place Box and Marble
        player = new Ball();
        objRoot.addChild(new Cube().getBG());
        objRoot.addChild(player.getBG());

        /*TODO
         * draw sky and ground based on gravity vector
         * draw reference ojects outside box (ex. trees) based on gravity vector
         * add fixed moving camera based on gravity vector and ball's position
         * add keyboard controls to move ball
         * add update method and/or transform group to slowly change gravity vector
         */

        gravity = new Vector3f(0.0f, -1.0f, 0.0f);

        // Create a new Behavior object to update each frame
        ComputeFrame cf = new ComputeFrame();
        BoundingSphere bounds = new BoundingSphere(new Point3d(),100.0);
        cf.setSchedulingBounds(bounds);
        objRoot.addChild(cf);
         

	return objRoot;
    } // end of CreateSceneGraph method

    
    class ComputeFrame extends Behavior
    {

        // This behavior updates the world for each frame
        long prevTime, currTime;
        float t;
        WakeupOnElapsedFrames stim = new WakeupOnElapsedFrames(0);

        public void initialize()
        {
            prevTime = 0L;
            wakeupOn(stim);
        }

        public void processStimulus(Enumeration criteria)
        {
            // Each frame we figure out how much time has
            // passed and call updateBall().
            // Get elapsed time
            currTime = getView().getCurrentFrameStartTime();
            t = (currTime-prevTime)/1000.0f;
            prevTime = currTime;
            player.updateBall(t, gravity);
            wakeupOn(stim);
        }

    }


    class keyL extends KeyAdapter
    {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_W: player.keyup = true; break;
                case KeyEvent.VK_S: player.keydown = true; break;
                case KeyEvent.VK_D: player.keyright = true; break;
                case KeyEvent.VK_A: player.keyleft = true; break;
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_W: player.keyup = false; break;
                case KeyEvent.VK_S: player.keydown = false; break;
                case KeyEvent.VK_D: player.keyright = false; break;
                case KeyEvent.VK_A: player.keyleft = false; break;
            }
        }
    }
/*
    void checkViewpoint()
    {
        TransformGroup vpTrans = simpleU.getViewingPlatform().getViewPlatformTransform();
        Transform3D T3D =new Transform3D(player.t3d);
        T3D.mul(player.rot3d);
        T3D.mul(followT3D);
        vpTrans.setTransform(T3D);
    }*/

    public Marble() {
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        
        
        canvas3D.addKeyListener(new keyL());

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

        /* setup the transform followT3D to place
        // the viewpoint "over the shoulder" of
        // the ball
        Vector3f backup = new Vector3f(0.0f, 0.0f, 5.0f);
		followT3D = new Transform3D();
		followT3D.rotX(-Math.PI/12.0);
		Transform3D scoot = new Transform3D();
		scoot.set(backup);
		followT3D.mul(scoot);*/
    }

    //  The following allows this to be run as an application
    //  as well as an applet
    public static void main(String[] args) {
        Frame frame = new MainFrame(new Marble(), 256, 256);
    } // end of main method

} // end of class

