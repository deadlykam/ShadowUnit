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
import java.io.IOException;

/**
 *
 * @author Kamran
 */
public class CreditControl extends AbstractControl{

    private float speed = 1;
    private float seqNo = 1.0f;
    private float speed_Dissolve = 0.01f;
    
    float blend = 0.1f;
    int dir = 1;
    boolean initialize = false, changing = false;
    Vector2f vSeq, vType1, vBlend1;;
    float timer = 400;
    
    
    public CreditControl(){
        
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(!initialize){
            Material mat = ((Geometry)((Node)spatial).getChild(0)).getMaterial();
            vSeq = new Vector2f(1, 0);
            vType1 =  new Vector2f(1, 0);
            mat.setVector2("Sequencing", vSeq);
            
            vBlend1 = new Vector2f(1, 1);
            mat.setVector2("Blend1", vBlend1);
            
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
                
                if(timer <= 0){
                nextSeq();
                timer = 400;
                
                }else{
                    timer--;
                }
            }
            
            if(changing){
                vSeq.setX(seqNo);
            }
            
            
        }
    }

    public void nextSeq(){
        seqNo++;
        
        if(seqNo <= 3.0f){
            vSeq.setX(seqNo);
        }else{
            seqNo = 1.0f;
            vSeq.setX(seqNo);
            spatial.getParent().getChild("Back_BTN").getControl(ButtonControl.class).setSelect(true);
        }
    }
    
    public void preSeq(){
        seqNo--;
        
        if(seqNo >= 1.0f){
            vSeq.setX(seqNo);
        }else{
            seqNo = 1.0f;
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        CreditControl control = new CreditControl();
        control.setSpeed(speed);
        control.setSeqNo(seqNo);
        control.setSpeed_Dissolve(speed_Dissolve);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        speed = in.readFloat("speed", 1.0f);
        speed_Dissolve = in.readFloat("speed_Dissolve", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(speed, "speed", 1.0f);
        out.write(speed_Dissolve, "speed_Dissolve", 1.0f);
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
     * @return the seqNo
     */
    public float getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(float seqNo) {
        this.seqNo = seqNo;
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


}
