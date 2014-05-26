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
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.TranslucentBucketFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import controlUI.AnimateControl;
import controlUI.ButtonControl;
import controlUI.MenuControl;
import controlUI.ScoreControl;
import controlUI.SequenceControl;
import main.Main;
import others.AllEnum;
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class MenuAppState extends AbstractAppState implements ActionListener{
    
    private SimpleApplication app;
    private Node              rootNode;
    private AssetManager      assetManager;
    private ViewPort          viewPort;
    private InputManager      inputManager;
    public  Camera            cam;
    private MouseInput        mouseInput;
    
    FilterPostProcessor fpp;
    
    public Spatial mainMenu;
    
    public boolean loadScoreBoard = false;
    
    public MenuAppState(MouseInput mouseInput){
        this.mouseInput = mouseInput;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode     = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.viewPort     = this.app.getViewPort();
        this.inputManager = this.app.getInputManager();
        this.cam          = this.app.getCamera();
        
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        
        loadMainMenu();
        
        if(loadScoreBoard){
            loadScoreMenu();
            loadScoreBoard = false;
        }
        
        setupCamera();
        setupKeys();
        setupFilters();
    }
    
    private void setupFilters(){
        fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        
        BloomFilter bf = new BloomFilter(BloomFilter.GlowMode.Objects);
       
        TranslucentBucketFilter tbf = new TranslucentBucketFilter();
        
        fpp.addFilter(bf);
        fpp.addFilter(tbf);
    }
    
    private void setupKeys(){
        inputManager.addMapping("LeftClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        inputManager.addListener(this, "LeftClick");
    }
    
    public void loadScoreMenu(){
        ((Node)mainMenu).getChild("GameMenu").getControl(MenuControl.class).setFocus(false);
        ((Node)mainMenu).getChild("GameMenu").setCullHint(Spatial.CullHint.Always);
        ((Node)mainMenu).getChild("ScoreMenu").getControl(MenuControl.class).setFocus(true);
    }
    
    private void selectedMenu_Item_Selection(){
        Ray ray = new Ray(mouseWorldPosition(), mouseWorldDirection());
        
        CollisionResults results = new CollisionResults();
        ((Node)mainMenu).collideWith(ray, results);
        
        if(results.size() > 0){
            ButtonControl bc = results.getClosestCollision().getGeometry().getParent().getControl(ButtonControl.class);
            AnimateControl ac = results.getClosestCollision().getGeometry().getParent().getControl(AnimateControl.class);
            
            if(bc != null){
                if(bc.getButtonEvents() == AllEnum.ButtonEvents.START){
                    if(((Node)((Node)mainMenu).getChild("Game")).getChild("Screen").getControl(SequenceControl.class).getSeqNo() == 1.0f){
                        //TODO: Start game
                        Main.gameState = Main.gameState = AllEnum.GameState.LOADSTAGE;
                    }
                }else{
                    bc.setSelect(true);
                }
            }else if(ac != null){
                ac.setSelect(true);
            }else{
                bc = results.getClosestCollision().getGeometry().getParent().getParent().getControl(ButtonControl.class);
                
                if(bc != null){
                    if(bc.getButtonEvents() == AllEnum.ButtonEvents.START){
                        if(((Node)((Node)mainMenu).getChild("Game")).getChild("Screen").getControl(SequenceControl.class).getSeqNo() == 1.0f){
                            //TODO: Start game
                             Main.gameState = Main.gameState = AllEnum.GameState.LOADSTAGE;
                        }
                    }else{
                        bc.setSelect(true);
                    }
                }
            }
        }
    }
    
    private Vector3f mouseWorldPosition(){
        Vector3f mousePosition;
        
        Vector2f mouse2D = inputManager.getCursorPosition();
        mousePosition = cam.getWorldCoordinates(new Vector2f(mouse2D.x, mouse2D.y), 0f).clone();
        
        return mousePosition;
    }
    
    private Vector3f mouseWorldDirection(){
        Vector3f mouseDirection = new Vector3f(0, 0, 0);
        
        Vector2f mouse2D = inputManager.getCursorPosition();
        Vector3f mouse3d = cam.getWorldCoordinates(new Vector2f(mouse2D.x, mouse2D.y), 0f).clone();
        mouseDirection = cam.getWorldCoordinates(new Vector2f(mouse2D.x, mouse2D.y), 1f).subtractLocal(mouse3d).normalizeLocal();
        
        return mouseDirection;
    }
    
    private void loadMainMenu(){
        mainMenu = assetManager.loadModel("Models/UI/Menus.j3o");
        rootNode.attachChild(mainMenu);
    }
    
    private void setupCamera(){
        cam.setLocation(((Node)mainMenu).getChild("CameraNode").getLocalTranslation());
        cam.setRotation(((Node)mainMenu).getChild("CameraNode").getLocalRotation());
    }
    
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("LeftClick")){
            if(isPressed){
                selectedMenu_Item_Selection();
            }
        }
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        this.app.getRootNode().detachAllChildren();
        fpp.removeAllFilters();
        viewPort.removeProcessor(fpp);
    }

    
}
