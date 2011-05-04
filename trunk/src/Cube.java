
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Andrew
 */
public class Cube {

    private static final float BOXDIM = 10.0f;  //actually half of side length
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
/*
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
*/
        boxBG.compile();
    }

    public BranchGroup getBG(){
        return boxBG;
    }
}
