package controls;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.FastMath;
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
public class CameraInfoControl extends AbstractControl implements Savable, Cloneable{

    private float distance = 0, verticalAngle = 0, maxVerticalAngle = 0, 
            minVerticalAngle = 0, horizontalAngle = 0;
    
    public CameraInfoControl(){}
    
    @Override
    protected void controlUpdate(float tpf) {
        if(verticalAngle > maxVerticalAngle)
	{
	    verticalAngle = maxVerticalAngle;
	}
	else if(verticalAngle < minVerticalAngle)
	{
	    verticalAngle = minVerticalAngle;
	}
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        CameraInfoControl control = new CameraInfoControl();
        control.setDistance(distance);
        control.setVerticalAngle(verticalAngle);
        control.setMaxVerticalAngle(maxVerticalAngle);
        control.setMinVerticalAngle(minVerticalAngle);
        control.setHorizontalAngle(horizontalAngle);
        control.setSpatial(spatial);
        return control;
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException
    {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(distance, "distance", 1.0f);
        oc.write(verticalAngle, "verticalAngle", 1.0f);
        oc.write(maxVerticalAngle, "maxVerticalAngle", 1.0f);
        oc.write(minVerticalAngle, "minVerticalAngle", 1.0f);
        oc.write(horizontalAngle, "horizontalAngle", 1.0f);
    }
 
    @Override
    public void read(JmeImporter im) throws IOException
    {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        distance = ic.readFloat("distance", 1.0f);
        verticalAngle = ic.readFloat("verticalAngle", 1.0f);
        maxVerticalAngle = ic.readFloat("maxVerticalAngle", 1.0f);
        minVerticalAngle = ic.readFloat("minVerticalAngle", 1.0f);
        horizontalAngle = ic.readFloat("horizontalAngle", 1.0f);
    }
    
    /**
     * @return the distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * @return the verticalAngle
     */
    public float getVerticalAngle() {
        return verticalAngle;
    }

    /**
     * @param verticalAngle the verticalAngle to set
     */
    public void setVerticalAngle(float verticalAngle) {
        this.verticalAngle = verticalAngle;
    }

    /**
     * @return the maxVerticalAngle
     */
    public float getMaxVerticalAngle() {
        return maxVerticalAngle;
    }

    /**
     * @param maxVerticalAngle the maxVerticalAngle to set
     */
    public void setMaxVerticalAngle(float maxVerticalAngle) {
        this.maxVerticalAngle = maxVerticalAngle;
    }

    /**
     * @return the minVerticalAngle
     */
    public float getMinVerticalAngle() {
        return minVerticalAngle;
    }

    /**
     * @param minVerticalAngle the minVerticalAngle to set
     */
    public void setMinVerticalAngle(float minVerticalAngle) {
        this.minVerticalAngle = minVerticalAngle;
    }

    /**
     * @return the horizontalAngle
     */
    public float getHorizontalAngle() {
        return horizontalAngle;
    }

    /**
     * @param horizontalAngle the horizontalAngle to set
     */
    public void setHorizontalAngle(float horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }
    
}
