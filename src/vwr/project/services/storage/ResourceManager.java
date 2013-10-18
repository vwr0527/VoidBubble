package vwr.project.services.storage;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Resource Manager - simplifies access to resource objects, such as
 * Texture objects, Audio objects, and TrueTypeFont objects.
 * It automatically loads the resources from the game directory into hash maps,
 * and names them by their file name.
 */
//Can't get rid of that damn depreciated warning.
//I would need to import another library.
public class ResourceManager
{
	private HashMap<String, Texture> textureList;
	private HashMap<String, Audio> soundList;
	private HashMap<String, TrueTypeFont> fontList;
	/**whether or not to print debugging messages*/
	private boolean debugging = true;
	
	/**
	 * 
	 * @param texDir - Directory to load textures from. PNG files only.
	 * @param sndDir - Directory to load sounds from. WAV files only.
	 * @param fontDir - Directory to load fonts from. TTF files only.
	 * @throws IOException if files are not found, or some general IO error
	 * @throws FontFormatException specifically something went wrong when loading fonts
	 */
	public ResourceManager(String texDir, String sndDir, String fontDir) throws IOException, FontFormatException
	{
		textureList = new HashMap<String, Texture>();
		soundList = new HashMap<String, Audio>();
		fontList = new HashMap<String, TrueTypeFont>();

		LoadTextures(texDir);
		LoadSounds(sndDir);
		LoadFonts(fontDir);
	}
	
	public Texture getTexture(String name)
	{
		return textureList.get(name);
	}
	
	public Audio getSound(String name)
	{
		return soundList.get(name);
	}
	
	public TrueTypeFont getFont(String name)
	{
		return fontList.get(name);
	}
	
	//I feel really bad about the duplicate code in the
	//next three functions. but it's kinda hard to fix...
	/**PNG files only*/
	private void LoadTextures(String texturesDirectory) throws IOException
	{
		String[] fileList = getFileNames(texturesDirectory);
		for(int i = 0; i < fileList.length; ++i)
		{
			String fileName = texturesDirectory + File.separator + fileList[i];
			DebugMessage(fileName);
			InputStream stream = ResourceLoader.getResourceAsStream(fileName);
			Texture tex = TextureLoader.getTexture("PNG", stream);
			textureList.put(fileList[i], tex);
		}
	}

	/**WAV files only*/
	private void LoadSounds(String soundsDirectory) throws IOException
	{
		String[] fileList = getFileNames(soundsDirectory);
		for(int i = 0; i < fileList.length; ++i)
		{
			String fileName = soundsDirectory + File.separator + fileList[i];
			DebugMessage(fileName);
			InputStream stream = ResourceLoader.getResourceAsStream(fileName);
			Audio tex = AudioLoader.getAudio("WAV", stream);
			soundList.put(fileList[i], tex);
		}
	}

	/**TTF files only*/
	private void LoadFonts(String fontsDirectory) throws IOException, FontFormatException
	{
		String[] fileList = getFileNames(fontsDirectory);
		for(int i = 0; i < fileList.length; ++i)
		{
			String fileName = fontsDirectory + File.separator + fileList[i];
			DebugMessage(fileName);
			InputStream stream = ResourceLoader.getResourceAsStream(fileName);
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			//size 48 was the largest I could get while still without errors.
			//bigger is better, because shrinking larger fonts in GL looks nicer
			//than enlarging smaller fonts.
			awtFont = awtFont.deriveFont(new AffineTransform(48, 0, 0, -48, 0, -24));
			fontList.put(fileList[i], new TrueTypeFont(awtFont, false));
		}
	}
	
	private void DebugMessage(String fileName)
	{
		if (debugging) System.out.println("Loading " + fileName);
	}

	private String[] getFileNames(String dirName)
	{
		File dir = new File(dirName);
		String[] fileList = dir.list();
		if (fileList == null)
		{
			return new String[0];
		}
		return fileList;
	}
}
