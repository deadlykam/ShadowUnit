/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

/**
 *
 * @author Kamran
 */
public class AllEnum {
    public enum GameState{
        NONE,
        LOADSCOREMENU,
        LOADMENU,
        LOADSTAGE,
        PLAYING,
        TEST
    }
    
    public enum SpecialEffects{
        NONE, FIRE, FREEZE, ARMOR
    }
    
    public enum ButtonEvents{
        NONE, 
        /*Menus*/ MAIN, OPTION, CREDIT, TUTORIAL, GAME, STORE,
        /*Actions*/ START, EXIT, NEXT, PREVIOUS, LIST_NXT, LIST_PRE, ATK_ADD, DEF_ADD, TUT_NXT, TUT_PRE
    }
    
    public enum EnemyType{
        NONE, RANGE, MELEE, HAMMER, CRYSTALDRAINER
    }
    
    public enum Levels{
        NONE, GROUNDLEVEL, FIRSTLEVEL, SECONDLEVEL, THIRDLEVEL, FOURTHLEVEL
    }
    
    public enum AIState{
        NONE, RUN, JUMP, ATTACK, STAY, AVOIDOBSTACLE, STUNNED
    }
    
    public enum Animations{
        NONE,
        RUN1, RUN2
    }
    
    public enum AnimationCommands{
        NONE,
        /*For Movements*/ F, B, L, R, J, FL, FR, BL, BR, FALL, F_HMR,
        /*Player Combos*/ PUNCH1, PUNCH2, KICK1, PUSH1, RHKICK1, PUSHKICK1, PALM1, POWER1,
        /*Enemy Combos*/ ATK1, ATK2, ATK3, HMRATK1,
        /*Others*/ TAUNT1, TAUNT2, DEAD1, STUNNED1, TAUNT_HMR1, CHARGE1
    }
    
    public enum ComboName{
        NONE, COMBO1, COMBO2, COMBO3, COMBO4, COMBO5, COMBO6
    }
}
