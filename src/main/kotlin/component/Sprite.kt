package component

import gameobject.GameObject
import gameobject.requireComponent
import org.w3c.dom.CanvasImageSource
import render.render

data class Sprite(private val image: CanvasImageSource) : Component
{
	override fun render(gameObject: GameObject) = render {
		val position = gameObject.requireComponent<Position>()
		drawImage(image, position.x, position.y)
	}
}
