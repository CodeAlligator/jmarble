import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;

/**
 *
 * @author Paul
 */
public class Coin {
    private BranchGroup boxBG;
    public float x;
    public float z;
    
    public Coin(float x, float z){
    	this.x = x;
    	this.z = z;
    	
    	boxBG = new BranchGroup();

        //set appearance
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f green = new Color3f(0.9f, 1.0f, 0.1f);
        Appearance appB = new Appearance();
        appB.setMaterial(new Material(green, black, green, white, 80.0f));
        
        Cylinder bottom = new Cylinder(.3f,.2f, appB);
        Transform3D t1 = new Transform3D();
        t1.set(new Vector3d(x, -10f, z));
        TransformGroup tg1 = new TransformGroup(t1);
        boxBG.addChild(tg1);
        tg1.addChild(bottom);

        boxBG.compile();
    }

    public BranchGroup getBG(){
        return boxBG;
    }
}