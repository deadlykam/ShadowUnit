/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controls;

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
import java.util.List;
import others.AllEnum.Levels;
import others.GameStarted;
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class GenObserverControl extends AbstractControl{

    private int enemyToKill = 0;
    private int enemyKill = 0;
    private int maxID = 0;
    private float tempXP = 0;
    private float tempMoney = 0;
    
    private int id = 1;
    private boolean stageDone = false;
    private boolean defeated = false;
    private boolean setGenerators = false;
    private Levels levels = Levels.NONE;
    private float timerToEnd = 100;
    
    public GenObserverControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        if(!stageDone && !defeated){
            if(GameStarted.gameStart){
                if(!setGenerators){
                    enemyKill = 0;
                    enableGenerators();
                    setGenerators = true;
                }else{
                    if(enemyKill >= enemyToKill){
                        disableGenerators();
                        killAllEnemies();
                        if(id > maxID){
                            stageDone = true;
                            StoredInfo.money = StoredInfo.money + tempMoney;
                            StoredInfo.tempMoney = tempMoney;
                            StoredInfo.xp = StoredInfo.xp + tempXP;
                            StoredInfo.tempXP = tempXP;
                        }else{
                            setGenerators = false;
                        }
                    }
                    
                    defeatConditions();
                }
            }
        }else if(stageDone){
            if(getTimerToEnd() >= 0){
                timerToEnd--;
            }
//            System.out.println("Stage Done! Hurray!");
        }else if(defeated){
            if(getTimerToEnd() >= 0){
                timerToEnd--;
            }
//            System.out.println("Defeated NOOOOOOoooooo.....!");
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    public void addXP(float amount){
        tempXP = tempXP + amount;
    }
    
    public void addMoney(float amount){
        tempMoney = tempMoney + amount;
    }
    
    public void enemyKilled(){
        enemyKill++;
    }
    
    private void defeatConditions(){
        playerKilled();
        crystalDestroyed();
        
    }
    
    private void playerKilled(){
        if(((Node)spatial).getChild("Player1").getControl(controlplayer.BasicInfo.class).getHp() <= 0){
            defeated = true;
            
            tempMoney = tempMoney / 2;
            tempXP = tempXP / 2;

            StoredInfo.money = StoredInfo.money + tempMoney;
            StoredInfo.tempMoney = tempMoney;
            StoredInfo.xp = StoredInfo.xp + tempXP;
            StoredInfo.tempXP = tempXP;
        }
    }
    
    private void crystalDestroyed(){
        if(levels == Levels.GROUNDLEVEL){
            if( ((Node)((Node)spatial).getChild("GroundLevel")).getChild("Crystal").getControl(CrystalControl.class).getHp() <= 0 ){
                defeated = true;
                
                tempMoney = tempMoney / 2;
                tempXP = tempXP / 2;

                StoredInfo.money = StoredInfo.money + tempMoney;
                StoredInfo.tempMoney = tempMoney;
                StoredInfo.xp = StoredInfo.xp + tempXP;
                StoredInfo.tempXP = tempXP;
            }
        }else if(levels == Levels.FIRSTLEVEL){
            if( ((Node)((Node)spatial).getChild("FirstLevel")).getChild("Crystal").getControl(CrystalControl.class).getHp() <= 0 ){
                defeated = true;
                
                tempMoney = tempMoney / 2;
                tempXP = tempXP / 2;

                StoredInfo.money = StoredInfo.money + tempMoney;
                StoredInfo.tempMoney = tempMoney;
                StoredInfo.xp = StoredInfo.xp + tempXP;
                StoredInfo.tempXP = tempXP;
            }
        }else if(levels == Levels.SECONDLEVEL){
            if( ((Node)((Node)spatial).getChild("SecondLevel")).getChild("Crystal").getControl(CrystalControl.class).getHp() <= 0 ){
                defeated = true;
                
                tempMoney = tempMoney / 2;
                tempXP = tempXP / 2;

                StoredInfo.money = StoredInfo.money + tempMoney;
                StoredInfo.tempMoney = tempMoney;
                StoredInfo.xp = StoredInfo.xp + tempXP;
                StoredInfo.tempXP = tempXP;
            }
        }else if(levels == Levels.THIRDLEVEL){
            if( ((Node)((Node)spatial).getChild("ThirdLevel")).getChild("Crystal").getControl(CrystalControl.class).getHp() <= 0 ){
                defeated = true;
                
                tempMoney = tempMoney / 2;
                tempXP = tempXP / 2;

                StoredInfo.money = StoredInfo.money + tempMoney;
                StoredInfo.tempMoney = tempMoney;
                StoredInfo.xp = StoredInfo.xp + tempXP;
                StoredInfo.tempXP = tempXP;
            }
        }else if(levels == Levels.FOURTHLEVEL){
            if( ((Node)((Node)spatial).getChild("FourthLevel")).getChild("Crystal").getControl(CrystalControl.class).getHp() <= 0 ){
                defeated = true;
                
                tempMoney = tempMoney / 2;
                tempXP = tempXP / 2;

                StoredInfo.money = StoredInfo.money + tempMoney;
                StoredInfo.tempMoney = tempMoney;
                StoredInfo.xp = StoredInfo.xp + tempXP;
                StoredInfo.tempXP = tempXP;
            }
        }
    }
    
    private void enableGenerators(){
        List<Spatial> allSpatial = ((Node)((Node)spatial).getChild("Generators")).getChildren();
        
        for(int i = 0; i < allSpatial.size(); i++){
            GeneratorControl gc = allSpatial.get(i).getControl(GeneratorControl.class);
            if(gc.getId() == id){
                gc.setEnable(true);
                
                if(gc.getEnemyToKill() != 0){
                    enemyToKill = gc.getEnemyToKill();
                }
                levels = gc.getLevels();
                enableCrystal();
            }
        }
        id++;
    }
    
    private void disableGenerators(){
        List<Spatial> allSpatial = ((Node)((Node)spatial).getChild("Generators")).getChildren();
        
        for(int i = 0; i < allSpatial.size(); i++){
            GeneratorControl gc = allSpatial.get(i).getControl(GeneratorControl.class);
            if(gc.getId() == id - 1){
                gc.setEnable(false);
                disableCrystal();
            }
        }
        
        levels = Levels.NONE;
    }
    
    private void killAllEnemies(){
        List<Spatial> allSpatial = ((Node)((Node)spatial).getChild("Gen_Enemies")).getChildren();
        
        for(int i = 0; i < allSpatial.size(); i++){
            allSpatial.get(i).getControl(controlnpc.BasicInfo.class).setHp(0);
        }
        
        enemyKill = 0;
    }
    
    private void enableCrystal(){
        if(levels == Levels.GROUNDLEVEL){
            ((Node)((Node)spatial).getChild("GroundLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(false);
        }else if(levels == Levels.FIRSTLEVEL){
            ((Node)((Node)spatial).getChild("FirstLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(false);
        }else if(levels == Levels.SECONDLEVEL){
            ((Node)((Node)spatial).getChild("SecondLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(false);
        }else if(levels == Levels.THIRDLEVEL){
            ((Node)((Node)spatial).getChild("ThirdLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(false);
        }else if(levels == Levels.FOURTHLEVEL){
            ((Node)((Node)spatial).getChild("FourthLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(false);
        }
    }
    
    private void disableCrystal(){
        if(levels == Levels.GROUNDLEVEL){
            ((Node)((Node)spatial).getChild("GroundLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(true);
        }else if(levels == Levels.FIRSTLEVEL){
            ((Node)((Node)spatial).getChild("FirstLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(true);
        }else if(levels == Levels.SECONDLEVEL){
            ((Node)((Node)spatial).getChild("SecondLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(true);
        }else if(levels == Levels.THIRDLEVEL){
            ((Node)((Node)spatial).getChild("ThirdLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(true);
        }else if(levels == Levels.FOURTHLEVEL){
            ((Node)((Node)spatial).getChild("FourthLevel")).getChild("Crystal").getControl(CrystalControl.class).setSaved(true);
        }
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        GenObserverControl control = new GenObserverControl();
        control.setMaxID(maxID);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        maxID = in.readInt("maxID", 1);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(maxID, "maxID", 1);
    }

    /**
     * @return the enemyToKill
     */
    public int getEnemyToKill() {
        return enemyToKill;
    }

    /**
     * @param enemyToKill the enemyToKill to set
     */
    public void setEnemyToKill(int enemyToKill) {
        this.enemyToKill = enemyToKill;
    }

    /**
     * @return the enemyKill
     */
    public int getEnemyKill() {
        return enemyKill;
    }

    /**
     * @param enemyKill the enemyKill to set
     */
    public void setEnemyKill(int enemyKill) {
        this.enemyKill = enemyKill;
    }

    /**
     * @return the maxID
     */
    public int getMaxID() {
        return maxID;
    }

    /**
     * @param maxID the maxID to set
     */
    public void setMaxID(int maxID) {
        this.maxID = maxID;
    }

    /**
     * @return the stageDone
     */
    public boolean isStageDone() {
        return stageDone;
    }

    /**
     * @return the defeated
     */
    public boolean isDefeated() {
        return defeated;
    }

    /**
     * @return the tempXP
     */
    public float getTempXP() {
        return tempXP;
    }

    /**
     * @param tempXP the tempXP to set
     */
    public void setTempXP(float tempXP) {
        this.tempXP = tempXP;
    }

    /**
     * @return the tempMoney
     */
    public float getTempMoney() {
        return tempMoney;
    }

    /**
     * @param tempMoney the tempMoney to set
     */
    public void setTempMoney(float tempMoney) {
        this.tempMoney = tempMoney;
    }

    /**
     * @return the timerToEnd
     */
    public float getTimerToEnd() {
        return timerToEnd;
    }


}
