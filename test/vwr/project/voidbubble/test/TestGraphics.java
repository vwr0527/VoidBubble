package vwr.project.voidbubble.test;

import java.io.IOException;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.geom.AffineTransform;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("deprecation")
public class TestGraphics
{

	/**
	 * Game is responsible for
	 * initializing all the subsystems,
	 * running the main update and render loop,
	 * and shutting down the game upon exiting.
	 */

	private Texture testTex;
	private TrueTypeFont testFont;
	protected long startTime;

	public void Run()
	{
		startTime = Sys.getTime();
		
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
	}
	
	protected void LoadResources()
	{
		// Implementation is currently temporary.
		// Load a texture
		try
		{
			testTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/texture/test.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Could not load texture");
		}
		
		try
		{
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/Aethv2.ttf"));
			awtFont = awtFont.deriveFont(new AffineTransform(48, 0, 0, -48, 0, -24));
			testFont = new TrueTypeFont(awtFont, false);
		}
		catch(FontFormatException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void Test()
	{
		if(Display.isActive())
		{
			DrawSomething();
		}
		else
		{
			System.out.println("Cannot test graphics, Display not initialized");
			System.exit(1);
		}
	}
	
	private void DrawSomething()
	{
		double x = 0;
		double y = 100;
		double z = 200;


		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
		glEnable(GL_TEXTURE_2D);
		glEnable( GL_DEPTH_TEST );
		glEnable( GL_BLEND );
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		double WHAngle = Math.atan((double)Display.getHeight()/(double)Display.getWidth());
		double frustWidth = Math.cos(WHAngle);
		double frustHeight = Math.sin(WHAngle);
		glFrustum(-frustWidth, frustWidth, -frustHeight, frustHeight, 1, 9999);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glRotatef(20,1,0,0);
		glTranslated(-x,-y,-z);

		glColor3f(1,0,0);
		float blah = (float)((Sys.getTime() - startTime))/Sys.getTimerResolution();
		//System.out.println(blah);
		float pooval = (float)(Math.cos(Math.PI/4)*50);
		float redval = 0.25f;
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		for(int i = 0; i < 4; ++i)
		{
			glColor3f(redval,0,0);
			redval+=0.25f;
			blah += Math.PI/2;
			glTexCoord2f(0,0);
			glVertex3f( (float)(Math.cos(blah)*50), pooval, (float)(Math.sin(blah)*50));
			glTexCoord2f(1,0);
			glVertex3f( (float)(Math.cos(blah)*50), -pooval, (float)(Math.sin(blah)*50));
			glTexCoord2f(1,1);
			blah -= Math.PI/2;
			glVertex3f( (float)(Math.cos(blah)*50), -pooval, (float)(Math.sin(blah)*50));
			glTexCoord2f(0,1);
			glVertex3f( (float)(Math.cos(blah)*50), pooval, (float)(Math.sin(blah)*50));
			blah += Math.PI/2;
		}
		glEnd();

		glColor3f(1,1,1);
		glEnable(GL_TEXTURE_2D);
		testTex.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex3f( -100, 0, -100 );
		glTexCoord2f(1,0);
		glVertex3f( -100, 0, 100 );
		glTexCoord2f(1,1);
		glVertex3f( 100, 0, 100 );
		glTexCoord2f(0,1);
		glVertex3f( 100, 0, -100 );
		glEnd();
		
		glPopMatrix();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glPushMatrix();
		glTranslated(0,0,0);
		glDisable (GL_DEPTH_TEST);
		testFont.drawString(150, 300, "Hello. Time=" + Math.round(blah), Color.green);
		glEnable (GL_DEPTH_TEST);
		glPopMatrix();
	}
	
	public static void main(String[] args)
	{
		TestGraphics test = new TestGraphics();
		test.Run();
	}
}
