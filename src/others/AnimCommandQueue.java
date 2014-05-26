package others;

import others.AllEnum.AnimationCommands;

/**
 *
 * @author Kamran
 */
public class AnimCommandQueue {
    AnimationCommands [] commands = new AnimationCommands[10];
    int size = 0;
    
    public void add(AnimationCommands command){
        if(size < commands.length){
            commands[size] = command;
            size++;
        }
    }
    
    private void shiftCommands(){
        for(int i = 0; i < size - 1; i++){
            commands[i] = commands[i + 1];
        }
    }
    
    public AnimationCommands pop(){
        AnimationCommands storedCommand = commands[0];
        size--;
        shiftCommands();
        return storedCommand;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
}
