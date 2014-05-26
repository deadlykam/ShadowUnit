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
import controlnpc.AI1Control;
import controlnpc.BasicInfo;
import java.io.IOException;
import others.AllEnum.*;
import others.GameStarted;

/**
 *
 * @author Kamran
 */
public class GeneratorControl extends AbstractControl{

    private int id = 0;
    private int maxEnemy = 0;
    private int enemyToKill = 0;
    private float timer_EnemyGenerate = 0;
    private String enemyModelName = "";
    private boolean enable = false;
    private Levels levels = Levels.NONE;
    
    float currentTimer_EnemyGenerate = 0;
    private int currentEnemy = 0;
    
    public GeneratorControl(){
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(enable){
            DissolveControl dc = spatial.getControl(DissolveControl.class);
            if(currentEnemy == 0){
                spatial.setCullHint(Spatial.CullHint.Dynamic);
                dc.setAppear(true);
            }else if(currentEnemy >= maxEnemy){
                dc.setAppear(false);
                if(!dc.isAppearedDone()){
                    spatial.setCullHint(Spatial.CullHint.Always);
                }
            }

            if(dc.isAppearedDone()){
                generateEnemy();
            }
        }else{
            DissolveControl dc = spatial.getControl(DissolveControl.class);
            dc.setAppear(false);
            if(!dc.isAppearedDone()){
                spatial.setCullHint(Spatial.CullHint.Always);
            }
        }
    }

    private void generateEnemy(){
        if(currentEnemy < maxEnemy){
            if(currentTimer_EnemyGenerate <= 0){
                Spatial enemy = getEnemySpatial().clone();
                enemy.setLocalTranslation(spatial.getWorldTranslation());
                enemy.setCullHint(Spatial.CullHint.Dynamic);
                ((Node)((Node)getStageNode().getChild("Enemies")).getChild("Gen_Enemies")).attachChild(enemy);

                BasicInfo bi = enemy.getControl(BasicInfo.class);
                bi.setGeneratorName(spatial.getName());

                AI1Control ai1 = enemy.getControl(AI1Control.class);
                ai1.setLevels(levels);
                ai1.setEnabled(true);

                currentEnemy++;
                currentTimer_EnemyGenerate = timer_EnemyGenerate;
            }else{
                currentTimer_EnemyGenerate--;
            }
        }
    }
    
    public void enemyKilled(){
        currentEnemy--;
    }
    
    private Spatial getEnemySpatial(){
        return ((Node)getStageNode().getChild("EnemyModels")).getChild(enemyModelName);
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent();
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        GeneratorControl control = new GeneratorControl();
        control.setId(id);
        control.setEnemyToKill(enemyToKill);
        control.setMaxEnemy(maxEnemy);
        control.setTimer_EnemyGenerate(timer_EnemyGenerate);
        control.setEnemyModelName(enemyModelName);
        control.setLevels(levels);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        id = in.readInt("id", 1);
        enemyToKill = in.readInt("enemyToKill", 1);
        maxEnemy = in.readInt("maxEnemy", 1);
        timer_EnemyGenerate = in.readFloat("timer_EnemyGenerate", 1.0f);
        enemyModelName = in.readString("enemyModelName", "");
        levels = in.readEnum("levels", Levels.class, Levels.NONE);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(id, "id", 1);
        out.write(enemyToKill, "enemyToKill", 1);
        out.write(maxEnemy, "maxEnemy", 1);
        out.write(timer_EnemyGenerate, "timer_EnemyGenerate", 1.0f);
        out.write(enemyModelName, "enemyModelName", "");
        out.write(levels, "levels", Levels.NONE);
    }

    /**
     * @return the maxEnemy
     */
    public int getMaxEnemy() {
        return maxEnemy;
    }

    /**
     * @param maxEnemy the maxEnemy to set
     */
    public void setMaxEnemy(int maxEnemy) {
        this.maxEnemy = maxEnemy;
    }

    /**
     * @return the timer_EnemyGenerate
     */
    public float getTimer_EnemyGenerate() {
        return timer_EnemyGenerate;
    }

    /**
     * @param timer_EnemyGenerate the timer_EnemyGenerate to set
     */
    public void setTimer_EnemyGenerate(float timer_EnemyGenerate) {
        this.timer_EnemyGenerate = timer_EnemyGenerate;
    }

    /**
     * @return the enemyModelName
     */
    public String getEnemyModelName() {
        return enemyModelName;
    }

    /**
     * @param enemyModelName the enemyModelName to set
     */
    public void setEnemyModelName(String enemyModelName) {
        this.enemyModelName = enemyModelName;
    }

    /**
     * @return the currentEnemy
     */
    public int getCurrentEnemy() {
        return currentEnemy;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * @return the levels
     */
    public Levels getLevels() {
        return levels;
    }

    /**
     * @param levels the levels to set
     */
    public void setLevels(Levels levels) {
        this.levels = levels;
    }


}
