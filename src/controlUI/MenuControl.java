/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlUI;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
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
public class MenuControl extends AbstractControl{

    private boolean focus = false;
    private float speed_Dissolve = 0;
    
    private boolean initialize = false;
    Vector2f vBlend1;
    
    public MenuControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        
        if(!initialize){
            Material mat = ((Geometry)((Node)spatial).getChild(0)).getMaterial();
            vBlend1 = new Vector2f(0, 0);
            mat.setVector2("DissolveParams", vBlend1);
            initialize = true;
        }else{
            if(!focus){
                if(vBlend1.getX() < 1){
                    vBlend1.setX(vBlend1.getX() + speed_Dissolve);
                }else{
                    spatial.setCullHint(Spatial.CullHint.Always);
                    spatial.setLocalTranslation(new Vector3f(0, 20, 0));
                }
            }else{
                spatial.setCullHint(Spatial.CullHint.Dynamic);
                spatial.setLocalTranslation(Vector3f.ZERO);
                
                if(vBlend1.getX() > 0){
                    vBlend1.setX(vBlend1.getX() - speed_Dissolve);
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
        MenuControl control = new MenuControl();
        control.setFocus(focus);
        control.setSpeed_Dissolve(speed_Dissolve);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        focus = in.readBoolean("focus", true);
        speed_Dissolve = in.readFloat("speed_Dissolve", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(focus, "focus", true);
        out.write(speed_Dissolve, "speed_Dissolve", 1.0f);
    }

    /**
     * @return the focus
     */
    public boolean isFocus() {
        return focus;
    }

    /**
     * @param focus the focus to set
     */
    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    /**
     * @return the speed_Dissolve
     */
    public float getSpeed_Dissolve() {
        return speed_Dissolve;
    }

    /**
     * @param speed_Dissolve the speed_Dissolve to set
     */
    public void setSpeed_Dissolve(float speed_Dissolve) {
        this.speed_Dissolve = speed_Dissolve;
    }


}
