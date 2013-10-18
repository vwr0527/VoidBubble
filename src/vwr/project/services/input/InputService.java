package vwr.project.services.input;

public class InputService
{
	InputManager input_m;
	public InputService(InputManager inputM)
	{
		input_m = inputM;
	}

	public float getMouseX() {return  input_m.getMouseX();}
	public float getMouseY() {return  input_m.getMouseY();}
	public int getMouseButton() {return input_m.getMouseButton();}
	public int getLeft() {return input_m.getLeft();}
	public int getRight() {return input_m.getRight();}
	public int getForward() {return input_m.getForward();}
	public int getBackward() {return input_m.getBackward();}
}
