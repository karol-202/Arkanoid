package dsl

import gameobject.GameObject
import scene.Scene

class SceneBuilder
{
	private var root = GameObject.create()

	fun build() = Scene(root)

	fun root(builder: GameObjectBuilder.() -> Unit)
	{
		root = gameObject(builder)
	}
}

fun scene(builder: SceneBuilder.() -> Unit) = SceneBuilder().apply(builder).build()
