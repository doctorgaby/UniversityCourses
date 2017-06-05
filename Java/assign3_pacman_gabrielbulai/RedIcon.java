import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import becker.robots.icons.Icon;

/**
 *@author Gabriel Bulai 
 *this class defines the prize icon color and shape
 */

public class RedIcon extends Icon
{  public RedIcon(double relativeSize)
   {  super(relativeSize);
   }
   protected void renderImage(Graphics2D g2, int width, int height)
   {  g2.setColor(Color.red);
      g2.fill(new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0));
   }
 }
