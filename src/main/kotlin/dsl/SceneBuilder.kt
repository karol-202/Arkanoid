package dsl

import gameobject.GameObject
import scene.Scene

class SceneBuilder
{
	private val gameObjects = mutableListOf<GameObject>()

	val scene get() = Scene(gameObjects)

	fun gameObject(builder: GameObjectBuilder.() -> Unit)
	{
		gameObjects += GameObjectBuilder().apply(builder).gameObject
	}
}

fun scene(builder: SceneBuilder.() -> Unit) = SceneBuilder().apply(builder).scene
