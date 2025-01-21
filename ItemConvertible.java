import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The ItemConvertibleObject interface represents objects that can be converted to items
 * and provides a method to obtain the associated image.
 * 
 * @author: Zhaoqi Xu 
 * @version: January 2024
 */

/**
* Gets the GreenfootImage associated with this item-convertible object.
* 
* @return The GreenfootImage representing the item.
*/
public interface ItemConvertible
{

    public GreenfootImage getItemImage();
}
