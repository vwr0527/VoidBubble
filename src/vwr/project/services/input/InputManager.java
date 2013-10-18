package vwr.project.services.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import vwr.project.services.storage.SettingManager;

/**Manages input, takes care of control-mapping.
 * For all the commands/input values, 2 means just-pressed,
 * 3 means just-released, and 1 means still holding on. 0
 * of course means not being pressed.*/
public class InputManager
{
	private SettingManager setting_m;
	
	private float cursor_x;
	private float cursor_y;
	private int cursor_b;
	
	private InputKeyPair moveLeft;
	private InputKeyPair moveRight;
	private InputKeyPair moveForward;
	private InputKeyPair moveBackward;
	
	public InputManager(SettingManager settings)
	{
		setting_m = settings;
		UpdateSettings();
	}

	public void UpdateSettings()
	{
		moveLeft = new InputKeyPair(Integer.parseInt(setting_m.Get("key left")));
		moveRight = new InputKeyPair(Integer.parseInt(setting_m.Get("key right")));
		moveForward = new InputKeyPair(Integer.parseInt(setting_m.Get("key forward")));
		moveBackward = new InputKeyPair(Integer.parseInt(setting_m.Get("key back")));
	}

	public void read()
	{
		if (setting_m.Changed()) UpdateSettings();
		
		ReadMouse();
		ReadKeys();
	}

	private void ReadKeys()
	{
		moveLeft.Read();
		moveRight.Read();
		moveForward.Read();
		moveBackward.Read();
	}

	private void ReadMouse()
	{
		cursor_x = Mouse.getX();
		cursor_y = Mouse.getY();
		if (Mouse.isButtonDown(0))
		{
			if (cursor_b == 0)
				cursor_b = 2; //pressed
			else
				cursor_b = 1; //holding down
		}
		else
		{
			if (cursor_b == 2 || cursor_b == 1)
				cursor_b = 3; //released
			else
				cursor_b = 0;
		}
	}

	public float getMouseX() {return  cursor_x;}
	public float getMouseY() {return  cursor_y;}
	public int getMouseButton() {return cursor_b;}
	public int getLeft() {return moveLeft.status;}
	public int getRight() {return moveRight.status;}
	public int getForward() {return moveForward.status;}
	public int getBackward() {return moveBackward.status;}
	
	private class InputKeyPair
	{
		int key;
		int status;
		public InputKeyPair(int k)
		{
			key = k;
		}
		
		public void Read()
		{
			if (Keyboard.isKeyDown(key))
			{
				if (status == 0)
					status = 2; //pressed
				else
					status = 1; //holding down
			}
			else
			{
				if (status == 2 || status == 1)
					status = 3; //released
				else
					status = 0;
			}
		}
	}
}
