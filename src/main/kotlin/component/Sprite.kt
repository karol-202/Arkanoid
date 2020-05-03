package component

import gameobject.ParentNode
import org.w3c.dom.CanvasImageSource

data class Sprite(private val image: CanvasImageSource) : Component
{
	override fun render(ownerNode: ParentNode.GameObjectNode) = render(ownerNode) { context ->
		val position = getGlobalPosition(ownerNode)
		context.drawImage(image, position.x, position.y)
	}
}
