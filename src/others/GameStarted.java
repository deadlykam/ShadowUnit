/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import com.jme3.asset.AssetManager;

/**
 *
 * @author Kamran
 */
public class GameStarted {
    public static boolean gameStart = false;
    public static AssetManager assetManager;
    
    public GameStarted(AssetManager assetManager){
        this.assetManager = assetManager;
        gameStart = true;
    }
}
