/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controls;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Kamran
 */
public class DissolveControl extends AbstractControl{

    private float speed = 0;
    private boolean appear = false;
    private boolean appearedDone = false;
    
    private boolean initialize = false;
    Vector2f vBlend1, vBlend2;
    
    public DissolveControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(!initialize){
            Material mat1 = ((Geometry)((Node)spatial).getChild(0)).getMaterial();
            Material mat2 = ((Geometry)((Node)spatial).getChild(1)).getMaterial();
            vBlend1 = new Vector2f(0, 0);
            vBlend2 = new Vector2f(0, 0);
            
            mat1.setVector2("DissolveParams", vBlend1);
            mat2.setVector2("DissolveParams", vBlend2);
            
            initialize = true;
        }else{
            if(!appear){
                if(vBlend1.getX() < 1){
                    vBlend1.setX(vBlend1.getX() + speed);
                    vBlend2.setX(vBlend2.getX() + speed);
                }else{
                    appearedDone = false;
                }
            }else{
                if(vBlend1.getX() > 0){
                    vBlend1.setX(vBlend1.getX() - speed);
                    vBlend2.setX(vBlend2.getX() - speed);
                }else{
                    appearedDone = true;
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        DissolveControl control = new DissolveControl();
        control.setSpeed(speed);
        control.setAppear(appear);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        speed = in.readFloat("speed", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(speed, "speed", 1.0f);
    }

    /**
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @return the appear
     */
    public boolean isAppear() {
        return appear;
    }

    /**
     * @param appear the appear to set
     */
    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    /**
     * @return the appearedDone
     */
    public boolean isAppearedDone() {
        return appearedDone;
    }


}
