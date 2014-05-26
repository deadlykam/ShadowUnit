/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlnpc;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.scene.debug.Arrow;
import controls.CrystalControl;
import java.io.IOException;
import java.util.Random;
import others.AllEnum.*;
import others.GameStarted;

/**
 *
 * @author Kamran
 */
public class AI1Control extends AbstractControl{

    private EnemyType enemyType = EnemyType.NONE;
    AIState aiState = AIState.STAY;
    private Levels levels = Levels.GROUNDLEVEL;
    //Variables for moving the spatial
    private float dist_Follow = 0;
    private float dist_StopFollow = 0;
    private float speed_Movement = 0;
    //Variables for obstacle movement
    private float timer_ObstacleMove = 0;
    private float current_TimerObMove = 0;
    //Variables for damaging crystal
    private float dmg_Crystal = 0;
    private float dmg_Rate = 0;
    private float currentDMGRateTimer = 0;
    //Variables for special effects
    private boolean useEffects = false;
    
    Random ran = new Random(); //<-- Internally Only
    private Vector3f newPoint = new Vector3f();
    private Vector3f faceDir = new Vector3f();
    int attackNo = 0;
    int tauntNo = 1;
    boolean updateTauntNo = false;
    int current_AttackNo = 0;
    boolean waitForDelay = false;
    boolean stunned = false;
    float acceleration_Fall = 0.15f;
    
    public AI1Control(){
        enabled = false;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(enabled){
            if(GameStarted.gameStart){
                if(enemyType == EnemyType.RANGE){
                    ai_Range();
                }else if(enemyType == EnemyType.MELEE){
                    ai_Melee();
                }else if(enemyType == EnemyType.HAMMER){
                    ai_Hammer();
                }else if(enemyType == EnemyType.CRYSTALDRAINER){
                    ai_CrystalDrainer();
                }
                
                if(!findGround()){
                    spatial.move(0, -acceleration_Fall, 0);
                }else{
//                    setLevelYCoords();
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    public void pushSpatial(float push_Force){
        if(enemyType != EnemyType.CRYSTALDRAINER){
            spatial.move(faceDir.negate().normalize().mult(push_Force));
        }else{
            Vector3f dir = getPlayerModel().getWorldTranslation().subtract(spatial.getWorldTranslation());
            dir = new Vector3f(dir.x, 0, dir.z);
            spatial.move(dir.negate().normalize().mult(push_Force));
        }
    }
    
    ////////=================ALL THE AI METHODS================///////
    
    
    private void ai_Hammer(){
        EnemyAnimControl eac = ((Node)spatial).getChild("Enemy").getControl(EnemyAnimControl.class);
        BasicInfo bi = spatial.getControl(BasicInfo.class);
        if(bi.getHp() > 0){
            if(playerLevelCollision() || aiState == AIState.ATTACK){
                if(aiState == AIState.STAY){
                    rotateSpatial();
                    eac.setSpeed(0.7f);
                    eac.setCommand(AnimationCommands.TAUNT_HMR1);

                    int chance = ran.nextInt(100);

                    if(chance >= 0 && chance < 50){
                        attackNo = 1;
                    }

                    if(attackNo > 0){
                        aiState = AIState.ATTACK;
                        waitForDelay = false;
                        current_AttackNo = 0;
                    }

                    if(spatial.getWorldTranslation().distance(getPlayerModel().getWorldTranslation()) > dist_Follow){
                        aiState = AIState.RUN;
                    }
                }else if(aiState == AIState.RUN){
                    moveSpatial();
                    rotateSpatial();
                    eac.setSpeed(0.8f);
                    eac.setCommand(AnimationCommands.F_HMR);

                    if(spatial.getWorldTranslation().distance(getPlayerModel().getWorldTranslation()) < dist_StopFollow){
                        aiState = AIState.STAY;
                    }
                }else if(aiState == AIState.ATTACK){
                    rotateSpatial();

                    if(!waitForDelay){
                        current_AttackNo++;

                        if(current_AttackNo <= attackNo){
                            if(current_AttackNo == 1){
                                eac.setSpeed(1);
                                eac.setCommand(AnimationCommands.HMRATK1);
                            }

                            waitForDelay = true;
                        }else{
                            aiState = AIState.STAY;
                            attackNo = 0;
                        }
                    }else{
                        if(eac.isAnimDone()){
                            waitForDelay = false;
                            spatial.getControl(MeleeInfoControl.class).attackPlayer(current_AttackNo, 
                                    getPlayerModel().getWorldTranslation().subtract(spatial.getWorldTranslation()).normalize());
                            
                            if(useEffects){
                                ((ParticleEmitter)((Node)spatial).getChild("Effect1")).emitAllParticles();
                            }
                        }
                    }
                }else if(aiState == AIState.ATTACK){
                    if(stunned){
                        eac.setSpeed(1);
                        eac.setCommand(AnimationCommands.STUNNED1);
                        stunned = false;
                    }else if(eac.isAnimDone()){
                        aiState = AIState.STAY;
                    }
                }
            }else{
                eac.setSpeed(0.7f);
                eac.setCommand(AnimationCommands.TAUNT_HMR1);
                rotateSpatial();
            }
        }else{
            eac.setSpeed(0.5f);
            eac.setCommand(AnimationCommands.DEAD1);
            //TODO: Dead
        }
        
    }
    
    private void ai_Melee(){
        EnemyAnimControl eac = ((Node)spatial).getChild("Enemy").getControl(EnemyAnimControl.class);
        BasicInfo bi = spatial.getControl(BasicInfo.class);
        if(bi.getHp() > 0){
            if(playerLevelCollision() || aiState == AIState.ATTACK){
                if(aiState == AIState.STAY){
                    rotateSpatial();
                    eac.setSpeed(0.7f);
                    eac.setCommand(AnimationCommands.NONE);

                    int chance = ran.nextInt(100);

                    if(chance >= 0 && chance < 50){
                        attackNo = 3;
                    }

                    if(attackNo > 0){
                        aiState = AIState.ATTACK;
                        waitForDelay = false;
                        current_AttackNo = 0;
                    }

                    if(spatial.getWorldTranslation().distance(getPlayerModel().getWorldTranslation()) > dist_Follow){
                        aiState = AIState.RUN;
                    }
                }else if(aiState == AIState.RUN){
                    moveSpatial();
                    rotateSpatial();
                    eac.setSpeed(0.8f);
                    eac.setCommand(AnimationCommands.F);

                    if(spatial.getWorldTranslation().distance(getPlayerModel().getWorldTranslation()) < dist_StopFollow){
                        aiState = AIState.STAY;
                    }
                }else if(aiState == AIState.ATTACK){
                    rotateSpatial();

                    if(!waitForDelay){
                        current_AttackNo++;

                        if(current_AttackNo <= attackNo){
                            if(current_AttackNo == 1){
                                eac.setSpeed(1);
                                eac.setCommand(AnimationCommands.ATK1);
                            }else if(current_AttackNo == 2){
                                eac.setSpeed(1);
                                eac.setCommand(AnimationCommands.ATK2);
                            }else if(current_AttackNo == 3){
                                eac.setSpeed(1);
                                eac.setCommand(AnimationCommands.ATK3);
                            }

                            waitForDelay = true;
                        }else{
                            aiState = AIState.STAY;
                            attackNo = 0;
                        }
                    }else{
                        if(eac.isAnimDone()){
                            waitForDelay = false;
                            spatial.getControl(MeleeInfoControl.class).attackPlayer(current_AttackNo, 
                                    getPlayerModel().getWorldTranslation().subtract(spatial.getWorldTranslation()).normalize());
                            
                            if(useEffects){
                                if(current_AttackNo == 1){
                                    ((ParticleEmitter)((Node)spatial).getChild("Effect1")).emitAllParticles();
                                }else if(current_AttackNo == 2){
                                    ((ParticleEmitter)((Node)spatial).getChild("Effect2")).emitAllParticles();
                                }else if(current_AttackNo == 3){
                                    ((ParticleEmitter)((Node)spatial).getChild("Effect3")).emitAllParticles();
                                }
                            }
                        }
                    }
                }else if(aiState == AIState.ATTACK){
                    if(stunned){
                        eac.setSpeed(1);
                        eac.setCommand(AnimationCommands.STUNNED1);
                        stunned = false;
                    }else if(eac.isAnimDone()){
                        aiState = AIState.STAY;
                    }
                }
            }else{
                eac.setSpeed(0.7f);
                eac.setCommand(AnimationCommands.TAUNT1);
                rotateSpatial();
            }
        }else{
            eac.setSpeed(0.5f);
            eac.setCommand(AnimationCommands.DEAD1);
            //TODO: Dead
        }
        
    }
    
    private void ai_CrystalDrainer(){
        EnemyAnimControl eac = ((Node)spatial).getChild("Enemy").getControl(EnemyAnimControl.class);
        
        BasicInfo bi = spatial.getControl(BasicInfo.class);
        if(bi.getHp() > 0){
            if(aiState == AIState.STAY){
                rotateSpatialToCrystal();
                eac.setSpeed(0.7f);
                eac.setCommand(AnimationCommands.CHARGE1);

                if(spatial.getWorldTranslation().distance(getCrystalLocation()) > dist_Follow){
                    aiState = AIState.RUN;
                    ((ParticleEmitter)((Node)((Node)spatial).getChild("BulletNode")).getChild("Orb")).setParticlesPerSec(0);
                }
                
                if(currentDMGRateTimer < 0){
                    getCrystalModel().getControl(CrystalControl.class).decreaseHP(dmg_Crystal);
                    currentDMGRateTimer = dmg_Rate;
                }else{
                    currentDMGRateTimer--;
                }
                
                
                
            }else if(aiState == AIState.RUN){
                moveSpatialToCrystal();
                rotateSpatialToCrystal();
                eac.setSpeed(0.8f);
                eac.setCommand(AnimationCommands.F);

                if(spatial.getWorldTranslation().distance(getCrystalLocation()) < dist_StopFollow){
                    aiState = AIState.STAY;
                    ((ParticleEmitter)((Node)((Node)spatial).getChild("BulletNode")).getChild("Orb")).setParticlesPerSec(60);
                }
            }
        }else{
            eac.setSpeed(0.5f);
            ((ParticleEmitter)((Node)((Node)spatial).getChild("BulletNode")).getChild("Orb")).setParticlesPerSec(0);
            eac.setCommand(AnimationCommands.DEAD1);
            //TODO: Dead
        }
    }
    
    private void ai_Range(){
        EnemyAnimControl eac = ((Node)spatial).getChild("Enemy").getControl(EnemyAnimControl.class);
        
        BasicInfo bi = spatial.getControl(BasicInfo.class);
        if(bi.getHp() > 0){
            if(playerLevelCollision() || aiState == AIState.ATTACK){
                if(aiState == AIState.STAY){
                    rotateSpatial();
                    eac.setSpeed(0.7f);
                    eac.setCommand(AnimationCommands.NONE);

                    int chance = ran.nextInt(100);

                    if(chance >= 0 && chance < 40){
                        attackNo = 1;
                    }else if(chance >= 40 && chance < 80){
                        attackNo = 2;
                    }else if(chance >= 80 && chance < 90){
                        attackNo = 3;
                    }

                    if(attackNo > 0){
                        aiState = AIState.ATTACK;
                        waitForDelay = false;
                        current_AttackNo = 0;
                    }

                    if(spatial.getWorldTranslation().distance(getPlayerModel().getWorldTranslation()) > dist_Follow){
                        aiState = AIState.RUN;
                    }
                }else if(aiState == AIState.RUN){
                    moveSpatial();
                    rotateSpatial();
                    eac.setSpeed(0.8f);
                    eac.setCommand(AnimationCommands.F);

                    if(spatial.getWorldTranslation().distance(getPlayerModel().getWorldTranslation()) < dist_StopFollow){
                        aiState = AIState.STAY;
                    }
                }else if(aiState == AIState.ATTACK){
                    rotateSpatial();

                    if(!waitForDelay){
                        current_AttackNo++;

                        if(current_AttackNo <= attackNo){
                            BulletInfoControl bic = ((Node)spatial).getChild("BulletNode").getControl(BulletInfoControl.class);

                            if(current_AttackNo == 1){
                                eac.setSpeed(.5f);
                                eac.setCommand(AnimationCommands.ATK1);
                            }else if(current_AttackNo == 2){
                                eac.setSpeed(.5f);
                                eac.setCommand(AnimationCommands.ATK2);
                            }else if(current_AttackNo == 3){
                                eac.setSpeed(.3f);
                                eac.setCommand(AnimationCommands.ATK3);
                            }

                            waitForDelay = true;
                        }else{
                            aiState = AIState.STAY;
                            attackNo = 0;
                        }
                    }else{
                        if(eac.isAnimDone()){
                            BulletInfoControl bic = ((Node)spatial).getChild("BulletNode").getControl(BulletInfoControl.class);
                            bic.setBulletNo(current_AttackNo);
                            bic.setShot(true);
                            waitForDelay = false;
                        }
                    }
                }
            }else{
                eac.setSpeed(0.7f);
                eac.setCommand(AnimationCommands.TAUNT1);
                rotateSpatial();
            }
        }else{
            eac.setSpeed(0.5f);
            eac.setCommand(AnimationCommands.DEAD1);
            //TODO: Dead
        }
    }
    
    ////////=================END=========================/////
    
    private void moveSpatial(){
        Vector3f dir = getPlayerModel().getWorldTranslation().subtract(spatial.getWorldTranslation());
        dir = new Vector3f(dir.x, 0, dir.z);
        dir.normalizeLocal();
        dir.multLocal(speed_Movement);
        spatial.move(dir);
    }
    
    private void moveSpatialToCrystal(){
        Vector3f dir = getCrystalLocation().subtract(spatial.getWorldTranslation());
        dir = new Vector3f(dir.x, 0, dir.z);
        dir.normalizeLocal();
        dir.multLocal(speed_Movement);
        spatial.move(dir);
    }
    
    private void obstacleMoveRot(){
        Vector3f dir = newPoint;
        dir.normalizeLocal();
        dir.multLocal(speed_Movement);
        spatial.move(dir);
        
        Quaternion rotateSpatial = new Quaternion();
        rotateSpatial.lookAt(newPoint, Vector3f.UNIT_Y);
        spatial.setLocalRotation(rotateSpatial);
    }
    
    private void rotateSpatial(){
        Vector3f dir = getPlayerModel().getWorldTranslation().subtract(spatial.getWorldTranslation());
        dir = new Vector3f(dir.x, 0, dir.z);
        faceDir = dir;
        Quaternion rotateSpatial = new Quaternion();
        rotateSpatial.lookAt(dir, Vector3f.UNIT_Y);
        spatial.setLocalRotation(rotateSpatial);
    }
    
    private void rotateSpatialToCrystal(){
        Vector3f dir = getCrystalLocation().subtract(spatial.getWorldTranslation());
        dir = new Vector3f(dir.x, 0, dir.z);
        faceDir = dir;
        Quaternion rotateSpatial = new Quaternion();
        rotateSpatial.lookAt(dir, Vector3f.UNIT_Y);
        spatial.setLocalRotation(rotateSpatial);
    }
    
    /**
     * @deprecated Not using for now but keeping if it to be safe.
     */
    private void checkForPath(){
        CollisionResults [] results = new CollisionResults [4];
        
        for(int i = 0; i < results.length; i++){
            results[i] = new CollisionResults();
        }
        
        getStageNode().getChild("TerrainNode").collideWith(new Ray(Vector3f.ZERO, Vector3f.UNIT_X), results[0]);
        
//        CollisionResults results2 = new CollisionResults();  
        getStageNode().getChild("TerrainNode").collideWith(new Ray(Vector3f.ZERO, Vector3f.UNIT_X.negate()), results[1]);
        
//        CollisionResults results3 = new CollisionResults();  
        getStageNode().getChild("TerrainNode").collideWith(new Ray(Vector3f.ZERO, Vector3f.UNIT_Z), results[2]);
        
//        CollisionResults results4 = new CollisionResults();  
        getStageNode().getChild("TerrainNode").collideWith(new Ray(Vector3f.ZERO, Vector3f.UNIT_Z.negate()), results[3]);
        
        if(results[0].size() <= 0){
            newPoint = new Vector3f(0, FastMath.HALF_PI, 0);
//            newPoint = Vector3f.UNIT_X;
        }else if(results[1].size() <= 0){
            newPoint = new Vector3f(0, -FastMath.HALF_PI, 0);
//            newPoint = Vector3f.UNIT_X.negate();
        }else if(results[2].size() <= 0){
            newPoint = Vector3f.UNIT_Z;
        }else if(results[3].size() <= 0){
            newPoint = Vector3f.UNIT_Z.negate();
        }else{
            float dist = spatial.getWorldTranslation().distance(results[0].getClosestCollision().getContactPoint());
            int index = 0;
            
            for(int i = 1; i < results.length; i++){
                if(spatial.getWorldTranslation().distance(results[i].getClosestCollision().getContactPoint()) > dist){
                    dist = spatial.getWorldTranslation().distance(results[i].getClosestCollision().getContactPoint());
                    index = i;
                }
            }
            
            newPoint = results[index].getClosestCollision().getContactPoint();
            
            
//            if(spatial.getWorldTranslation().distance(results1.getClosestCollision().getContactPoint()) > 
//                    spatial.getWorldTranslation().distance(results2.getClosestCollision().getContactPoint())){
//                newPoint = Vector3f.UNIT_X;
//            }else{
//                newPoint = Vector3f.UNIT_X.negate();
//            }
        }
        
        current_TimerObMove = timer_ObstacleMove;
    }
    
    private boolean collideWithTerrain(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = ((Node)spatial).getChild("ObstacleDetector").getWorldBound();
        getStageNode().getChild("TerrainNode").collideWith(bv, results);
        
        if(results.size() > 0){
            return true;
        }
        
        return false;
    }
    
    private boolean playerLevelCollision(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = getStageNode().getChild("Player1").getWorldBound();
//        ((Node)getStageNode().getChild(getLevelString())).getChild("Collider").collideWith(bv, results);
        ((Node)getStageNode().getChild(getLevelString())).collideWith(bv, results);
        
        if(results.size() > 0){
            return true;
        }
        
        return false;
    }
    
    private String getLevelString(){
        
        if(levels == Levels.GROUNDLEVEL){
            return "GroundLevel";
        }else if(levels == Levels.FIRSTLEVEL){
            return "FirstLevel";
        }else if(levels == Levels.SECONDLEVEL){
            return "SecondLevel";
        }else if(levels == Levels.THIRDLEVEL){
            return "ThirdLevel";
        }else if(levels == Levels.FOURTHLEVEL){
            return "FourthLevel";
        }
        
        return "none";
    }
    
    private void setLevelYCoords(){
        if(levels == Levels.GROUNDLEVEL){
            spatial.setLocalTranslation(spatial.getLocalTranslation().x,
                                        -0.16083717f,
                                        spatial.getLocalTranslation().z);
        }else if(levels == Levels.FIRSTLEVEL){
            spatial.setLocalTranslation(spatial.getLocalTranslation().x,
                                        13.839163f,
                                        spatial.getLocalTranslation().z);
        }else if(levels == Levels.SECONDLEVEL){
            spatial.setLocalTranslation(spatial.getLocalTranslation().x,
                                        27.83916f,
                                        spatial.getLocalTranslation().z);
        }else if(levels == Levels.THIRDLEVEL){
            spatial.setLocalTranslation(spatial.getLocalTranslation().x,
                                        41.83916f,
                                        spatial.getLocalTranslation().z);
        }else if(levels == Levels.FOURTHLEVEL){
            spatial.setLocalTranslation(spatial.getLocalTranslation().x,
                                        55.839165f,
                                        spatial.getLocalTranslation().z);
        }
    }
    
    private Vector3f getCrystalLocation(){
        if(levels != Levels.NONE){
            return getCrystalModel().getWorldTranslation();
        }
        
        return Vector3f.ZERO;
    }
    
    private Spatial getCrystalModel(){
        return ((Node)((Node)getStageNode().getChild("Levels")).getChild(getLevelString())).getChild("Crystal");
    }
    
    private boolean findGround(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = ((Node)spatial).getChild("Collider").getWorldBound();
        getStageNode().getChild("TerrainNode").collideWith(bv, results);
        
        if(results.size() > 0){
            return true;
        }
        
        
        return false;
    }
    
    private Node getStageNode(){
        return spatial.getParent().getParent().getParent();
    }
    
    private Spatial getPlayerModel(){
        return getStageNode().getChild("Player1");
    }
    
    private void createArrow(ColorRGBA color, Vector3f direction){
        Arrow arrow = new Arrow(direction.mult(4));
        arrow.setLineWidth(3);
        Geometry g = new Geometry("arrow", arrow);
        Material m = new Material(GameStarted.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", color);
        g.setMaterial(m);
        ((Node)spatial).attachChild(g);
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        AI1Control control = new AI1Control();
        control.setEnemyType(enemyType);
        control.setLevels(levels);
        control.setDist_Follow(dist_Follow);
        control.setDist_StopFollow(dist_StopFollow);
        control.setSpeed_Movement(speed_Movement);
        control.setTimer_ObstacleMove(timer_ObstacleMove);
        control.setDmg_Crystal(dmg_Crystal);
        control.setDmg_Rate(dmg_Rate);
        control.setUseEffects(useEffects);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        enemyType = in.readEnum("enemyType", EnemyType.class, EnemyType.NONE);
        dist_Follow = in.readFloat("dist_Follow", 1.0f);
        dist_StopFollow = in.readFloat("dist_StopFollow", 1.0f);
        speed_Movement = in.readFloat("speed_Movement", 1.0f);
        timer_ObstacleMove = in.readFloat("timer_ObstacleMove", 1.0f);
        dmg_Crystal = in.readFloat("dmg_Crystal", 1.0f);
        dmg_Rate = in.readFloat("dmg_Rate", 1.0f);
        useEffects = in.readBoolean("useEffects", true);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(enemyType, "enemyType", EnemyType.NONE);
        out.write(dist_Follow, "dist_Follow", 1.0f);
        out.write(dist_StopFollow, "dist_StopFollow", 1.0f);
        out.write(speed_Movement, "speed_Movement", 1.0f);
        out.write(timer_ObstacleMove, "timer_ObstacleMove", 1.0f);
        out.write(dmg_Crystal, "dmg_Crystal", 1.0f);
        out.write(dmg_Rate, "dmg_Rate", 1.0f);
        out.write(useEffects, "useEffects", true);
    }

    /**
     * @return the enemyType
     */
    public EnemyType getEnemyType() {
        return enemyType;
    }

    /**
     * @param enemyType the enemyType to set
     */
    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    /**
     * @return the dist_Follow
     */
    public float getDist_Follow() {
        return dist_Follow;
    }

    /**
     * @param dist_Follow the dist_Follow to set
     */
    public void setDist_Follow(float dist_Follow) {
        this.dist_Follow = dist_Follow;
    }

    /**
     * @return the dist_StopFollow
     */
    public float getDist_StopFollow() {
        return dist_StopFollow;
    }

    /**
     * @param dist_StopFollow the dist_StopFollow to set
     */
    public void setDist_StopFollow(float dist_StopFollow) {
        this.dist_StopFollow = dist_StopFollow;
    }

    /**
     * @return the speed_Movement
     */
    public float getSpeed_Movement() {
        return speed_Movement;
    }

    /**
     * @param speed_Movement the speed_Movement to set
     */
    public void setSpeed_Movement(float speed_Movement) {
        this.speed_Movement = speed_Movement;
    }

    /**
     * @return the timer_ObstacleMove
     */
    public float getTimer_ObstacleMove() {
        return timer_ObstacleMove;
    }

    /**
     * @param timer_ObstacleMove the timer_ObstacleMove to set
     */
    public void setTimer_ObstacleMove(float timer_ObstacleMove) {
        this.timer_ObstacleMove = timer_ObstacleMove;
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

    /**
     * @return the dmg_Crystal
     */
    public float getDmg_Crystal() {
        return dmg_Crystal;
    }

    /**
     * @param dmg_Crystal the dmg_Crystal to set
     */
    public void setDmg_Crystal(float dmg_Crystal) {
        this.dmg_Crystal = dmg_Crystal;
    }

    /**
     * @return the dmg_Rate
     */
    public float getDmg_Rate() {
        return dmg_Rate;
    }

    /**
     * @param dmg_Rate the dmg_Rate to set
     */
    public void setDmg_Rate(float dmg_Rate) {
        this.dmg_Rate = dmg_Rate;
    }

    /**
     * @return the useEffects
     */
    public boolean isUseEffects() {
        return useEffects;
    }

    /**
     * @param useEffects the useEffects to set
     */
    public void setUseEffects(boolean useEffects) {
        this.useEffects = useEffects;
    }


}
