package scene

import gameobject.GameObject

data class Scene(private val gameObjects: List<GameObject>)
{
	fun update(deltaTime: Double) = gameObjects.fold(this) { currentScene, gameObject ->
		gameObject.update(deltaTime, currentScene)
	}

	fun render() = gameObjects.flatMap { it.render() }
}
