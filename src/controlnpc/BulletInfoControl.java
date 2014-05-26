/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlnpc;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Quaternion;
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
public class BulletInfoControl extends AbstractControl{

    private float damage = 0;
    private float damage2 = 0;
    private float damage3 = 0;
    private float push_Force = 0;
    private float push_Force2 = 0;
    private float push_Force3 = 0;
    private float range = 0;
    private float speed = 0;
    private float removalTime = 0;
    private SpecialEffects effects = SpecialEffects.NONE;
    private float timer_Effect = 0;
    private boolean shot = false;
    private int bulletNo = 0;
    
    public BulletInfoControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(shot){
            Spatial tempBullet = ((Node)spatial).getChild("Bullet" + bulletNo).clone();
            tempBullet.setCullHint(Spatial.CullHint.Dynamic);
            ((Node)spatial).getChild("Collider").setCullHint(Spatial.CullHint.Always);
            
            if(bulletNo == 1){
                tempBullet.setLocalTranslation(spatial.getParent().getChild("Hand_R").getWorldTranslation());
            }else if(bulletNo == 2){
                tempBullet.setLocalTranslation(spatial.getParent().getChild("Hand_L").getWorldTranslation());
            }else if(bulletNo == 3){
                tempBullet.setLocalTranslation(spatial.getParent().getChild("Body").getWorldTranslation());
            }
            
            Quaternion q = new Quaternion();
            q.lookAt(((Node)getPlayerModel()).getChild("Collider").getWorldTranslation().subtract(spatial.getParent().getWorldTranslation()), Vector3f.UNIT_Y);
            tempBullet.setLocalRotation(q);
            getStageNode().attachChild(tempBullet);
            
            BulletControl bc = tempBullet.getControl(BulletControl.class);
            
            if(bulletNo == 1){
                bc.setDamage(damage);
                bc.setPush_Force(push_Force);
            }else if(bulletNo == 2){
                bc.setDamage(damage2);
                bc.setPush_Force(push_Force2);
            }else if(bulletNo == 3){
                bc.setDamage(damage3);
                bc.setPush_Force(push_Force3);
            }
            
            bc.setSpeed(speed);
            bc.setRange(range);
            bc.setRemovalTime(removalTime);
            
            if(effects != SpecialEffects.NONE){
                bc.setEffects(effects);
                bc.setTimer_Effect(timer_Effect);
            }
            
            bc.setDir(getPlayerModel().getWorldTranslation().subtract(spatial.getParent().getWorldTranslation()).normalize().clone());
            bc.setOrigin(spatial.getParent().getChild("Hand_R").getWorldTranslation().clone());
            bc.setEnabled(true);
            
            shot = false;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    private Spatial getPlayerModel(){
        return getStageNode().getChild("Player1");
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent().getParent().getParent();
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        BulletInfoControl control = new BulletInfoControl();
        control.setDamage(damage);
        control.setDamage2(damage2);
        control.setDamage3(damage3);
        control.setPush_Force(push_Force);
        control.setPush_Force2(push_Force2);
        control.setPush_Force3(push_Force3);
        control.setRange(range);
        control.setSpeed(speed);
        control.setRemovalTime(removalTime);
        control.setEffects(effects);
        control.setTimer_Effect(timer_Effect);
        control.setShot(shot);
        control.setBulletNo(bulletNo);
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
        range = in.readFloat("range", 1.0f);
        speed = in.readFloat("speed", 1.0f);
        effects = in.readEnum("effects", SpecialEffects.class, SpecialEffects.NONE);
        timer_Effect = in.readFloat("timer_Effect", 1.0f);
        removalTime = in.readFloat("removalTime", 1.0f);
        shot = in.readBoolean("shot", true);
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
        out.write(range, "range", 1.0f);
        out.write(speed, "speed", 1.0f);
        out.write(effects, "effects", SpecialEffects.NONE);
        out.write(timer_Effect, "timer_Effect", 1.0f);
        out.write(removalTime, "removalTime", 1.0f);
        out.write(shot, "shot", true);
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
     * @return the range
     */
    public float getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(float range) {
        this.range = range;
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
     * @return the removalTime
     */
    public float getRemovalTime() {
        return removalTime;
    }

    /**
     * @param removalTime the removalTime to set
     */
    public void setRemovalTime(float removalTime) {
        this.removalTime = removalTime;
    }

    /**
     * @return the shot
     */
    public boolean isShot() {
        return shot;
    }

    /**
     * @param shot the shot to set
     */
    public void setShot(boolean shot) {
        this.shot = shot;
    }

    /**
     * @return the bulletNo
     */
    public int getBulletNo() {
        return bulletNo;
    }

    /**
     * @param bulletNo the bulletNo to set
     */
    public void setBulletNo(int bulletNo) {
        this.bulletNo = bulletNo;
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
