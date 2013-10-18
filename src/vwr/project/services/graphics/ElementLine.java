package vwr.project.services.graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * A primitive that represents a line.
 * It has two color points.
 */
public class ElementLine extends Element
{
	private PointColor p1;
	private PointColor p2;

	/**
	 * Call for each point you want to add.
	 */
	public void AddPoint(PointColor p)
	{
		if (p1 == null) p1 = p;
		else if (p2 == null) p2 = p;
	}

	public void Render()
	{
		if (p1 == null || p2 == null) return;
		p1.bindColor();
		glBegin(GL_LINES);
		glVertex3f(p1.x, p1.y, 0);
		p2.bindColor();
		glVertex3f(p2.x, p2.y, 0);
		glEnd();
	}

	public void Reset()
	{
		p1 = null;
		p2 = null;
	}
}
