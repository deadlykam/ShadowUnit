/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlnpc;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
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
import controlplayer.BasicInfo;
import controlplayer.EffectControl;
import java.io.IOException;
import java.util.List;
import others.AllEnum.*;

/**
 *
 * @author Kamran
 */
public class BulletControl extends AbstractControl{

    private Vector3f dir = new Vector3f();
    private Vector3f origin = new Vector3f();
    private float damage = 0;
    private float push_Force = 0;
    private float range = 0;
    private float speed = 0;
    private float removalTime = 0;
    private SpecialEffects effects = SpecialEffects.NONE;
    private float timer_Effect = 0;
    private boolean shot = false;
    private int bulletNo = 0;
    
    private boolean killSpatial = false;
    
    public BulletControl(){
        enabled = false;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(enabled){
            if(killSpatial){
//                ((ParticleEmitter) ((Node)spatial).getChild("Trail")).setParticlesPerSec(0);
                killAllParticles();
                if(removalTime < 0){
                    enabled = false;
                    spatial.removeFromParent();
                }
                removalTime--;
            }else{
                spatial.move(dir.mult(speed));
                killConditions();
                
            }
        }
    }

    private void killConditions(){
        outOfRange();
        
        if(!killSpatial){
            collisionPlayer();
        }
        
        collisionTerrain();
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    private void collisionTerrain(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = ((Node)spatial).getChild("Collider").getWorldBound();
        spatial.getParent().getChild("TerrainNode").collideWith(bv, results);
        
        if(results.size() > 0){
            killSpatial = true;
        }
    }
    
    private void collisionPlayer(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = ((Node)spatial).getChild("Collider").getWorldBound();
        ((Node)spatial.getParent().getChild("Player1")).getChild("Collider").collideWith(bv, results);
            
        //TODO: Lower Players health
//            ((Node)spatial.getParent().getChild("Player1")).getChild("HealthBar1Node").getControl(HealthBarControl.class).subCurrentHealth(damage);
        if(results.size() > 0){
            ((Node)spatial.getParent().getChild("Player1")).getControl(BasicInfo.class).decreaseHP(damage);
            ((Node)spatial.getParent().getChild("Player1")).getControl(EffectControl.class).setDir_Push(dir.mult(push_Force));
            if(effects != SpecialEffects.NONE){
                ((Node)spatial.getParent().getChild("Player1")).getControl(EffectControl.class).setEffects(effects);
                ((Node)spatial.getParent().getChild("Player1")).getControl(EffectControl.class).setTimer_Effects(timer_Effect);
            }
            
//            System.out.println("Dir: " + dir);
            killSpatial = true;
        }
    }
    
    private void outOfRange(){
        if(origin.distance(spatial.getWorldTranslation()) > range){
            killSpatial = true;
        }
    }
    
    private void killAllParticles(){
        List<Spatial> allSpatial = ((Node)spatial).getChildren();
        
        for(int index = 0; index < allSpatial.size(); index++){
            if(!allSpatial.get(index).getName().equals("Collider")){
                ((ParticleEmitter)allSpatial.get(index)).setParticlesPerSec(0);
            }
        }
    }
    
    private void emitAllParticles(){
        List<Spatial> allSpatial = ((Node)spatial).getChildren();
        
        for(int index = 0; index < allSpatial.size(); index++){
            if(!allSpatial.get(index).getName().equals("CollisionShape")){
                ((ParticleEmitter)allSpatial.get(index)).emitAllParticles();
            }
        }
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        BulletControl control = new BulletControl();
        control.setDamage(damage);
        control.setRange(range);
        control.setSpeed(speed);
        control.setRemovalTime(removalTime);
        control.setShot(shot);
        control.setBulletNo(bulletNo);
        control.setEffects(effects);
        control.setTimer_Effect(timer_Effect);
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
     * @return the dir
     */
    public Vector3f getDir() {
        return dir;
    }

    /**
     * @param dir the dir to set
     */
    public void setDir(Vector3f dir) {
        this.dir = dir;
    }

    /**
     * @return the origin
     */
    public Vector3f getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(Vector3f origin) {
        this.origin = origin;
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
