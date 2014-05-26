/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controls;

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
import java.io.IOException;
import others.AllEnum.*;
import others.GameStarted;

/**
 *
 * @author Kamran
 */
public class CrystalControl extends AbstractControl{

    private float maxHp = 100;
    private float hp = 100;
    private boolean saved = false;
    
    private float timer = 200;
    private float dmg = 1;
    
    public CrystalControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(GameStarted.gameStart){
            if(!saved){
                if(!playerLevelCollision()){
                    if(timer <= 0){
                        decreaseHP(dmg);
                        timer = 200;
                    }else{
                        timer--;
                    }
                }
            }
        }
        
        if(hp < 0){
            //TODO dead
        }
        
        
        ((Node)spatial).getChild("InnerCrystal").setLocalScale((hp/maxHp) + .001f);
    }

    public void decreaseHP(float damage){
        if(hp > 0){
            hp = hp - damage;
        }
        
        if(hp < 0){
            hp = 0;
        }
    }
    
    private boolean playerLevelCollision(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = getPlayerModel().getWorldBound();
        spatial.getParent().collideWith(bv, results);
        
        if(results.size() > 0){
            return true;
        }
        
        return false;
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent().getParent().getParent();
    }
    
    private Spatial getPlayerModel(){
        return getStageNode().getChild("Player1");
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        CrystalControl control = new CrystalControl();
        control.setMaxHp(maxHp);
        control.setHp(hp);
        control.setSaved(saved);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        maxHp = in.readFloat("maxHp", 1.0f);
        hp = in.readFloat("hp", 1.0f);
        saved = in.readBoolean("saved", true);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(maxHp, "maxHp", 1.0f);
        out.write(hp, "hp", 1.0f);
        out.write(saved, "saved", true);
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
     * @return the saved
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * @param saved the saved to set
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }


}
