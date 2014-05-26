/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlnpc;

import controlplayer.BasicInfo;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
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
import controlplayer.EffectControl;
import java.io.IOException;
import others.AllEnum.*;

/**
 *
 * @author Kamran
 */
public class MeleeInfoControl extends AbstractControl{

    private float damage = 0;
    private float damage2 = 0;
    private float damage3 = 0;
    private float push_Force = 0;
    private float push_Force2 = 0;
    private float push_Force3 = 0;
    private boolean weapon = false;
    private SpecialEffects effects = SpecialEffects.NONE;
    private float timer_Effect = 0;
    
    public MeleeInfoControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        //TODO: add code that controls Spatial,
        //e.g. spatial.rotate(tpf,tpf,tpf);
    }

    public void attackPlayer(int attackNo, Vector3f dir){
        if(playerAttackCollision()){
            BasicInfo bi = getPlayerModel().getControl(BasicInfo.class);
            EffectControl ec = getPlayerModel().getControl(EffectControl.class);
            
            if(attackNo == 1){
                bi.decreaseHP(damage);
                ec.setDir_Push(dir.mult(push_Force));
                
                if(effects != SpecialEffects.NONE){
                    ec.setEffects(effects);
                    ec.setTimer_Effects(timer_Effect);
                }
            }else if(attackNo == 2){
                bi.decreaseHP(damage2);
                ec.setDir_Push(dir.mult(push_Force2));
                
                if(effects != SpecialEffects.NONE){
                    ec.setEffects(effects);
                    ec.setTimer_Effects(timer_Effect);
                }
            }else if(attackNo == 3){
                bi.decreaseHP(damage3);
                ec.setDir_Push(dir.mult(push_Force3));
                
                if(effects != SpecialEffects.NONE){
                    ec.setEffects(effects);
                    ec.setTimer_Effects(timer_Effect);
                }
                
                
            }
        }
    }
    
    private boolean playerAttackCollision(){
        if(!weapon){
            CollisionResults [] results = new CollisionResults [4];

            for(int i = 0; i < results.length; i++){
                results[i] = new CollisionResults();
            }

            BoundingVolume bv = ((Node)getStageNode().getChild("Player1")).getChild("Collider").getWorldBound();

            ((Node)spatial).getChild("Hand_R").collideWith(bv, results[0]);
            if(results[0].size() > 0){
                return true;
            }

            ((Node)spatial).getChild("Hand_L").collideWith(bv, results[1]);
            if(results[1].size() > 0){
                return true;
            }

            ((Node)spatial).getChild("Foot_R").collideWith(bv, results[2]);
            if(results[2].size() > 0){
                return true;
            }

            ((Node)spatial).getChild("Foot_L").collideWith(bv, results[3]);
            if(results[3].size() > 0){
                return true;
            }
        }else{
             CollisionResults results = new CollisionResults();
             BoundingVolume bv = ((Node)getStageNode().getChild("Player1")).getChild("Collider").getWorldBound();
             ((Node)spatial).getChild("Weapon_R").collideWith(bv, results);
             
             if(results.size() > 0){
                return true;
            }
        }
        return false;
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent().getParent();
    }
    
    private Spatial getPlayerModel(){
        return getStageNode().getChild("Player1");
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        MeleeInfoControl control = new MeleeInfoControl();
        control.setDamage(damage);
        control.setDamage2(damage2);
        control.setDamage3(damage3);
        control.setPush_Force(push_Force);
        control.setPush_Force2(push_Force2);
        control.setPush_Force3(push_Force3);
        control.setWeapon(weapon);
        control.setEffects(effects);
        control.setTimer_Effect(timer_Effect);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        damage = in.readFloat("damage", 1.0f);
        damage2 = in.readFloat("damage2", 1.0f);
        damage3 = in.readFloat("damage3", 1.0f);
        push_Force = in.readFloat("push_Force", 1.0f);
        push_Force2 = in.readFloat("push_Force2", 1.0f);
        push_Force3 = in.readFloat("push_Force3", 1.0f);
        weapon = in.readBoolean("weapon", true);
        effects = in.readEnum("effects", SpecialEffects.class, SpecialEffects.NONE);
        timer_Effect = in.readFloat("timer_Effect", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(damage, "damage", 1.0f);
        out.write(damage2, "damage2", 1.0f);
        out.write(damage3, "damage3", 1.0f);
        out.write(push_Force, "push_Force", 1.0f);
        out.write(push_Force2, "push_Force2", 1.0f);
        out.write(push_Force3, "push_Force3", 1.0f);
        out.write(weapon, "weapon", true);
        out.write(effects, "effects", SpecialEffects.NONE);
        out.write(timer_Effect, "timer_Effect", 1.0f);
    }

    /**
     * @return the damage
     */
    public float getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * @return the damage2
     */
    public float getDamage2() {
        return damage2;
    }

    /**
     * @param damage2 the damage2 to set
     */
    public void setDamage2(float damage2) {
        this.damage2 = damage2;
    }

    /**
     * @return the damage3
     */
    public float getDamage3() {
        return damage3;
    }

    /**
     * @param damage3 the damage3 to set
     */
    public void setDamage3(float damage3) {
        this.damage3 = damage3;
    }

    /**
     * @return the push_Force
     */
    public float getPush_Force() {
        return push_Force;
    }

    /**
     * @param push_Force the push_Force to set
     */
    public void setPush_Force(float push_Force) {
        this.push_Force = push_Force;
    }

    /**
     * @return the push_Force2
     */
    public float getPush_Force2() {
        return push_Force2;
    }

    /**
     * @param push_Force2 the push_Force2 to set
     */
    public void setPush_Force2(float push_Force2) {
        this.push_Force2 = push_Force2;
    }

    /**
     * @return the push_Force3
     */
    public float getPush_Force3() {
        return push_Force3;
    }

    /**
     * @param push_Force3 the push_Force3 to set
     */
    public void setPush_Force3(float push_Force3) {
        this.push_Force3 = push_Force3;
    }

    /**
     * @return the weapon
     */
    public boolean isWeapon() {
        return weapon;
    }

    /**
     * @param weapon the weapon to set
     */
    public void setWeapon(boolean weapon) {
        this.weapon = weapon;
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
     * @return the timer_Effect
     */
    public float getTimer_Effect() {
        return timer_Effect;
    }

    /**
     * @param timer_Effect the timer_Effect to set
     */
    public void setTimer_Effect(float timer_Effect) {
        this.timer_Effect = timer_Effect;
    }


}
