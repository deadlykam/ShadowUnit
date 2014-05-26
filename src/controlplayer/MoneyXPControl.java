/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlplayer;

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

/**
 *
 * @author Kamran
 */
public class MoneyXPControl extends AbstractControl{

    private int level = 1;
    private float xp = 0;
    private float money = 0;
    
    public MoneyXPControl(){}

    @Override
    protected void controlUpdate(float tpf) {
        //TODO: add code that controls Spatial,
        //e.g. spatial.rotate(tpf,tpf,tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        MoneyXPControl control = new MoneyXPControl();
        control.setLevel(level);
        control.setXp(xp);
        control.setMoney(money);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        level = in.readInt("level", 1);
        xp = in.readFloat("xp", 1.0f);
        money = in.readFloat("money", 1.0f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(level, "level", 1);
        out.write(xp, "xp", 1.0f);
        out.write(money, "money", 1.0f);
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

    /**
     * @return the money
     */
    public float getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(float money) {
        this.money = money;
    }

    /**
     * @return the xp
     */
    public float getXp() {
        return xp;
    }

    /**
     * @param xp the xp to set
     */
    public void setXp(float xp) {
        this.xp = xp;
    }


}
