package vwr.project.services.audio;

import org.newdawn.slick.openal.SoundStore;

import vwr.project.services.storage.SettingManager;

/**Sound manager is not needed at this point, but it may be
 * in the future, when directional sound and loudness will
 * be calculated from the distance and direction from the
 * origin of the sound to the camera position.
 */
public class SoundManager
{

	public SoundManager(SettingManager settings)
	{

	}

	public void play()
	{
		SoundStore.get().poll(0);
	}

}
