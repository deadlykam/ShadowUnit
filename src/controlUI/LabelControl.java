/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlUI;

import com.jme3.asset.AssetManager;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.control.Control;
import com.jme3.system.JmeSystem;
import java.io.IOException;
import others.GameStarted;
import others.StoredInfo;

/**
 *
 * @author Kamran
 */
public class LabelControl extends AbstractControl implements Savable, Cloneable{
    
    private String message = "Put Message Here!";
    private boolean opaque = false, translucent = true, transparent = false;
    private Vector3f position = new Vector3f();
    private float size = 1;
    private ColorRGBA textColor = new ColorRGBA(ColorRGBA.White);
    private boolean showScore = false;
    private boolean showStat = false;
    
    BitmapText text;
    boolean change = false;
    private boolean init = false;
    
    public LabelControl(){}
    
    @Override
    protected void controlUpdate(float tpf) {
        if(GameStarted.gameStart){
            if(!init){
//                initAssetManager();
                BitmapFont font = GameStarted.assetManager.loadFont("Interface/Fonts/Default.fnt"); //<-- AssetReference
//                BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
                text = new BitmapText(font, false);
                text.setBox(new Rectangle(0, 0, 7, 7));
                if(opaque){
                    text.setQueueBucket(RenderQueue.Bucket.Opaque);
                }else if(translucent){
                    text.setQueueBucket(RenderQueue.Bucket.Translucent);
                }else if(transparent){
                    text.setQueueBucket(RenderQueue.Bucket.Transparent);
                }
                text.setSize(size);
                text.setText(message);
                text.setLocalTranslation(position);
                text.setColor(textColor);
                ((Node)spatial).attachChild(text);
                text.setShadowMode(RenderQueue.ShadowMode.Off);
                
                init = true;
            }else{
                if(spatial.getParent().getControl(MenuControl.class).isFocus()){
                    text.setCullHint(Spatial.CullHint.Dynamic);
                }else{
                    text.setCullHint(Spatial.CullHint.Always);
                }
                
                if(showScore){
                    String score = "Money: " + StoredInfo.tempMoney + "\n";
                    score = score + "XP: " + StoredInfo.tempXP + "\n"; 
                    score = score + "Score: " + (StoredInfo.tempMoney + StoredInfo.tempXP);
                    
                    text.setText(score);
                }
                
                if(showStat){
                    String score = "Money: " + StoredInfo.money + "\n";
                    score = score + "XP: " + StoredInfo.xp +  "/" + StoredInfo.nextXP + "\n"; 
                    score = score + "Level: " + StoredInfo.level;
                    
                    text.setText(score);
                }
            }
        }
    }
    
    public void changeText(String name){
        text.setText(name);
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        LabelControl control = new LabelControl();
        control.setMessage(message);
        control.setOpaque(opaque);
        control.setTranslucent(translucent);
        control.setTransparent(transparent);
        control.setPosition(position);
        control.setSize(size);
        control.setTextColor(textColor);
        control.setShowScore(showScore);
        control.setShowStat(showStat);
        control.setSpatial(spatial);
        return control;
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException
    {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(size, "size", 1.0f);
        oc.write(message, "message", "");
        oc.write(opaque, "opaque", true);
        oc.write(translucent, "translucent", true);
        oc.write(transparent, "transparent", true);
        oc.write(position, "position", new Vector3f());
        oc.write(textColor, "textColor", new ColorRGBA());
        oc.write(showScore, "showScore", true);
        oc.write(showStat, "showStat", true);
    }
 
    @Override
    public void read(JmeImporter im) throws IOException
    {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        size = ic.readFloat("size", 1.0f);
        message = ic.readString("message", "");
        opaque = ic.readBoolean("opaque", true);
        translucent = ic.readBoolean("translucent", true);
        transparent = ic.readBoolean("transparent", true);
        position = (Vector3f) ic.readSavable("position", new Vector3f());
        textColor = (ColorRGBA) ic.readSavable("textColor", new ColorRGBA());
        showScore = ic.readBoolean("showScore", true);
        showStat = ic.readBoolean("showStat", true);
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
//        change = true;
    }

    /**
     * @return the size
     */
    public float getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(float size) {
        this.size = size;
//        change = true;
    }

    /**
     * @return the opaque
     */
    public boolean isOpaque() {
        return opaque;
    }

    /**
     * @param opaque the opaque to set
     */
    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }

    /**
     * @return the translucent
     */
    public boolean isTranslucent() {
        return translucent;
    }

    /**
     * @param translucent the translucent to set
     */
    public void setTranslucent(boolean translucent) {
        this.translucent = translucent;
    }

    /**
     * @return the transparent
     */
    public boolean isTransparent() {
        return transparent;
    }

    /**
     * @param transparent the transparent to set
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    /**
     * @return the textColor
     */
    public ColorRGBA getTextColor() {
        return textColor;
    }

    /**
     * @param textColor the textColor to set
     */
    public void setTextColor(ColorRGBA textColor) {
        this.textColor = textColor;
    }

    /**
     * @return the position
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return the showScore
     */
    public boolean isShowScore() {
        return showScore;
    }

    /**
     * @param showScore the showScore to set
     */
    public void setShowScore(boolean showScore) {
        this.showScore = showScore;
    }

    /**
     * @return the showStat
     */
    public boolean isShowStat() {
        return showStat;
    }

    /**
     * @param showStat the showStat to set
     */
    public void setShowStat(boolean showStat) {
        this.showStat = showStat;
    }
    
}
