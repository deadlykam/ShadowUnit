/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appstates;

import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import controlplayer.BasicInfo;
import controlplayer.EffectControl;
import controls.CharAnimationControl;
import controls.ComboSetupControl;
import controls.GenObserverControl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import others.AllEnum.*;
import others.AnimCommandQueue;
import others.ComboMove;
import others.ComboMoveExecution;

/**
 *
 * @author Kamran
 */
public class InputAppState extends AbstractAppState implements ActionListener, 
        AnalogListener{

    private SimpleApplication app;
    private AppStateManager   stateManager;
    private InputManager      inputManager;
    private Camera            cam;
    
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private float mouselookSpeed = FastMath.PI/2;
    private float bounceAcceleration = 0;
    
    public boolean forward = false, backward = false, left = false, right = false, 
            jump = false, midair = false, fall = false, comboInUse = false, special = false;
    
    public boolean disableKeys = false;
    
    //////////================= Combo Variables
    private HashSet<String> pressedMappings = new HashSet<String>();
//    AnimCommandQueue comboQueue;
    
    private ComboMove combo1;
    private ComboMoveExecution combo1Exec_C1;
    private ComboMove combo2;
    private ComboMoveExecution combo2Exec_C1;
    private ComboMove combo3;
    private ComboMoveExecution combo3Exec_C1;
    private ComboMove combo4;
    private ComboMoveExecution combo4Exec_C1;
    private ComboMove combo5;
    private ComboMoveExecution combo5Exec_C1;
    private ComboMove combo6;
    private ComboMoveExecution combo6Exec_C1;
    
    private ComboMove currentMove = null;
    private float currentMoveCastTime = 0;
    private float time = 0;
    float jumpDelay = 10f, comboInUseDelay = 10, specialComboDelay = 10;
    int comboSize = 3;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.inputManager = this.app.getInputManager();
        this.stateManager = this.app.getStateManager();
        this.cam          = this.app.getCamera();

        disableKeys = false;
        comboInUse = false;
        
        setupKeys();
        setupCombos();
    }
    
    private void setupKeys(){
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("TurnLeft", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping("TurnRight", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping("MouselookDown", new MouseAxisTrigger(MouseInput.AXIS_Y,true));
	inputManager.addMapping("MouselookUp", new MouseAxisTrigger(MouseInput.AXIS_Y,false));
        inputManager.addMapping("MouseAttack1", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("MouseAttack2", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        
        inputManager.addListener(this, "Forward");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Backward");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "TurnLeft");
        inputManager.addListener(this, "TurnRight");
        inputManager.addListener(this, "MouselookDown");
        inputManager.addListener(this, "MouselookUp");
        inputManager.addListener(this, "MouseAttack1");
        inputManager.addListener(this, "MouseAttack2");
        inputManager.addListener(this, "Jump");
    }
    
    private void setupCombos(){
//        comboQueue = new AnimCommandQueue();
        
        combo1 = new ComboMove("Combo1");
        combo1.press("MouseAttack1").done();
        combo1.setCastTime(0);
        combo1.setPriority(0.1f);
        combo1.setUseFinalState(false);
        combo1Exec_C1 = new ComboMoveExecution(combo1);
        
        combo2 = new ComboMove("Combo2");
        combo2.press("MouseAttack1").done();
        combo2.notPress("MouseAttack1").done();
        combo2.press("MouseAttack1").done();
        combo2.setCastTime(0);
        combo2.setPriority(0.2f);
        combo2.setUseFinalState(false);
        combo2Exec_C1 = new ComboMoveExecution(combo2);
        
        combo3 = new ComboMove("Combo3");
        combo3.press("MouseAttack1").done();
        combo3.notPress("MouseAttack1").done();
        combo3.press("MouseAttack1").done();
        combo3.notPress("MouseAttack1").done();
        combo3.press("MouseAttack1").done();
        combo3.setCastTime(0);
        combo3.setPriority(0.3f);
        combo3.setUseFinalState(false);
        combo3Exec_C1 = new ComboMoveExecution(combo3);
        
        combo4 = new ComboMove("Combo4");
        combo4.press("MouseAttack1").done();
        combo4.notPress("MouseAttack1").done();
        combo4.press("MouseAttack1").done();
        combo4.notPress("MouseAttack1").done();
        combo4.press("MouseAttack1").done();
        combo4.notPress("MouseAttack1").done();
        combo4.press("MouseAttack1").done();
        combo4.setCastTime(0);
        combo4.setPriority(0.4f);
        combo4.setUseFinalState(false);
        combo4Exec_C1 = new ComboMoveExecution(combo4);
        
        combo5 = new ComboMove("Combo5");
        combo5.press("MouseAttack1").done();
        combo5.notPress("MouseAttack1").done();
        combo5.press("MouseAttack1").done();
        combo5.notPress("MouseAttack1").done();
        combo5.press("MouseAttack1").done();
        combo5.notPress("MouseAttack1").done();
        combo5.press("MouseAttack1").done();
        combo5.notPress("MouseAttack1").done();
        combo5.press("MouseAttack1").done();
        combo5.setCastTime(0);
        combo5.setPriority(0.5f);
        combo5.setUseFinalState(false);
        combo5Exec_C1 = new ComboMoveExecution(combo5);
        
        combo6 = new ComboMove("Combo6");
        combo6.press("MouseAttack1").done();
        combo6.notPress("MouseAttack1").done();
        combo6.press("MouseAttack1").done();
        combo6.notPress("MouseAttack1").done();
        combo6.press("MouseAttack1").done();
        combo6.notPress("MouseAttack1").done();
        combo6.press("MouseAttack1").done();
        combo6.notPress("MouseAttack1").done();
        combo6.press("MouseAttack1").done();
        combo6.notPress("MouseAttack1").done();
        combo6.press("MouseAttack1").done();
        combo6.setCastTime(0);
        combo6.setPriority(0.6f);
        combo6.setUseFinalState(false);
        combo6Exec_C1 = new ComboMoveExecution(combo6);
    }
    
    public void onAction(String name, boolean isPressed, float tpf) {
        if(!disableKeys){
            if (isPressed){
                pressedMappings.add(name);
            }else{
                pressedMappings.remove(name);
            }

            if(name.equals("Forward")){
                if(isPressed){
                    forward = true;
                }else{
                    forward = false;
                }
            }

            if(name.equals("Backward")){
                if(isPressed){
                    backward = true;
                }else{
                    backward = false;
                }
            }

            if(name.equals("Left")){
                if(isPressed){
                    left = true;
                }else{
                    left = false;
                }
            }

            if(name.equals("Right")){
                if(isPressed){
                    right = true;
                }else{
                    right = false;
                }
            }

            if(name.equals("MouseAttack1")){
                //TODO: Fix this later.
    //            if(isPressed){
                    comboInUse = true;
                    comboInUseDelay = 10;
    //                System.out.println("ComboInUse is True");
    //            }
            }

            if(name.equals("MouseAttack2")){
    //            if(isPressed){
    //                comboInUse = true;
    //                comboInUseDelay = 10;
    //            }
            }

            if(name.equals("Jump")){
                if(isPressed){
                    if(getBCC().isOnGround()){
    //                    CharAnimationControl2 control = getPlayerModel().getControl(CharAnimationControl2.class);
                        getBCC().jump();
                        jumpDelay = 10f;
    //                    control.setCommand(control.JUMP);
                        jump = true;
                    }
                }
            }

            //==============Combo Codes

            // the pressed mappings was changed. update combo executions
            if(specialComboDelay <= 0){
                List<ComboMove> invokedMoves = new ArrayList<ComboMove>();

                if (combo1Exec_C1.updateState(pressedMappings, time)){
                    invokedMoves.add(combo1);
                }

                if (combo2Exec_C1.updateState(pressedMappings, time)){
                    invokedMoves.add(combo2);
                }

                if (combo3Exec_C1.updateState(pressedMappings, time)){
                    invokedMoves.add(combo3);
                }

                if(comboSize > 3){
                    if (combo4Exec_C1.updateState(pressedMappings, time)){
                        invokedMoves.add(combo4);
                    }
                }

                if(comboSize > 4){
                    if (combo5Exec_C1.updateState(pressedMappings, time)){
                        invokedMoves.add(combo5);
                    }
                }

                if(comboSize > 5){
                    if (combo6Exec_C1.updateState(pressedMappings, time)){
                        invokedMoves.add(combo6);
                    }
                }
                if (invokedMoves.size() > 0){
                    // choose move with highest priority
                    float priority = 0;
                    ComboMove toExec = null;
                    for (ComboMove move : invokedMoves){
                        if (move.getPriority() > priority){
                            priority = move.getPriority();
                            toExec = move;
                            }
                        }
                        if (currentMove != null && currentMove.getPriority() > toExec.getPriority()){
                            return;
                        }

                        currentMove = toExec;
                        currentMoveCastTime = currentMove.getCastTime();
                }
                //System.out.println("CASTING " + currentMove.getMoveName());
            }
        }
    }

    public void onAnalog(String name, float value, float tpf) {
        if(!disableKeys){
            if(name.equals("TurnLeft")){
                Quaternion turn = new Quaternion();
                turn.fromAngleAxis(mouselookSpeed*value, Vector3f.UNIT_Y);
                        getBCC().setViewDirection(turn.mult(getBCC().getViewDirection()));
    //                    getPlayerAppState().horizontalRotate(mouselookSpeed * value);
            }

            if (name.equals("TurnRight")){
                Quaternion turn = new Quaternion();
                turn.fromAngleAxis(-mouselookSpeed*value, Vector3f.UNIT_Y);
                        getBCC().setViewDirection(turn.mult(getBCC().getViewDirection()));
    //                    getPlayerAppState().horizontalRotate(-mouselookSpeed * value);
            }

            if (name.equals("MouselookDown")){
                getPlayerAppState().verticalRotate(mouselookSpeed*value);

            }

            if (name.equals("MouselookUp")){
                getPlayerAppState().verticalRotate(-mouselookSpeed*value);
            }
        }
        
    }
    
    private void setMovementAnim(float tpf){
        CharAnimationControl animation = ((Node)getPlayerAppState().getPlayerModel()).getChild("Ninja").getControl(CharAnimationControl.class);
        
        if(!comboInUse && comboInUseDelay <= 0){
            if(midair){
                animation.setCommand(AnimationCommands.J);
            }else if(fall){
                animation.setCommand(AnimationCommands.FALL);
            }else if(forward && left){
                animation.setCommand(AnimationCommands.FL);
            }else if(forward && right){
                animation.setCommand(AnimationCommands.FR);
            }else if(backward && left){
                animation.setCommand(AnimationCommands.BL);
            }else if(backward && right){
                animation.setCommand(AnimationCommands.BR);
            }else if(forward){
                animation.setCommand(AnimationCommands.F);
            }else if(right){
                animation.setCommand(AnimationCommands.R);
            }else if(left){
                animation.setCommand(AnimationCommands.L);
            }else if(backward){
                animation.setCommand(AnimationCommands.B);
            }else{
                if(getPlayerAppState().getPlayerModel().getControl(BasicInfo.class).getHp() > 0){
                    animation.setCommand(AnimationCommands.NONE);
                }else{
                    animation.setCommand(AnimationCommands.DEAD1);
                }
//                System.out.println("NONE animation");
            }
        }
        
        if(animation.isAnimDone()){
            comboInUse = false;
        }
        
        if(comboInUseDelay > 0){
            comboInUseDelay--;
        }
    }
    
//    private void storeCombo(AnimationCommands command){
//        comboQueue.add(command);
//    }
    
    /**
     * @deprecated Not used anymore.
     */
    private void setComboAnim(AnimationCommands commands){
//        if(!comboQueue.isEmpty()){
//            CharAnimationControl animation = ((Node)getPlayerAppState().getPlayerModel()).getChild("Ninja").getControl(CharAnimationControl.class);
////            if(animation.isAnimDone() || animation.getLoopMode() == LoopMode.Loop){
//                animation.setCommand(comboQueue.pop());
////            }
//        }
        
        CharAnimationControl animation = ((Node)getPlayerAppState().getPlayerModel()).getChild("Ninja").getControl(CharAnimationControl.class);
        animation.setCommand(commands);
    }
    
    private void sendComboCommand(ComboName command){
        ComboSetupControl csc = getPlayerAppState().getPlayerModel().getControl(ComboSetupControl.class);
        csc.setComboCommand(command);
        
        comboSize = csc.getComboChainSize();
    }
    
    private PlayerAppState getPlayerAppState(){
        return this.app.getStateManager().getState(PlayerAppState.class);
    }
    
    private BetterCharacterControl getBCC(){
        return this.app.getStateManager().getState(StageAppState.class).physicsControl;
    }
    
    private void updateCombos(float tpf){
        time += tpf;
        
        if(specialComboDelay <= 0){
            combo1Exec_C1.updateExpiration(time);
            combo2Exec_C1.updateExpiration(time);
            combo3Exec_C1.updateExpiration(time);
            
            if(comboSize > 3){
                combo4Exec_C1.updateExpiration(time);
            }
            if(comboSize > 4){
                combo5Exec_C1.updateExpiration(time);
            }
            
            if(comboSize > 5){
                combo6Exec_C1.updateExpiration(time);
            }
        }
        
        if (currentMove != null){
            currentMoveCastTime -= tpf;
            if (currentMoveCastTime <= 0){
//                System.out.println("DONE CASTING " + currentMove.getMoveName());
                
                if(currentMove.getMoveName().equals("Combo1")){
                    sendComboCommand(ComboName.COMBO1);
                }
                
                if(currentMove.getMoveName().equals("Combo2")){
                    sendComboCommand(ComboName.COMBO2);
                }
                
                if(currentMove.getMoveName().equals("Combo3")){
                    sendComboCommand(ComboName.COMBO3);
                    
                    if(comboSize == 3){
                        special = true;
                        specialComboDelay = 10;
//                        System.out.println("Special True");
                    }
                }
                
                if(currentMove.getMoveName().equals("Combo4")){
                    sendComboCommand(ComboName.COMBO4);
                    
                    if(comboSize == 4){
                        special = true;
                        specialComboDelay = 10;
                    }
                }
                
                if(currentMove.getMoveName().equals("Combo5")){
                    sendComboCommand(ComboName.COMBO5);
                    
                    if(comboSize == 5){
                        special = true;
                        specialComboDelay = 10;
                    }
                }
                
                if(currentMove.getMoveName().equals("Combo6")){
                    sendComboCommand(ComboName.COMBO6);
                    
                    if(comboSize == 6){
                        special = true;
                        specialComboDelay = 10;
                    }
                }
                
                currentMoveCastTime = 0;
                currentMove = null;
            }
        }
        
        if(specialComboDelay > 0){
            specialComboDelay--;
        }
    }
    
    @Override
    public void update(float tpf) {
        BasicInfo bi = getPlayerAppState().getPlayerModel().getControl(BasicInfo.class);
        EffectControl ec = getPlayerAppState().getPlayerModel().getControl(EffectControl.class);
        
        Vector3f camDir = cam.getDirection().clone();
        camDir.y = 0;
        Vector3f camLeft = cam.getLeft().clone();
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);

        if(!comboInUse){
            if (left){
                walkDirection.addLocal(camLeft);
            }
            if (right){ 
                walkDirection.addLocal(camLeft.negate());
            }
            if (forward){ 
                walkDirection.addLocal(camDir);
                if(!jump && !midair){
            //                    animation.setState(animation.RUN_3);
                }
            }
            if (backward){ 
                walkDirection.addLocal(camDir.negate());
                if(!jump && !midair){
            //                    animation.setState(animation.RUNBACK_1);
                }
            }
        }else{
            walkDirection.addLocal(camDir);
        }
        
//        if(jump){
//            midair = true;
//            jump = false;
//        }
        
        if(jump){
            if(!getBCC().isOnGround() || jumpDelay > 0){
               midair = true;
               jumpDelay--;
            }else{
                midair = false; 
                jump = false;
            }
        }else{
            if(!getBCC().isOnGround()){
                fall = true;
            }else{
                fall = false;
            }
        }
        
        if(!comboInUse){
            walkDirection.normalizeLocal().multLocal(bi.getSpeed_Movement());
        }else{
//            if(!special){
//                walkDirection.normalizeLocal().multLocal(bi.getSpeed_AttackMovement());
////                System.out.println("Attack Move");
//            }else{
//                walkDirection.normalizeLocal().multLocal(bi.getSpeed_SpecialMovement());
//                special = false;
////                System.out.println("Special Move");
//            }
        }
        
//        walkDirection.addLocal(ec.getDir_Push());
//        ec.setDir_Push(Vector3f.ZERO);
        
        if(getPlayerAppState().getPlayerModel().getControl(EffectControl.class).getEffects() == SpecialEffects.FREEZE){
            walkDirection.multLocal(getPlayerAppState().getPlayerModel().getControl(EffectControl.class).getSpeedReduction());
        }
        
        getBCC().setWalkDirection(walkDirection);
        setMovementAnim(tpf);
        updateCombos(tpf);
//        setComboAnim();
    }
    
    
    @Override
    public void cleanup() {
        super.cleanup();
        this.app.getRootNode().detachAllChildren();
    }
}
