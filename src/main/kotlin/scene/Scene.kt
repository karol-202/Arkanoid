package scene

import gameobject.GameObject

data class Scene(private val root: GameObject)
{
	fun update(deltaTime: Double) = root.update(deltaTime, this)

	fun render() = root.render()
}
