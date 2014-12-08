/**
 * @author Dan Han, Weiqi Zhang
 * PlayMidi class can play .mid format music 
 */



import java.io.File;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class PlayMidi
{
    private static File sound;
    private static Sequence seq;
    public static Sequencer midi;
    
    //constructor
    public PlayMidi(String filename){
    	try 
        {
            sound = new File(filename);
            seq = MidiSystem.getSequence(sound);
            midi= MidiSystem.getSequencer();
            midi.open();
            midi.setSequence(seq);
            midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);  // continuously play
            
        } 
    	catch (Exception ex) {
        	System.err.println(ex);
        }
    }
    
    
    // play music
    public void Play()
    {
         if(!midi.isRunning())
             midi.start();
         
    }
    
    // stop playing music
    public static void Stop()
    {
    	if(midi.isRunning())
			midi.stop();
    	
    	if(midi.isOpen())
    		midi.close();
    }
 
} 
