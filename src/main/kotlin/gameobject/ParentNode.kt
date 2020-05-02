package gameobject

import scene.Scene

sealed class ParentNode
{
	data class SceneNode(override val scene: Scene) : ParentNode()

	data class GameObjectNode(val gameObject: GameObject,
	                          val parentNode: ParentNode) : ParentNode()
	{
		override val scene get() = parentNode.scene
	}

	abstract val scene: Scene
}
