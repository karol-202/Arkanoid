package gameobject

import component.Component

class GameObjectBuilder
{
	private val children = mutableListOf<GameObject>()
	private val components = mutableListOf<Component>()
	var enabled = true

	fun build() = GameObject.create(children, components, enabled)

	operator fun GameObject.unaryPlus()
	{
		this@GameObjectBuilder.children += this
	}

	operator fun Component.unaryPlus()
	{
		components += this
	}
}

fun gameObject(builder: GameObjectBuilder.() -> Unit) = GameObjectBuilder().apply(builder).build()
