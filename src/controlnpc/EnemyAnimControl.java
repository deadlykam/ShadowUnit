/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlnpc;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
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
import others.AllEnum.*;

/**
 *
 * @author Kamran
 */
public class EnemyAnimControl extends AbstractControl{

    /*===========List of Animation===========*/
    private String ATTACK_1 = "Attack_1";
    private String ATTACK_2 = "Attack_2";
    private String ATTACK_3 = "Attack_3";
    private String CHARGE_1 = "Charge_1";
    private String DEAD_1 = "Dead_1";
    private String FALL_1 = "Fall_1";
    private String HAMMERATTACK_1 = "HammerAttack_1";
    private String FIGHTSTANCE_1 = "FightStance_1";
    private String FIGHTSTANCE_2 = "FightStance_2";
    private String JUMP_1 = "Jump_1";
    private String RUN_1 = "Run_1";
    private String RUNHAMMER_1 = "RunHammer_1";
    private String STANCE_1 = "Stance_1";
    private String STUNNED_1 = "Stunned_1";
    private String TAUNT_1 = "Taunt_1";
    private String TAUNT_2 = "Taunt_2";
    private String TAUNTHAMMER_1 = "TauntHammer_1";
    /*=====================END====================*/
    
    private AnimationCommands command = AnimationCommands.NONE;
    private float speed = 1;
    
    private AnimControl animControl;
    private AnimChannel animChannel;
    float blendSpeed = 0.3f;
    
    public EnemyAnimControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(checkAnimControl()){
            if(getCommand() == AnimationCommands.F){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(RUN_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUN_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(RUN_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.F_HMR){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(RUNHAMMER_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUNHAMMER_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(RUNHAMMER_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.TAUNT_HMR1){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(TAUNTHAMMER_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(TAUNTHAMMER_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(TAUNTHAMMER_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.TAUNT1){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(TAUNT_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(TAUNT_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(TAUNT_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.TAUNT2){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(TAUNT_2, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(TAUNT_2)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(TAUNT_2, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.DEAD1){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(DEAD_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(DEAD_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(DEAD_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.STUNNED1){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(STUNNED_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(STUNNED_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(STUNNED_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.CHARGE1){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(CHARGE_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(CHARGE_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(CHARGE_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.HMRATK1){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(HAMMERATTACK_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(HAMMERATTACK_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(HAMMERATTACK_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.ATK1){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(ATTACK_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(ATTACK_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(ATTACK_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.ATK2){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(ATTACK_2, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(ATTACK_2)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(ATTACK_2, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.ATK3){
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(ATTACK_3, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(ATTACK_3)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(ATTACK_3, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else{
                if(animChannel.getAnimationName() == null){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(STANCE_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(STANCE_1)){
    //                        setSpeed(2 * effectSpeed());
//                    speed = 1;
                    animChannel.setAnim(STANCE_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    public boolean isAnimDone(){
        if(animChannel != null){
            if(animChannel.getAnimMaxTime() == animChannel.getTime()){
                return true;
            }
        }
        return false;
    }
    
    private boolean checkAnimControl(){
        AnimControl control = spatial.getControl(AnimControl.class);
        if(control != animControl){
            this.animControl = control;
            if(animControl != null){
                animChannel = animControl.createChannel();
            }
        }
        
        return animControl != null;
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        EnemyAnimControl control = new EnemyAnimControl();
        control.setCommand(command);
        control.setSpeed(speed);
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
     * @return the command
     */
    public AnimationCommands getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(AnimationCommands command) {
        this.command = command;
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


}

