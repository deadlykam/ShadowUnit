/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlplayer;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
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
import controls.HealthBarControl;
import java.io.IOException;
import others.GameStarted;

/**
 *
 * @author Kamran
 */
public class BasicInfo extends AbstractControl{

    private float maxHp = 100;
    private float hp = 100;
    private float speed_Movement = 0;
    private float speed_Attack = 0;
    private float speed_AttackMovement = 0;
    private float speed_Special = 0;
    private float speed_SpecialMovement = 0;
    private float damage = 0;
    private float defense = 0;
    
    public BasicInfo(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(hp <= 0){
            //TODO: Player dies.
        }
        
        if(GameStarted.gameStart){
            if(playerBoundaryCollision()){
                getChildModels("Sign").setCullHint(Spatial.CullHint.Dynamic);
            }else{
                getChildModels("Sign").setCullHint(Spatial.CullHint.Always);
            }
        }
        
        if(getChildModels("HealthBarModel") != null){
            getChildModels("HealthBarModel").getControl(HealthBarControl.class).setPercentage(hp / maxHp);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    public void decreaseHP(float damage){
        
        if(hp > 0){
            
            EffectControl ec = spatial.getControl(EffectControl.class);
            //TODO armor calculation.
            hp = hp - (damage - (damage * (defense * ec.getArmorReduction())));
        }
        
        if(hp < 0){
            hp = 0;
        }
//        System.out.println("Player got Hit! HP: " + hp);
    }
    
    public void increaseHP(float amount){
        if(hp < maxHp){
            hp = hp + amount;
        }
        
        if(hp > maxHp){
            hp = maxHp;
        }
    }
    
    private Spatial getChildModels(String name){
        return ((Node)spatial).getChild(name);
    }
    
    private boolean playerBoundaryCollision(){
        if(getStageNode().getChild("Boundary") != null){
            CollisionResults results = new CollisionResults();
            BoundingVolume bv = getChildModels("Collider").getWorldBound();
            getStageNode().getChild("Boundary").collideWith(bv, results);

            if(results.size() > 0){
                return true;
            }
        }
        
        return false;
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent().getParent();
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        BasicInfo control = new BasicInfo();
        control.setMaxHp(maxHp);
        control.setHp(hp);
        control.setSpeed_Movement(speed_Movement);
        control.setSpeed_Attack(speed_Attack);
        control.setSpeed_AttackMovement(speed_AttackMovement);
        control.setSpeed_Special(speed_Special);
        control.setSpeed_SpecialMovement(speed_SpecialMovement);
        control.setDamage(damage);
        control.setDefense(defense);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        maxHp = in.readFloat("maxHp", 1.0f);
        hp = in.readFloat("hp", 1.0f);
        speed_Movement = in.readFloat("speed_Movement", 1.0f);
        speed_Attack = in.readFloat("speed_Attack", 1.0f);
        speed_AttackMovement = in.readFloat("speed_AttackMovement", 1.0f);
        speed_Special = in.readFloat("speed_Special", 1.0f);
        speed_SpecialMovement = in.readFloat("speed_SpecialMovement", 1.0f);
        damage = in.readFloat("damage", 1.0f);
        defense = in.readFloat("defense", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(maxHp, "maxHp", 1.0f);
        out.write(hp, "hp", 1.0f);
        out.write(speed_Movement, "speed_Movement", 1.0f);
        out.write(speed_Attack, "speed_Attack", 1.0f);
        out.write(speed_AttackMovement, "speed_AttackMovement", 1.0f);
        out.write(speed_Special, "speed_Special", 1.0f);
        out.write(speed_SpecialMovement, "speed_SpecialMovement", 1.0f);
        out.write(damage, "damage", 1.0f);
        out.write(defense, "defense", 1.0f);
    }

    /**
     * @return the maxHp
     */
    public float getMaxHp() {
        return maxHp;
    }

    /**
     * @param maxHp the maxHp to set
     */
    public void setMaxHp(float maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * @return the hp
     */
    public float getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(float hp) {
        this.hp = hp;
    }

    /**
     * @return the speed_Movement
     */
    public float getSpeed_Movement() {
        return speed_Movement;
    }

    /**
     * @param speed_Movement the speed_Movement to set
     */
    public void setSpeed_Movement(float speed_Movement) {
        this.speed_Movement = speed_Movement;
    }

    /**
     * @return the speed_Attack
     */
    public float getSpeed_Attack() {
        return speed_Attack;
    }

    /**
     * @param speed_Attack the speed_Attack to set
     */
    public void setSpeed_Attack(float speed_Attack) {
        this.speed_Attack = speed_Attack;
    }

    /**
     * @return the speed_AttackMovement
     */
    public float getSpeed_AttackMovement() {
        return speed_AttackMovement;
    }

    /**
     * @param speed_AttackMovement the speed_AttackMovement to set
     */
    public void setSpeed_AttackMovement(float speed_AttackMovement) {
        this.speed_AttackMovement = speed_AttackMovement;
    }

    /**
     * @return the speed_Special
     */
    public float getSpeed_Special() {
        return speed_Special;
    }

    /**
     * @param speed_Special the speed_Special to set
     */
    public void setSpeed_Special(float speed_Special) {
        this.speed_Special = speed_Special;
    }

    /**
     * @return the speed_SpecialMovement
     */
    public float getSpeed_SpecialMovement() {
        return speed_SpecialMovement;
    }

    /**
     * @param speed_SpecialMovement the speed_SpecialMovement to set
     */
    public void setSpeed_SpecialMovement(float speed_SpecialMovement) {
        this.speed_SpecialMovement = speed_SpecialMovement;
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
     * @return the defense
     */
    public float getDefense() {
        return defense;
    }

    /**
     * @param defense the defense to set
     */
    public void setDefense(float defense) {
        this.defense = defense;
    }


}
