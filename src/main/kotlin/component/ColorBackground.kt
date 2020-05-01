package component

import gameobject.GameObject
import render.render
import render.height
import render.width
import scene.Scene

data class ColorBackground(private val color: String) : Component
{
	override fun render(gameObject: GameObject) = render {
		fillStyle = color
		fillRect(0.0, 0.0, width, height)
	}
}
