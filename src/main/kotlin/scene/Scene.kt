package scene

import gameobject.GameObject
import gameobject.ParentNode
import update.UpdateContext

data class Scene(private val root: GameObject)
{
	private val sceneNode = ParentNode.SceneNode(this)

	fun update(updateContext: UpdateContext) = withRoot(root.update(sceneNode, updateContext))

	fun render() = root.render(sceneNode)

	private fun withRoot(root: GameObject) = copy(root = root)
}
