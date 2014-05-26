/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlplayer;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import others.AllEnum.*;

/**
 *
 * @author Kamran
 */
public class EffectControl extends AbstractControl{

    private Vector3f dir_Push = new Vector3f();
    private SpecialEffects effects = SpecialEffects.NONE;
    private float dmgPerTimer = 0;
    private float dmg = 0;
    private float speedReduction = 0;
    private float armorReduction = 0;
    private float timer_Effects = 0;
    
    private float current_DmgPerTimer = 0;
    
    public EffectControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(timer_Effects > 0){
            if(effects == SpecialEffects.FIRE){
                ((Node)((Node)spatial).getChild("EffectNode")).getChild("Fire").setCullHint(Spatial.CullHint.Dynamic);
                if(current_DmgPerTimer <= 0){
                    spatial.getControl(BasicInfo.class).decreaseHP(dmg);
                    current_DmgPerTimer = dmgPerTimer;
                }else{
                    current_DmgPerTimer--;
                }
            }else if(effects == SpecialEffects.FREEZE){
                ((Node)((Node)spatial).getChild("EffectNode")).getChild("Freeze").setCullHint(Spatial.CullHint.Dynamic);
            }else if(effects == SpecialEffects.ARMOR){
                ((Node)((Node)spatial).getChild("EffectNode")).getChild("Armor").setCullHint(Spatial.CullHint.Dynamic);
            }
            timer_Effects--;
        }else if(timer_Effects <= 0){
            ((Node)((Node)spatial).getChild("EffectNode")).getChild("Fire").setCullHint(Spatial.CullHint.Always);
            ((Node)((Node)spatial).getChild("EffectNode")).getChild("Freeze").setCullHint(Spatial.CullHint.Always);
            ((Node)((Node)spatial).getChild("EffectNode")).getChild("Armor").setCullHint(Spatial.CullHint.Always);
            effects = SpecialEffects.NONE;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        EffectControl control = new EffectControl();
        control.setEffects(effects);
        control.setDmgPerTimer(dmgPerTimer);
        control.setDmg(dmg);
        control.setSpeedReduction(speedReduction);
        control.setArmorReduction(armorReduction);
        control.setTimer_Effects(timer_Effects);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        effects = in.readEnum("effects", SpecialEffects.class, SpecialEffects.NONE);
        dmgPerTimer = in.readFloat("dmgPerTimer", 1.0f);
        dmg = in.readFloat("dmg", 1.0f);
        speedReduction = in.readFloat("speedReduction", 1.0f);
        armorReduction = in.readFloat("armorReduction", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(effects, "effects", SpecialEffects.NONE);
        out.write(dmgPerTimer, "dmgPerTimer", 1.0f);
        out.write(dmg, "dmg", 1.0f);
        out.write(speedReduction, "speedReduction", 1.0f);
        out.write(armorReduction, "armorReduction", 1.0f);
    }

    /**
     * @return the dir_Push
     */
    public Vector3f getDir_Push() {
        return dir_Push;
    }

    /**
     * @param dir_Push the dir_Push to set
     */
    public void setDir_Push(Vector3f dir_Push) {
        this.dir_Push = dir_Push;
    }

    /**
     * @return the effects
     */
    public SpecialEffects getEffects() {
        return effects;
    }

    /**
     * @param effects the effects to set
     */
    public void setEffects(SpecialEffects effects) {
        this.effects = effects;
    }

    /**
     * @return the dmgPerTimer
     */
    public float getDmgPerTimer() {
        return dmgPerTimer;
    }

    /**
     * @param dmgPerTimer the dmgPerTimer to set
     */
    public void setDmgPerTimer(float dmgPerTimer) {
        this.dmgPerTimer = dmgPerTimer;
    }

    /**
     * @return the dmg
     */
    public float getDmg() {
        return dmg;
    }

    /**
     * @param dmg the dmg to set
     */
    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    /**
     * @return the speedReduction
     */
    public float getSpeedReduction() {
        return speedReduction;
    }

    /**
     * @param speedReduction the speedReduction to set
     */
    public void setSpeedReduction(float speedReduction) {
        this.speedReduction = speedReduction;
    }

    /**
     * @return the armorReduction
     */
    public float getArmorReduction() {
        return armorReduction;
    }

    /**
     * @param armorReduction the armorReduction to set
     */
    public void setArmorReduction(float armorReduction) {
        this.armorReduction = armorReduction;
    }

    /**
     * @return the timer_Effects
     */
    public float getTimer_Effects() {
        return timer_Effects;
    }

    /**
     * @param timer_Effects the timer_Effects to set
     */
    public void setTimer_Effects(float timer_Effects) {
        this.timer_Effects = timer_Effects;
    }
}
