/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.Listener;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.system.AppSettings;
import controlplayer.BasicInfo;
import controls.CameraInfoControl;
import controls.ComboSetupControl;
import controls.DropBoxControl;
import controls.GenObserverControl;
import others.AllEnum;
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class PlayerAppState extends AbstractAppState{
    
    private SimpleApplication app;
    private Node              rootNode;
    private Node              guiNode;
    private AssetManager      assetManager;
    private AppStateManager   stateManager;
    private InputManager      inputManager;
    private ViewPort          viewPort;
    private BulletAppState    physics;
    private AppSettings       settings;
    public  Camera            cam;
    private Listener          listener;
    
    public BitmapText stats, objective;
    
    public CameraNode cameraNode;
    
    public PlayerAppState(AppSettings settings){
        this.settings = settings;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode     = this.app.getRootNode();
        this.guiNode      = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort     = this.app.getViewPort();
        this.physics      = this.stateManager.getState(BulletAppState.class);
        this.cam          = this.app.getCamera();
        this.listener     = this.app.getListener();
        
        setupPlayerInfo();
        setupCameraNode();
        
        setupText();
    }
    
    private void setupText(){
        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        stats = new BitmapText(fnt, false);
        stats.setSize(fnt.getCharSet().getRenderedSize() * 1.5f);
        String tempState = "Score: 0.0" + "\n" + "XP: 0.0" + "\n" + "Money: ";
        stats.setText(tempState);
        int perWidth = (int)(settings.getWidth() * 0.03f);
        int perHeight = (int)(settings.getHeight() * 1f);
        stats.setLocalTranslation(perWidth, perHeight, 0);
        
        objective = new BitmapText(fnt, false);
        objective.setSize(fnt.getCharSet().getRenderedSize() * 1.5f);
        objective.setText("0/0");
        int perWidth2 = (int)(settings.getWidth() * 0.4f);
        int perHeight2 = (int)(settings.getHeight() * 1f);
        objective.setLocalTranslation(perWidth2, perHeight2, 0);
        
        guiNode.attachChild(stats);
        guiNode.attachChild(objective);
    }
    
    private void updateTexts(){
        GenObserverControl goc = getStageModel().getControl(GenObserverControl.class);
        
        String tempState = "Score: " + (goc.getTempMoney() + goc.getTempXP()) 
                + "\n" + "XP: " + goc.getTempXP() + "\n" + "Money: " + goc.getTempMoney();
        
        String tempObjective = goc.getEnemyKill() + " / " + goc.getEnemyToKill();
        
        stats.setText(tempState);
        objective.setText(tempObjective);
    }
    
    private void setupPlayerInfo(){
        getPlayerModel().getControl(BasicInfo.class).setDefense(StoredInfo.defense);
        getPlayerModel().getControl(BasicInfo.class).setDamage(StoredInfo.damage);
        
        getPlayerModel().getControl(ComboSetupControl.class).setCombo1_1(StoredInfo.combo1_1);
        getPlayerModel().getControl(ComboSetupControl.class).setCombo2_1(StoredInfo.combo2_1);
        getPlayerModel().getControl(ComboSetupControl.class).setCombo3_1(StoredInfo.combo3_1);
        getPlayerModel().getControl(ComboSetupControl.class).setCombo4_1(StoredInfo.combo4_1);
        getPlayerModel().getControl(ComboSetupControl.class).setCombo5_1(StoredInfo.combo5_1);
        getPlayerModel().getControl(ComboSetupControl.class).setCombo6_1(StoredInfo.combo6_1);
    }
    
    private void setupCameraNode(){
        CameraInfoControl control = ((Node)getPlayerModel()).getChild("CameraNode").getControl(CameraInfoControl.class);
        cameraNode = new CameraNode("CamNode", cam);
        cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        ((Node)((Node)getPlayerModel()).getChild("CameraNode")).attachChild(cameraNode);
        cameraNode.setLocalTranslation(new Vector3f(0, 0, control.getDistance()));
        cameraNode.lookAt(((Node)((Node)getPlayerModel()).getChild("CameraNode")).getLocalTranslation(), Vector3f.UNIT_Y);
        ((Node)((Node)getPlayerModel()).getChild("CameraNode")).getLocalRotation().fromAngleAxis(-control.getVerticalAngle(), Vector3f.UNIT_X);
    }
    
    public void horizontalRotate(float angle){
        CameraInfoControl control = ((Node)getPlayerModel()).getChild("CameraNode").getControl(CameraInfoControl.class);
        control.setHorizontalAngle(control.getHorizontalAngle() + angle);
        ((Node)((Node)getPlayerModel()).getChild("CameraNode")).getLocalRotation().fromAngleAxis(control.getHorizontalAngle(), Vector3f.UNIT_Y);
    }
    
    public void verticalRotate(float angle){
        CameraInfoControl control = ((Node)getPlayerModel()).getChild("CameraNode").getControl(CameraInfoControl.class);
        control.setVerticalAngle(control.getVerticalAngle() + angle);
        ((Node)((Node)getPlayerModel()).getChild("CameraNode")).getLocalRotation().fromAngleAxis(-control.getVerticalAngle(), Vector3f.UNIT_X);
    }
    
    public Spatial getPlayerModel(){
        return ((Node)this.app.getStateManager().getState(StageAppState.class).stage).getChild("Player1");
    }
    
    public Node getStageModel(){
        return ((Node)this.app.getStateManager().getState(StageAppState.class).stage);
    }
    
    private BetterCharacterControl getBCC(){
        return this.app.getStateManager().getState(StageAppState.class).physicsControl;
    }
    
    private InputAppState getInputAppState(){
        return  this.app.getStateManager().getState(InputAppState.class);
    }
    
    private void playerJumperCollide(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = getPlayerModel().getWorldBound();
        getStageModel().getChild("JumperNodes").collideWith(bv, results);
        
        if(results.size() > 0){
            String parentName = results.getClosestCollision().getGeometry().getParent().getName();
            
            if(parentName.equals("Collider")){
                getBCC().setJumpForce(new Vector3f(0, 20f, 0));
                getBCC().jump();
                getInputAppState().jump = true;
                getBCC().setJumpForce(new Vector3f(0, 9f, 0));
//                System.out.println("JUMP!");
            }
        }
    }
    
    private void playerItemsCollide(){
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = getPlayerModel().getWorldBound();
        getStageModel().getChild("Items").collideWith(bv, results);
        
        if(results.size() > 0){
            String parentName = results.getClosestCollision().getGeometry().getParent().getName();
            DropBoxControl dbc = results.getClosestCollision().getGeometry().getParent().getControl(DropBoxControl.class);
            
            if(parentName.equals("HPDrop")){
                getPlayerModel().getControl(BasicInfo.class).increaseHP(dbc.getAmount());
            }else if(parentName.equals("MoneyDrop")){
                getStageModel().getControl(GenObserverControl.class).addMoney(dbc.getAmount());
            }else if(parentName.equals("XPDrop")){
                getStageModel().getControl(GenObserverControl.class).addXP(dbc.getAmount());
            }
            
            dbc.setTaken(true);
        }
    }
    
    private void moveListener(){
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
    }
    
    private void disableInput(){
        if(getPlayerModel().getControl(BasicInfo.class).getHp() <= 0){
            getInputAppState().disableKeys = true;
        }
    }
    
    @Override
    public void update(float tpf) {
        playerJumperCollide();
        playerItemsCollide();
        moveListener();
        updateTexts();
        disableInput();
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        this.app.getRootNode().detachAllChildren();
        this.app.getGuiNode().detachAllChildren();
    }
}
