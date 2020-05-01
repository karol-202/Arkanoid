package component

import render.render
import render.height
import render.width

class ColorBackground(private val color: String) : Component
{
	override fun render() = render {
		fillStyle = color
		fillRect(0.0, 0.0, width, height)
	}
}
