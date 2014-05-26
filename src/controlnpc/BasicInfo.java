/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlnpc;

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
import controls.DropBoxControl;
import controls.GenObserverControl;
import controls.GeneratorControl;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Kamran
 */
public class BasicInfo extends AbstractControl{

    private float maxHp = 100;
    private float hp = 100;
    private float defense = 0;
    private float timer_Remove = 0;
    private float amount_XP = 0;
    private float amount_Money = 0;
    
    //Variables for connecting with generator
    private String generatorName = "";
    boolean sendSignal = false;
    
    Random ran = new Random();
    
    public BasicInfo(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(hp <= 0){
            if(!sendSignal){
                Spatial generator = ((Node)getStageNode().getChild("Generators")).getChild(generatorName);
                GeneratorControl gc = generator.getControl(GeneratorControl.class);
                gc.enemyKilled();
                
                GenObserverControl goc = getStageNode().getControl(GenObserverControl.class);
                
                goc.enemyKilled();
                goc.addXP(amount_XP);
                goc.addMoney(amount_Money);
                
                dropLootChance();
                sendSignal = true;
            }
            
            if(timer_Remove < 0){
                spatial.setCullHint(Spatial.CullHint.Always);
                spatial.removeFromParent();
            }
            timer_Remove--;
        }
    }

    public void decreaseHP(float damage){
        if(hp > 0){
            hp = hp - (damage - (damage * defense));
        }
        
        if(hp < 0){
            hp = 0;
        }
    }
    
    private void dropLootChance(){
        if(ran.nextInt(100) < 50){
            int chance = ran.nextInt(100);
            
            if(chance >= 0 && chance < 50){
                dropLoot("HPDrop");
            }else if(chance >= 50 && chance < 80){
                dropLoot("MoneyDrop");
            }else if(chance >= 80){
                dropLoot("XPDrop");
            }
        }
    }
    
    private void dropLoot(String name){
        Spatial tempLoot = getStageNode().getChild(name).clone();
        Vector3f genPoint = spatial.getWorldTranslation().clone();
        genPoint = new Vector3f(genPoint.x, genPoint.y + 1, genPoint.z);
        tempLoot.setLocalTranslation(genPoint);
        ((Node)getStageNode().getChild("Items")).attachChild(tempLoot);
        
        DropBoxControl dbx = tempLoot.getControl(DropBoxControl.class);
        dbx.setStartTimer(true);
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent().getParent();
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        BasicInfo control = new BasicInfo();
        control.setMaxHp(maxHp);
        control.setHp(hp);
        control.setDefense(defense);
        control.setTimer_Remove(timer_Remove);
        control.setAmount_XP(amount_XP);
        control.setAmount_Money(amount_Money);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        maxHp = in.readFloat("maxHp", 1.0f);
        hp = in.readFloat("hp", 1.0f);
        defense = in.readFloat("defense", 1.0f);
        timer_Remove = in.readFloat("timer_Remove", 1.0f);
        amount_XP = in.readFloat("amount_XP", 1.0f);
        amount_Money = in.readFloat("amount_Money", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(maxHp, "maxHp", 1.0f);
        out.write(hp, "hp", 1.0f);
        out.write(defense, "defense", 1.0f);
        out.write(timer_Remove, "timer_Remove", 1.0f);
        out.write(amount_XP, "amount_XP", 1.0f);
        out.write(amount_Money, "amount_Money", 1.0f);
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

    /**
     * @return the generatorName
     */
    public String getGeneratorName() {
        return generatorName;
    }

    /**
     * @param generatorName the generatorName to set
     */
    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    /**
     * @return the timer_Remove
     */
    public float getTimer_Remove() {
        return timer_Remove;
    }

    /**
     * @param timer_Remove the timer_Remove to set
     */
    public void setTimer_Remove(float timer_Remove) {
        this.timer_Remove = timer_Remove;
    }

    /**
     * @return the amount_XP
     */
    public float getAmount_XP() {
        return amount_XP;
    }

    /**
     * @param amount_XP the amount_XP to set
     */
    public void setAmount_XP(float amount_XP) {
        this.amount_XP = amount_XP;
    }

    /**
     * @return the amount_Money
     */
    public float getAmount_Money() {
        return amount_Money;
    }

    /**
     * @param amount_Money the amount_Money to set
     */
    public void setAmount_Money(float amount_Money) {
        this.amount_Money = amount_Money;
    }


}
