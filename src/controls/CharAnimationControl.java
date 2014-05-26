package controls;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.export.Savable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import controlplayer.BasicInfo;
import others.AllEnum.*;

/**
 *
 * @author Kamran
 */
public class CharAnimationControl extends AbstractControl implements Savable, Cloneable{

    /*===========List of Animation===========*/
    private String DYING_1 = "Dying_1";
    private String FALL_1 = "Fall_1";
    private String JUMP_1 = "Jump_1";
    private String KICK_1 = "Kick_1";
    private String PALM_1 = "Palm_1";
    private String POWER_1 = "Power_1";
    private String PUNCH_1 = "Punch_1";
    private String PUNCH_2 = "Punch_2";
    private String PUSH_1 = "Push_1";
    private String PUSHKICK_1 = "PushKick_1";
    private String RHKICK_1 = "RHKick_1";
    private String RUN_1 = "Run_1";
    private String RUN_2 = "Run_2";
    private String RUN_2_L = "Run_2_L";
    private String RUN_2_R = "Run_2_R";
    private String RUN_2_LD = "Run_2_LD";
    private String RUN_2_RD = "Run_2.RD";
    private String RUNBACK_1 = "RunBack_1";
    private String RUNBACK_1_LD = "RunBack_1_LD";
    private String RUNBACK_1_RD = "RunBack_1_RD";
    private String STANCE_1 = "Stance_1";
    /*=====================END====================*/
    
    private AnimationCommands command = AnimationCommands.NONE;
    private float speed = 1;
    
    private AnimControl animControl;
    private AnimChannel animChannel;
    
    public boolean change = false;
    
//  Variables for this control ONLY
    float blendSpeed = 0.3f;
    
    public CharAnimationControl(){}
    
    @Override
    protected void controlUpdate(float tpf) {
        BasicInfo bi = spatial.getParent().getControl(BasicInfo.class);
        
        if(checkAnimControl()){
            //==========Anims For No Weapons=========//
            if(getCommand() == AnimationCommands.F){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUN_2)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.L){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_L, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUN_2_L)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_L, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.R){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_R, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUN_2_R)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_R, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.FL){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_LD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUN_2_LD)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_LD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.FR){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_RD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUN_2_RD)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(RUN_2_RD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.B){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 2.8f;
                    animChannel.setAnim(RUNBACK_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUNBACK_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2.8f;
                    animChannel.setAnim(RUNBACK_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.BL){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 2.8f;
                    animChannel.setAnim(RUNBACK_1_LD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUNBACK_1_LD)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2.8f;
                    animChannel.setAnim(RUNBACK_1_LD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.BR){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 2.8f;
                    animChannel.setAnim(RUNBACK_1_RD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(RUNBACK_1_RD)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2.8f;
                    animChannel.setAnim(RUNBACK_1_RD, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.J){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1.5f;
                    animChannel.setAnim(JUMP_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(JUMP_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1.5f;
                    animChannel.setAnim(JUMP_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.FALL){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(FALL_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(FALL_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(FALL_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if(getCommand() == AnimationCommands.PUNCH1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Attack();
                    animChannel.setAnim(PUNCH_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(PUNCH_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Attack();
                    animChannel.setAnim(PUNCH_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.PUNCH2){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Attack();
                    animChannel.setAnim(PUNCH_2, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(PUNCH_2)){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Attack();
                    animChannel.setAnim(PUNCH_2, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.KICK1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Special();
                    animChannel.setAnim(KICK_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(KICK_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2;
                    animChannel.setAnim(KICK_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.PUSH1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Special();
                    animChannel.setAnim(PUSH_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(PUSH_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2;
                    animChannel.setAnim(PUSH_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.PALM1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Special();
                    animChannel.setAnim(PALM_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(PALM_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2;
                    animChannel.setAnim(PALM_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.POWER1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Special();
                    animChannel.setAnim(POWER_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(POWER_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2;
                    animChannel.setAnim(POWER_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.DEAD1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Special();
                    animChannel.setAnim(DYING_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(DYING_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2;
                    animChannel.setAnim(DYING_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.PUSHKICK1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Special();
                    animChannel.setAnim(PUSHKICK_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(PUSHKICK_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2;
                    animChannel.setAnim(PUSHKICK_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else if(getCommand() == AnimationCommands.RHKICK1){
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = bi.getSpeed_Special();
                    animChannel.setAnim(RHKICK_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }else if(!animChannel.getAnimationName().equals(RHKICK_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 2;
                    animChannel.setAnim(RHKICK_1, 1);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.DontLoop);
                }
            }else{
                if(animChannel.getAnimationName() == null){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(STANCE_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }else if(!animChannel.getAnimationName().equals(STANCE_1)){
//                        setSpeed(2 * effectSpeed());
                    speed = 1;
                    animChannel.setAnim(STANCE_1, blendSpeed);
                    animChannel.setSpeed(speed);
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }
//            System.out.println("AnimationCommand: " + command);
        }
        
    }

    
    public boolean isAnimDone(){
        if(animChannel != null){
            if(animChannel.getAnimMaxTime() == animChannel.getTime()){
                return true;
            }
        }
        return false;
    }
    
    public LoopMode getLoopMode(){
        if(animChannel != null){
            return animChannel.getLoopMode();
        }
        return LoopMode.Loop;
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
    
//    private float effectSpeed(){
//        EffectControl ec = ((Node)spatial).getChild("Effects").getControl(EffectControl.class);
//        
//        if(ec != null){
//            if(ec.getSpatialEffectType() == EffectControl.SpatialEffectType.FROZEN){
//                return ec.getEffectValue();
//            }
//        }
//        
//        return 1.0f;
//    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        CharAnimationControl control = new CharAnimationControl();
        control.setCommand(command);
        control.setSpeed(speed);
        control.setSpatial(spatial);
        return control;
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
