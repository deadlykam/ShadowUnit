/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlUI;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import controls.ComboListControl;
import java.io.IOException;
import others.AllEnum.*;
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class ButtonControl extends AbstractControl{

    private ButtonEvents buttonEvents = ButtonEvents.NONE;
    private float speed_Dissolve = 0.01f;
    private boolean select = false;
    private String target = "";
    
    private boolean initialize = false;
    Vector2f vBlend1;
    
    public ButtonControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(!initialize){
            Material mat = ((Geometry)((Node)spatial).getChild(0)).getMaterial();
            vBlend1 = new Vector2f(0, 0);
            mat.setVector2("DissolveParams", vBlend1);
            initialize = true;
        }else{
            if(!spatial.getParent().getControl(MenuControl.class).isFocus()){
                if(vBlend1.getX() < 1){
                    vBlend1.setX(vBlend1.getX() + getSpeed_Dissolve());
                }
            }else{
                if(vBlend1.getX() > 0){
                    vBlend1.setX(vBlend1.getX() - getSpeed_Dissolve());
                }
            }
            
            buttonEventAction();
        }
    }

    private void buttonEventAction(){
        if(select){
            //Menus
            if(getButtonEvents() == ButtonEvents.MAIN){
                spatial.getParent().getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Credit").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Tutorial").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Game").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Store").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("GameMenu").getControl(MenuControl.class).setFocus(false);
                
                spatial.getParent().getParent().getChild("GameMenu").getControl(MenuControl.class).setFocus(true);
            }else if(getButtonEvents() == ButtonEvents.OPTION){
                spatial.getParent().getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Option").getControl(MenuControl.class).setFocus(true);
            }else if(getButtonEvents() == ButtonEvents.CREDIT){
                spatial.getParent().getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Credit").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Tutorial").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Game").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Store").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("GameMenu").getControl(MenuControl.class).setFocus(false);
                
                spatial.getParent().getParent().getChild("Credit").getControl(MenuControl.class).setFocus(true);
            }else if(getButtonEvents() == ButtonEvents.TUTORIAL){
                spatial.getParent().getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Credit").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Tutorial").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Game").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Store").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("GameMenu").getControl(MenuControl.class).setFocus(false);
                
                spatial.getParent().getParent().getChild("Tutorial").getControl(MenuControl.class).setFocus(true);
            }else if(getButtonEvents() == ButtonEvents.GAME){
                spatial.getParent().getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Credit").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Tutorial").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Game").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Store").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("GameMenu").getControl(MenuControl.class).setFocus(false);
                
                spatial.getParent().getParent().getChild("Game").getControl(MenuControl.class).setFocus(true);
            }else if(getButtonEvents() == ButtonEvents.STORE){
                spatial.getParent().getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Credit").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Tutorial").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Game").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("Store").getControl(MenuControl.class).setFocus(false);
                spatial.getParent().getParent().getChild("GameMenu").getControl(MenuControl.class).setFocus(false);
                
                spatial.getParent().getParent().getChild("Store").getControl(MenuControl.class).setFocus(true);
            }
            
            //Actions
            if(getButtonEvents() == ButtonEvents.PREVIOUS){
                spatial.getParent().getChild(target).getControl(SequenceControl.class).preSeq();
            }else if(getButtonEvents() == ButtonEvents.NEXT){
                spatial.getParent().getChild(target).getControl(SequenceControl.class).nextSeq();
            }else if(getButtonEvents() == ButtonEvents.EXIT){
                System.exit(0);
            }else if(getButtonEvents() == ButtonEvents.LIST_NXT){
                spatial.getParent().getChild(target).getControl(ComboListControl.class).nextCombo();
            }else if(getButtonEvents() == ButtonEvents.LIST_PRE){
                spatial.getParent().getChild(target).getControl(ComboListControl.class).preCombo();
            }else if(getButtonEvents() == ButtonEvents.TUT_NXT){
                spatial.getParent().getChild(target).getControl(SequenceControl.class).nextSeq();
            }else if(getButtonEvents() == ButtonEvents.TUT_PRE){
                spatial.getParent().getChild(target).getControl(SequenceControl.class).preSeq();
            }else if(getButtonEvents() == ButtonEvents.ATK_ADD){
                if(StoredInfo.money > 30000){
                    StoredInfo.money = StoredInfo.money - 30000;
                    StoredInfo.damage = StoredInfo.damage + 0.2f;
                    
                    LabelControl lc = spatial.getControl(LabelControl.class);
                    lc.changeText("+ ATTACK(" + StoredInfo.damage +")");
                }
            }else if(getButtonEvents() == ButtonEvents.DEF_ADD){
                if(StoredInfo.money > 30000){
                    if(StoredInfo.defense < .7){
                        StoredInfo.money = StoredInfo.money - 30000;
                        StoredInfo.defense = StoredInfo.defense + 0.05f;

                        LabelControl lc = spatial.getControl(LabelControl.class);
                        lc.changeText("+ DEFENSE(" + StoredInfo.defense +")");
                    }
                }
            }
            
            select = false;
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ButtonControl control = new ButtonControl();
        control.setButtonEvents(buttonEvents);
        control.setSpeed_Dissolve(speed_Dissolve);
        control.setTarget(target);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        buttonEvents = in.readEnum("buttonEvents", ButtonEvents.class, ButtonEvents.NONE);
        speed_Dissolve = in.readFloat("speed_Dissolve", 1.0f);
        target = in.readString("target", "");
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(buttonEvents, "buttonEvents", ButtonEvents.NONE);
        out.write(speed_Dissolve, "speed_Dissolve", 1.0f);
        out.write(target, "target", "");
    }

    /**
     * @return the select
     */
    public boolean isSelect() {
        return select;
    }

    /**
     * @param select the select to set
     */
    public void setSelect(boolean select) {
        this.select = select;
    }

    /**
     * @return the speed_Dissolve
     */
    public float getSpeed_Dissolve() {
        return speed_Dissolve;
    }

    /**
     * @param speed_Dissolve the speed_Dissolve to set
     */
    public void setSpeed_Dissolve(float speed_Dissolve) {
        this.speed_Dissolve = speed_Dissolve;
    }

    /**
     * @return the buttonEvents
     */
    public ButtonEvents getButtonEvents() {
        return buttonEvents;
    }

    /**
     * @param buttonEvents the buttonEvents to set
     */
    public void setButtonEvents(ButtonEvents buttonEvents) {
        this.buttonEvents = buttonEvents;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }


}
