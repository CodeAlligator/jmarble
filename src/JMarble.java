
/*
 * Marble.java
 * Authors: Andrew Johnson & Paul Schrauder
 * Programming Computer Games
 * Assignment 8
 * 5/6/2011
 *
 * Uses code from Mike Slattery's MBall
 *
 * The game:
 * 	You are a marble that rolls around the world. Use the classic AWSD keys to control
 * which direction to roll in. Collect coins by rolling into them.
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
import java.util.ArrayList;
import java.util.Enumeration;

public class JMarble extends Applet{

    SimpleUniverse simpleU=null;
    Vector3f gravity;  //direction of marble acceleration and world position
    Ball player;
    ArrayList<Coin> coins = new ArrayList<Coin>();
    Transform3D followT3D;

    // main drawing section
    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        //Ambient light
        AmbientLight lightA = new AmbientLight();
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000);
        lightA.setInfluencingBounds(bs);
        lightA.setColor(new Color3f(1.0f, 0.1f, 0.7f));
        objRoot.addChild(lightA);

        // Directional white light
        DirectionalLight lightD = new DirectionalLight();
        lightD.setInfluencingBounds(bs);
        Vector3f direction = new Vector3f(-1.0f, -1.0f, -1.0f);
        direction.normalize();
        lightD.setDirection(direction);
        lightD.setColor(new Color3f(1f, 1f, 1f));
        objRoot.addChild(lightD);

        //	Directional colored light
        DirectionalLight lightE = new DirectionalLight();
        lightE.setInfluencingBounds(bs);
        Vector3f direction2 = new Vector3f(0.4f, -0.1f, -1.0f);
        direction2.normalize();
        lightE.setDirection(direction2);
        lightE.setColor(new Color3f(1f, .7f, .3f));
        objRoot.addChild(lightE);

        // Set the background sky to blue
        Background bg = new Background();
        bg.setColor(0.0f, 0.0f, 1.0f); // yellow
        bg.setApplicationBounds(new BoundingSphere());
        objRoot.addChild(bg);

        //	Create the coins
        coins.add(new Coin(.9f,.2f));
        coins.add(new Coin(1.3f,-3.2f));
        coins.add(new Coin(-2.3f,2.2f));
        coins.add(new Coin(3.3f,1.2f));
        coins.add(new Coin(-2.3f,-2.2f));
        coins.add(new Coin(-3.3f,1.2f));

        // Place Box and Marble
        player = new Ball();
        objRoot.addChild(new Cube().getBG());

        //	Add the coins
        for(int i = 0; i < 6; i++){
        	objRoot.addChild(coins.get(i).getBG());
        }

        //	Add the player
        objRoot.addChild(player.getBG());

        objRoot.addChild(new ColorCube(2.3));


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
            checkViewpoint();
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
                case KeyEvent.VK_L: gravity = new Vector3f(-1.0f, 0.0f, 0.0f);; break;//left
                case KeyEvent.VK_R: gravity = new Vector3f(1.0f, 0.0f, 0.0f);; break;//right
                case KeyEvent.VK_T: gravity = new Vector3f(0.0f, 1.0f, 0.0f);; break;//top
                case KeyEvent.VK_G: gravity = new Vector3f(0.0f, -1.0f, 0.0f);; break;//ground
                case KeyEvent.VK_F: gravity = new Vector3f(0.0f, 0.0f, -1.0f);; break;//front
                case KeyEvent.VK_B: gravity = new Vector3f(0.0f, 0.0f, 1.0f);; break;//back
            }

            //	Check if player hit coin
            if(coins.size() > 0 && (player.position.x > coins.get(0).x - .4f) && (player.position.x < coins.get(0).x + .4f) && (player.position.z > coins.get(0).z - .4f) && (player.position.z < coins.get(0).z + .4f)){
        		System.out.println("Collected a coin");
        		coins.remove(0);
        	}
            if(coins.size() > 1 && (player.position.x > coins.get(1).x - .4f) && (player.position.x < coins.get(1).x + .4f) && (player.position.z > coins.get(1).z - .4f) && (player.position.z < coins.get(1).z + .4f)){
        		System.out.println("Collected a coin");
        		coins.remove(1);
        	}
            if(coins.size() > 2 && (player.position.x > coins.get(2).x - .4f) && (player.position.x < coins.get(2).x + .4f) && (player.position.z > coins.get(2).z - .4f) && (player.position.z < coins.get(2).z + .4f)){
        		System.out.println("Collected a coin");
        		coins.remove(2);
        	}
            if(coins.size() > 3 && (player.position.x > coins.get(3).x - .4f) && (player.position.x < coins.get(3).x + .4f) && (player.position.z > coins.get(3).z - .4f) && (player.position.z < coins.get(3).z + .4f)){
        		System.out.println("Collected a coin");
        		coins.remove(3);
        	}
            if(coins.size() > 4 && (player.position.x > coins.get(4).x - .4f) && (player.position.x < coins.get(4).x + .4f) && (player.position.z > coins.get(4).z - .4f) && (player.position.z < coins.get(4).z + .4f)){
        		System.out.println("Collected a coin");
        		coins.remove(4);
        	}
            if(coins.size() > 5 && (player.position.x > coins.get(5).x - .4f) && (player.position.x < coins.get(5).x + .4f) && (player.position.z > coins.get(5).z - .4f) && (player.position.z < coins.get(5).z + .4f)){
        		System.out.println("Collected a coin");
        		coins.remove(5);
        	}

        	if(coins.size() == 0){
				System.out.println("You Won! Game Over!");
			}
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_W:	//	forward
                	player.keyup = false;
                	break;
                case KeyEvent.VK_S:	//	back
                	player.keydown = false;
                	break;
                case KeyEvent.VK_D:	//	right
                	player.keyright = false;
                	break;
                case KeyEvent.VK_A:	//	left
                	player.keyleft = false;
                	break;
            }
        }
    }

    void checkViewpoint()
    {
        TransformGroup vpTrans = simpleU.getViewingPlatform().getViewPlatformTransform();
        Transform3D T3D =new Transform3D(player.t3d);
        T3D.mul(player.rot3d);

//        xform = new Transform3D();
//        Point3d eye = new Point3d(player.position.x, player.position.y, player.position.z);
//        Point3d center = new Point3d(player.pointing.x, player.pointing.y, player.pointing.z);
//        Vector3d grav = new Vector3d(gravity);
//        grav.scale(-1.0);
//        xform.lookAt(eye, center, grav);

        //Transform3D T3D =new Transform3D(xform);
        T3D.mul(followT3D);
        vpTrans.setTransform(T3D);
    }

    public JMarble() {
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);


        canvas3D.addKeyListener(new keyL());

        // SimpleUniverse is a Convenience Utility class
        simpleU = new SimpleUniverse(canvas3D);

        BranchGroup scene = createSceneGraph();

        //set up a KeyNavigator to let us move about
        TransformGroup vpTrans = simpleU.getViewingPlatform().getViewPlatformTransform();


//        KeyNavigatorBehavior keyNav = new KeyNavigatorBehavior(vpTrans);
//        keyNav.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
//        scene.addChild(keyNav);


	scene.compile();

        simpleU.addBranchGraph(scene);

        // setup the transform followT3D to place
        // the viewpoint "over the shoulder" of
        // the ball
        Vector3f backup = new Vector3f(0.0f, 0.0f, 5.0f);
		followT3D = new Transform3D();
		followT3D.rotX(-Math.PI/12.0);
		Transform3D scoot = new Transform3D();
		scoot.set(backup);
		followT3D.mul(scoot);
    }

    //  The following allows this to be run as an application
    //  as well as an applet
    public static void main(String[] args) {
        Frame frame = new MainFrame(new JMarble(), 256, 256);
        frame.setSize(800, 600);
    }
}
