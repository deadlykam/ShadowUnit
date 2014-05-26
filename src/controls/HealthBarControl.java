/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controls;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Kamran
 */
public class HealthBarControl extends AbstractControl{

    private float percentage = 1;
    
    public HealthBarControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        ((Node)spatial).getChild("HealthBar").setLocalScale(1, percentage, 1);
        
        if(percentage > 0){
            ((Node)spatial).getChild("HealthBar").setCullHint(Spatial.CullHint.Dynamic);
        }else{
            ((Node)spatial).getChild("HealthBar").setCullHint(Spatial.CullHint.Always);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        HealthBarControl control = new HealthBarControl();
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        //this.value = in.readFloat("name", defaultValue);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        //out.write(this.value, "name", defaultValue);
    }

    /**
     * @return the percentage
     */
    public float getPercentage() {
        return percentage;
    }

    /**
     * @param percentage the percentage to set
     */
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }


}
