import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;


/**
 *
 * @author Andrew
 */
public class Coin {
    protected static final float BOXDIM = 1.0f;  //actually half of side length
    private BranchGroup boxBG;

    public Coin(){
        boxBG = new BranchGroup();

        //set appearance
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f blue = new Color3f(0.1f, 0.2f, 1.0f);
        Appearance appB = new Appearance();
        appB.setMaterial(new Material(blue, black, blue, white, 80.0f));

        //cube sides

        Box bottom = new Box(BOXDIM,.001f,BOXDIM,appB);
        Transform3D t1 = new Transform3D();
        t1.set(new Vector3d(0, -BOXDIM, 0));
        TransformGroup tg1 = new TransformGroup(t1);
        boxBG.addChild(tg1);
        tg1.addChild(bottom);

        Box top = new Box(BOXDIM,.001f,BOXDIM,appB);
        Transform3D t2 = new Transform3D();
        t2.set(new Vector3d(0, BOXDIM, 0));
        TransformGroup tg2 = new TransformGroup(t2);
        boxBG.addChild(tg2);
        tg2.addChild(top);

        Box front = new Box(BOXDIM,BOXDIM,0.001f,appB);
        Transform3D t3 = new Transform3D();
        t3.set(new Vector3d(0, 0, BOXDIM));
        TransformGroup tg3 = new TransformGroup(t3);
        boxBG.addChild(tg3);
        tg3.addChild(front);

        Box back = new Box(BOXDIM,BOXDIM,0.001f,appB);
        Transform3D t4 = new Transform3D();
        t4.set(new Vector3d(0, 0, -BOXDIM));
        TransformGroup tg4 = new TransformGroup(t4);
        boxBG.addChild(tg4);
        tg4.addChild(back);
/*
        Box left = new Box(.001f,BOXDIM,BOXDIM,appB);
        Transform3D t5 = new Transform3D();
        t5.set(new Vector3d(-BOXDIM, 0, 0));
        TransformGroup tg5 = new TransformGroup(t5);
        boxBG.addChild(tg5);
        tg5.addChild(left);

        Box right = new Box(.001f,BOXDIM,BOXDIM,appB);
        Transform3D t6 = new Transform3D();
        t6.set(new Vector3d(BOXDIM, 0, 0));
        TransformGroup tg6 = new TransformGroup(t6);
        boxBG.addChild(tg6);
        tg6.addChild(right);
*/
        boxBG.compile();
    }

    public BranchGroup getBG(){
        return boxBG;
    }
}