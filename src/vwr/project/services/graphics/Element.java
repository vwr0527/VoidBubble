package vwr.project.services.graphics;

/**
 * Element is an abstract class that encompasses
 * all elements. An element is a primitive that can
 * be drawn. It needs references to some points that
 * can be used to draw. Some examples are triangles,
 * lines, and circles. There can be textures and colors
 * or just colors.
 */
public abstract class Element
{
	/**whether or not it is affected by the camera*/
	public boolean camTrans;

	/** Call each time you want to add a point */
	public void AddPoint(PointColor p) {}
	
	/** Clears the element of all it's data. */
	public void Reset() {}

	/** Draws the element. */
	public void Render() {}
}
