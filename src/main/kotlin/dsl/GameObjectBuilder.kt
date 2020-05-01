package dsl

import component.Component
import gameobject.GameObject

class GameObjectBuilder
{
	private val children = mutableListOf<GameObject>()
	private val components = mutableListOf<Component>()
	var enabled = true

	fun build() = GameObject(children, components, enabled)

	operator fun GameObject.unaryPlus()
	{
		children += this
	}

	operator fun Component.unaryPlus()
	{
		components += this
	}
}

fun gameObject(builder: GameObjectBuilder.() -> Unit) = GameObjectBuilder().apply(builder).build()
