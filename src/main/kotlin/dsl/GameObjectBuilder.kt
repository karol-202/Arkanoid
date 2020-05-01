package dsl

import component.Component
import gameobject.GameObject

class GameObjectBuilder
{
	private val children = mutableListOf<GameObject>()
	private val components = mutableListOf<Component>()

	fun build() = GameObject(children, components)

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
