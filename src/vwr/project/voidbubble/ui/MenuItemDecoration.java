package vwr.project.voidbubble.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import vwr.project.services.audio.SoundService;
import vwr.project.services.graphics.DrawService;
import vwr.project.services.input.InputService;
import vwr.project.services.storage.ResourceService;
import vwr.project.services.storage.ScoreManager;
import vwr.project.services.storage.SettingManager;
import vwr.project.voidbubble.domain.world.WorldService;

/**
 * Non interactive menu items.
 * Does not support having draw elements.
 * No sound. Only textures and text.
 */
public class MenuItemDecoration extends MenuItem
{
	private StringBuffer _text;
	private TrueTypeFont _font;
	private Texture _texture;

	public float _fontSize;
	public Color _fontCol;
	
	/**
	 * Create a new MenuItemDecoration Object
	 * @param resourceS - Resource service
	 * @param scores 
	 * @param text - Text to render
	 * @param font - Font to render text with
	 * @param texture - Texture to render
	 */
	public MenuItemDecoration(ResourceService resourceS,
			SettingManager settingM, ScoreManager scores, DrawService drawS,
			String text, String font, String texture)
	{
		super(resourceS, settingM, drawS);
		_text = new StringBuffer(text);
		_font = resource_s.GetFont(font);
		_texture = resource_s.GetTexture(texture);
		_fontSize = 1.0f;
		_fontCol = new Color(1.0f, 1.0f, 1.0f);
	}
	
	public void Update(long ticks)
	{
		draw_s.DrawUISprite(_x, _y, 0, _size, _texture, _col);
		draw_s.DrawString(_font, _x, _y, _fontSize, _fontCol, _text);
	}
}
