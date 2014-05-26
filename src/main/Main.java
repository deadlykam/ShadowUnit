package main;

import appstates.InputAppState;
import appstates.MenuAppState;
import appstates.PlayerAppState;
import appstates.StageAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import others.AllEnum.GameState;
import others.GameStarted;

/**
 * test
 * @author Kamran Wali
 */
public class Main extends SimpleApplication {
    
    StageAppState stageAppState;
    PlayerAppState playerAppState;
    InputAppState inputAppState;
    MenuAppState menuAppState;
    
    GameStarted gameStarted; //<-- For testing only.
    
    public static GameState gameState = GameState.LOADMENU;
    
    Spatial stage;
    private AudioNode music;
    
    BulletAppState bulletAppState;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings newSetting = new AppSettings(true);
        newSetting.setFrameRate(30);
        newSetting.setResolution(1280, 720);
        newSetting.setTitle("Shadow Unit");
        app.setSettings(newSetting);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(30f);
        
        setDisplayFps(false);
        setDisplayStatView(false);
        
        setupPhysics();
        initAllAppStates();
        gameStarted = new GameStarted(assetManager); //<-- For testing only.
//        setupMusic();
    }

    private void setupMusic(){
        music = new AudioNode(assetManager, "Sounds/music1.wav");
        music.setLooping(true);  // activate continuous playing
        music.setPositional(true);   
        music.setVolume(3);
        rootNode.attachChild(music);
        music.play();
    }
    
    private void initAllAppStates(){
        stageAppState = new StageAppState();
        playerAppState = new PlayerAppState(settings);
        inputAppState = new InputAppState();
        menuAppState = new MenuAppState(mouseInput);
    }
    
    private void setupPhysics(){
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);//<--For Debug only
    }
    
    ///*******************************ALL LOADING METHODS***************************///
    
    private void loadStage(){
        mouseInput.setCursorVisible(false);
	flyCam.setEnabled(true);
        
        stateManager.detach(menuAppState);
        stateManager.attach(inputAppState);
        stateManager.attach(stageAppState);
        stateManager.attach(playerAppState);
    }
    
    private void loadMenu(){
        mouseInput.setCursorVisible(true);
	flyCam.setEnabled(false);
        
        stateManager.detach(inputAppState);
        stateManager.detach(stageAppState);
        stateManager.detach(playerAppState);
        stateManager.attach(menuAppState);
        
//        setupMusic();
    }
    
    private void loadScoreMenu(){
        mouseInput.setCursorVisible(true);
	flyCam.setEnabled(false);
        
        stateManager.detach(inputAppState);
        stateManager.detach(stageAppState);
        stateManager.detach(playerAppState);
        stateManager.attach(menuAppState);
        
        menuAppState.loadScoreBoard = true;
    }
    
    private void loadTest(){
//        rootNode.attachChild(assetManager.loadModel("Models/Others/Bounce1/Bounce1.mesh.j3o"));
////        cam.setLocation(Vector3f.ZERO);
//        System.out.println("Load Test");
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        if(gameState == GameState.LOADSTAGE){
            loadStage();
            gameState = GameState.PLAYING;
        }else if(gameState == GameState.LOADMENU){
            loadMenu();
            gameState = GameState.PLAYING;
        }else if(gameState == GameState.LOADSCOREMENU){
           loadScoreMenu();
            gameState = GameState.PLAYING;
        }else if(gameState == GameState.TEST){
            loadTest();
            gameState = GameState.PLAYING;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
