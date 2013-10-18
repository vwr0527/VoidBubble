package vwr.project.voidbubble.test;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TestAudioAndInput
{
	//press a to play sound 1
	//press b to play sound 2
	
	private Audio wavTest1;
	private Audio wavTest2;
	
	public void Run()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(640, 400));
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		LoadResources();
		
		while(!Display.isCloseRequested())
		{
			Test();
			Display.update();
		}
		
		Display.destroy();
		AL.destroy();
	}
	
	
	protected void LoadResources()
	{
		try
		{
			wavTest1 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/boop.wav"));
			wavTest2 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/woob.wav"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	protected void Test()
	{
		if(!Keyboard.next()) return;
		if(!Keyboard.getEventKeyState()) return;
		if (Keyboard.getEventKey() == Keyboard.KEY_A)
		{
			wavTest1.playAsSoundEffect(0.9f, 1f, false);
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_B)
		{
			wavTest2.playAsSoundEffect(1.7f, 0.5f, false);
		}
	}
	
	public static void main(String[] args)
	{
		TestAudioAndInput test = new TestAudioAndInput();
		test.Run();
	}
}
