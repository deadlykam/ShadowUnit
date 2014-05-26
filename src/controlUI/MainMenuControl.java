/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlUI;

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
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class MainMenuControl extends AbstractControl{

    private int level = 1;
    
    boolean init = false;
    
    public MainMenuControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(!init){
            checkLevel();
            init = true;
        }
    }

    private void checkLevel(){
        if(StoredInfo.xp >= 3600){
            level = 2;
            StoredInfo.nextXP = 5400;
        }
        
        if(StoredInfo.xp >= 5400){
            level = 3;
            StoredInfo.nextXP = 7200;
        }
        
        if(StoredInfo.xp >= 7200){
            level = 4;
            StoredInfo.nextXP = 9000;
        }
        
        if(StoredInfo.xp >= 9000){
            level = 5;
            StoredInfo.nextXP = 10800;
        }
        
        if(StoredInfo.xp >= 10800){
            StoredInfo.nextXP = 12600;
            level = 6;
        }
        
        if(StoredInfo.xp >= 12600){
            StoredInfo.nextXP = 14400;
            level = 7;
        }
        
        if(StoredInfo.xp >= 14400){
            StoredInfo.nextXP = 16200;
            level = 8;
        }
        
        if(StoredInfo.xp >= 16200){
            StoredInfo.nextXP = 18000;
            level = 9;
        }
        
        if(StoredInfo.xp >= 18000){
            StoredInfo.nextXP = 18000;
            level = 10;
        }
        
        StoredInfo.level = level;
        
        if(StoredInfo.xp > 18000){
            StoredInfo.xp = 18000;
        }
        
        if(StoredInfo.money > 99999){
            StoredInfo.money = 99999;
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        MainMenuControl control = new MainMenuControl();
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
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
