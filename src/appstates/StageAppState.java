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
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.filters.TranslucentBucketFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;
import controls.GenObserverControl;
import others.AllEnum;
import others.GameStarted;

/**
 *
 * @author Kamran
 */
public class StageAppState extends AbstractAppState{
    private SimpleApplication app;
    private Node              rootNode;
    private AssetManager      assetManager;
    private AppStateManager   stateManager;
    private ViewPort          viewPort;
    private BulletAppState    physics;
    
    public Spatial stage;
    public BetterCharacterControl physicsControl;
    FilterPostProcessor fpp;
    DirectionalLightShadowRenderer dlsr;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.viewPort = this.app.getViewPort();
        this.physics = this.stateManager.getState(BulletAppState.class);
        
        viewPort.setBackgroundColor(new ColorRGBA(0,0,0,0));
        
        loadStage();
        setupBetterCharacterControl();
        addPhysics();
        setupFilters();
    }
    
    private void setupFilters(){
        fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        
        BloomFilter bf = new BloomFilter(BloomFilter.GlowMode.Objects);
        
        FogFilter ff = new FogFilter(ColorRGBA.White, 1, 50);
        
        TranslucentBucketFilter tbf = new TranslucentBucketFilter();
        
        fpp.addFilter(bf);
        fpp.addFilter(ff);
        fpp.addFilter(tbf);
    }
    
    /**
     * Loads the stage
     */
    public void loadStage(){
        stage = assetManager.loadModel("Scenes/Stage1.j3o");
        rootNode.attachChild(stage);
    }
    
    /**
     * Sets up the physics for the player character.
     */
    private void setupBetterCharacterControl(){
        physicsControl = new BetterCharacterControl(.2f, 2, 1);
        physicsControl.setGravity(new Vector3f(0, -9.81f, 0)); //-9.81f
        physicsControl.setJumpForce(new Vector3f(0, 9f, 0));
//        physicsControl.
        ((Node)stage).getChild("Player1").addControl(physicsControl);
        physics.getPhysicsSpace().add(physicsControl);
    }
    
    private void addPhysics(){
        physics.getPhysicsSpace().addAll(stage);
    }
    
    @Override
    public void update(float tpf) {
        if(stage.getControl(GenObserverControl.class).getTimerToEnd() <= 0){
            this.app.getStateManager().getState(InputAppState.class).disableKeys = true;
            main.Main.gameState = AllEnum.GameState.LOADSCOREMENU;
        }
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        this.app.getRootNode().detachAllChildren();
        fpp.removeAllFilters();
        viewPort.removeProcessor(fpp);
    }
}
