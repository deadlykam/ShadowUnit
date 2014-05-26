/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlUI;

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
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class ScoreControl extends AbstractControl{
    
    public ScoreControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        //TODO: add code that controls Spatial,
        //e.g. spatial.rotate(tpf,tpf,tpf);
    }

    public void showScore(float money, float xp){
        LabelControl lc1 = (LabelControl) spatial.getControl(0);
        LabelControl lc2 = (LabelControl) spatial.getControl(1);
        LabelControl lc3 = (LabelControl) spatial.getControl(2);
        
        float tmoney = StoredInfo.tempMoney;
        float txp = StoredInfo.tempMoney;
        
        lc1.changeText("Money: " + tmoney + "");
        lc2.changeText("XP: " + txp + "");
        lc3.changeText("Score: " + tmoney + txp + "");
        System.out.println("Show Score. tempMoney: " + StoredInfo.tempMoney + " tempXP: " + StoredInfo.tempXP);
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ScoreControl control = new ScoreControl();
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
}
