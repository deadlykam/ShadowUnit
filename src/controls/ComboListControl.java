/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controls;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import controlUI.LabelControl;
import java.io.IOException;
import others.AllEnum;
import others.GameStarted;
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class ComboListControl extends AbstractControl{

    private int comboNumber = 0;
    
    private int numberOfCombos = 4;
    private int currentComboNo = 1;
    private boolean init = false;
    
    public ComboListControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(GameStarted.gameStart){
            if(!init){
                if(comboNumber == 1){
//                    currentComboNo = animationToInt(((Node)spatial.getParent().getChild("Model")).getChild("Player1").getControl(ComboSetupControl.class).getCombo1_1());
                    currentComboNo = animationToInt(StoredInfo.combo1_1);
                }else if(comboNumber == 2){
                    currentComboNo = animationToInt(StoredInfo.combo2_1);
                }else if(comboNumber == 3){
                    currentComboNo = animationToInt(StoredInfo.combo3_1);
                }else if(comboNumber == 4){
                    currentComboNo = animationToInt(StoredInfo.combo4_1);
                }else if(comboNumber == 5){
                    currentComboNo = animationToInt(StoredInfo.combo5_1);
                }else if(comboNumber == 6){
                    currentComboNo = animationToInt(StoredInfo.combo6_1);
                }

                spatial.getControl(LabelControl.class).changeText(comboList());
                init = true;
            }
            
            checkMoveAvailability();
        }
    }

    private void checkMoveAvailability(){
        if(StoredInfo.level >= 4){
            numberOfCombos = 5;
        }
        
        if(StoredInfo.level >= 6){
            numberOfCombos = 6;
        }
        
        if(StoredInfo.level >= 8){
            numberOfCombos = 7;
        }
        
        if(StoredInfo.level >= 10){
            numberOfCombos = 8;
        }
        
        if(comboNumber == 4){
            if(StoredInfo.level < 3){
                spatial.getControl(LabelControl.class).changeText("Level 3");
            }
        }else if(comboNumber == 5){
            if(StoredInfo.level < 8){
                spatial.getControl(LabelControl.class).changeText("Level 8");
            }
        }else if(comboNumber == 6){
            if(StoredInfo.level < 9){
                spatial.getControl(LabelControl.class).changeText("Level 10");
            }
        }
    }
    
    public void preCombo(){
        currentComboNo--;
        
        if(currentComboNo >= 1){
            
            spatial.getControl(LabelControl.class).changeText(comboList());
            
            if(comboNumber == 4){
                if(StoredInfo.level < 3){
                    spatial.getControl(LabelControl.class).changeText("Level 3");
                }
            }else if(comboNumber == 5){
                if(StoredInfo.level < 8){
                    spatial.getControl(LabelControl.class).changeText("Level 8");
                }
            }else if(comboNumber == 6){
                if(StoredInfo.level < 9){
                    spatial.getControl(LabelControl.class).changeText("Level 10");
                }
            }
            
            setupComboMove();
        }else{
            currentComboNo = 1;
        }
    }
    
    public void nextCombo(){
        currentComboNo++;
        
        if(currentComboNo <= numberOfCombos){
            spatial.getControl(LabelControl.class).changeText(comboList());
            
            
            if(comboNumber == 4){
                if(StoredInfo.level < 3){
                    spatial.getControl(LabelControl.class).changeText("Level 3");
                }
            }else if(comboNumber == 5){
                if(StoredInfo.level < 8){
                    spatial.getControl(LabelControl.class).changeText("Level 8");
                }
            }else if(comboNumber == 6){
                if(StoredInfo.level < 9){
                    spatial.getControl(LabelControl.class).changeText("Level 10");
                }
            }
            
            setupComboMove();
        }else{
            currentComboNo = numberOfCombos;
        }
    }
    
    private void setupComboMove(){
        if(comboNumber == 1){
//            ((Node)spatial.getParent().getChild("Model")).getChild("Player1").getControl(ComboSetupControl.class).setCombo1_1(intToAnimation());
            StoredInfo.combo1_1 = intToAnimation();
        }else if(comboNumber == 2){
            StoredInfo.combo2_1 = intToAnimation();
        }else if(comboNumber == 3){
            StoredInfo.combo3_1 = intToAnimation();
        }else if(comboNumber == 4){
            StoredInfo.combo4_1 = intToAnimation();
        }else if(comboNumber == 5){
            StoredInfo.combo5_1 = intToAnimation();
        }else if(comboNumber == 6){
            StoredInfo.combo6_1 = intToAnimation();
        }
    }
    
    private String comboList(){
        if(currentComboNo == 1){
            return "Punch1";
        }
        
        if(currentComboNo == 2){
            return "Punch2";
        }
        
        if(currentComboNo == 3){
            return "Kick";
        }
        
        if(currentComboNo == 4){
            return "Push";
        }
        
        if(currentComboNo == 5){
            return "Palm";
        }
        
        if(currentComboNo == 6){
            return "PushKick";
        }
        
        if(currentComboNo == 7){
            return "RH Kick";
        }
        
        if(currentComboNo == 8){
            return "Power";
        }
        
        return "none";
    }
    
    private int animationToInt(AllEnum.AnimationCommands commands){
        if(commands == AllEnum.AnimationCommands.PUNCH1){
            return 1;
        }
        
        if(commands == AllEnum.AnimationCommands.PUNCH2){
            return 2;
        }
        
        if(commands == AllEnum.AnimationCommands.KICK1){
            return 3;
        }
        
        if(commands == AllEnum.AnimationCommands.PUSH1){
            return 4;
        }
        
        if(commands == AllEnum.AnimationCommands.PALM1){
            return 5;
        }
        
        if(commands == AllEnum.AnimationCommands.PUSHKICK1){
            return 6;
        }
        
        if(commands == AllEnum.AnimationCommands.RHKICK1){
            return 7;
        }
        
        if(commands == AllEnum.AnimationCommands.POWER1){
            return 8;
        }
        
        return 0;
    }
    
    public AllEnum.AnimationCommands intToAnimation(){
        if(currentComboNo == 1){
            return AllEnum.AnimationCommands.PUNCH1;
        }
        
        if(currentComboNo == 2){
            return AllEnum.AnimationCommands.PUNCH2;
        }
        
        if(currentComboNo == 3){
            return AllEnum.AnimationCommands.KICK1;
        }
        
        if(currentComboNo == 4){
            return AllEnum.AnimationCommands.PUSH1;
        }
        
        if(currentComboNo == 5){
            return AllEnum.AnimationCommands.PALM1;
        }
        
        if(currentComboNo == 6){
            return AllEnum.AnimationCommands.PUSHKICK1;
        }
        
        if(currentComboNo == 7){
            return AllEnum.AnimationCommands.RHKICK1;
        }
        
        if(currentComboNo == 8){
            return AllEnum.AnimationCommands.POWER1;
        }
        
        return AllEnum.AnimationCommands.NONE;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ComboListControl control = new ComboListControl();
        control.setComboNumber(comboNumber);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        comboNumber = in.readInt("comboNumber", 1);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(comboNumber, "comboNumber", 1);
    }

    /**
     * @return the comboNumber
     */
    public int getComboNumber() {
        return comboNumber;
    }

    /**
     * @param comboNumber the comboNumber to set
     */
    public void setComboNumber(int comboNumber) {
        this.comboNumber = comboNumber;
    }


}
