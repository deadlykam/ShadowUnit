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
import controlnpc.MeleeControl;
import java.io.IOException;
import others.AllEnum.*;
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class ComboSetupControl extends AbstractControl{

    private ComboName comboCommand = ComboName.NONE;
    
    private AnimationCommands combo1_1 = AnimationCommands.NONE;
    private AnimationCommands combo2_1 = AnimationCommands.NONE;
    private AnimationCommands combo3_1 = AnimationCommands.NONE;
    private AnimationCommands combo4_1 = AnimationCommands.NONE;
    private AnimationCommands combo5_1 = AnimationCommands.NONE;
    private AnimationCommands combo6_1 = AnimationCommands.NONE;
    private boolean specialCombo = false;
    private int comboChainSize = 3;
    
    public ComboSetupControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(((Node)spatial).getChild("Ninja") != null){
            CharAnimationControl cac = ((Node)spatial).getChild("Ninja").getControl(CharAnimationControl.class);
            MeleeControl mc = spatial.getControl(MeleeControl.class);
            
            if(comboCommand == ComboName.COMBO1){
                mc.setCommands(combo1_1);
                mc.setEnabled(enabled);
                cac.setCommand(combo1_1);
            }else if(comboCommand == ComboName.COMBO2){
                mc.setCommands(combo2_1);
                mc.setEnabled(enabled);
                cac.setCommand(combo2_1);
            }else if(comboCommand == ComboName.COMBO3){
                mc.setCommands(combo3_1);
                mc.setEnabled(enabled);
                cac.setCommand(combo3_1);
            }else if(comboCommand == ComboName.COMBO4){
                mc.setCommands(combo4_1);
                mc.setEnabled(enabled);
                cac.setCommand(combo4_1);
            }else if(comboCommand == ComboName.COMBO5){
                mc.setCommands(combo5_1);
                mc.setEnabled(enabled);
                cac.setCommand(combo5_1);
            }else if(comboCommand == ComboName.COMBO6){
                mc.setCommands(combo6_1);
                mc.setEnabled(enabled);
                cac.setCommand(combo6_1);
            }

            comboCommand = ComboName.NONE;
        }
        
        checkComboChain();
    }
    
    private void checkComboChain(){
        if(StoredInfo.level >= 3){
            comboChainSize = 4;
        }
        
        if(StoredInfo.level >= 8){
            comboChainSize = 5;
        }
        
        if(StoredInfo.level >= 10){
            comboChainSize = 6;
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ComboSetupControl control = new ComboSetupControl();
        control.setComboCommand(comboCommand);
        control.setCombo1_1(combo1_1);
        control.setCombo2_1(combo2_1);
        control.setCombo3_1(combo3_1);
        control.setCombo4_1(combo4_1);
        control.setCombo5_1(combo5_1);
        control.setCombo6_1(combo6_1);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        combo1_1 = in.readEnum("combo1_1", AnimationCommands.class, AnimationCommands.NONE);
        combo2_1 = in.readEnum("combo2_1", AnimationCommands.class, AnimationCommands.NONE);
        combo3_1 = in.readEnum("combo3_1", AnimationCommands.class, AnimationCommands.NONE);
        combo4_1 = in.readEnum("combo4_1", AnimationCommands.class, AnimationCommands.NONE);
        combo5_1 = in.readEnum("combo5_1", AnimationCommands.class, AnimationCommands.NONE);
        combo6_1 = in.readEnum("combo6_1", AnimationCommands.class, AnimationCommands.NONE);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(combo1_1, "combo1_1", AnimationCommands.NONE);
        out.write(combo2_1, "combo2_1", AnimationCommands.NONE);
        out.write(combo3_1, "combo3_1", AnimationCommands.NONE);
        out.write(combo4_1, "combo4_1", AnimationCommands.NONE);
        out.write(combo5_1, "combo5_1", AnimationCommands.NONE);
        out.write(combo6_1, "combo6_1", AnimationCommands.NONE);
    }

    /**
     * @return the comboCommand
     */
    public ComboName getComboCommand() {
        return comboCommand;
    }

    /**
     * @param comboCommand the comboCommand to set
     */
    public void setComboCommand(ComboName comboCommand) {
        this.comboCommand = comboCommand;
    }

    /**
     * @return the combo1_1
     */
    public AnimationCommands getCombo1_1() {
        return combo1_1;
    }

    /**
     * @param combo1_1 the combo1_1 to set
     */
    public void setCombo1_1(AnimationCommands combo1_1) {
        this.combo1_1 = combo1_1;
    }

    /**
     * @return the combo2_1
     */
    public AnimationCommands getCombo2_1() {
        return combo2_1;
    }

    /**
     * @param combo2_1 the combo2_1 to set
     */
    public void setCombo2_1(AnimationCommands combo2_1) {
        this.combo2_1 = combo2_1;
    }

    /**
     * @return the combo3_1
     */
    public AnimationCommands getCombo3_1() {
        return combo3_1;
    }

    /**
     * @param combo3_1 the combo3_1 to set
     */
    public void setCombo3_1(AnimationCommands combo3_1) {
        this.combo3_1 = combo3_1;
    }

    /**
     * @return the combo4_1
     */
    public AnimationCommands getCombo4_1() {
        return combo4_1;
    }

    /**
     * @param combo4_1 the combo4_1 to set
     */
    public void setCombo4_1(AnimationCommands combo4_1) {
        this.combo4_1 = combo4_1;
    }

    /**
     * @return the combo5_1
     */
    public AnimationCommands getCombo5_1() {
        return combo5_1;
    }

    /**
     * @param combo5_1 the combo5_1 to set
     */
    public void setCombo5_1(AnimationCommands combo5_1) {
        this.combo5_1 = combo5_1;
    }

    /**
     * @return the combo6_1
     */
    public AnimationCommands getCombo6_1() {
        return combo6_1;
    }

    /**
     * @param combo6_1 the combo6_1 to set
     */
    public void setCombo6_1(AnimationCommands combo6_1) {
        this.combo6_1 = combo6_1;
    }

    /**
     * @return the specialCombo
     */
    public boolean isSpecialCombo() {
        return specialCombo;
    }

    /**
     * @param specialCombo the specialCombo to set
     */
    public void setSpecialCombo(boolean specialCombo) {
        this.specialCombo = specialCombo;
    }

    /**
     * @return the comboChainSize
     */
    public int getComboChainSize() {
        return comboChainSize;
    }

    /**
     * @param comboChainSize the comboChainSize to set
     */
    public void setComboChainSize(int comboChainSize) {
        this.comboChainSize = comboChainSize;
    }


}
