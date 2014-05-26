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
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Kamran
 */
public class DropBoxControl extends AbstractControl{

    private float amount = 0;
    private boolean taken = false;
    private boolean startTimer = false;
    
    private float timer = 500f;
    
    public DropBoxControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(taken){
            spatial.setCullHint(Spatial.CullHint.Always);
            spatial.removeFromParent();
        }
        
        if(startTimer){
            if(timer < 0){
                spatial.setCullHint(Spatial.CullHint.Always);
                spatial.removeFromParent();
            }else{
                timer--;
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
        DropBoxControl control = new DropBoxControl();
        control.setAmount(amount);
        control.setTaken(taken);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        amount = in.readFloat("amount", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(amount, "amount", 1.0f);
    }

    /**
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * @return the taken
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     * @param taken the taken to set
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    /**
     * @return the startTimer
     */
    public boolean isStartTimer() {
        return startTimer;
    }

    /**
     * @param startTimer the startTimer to set
     */
    public void setStartTimer(boolean startTimer) {
        this.startTimer = startTimer;
    }


}
