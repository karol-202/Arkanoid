package scene

import gameobject.GameObject
import gameobject.GameObjectBuilder
import gameobject.gameObject

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
