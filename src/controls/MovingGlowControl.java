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
public class MovingGlowControl extends AbstractControl {
    private float speed = 0;
    private float blend = 0;
    private boolean left = false;
    
    private boolean initialize = false;
    private Vector2f vMoveGlow;
    private Vector2f vGlowBlend;
    Material mat;
    public MovingGlowControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(!initialize){
            mat = ((Geometry)((Node)spatial).getChild(0)).getMaterial();
            vMoveGlow = new Vector2f(-1, 0);
            vGlowBlend = new Vector2f(blend, 0);
            mat.setVector2("MoveGlow", vMoveGlow);
            mat.setVector2("Glow_DissolveParams", vGlowBlend);
            initialize = true;
        }else{
            if(left){
                vMoveGlow.setX(vMoveGlow.getX() + speed);
                if(vMoveGlow.getX() > 1){
                    vMoveGlow.setX(-1);
                }
            }else{
                vMoveGlow.setX(vMoveGlow.getX() - speed);
                if(vMoveGlow.getX() < -1){
                    vMoveGlow.setX(1);
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
        MovingGlowControl control = new MovingGlowControl();
        control.setSpeed(speed);
        control.setBlend(blend);
        control.setLeft(left);
        control.setSpatial(spatial);
        return control;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        speed = in.readFloat("speed", 1.0f);
        blend = in.readFloat("blend", 1.0f);
        left = in.readBoolean("left", true);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(speed, "speed", 1.0f);
        out.write(blend, "blend", 1.0f);
        out.write(left, "left", true);
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
     * @return the blend
     */
    public float getBlend() {
        return blend;
    }

    /**
     * @param blend the blend to set
     */
    public void setBlend(float blend) {
        this.blend = blend;
    }

    /**
     * @return the left
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(boolean left) {
        this.left = left;
    }
}
