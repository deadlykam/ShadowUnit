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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import controls.CharAnimationControl;
import controls.ComboListControl;
import controls.ComboSetupControl;
import java.io.IOException;
import others.AllEnum;

/**
 *
 * @author Kamran
 */
public class AnimateControl extends AbstractControl{
    private int comboNo = 0;
    private boolean select = false;
    
    public AnimateControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(select){
            animateModel();
            select = false;
        }
        
        CharAnimationControl cac = ((Node)((Node)spatial.getParent().getChild("Model")).getChild("Player1")).getChild("Ninja").getControl(CharAnimationControl.class);
        
        if(cac.isAnimDone()){
            cac.setCommand(AllEnum.AnimationCommands.NONE);
        }
    }

    private void animateModel(){
        if(comboNo == 1){
            ComboListControl clc = spatial.getParent().getChild("Arrow_C1P").getControl(ComboListControl.class);
            CharAnimationControl cac = ((Node)((Node)spatial.getParent().getChild("Model")).getChild("Player1")).getChild("Ninja").getControl(CharAnimationControl.class);
            cac.setCommand(clc.intToAnimation());
        }else if(comboNo == 2){
            ComboListControl clc = spatial.getParent().getChild("Arrow_C2P").getControl(ComboListControl.class);
            CharAnimationControl cac = ((Node)((Node)spatial.getParent().getChild("Model")).getChild("Player1")).getChild("Ninja").getControl(CharAnimationControl.class);
            cac.setCommand(clc.intToAnimation());
        }else if(comboNo == 3){
            ComboListControl clc = spatial.getParent().getChild("Arrow_C3P").getControl(ComboListControl.class);
            CharAnimationControl cac = ((Node)((Node)spatial.getParent().getChild("Model")).getChild("Player1")).getChild("Ninja").getControl(CharAnimationControl.class);
            cac.setCommand(clc.intToAnimation());
        }else if(comboNo == 4){
            ComboListControl clc = spatial.getParent().getChild("Arrow_C4P").getControl(ComboListControl.class);
            CharAnimationControl cac = ((Node)((Node)spatial.getParent().getChild("Model")).getChild("Player1")).getChild("Ninja").getControl(CharAnimationControl.class);
            cac.setCommand(clc.intToAnimation());
        }else if(comboNo == 5){
            ComboListControl clc = spatial.getParent().getChild("Arrow_C5P").getControl(ComboListControl.class);
            CharAnimationControl cac = ((Node)((Node)spatial.getParent().getChild("Model")).getChild("Player1")).getChild("Ninja").getControl(CharAnimationControl.class);
            cac.setCommand(clc.intToAnimation());
        }else if(comboNo == 6){
            ComboListControl clc = spatial.getParent().getChild("Arrow_C6P").getControl(ComboListControl.class);
            CharAnimationControl cac = ((Node)((Node)spatial.getParent().getChild("Model")).getChild("Player1")).getChild("Ninja").getControl(CharAnimationControl.class);
            cac.setCommand(clc.intToAnimation());
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        AnimateControl control = new AnimateControl();
        control.setComboNo(comboNo);
        control.setSelect(select);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        comboNo = in.readInt("comboNo", 1);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(comboNo, "comboNo", 1);
    }

    /**
     * @return the select
     */
    public boolean isSelect() {
        return select;
    }

    /**
     * @param select the select to set
     */
    public void setSelect(boolean select) {
        this.select = select;
    }

    /**
     * @return the comboNo
     */
    public int getComboNo() {
        return comboNo;
    }

    /**
     * @param comboNo the comboNo to set
     */
    public void setComboNo(int comboNo) {
        this.comboNo = comboNo;
    }


}
