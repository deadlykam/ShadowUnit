/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlnpc;

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
import controls.CharAnimationControl;
import java.io.IOException;
import others.AllEnum.*;

/**
 *
 * @author Kamran
 */
public class MeleeControl extends AbstractControl{

    float damage = 0;
    float push_Force = 0;
    private AnimationCommands commands;
    
    public MeleeControl(){
        enabled = false;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(enabled){
            CharAnimationControl cac = ((Node)spatial).getChild("Ninja").getControl(CharAnimationControl.class);
            
            if(cac.isAnimDone()){
                setupDamageForce();
                enemyAttackCollision(commands);
                enabled = false;
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
    
    private boolean enemyAttackCollision(AnimationCommands commands){
        
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = ((Node)spatial).getChild("Ninja").getWorldBound();
        getEnemyModels().collideWith(bv, results);
        
        if(results.size() > 0){
            String parentName = results.getCollision(0).getGeometry().getParent().getName();

            if(parentName.equals("AttackCollider")){
                results.getCollision(0).getGeometry().getParent().getParent().getParent().getParent().getControl(controlnpc.BasicInfo.class).decreaseHP(damage);
                results.getCollision(0).getGeometry().getParent().getParent().getParent().getParent().getControl(AI1Control.class).pushSpatial(push_Force);
            }else if(parentName.equals("Enemy") || parentName.equals("Collider")){
                results.getCollision(0).getGeometry().getParent().getParent().getControl(controlnpc.BasicInfo.class).decreaseHP(damage);
                results.getCollision(0).getGeometry().getParent().getParent().getControl(AI1Control.class).pushSpatial(push_Force);
            }
                    
                for(int i = 1; i < results.size(); i++){
                    parentName = results.getCollision(i).getGeometry().getParent().getName();

                    if(!parentName.equals(results.getCollision(i-1).getGeometry().getParent().getName())){
                        if(parentName.equals("AttackCollider")){
                            results.getCollision(i).getGeometry().getParent().getParent().getParent().getParent().getControl(controlnpc.BasicInfo.class).decreaseHP(damage);
                            results.getCollision(i).getGeometry().getParent().getParent().getParent().getParent().getControl(AI1Control.class).pushSpatial(push_Force);
                        }else if(parentName.equals("Enemy") || parentName.equals("Collider")){
                            results.getCollision(i).getGeometry().getParent().getParent().getControl(controlnpc.BasicInfo.class).decreaseHP(damage);
                            results.getCollision(i).getGeometry().getParent().getParent().getControl(AI1Control.class).pushSpatial(push_Force);
                        }
                        
                    }
            }
            
//            String parentName = results.getClosestCollision().getGeometry().getParent().getName();
//
//            if(parentName.equals("AttackCollider")){
//                results.getClosestCollision().getGeometry().getParent().getParent().getParent().getParent().getControl(controlnpc.BasicInfo.class).decreaseHP(damage);
//                results.getClosestCollision().getGeometry().getParent().getParent().getParent().getParent().getControl(AI1Control.class).pushSpatial(push_Force);
//            }else if(parentName.equals("Enemy") || parentName.equals("Collider")){
//                results.getClosestCollision().getGeometry().getParent().getParent().getControl(controlnpc.BasicInfo.class).decreaseHP(damage);
//                results.getClosestCollision().getGeometry().getParent().getParent().getControl(AI1Control.class).pushSpatial(push_Force);
//            }
        }
        return false;
    }
    
    private void setupDamageForce(){
        controlplayer.BasicInfo bi = spatial.getControl(controlplayer.BasicInfo.class);
        
        if(commands == AnimationCommands.KICK1){
            damage = 4 + bi.getDamage();
            push_Force = .5f;
        }else if(commands == AnimationCommands.PUNCH1){
            damage = 1.5f + bi.getDamage();
            push_Force = .5f;
        }else if(commands == AnimationCommands.PUNCH2){
            damage = 1.5f + bi.getDamage();
            push_Force = .5f;
        }else if(commands == AnimationCommands.PUSH1){
            damage = 3.5f + bi.getDamage();
            push_Force = .7f;
        }else if(commands == AnimationCommands.PALM1){
            damage = 2f + bi.getDamage();
            push_Force = .7f;
        }else if(commands == AnimationCommands.PUSHKICK1){
            damage = 4.5f + bi.getDamage();
            push_Force = .7f;
        }else if(commands == AnimationCommands.RHKICK1){
            damage = 5f + bi.getDamage();
            push_Force = .7f;
        }else if(commands == AnimationCommands.POWER1){
            damage = 10f + bi.getDamage();
            push_Force = 3f;
        }
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent().getParent();
    }
    
    private Spatial getEnemyModels(){
        return getStageNode().getChild("Enemies");
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        MeleeControl control = new MeleeControl();
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
     * @return the commands
     */
    public AnimationCommands getCommands() {
        return commands;
    }

    /**
     * @param commands the commands to set
     */
    public void setCommands(AnimationCommands commands) {
        this.commands = commands;
    }


}
