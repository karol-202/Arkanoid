package scene

import gameobject.GameObject
import gameobject.ParentNode

data class Scene(private val root: GameObject)
{
	private val sceneNode = ParentNode.SceneNode(this)

	fun update(deltaTime: Double) = withRoot(root.update(deltaTime, sceneNode))

	fun render() = root.render(sceneNode)

	private fun withRoot(root: GameObject) = copy(root = root)
}
