package component

import gameobject.ParentNode
import render.height
import render.width

data class ColorBackground(private val color: String) : Component
{
	override fun render(ownerNode: ParentNode.GameObjectNode) = render(ownerNode) { context ->
		context.fillStyle = color
		context.fillRect(0.0, 0.0, context.width, context.height)
	}
}
