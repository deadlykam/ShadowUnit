/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controls;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Kamran
 */
public class RotationControl extends AbstractControl{

    private Vector3f rotation = new Vector3f();
    
    public RotationControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        spatial.rotate(rotation.x, rotation.y, rotation.z);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        RotationControl control = new RotationControl();
        control.setRotation(rotation);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        rotation = (Vector3f) in.readSavable("rotation", new Vector3f());
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(rotation, "rotation", new Vector3f());
    }

    /**
     * @return the rotation
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }


}
