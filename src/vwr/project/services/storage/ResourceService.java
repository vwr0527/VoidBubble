package vwr.project.services.storage;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.Texture;

/**
 * Resource Service allows limited access to the resource manager.
 * It only allows you to get the resources.
 */
public class ResourceService
{
	private ResourceManager resource_m;
	public ResourceService(ResourceManager resourceM)
	{
		resource_m = resourceM;
	}
	/**
	 * @param name - Name of texture. include .png
	 * @return Texture object requested
	 */
	public Texture GetTexture(String name)
	{
		return resource_m.getTexture(name);
	}

	/**
	 * @param name - Name of sound. include .wav
	 * @return Audio object requested
	 */
	public Audio GetSound(String name)
	{
		return resource_m.getSound(name);
	}

	/**
	 * @param name - Name of font. include .ttf
	 * @return TrueTypeFont object requested
	 */
	public TrueTypeFont GetFont(String name)
	{
		return resource_m.getFont(name);
	}
}
