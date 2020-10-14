/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sun.speech.freetts.VoiceManager;

/**
 *
 * @author Admin
 */
public class Voice {
    private String name;
    
    private com.sun.speech.freetts.Voice voice;
    
    public Voice(String name){
        this.name = name;
        this.voice = VoiceManager.getInstance().getVoice(this.name);
        this.voice.allocate();
    }
    
    public void say(String something){
        this.voice.speak(something);
    }
}
