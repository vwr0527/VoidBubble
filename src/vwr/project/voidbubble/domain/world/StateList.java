package vwr.project.voidbubble.domain.world;

import java.util.HashMap;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.entitystates.*;
import vwr.project.voidbubble.domain.world.Entity.State;
/**
 * StateList simply lists all the states for easy access. It would be nice
 * to load the states from files, but that would require translating text
 * to machine code methods.
 */
public class StateList
{
	/**States stored here*/
	private HashMap<String, State> stateMap;
	
	/**Local reference to settings manager*/
	private SettingManager settings;
	
	/**Creates all the states. States need services to be initialized, and
	 * a reference to spawner so they can spawn entities of their own.*/
	public StateList(DrawService drawS, SoundService soundS,
			ResourceService resourceS, InputService inputS, SettingManager settingM,
			ScoreManager scoreM, Spawner spawn)
	{
		settings = settingM;
		stateMap = new HashMap<String, State>();
		stateMap.put("enemystate", new EnemyState(resourceS, settingM, scoreM, inputS, drawS, soundS, spawn, this));
		stateMap.put("playerstate", new PlayerState(resourceS, settingM, scoreM, inputS, drawS, soundS, spawn, this));
		stateMap.put("laser", new Laser(resourceS, settingM, scoreM, inputS, drawS, soundS, spawn, this));
	}
	
	/**If difficulty has changed, update states accordingly.*/
	public void Update()
	{
		if (settings.Changed()) { /*change states accordingly*/ }
	}

	/**
	 * Return a state from it's given name
	 * @param name - name of state
	 * @return state with that name
	 */
	public State Get(String name)
	{
		return stateMap.get(name);
	}
}
