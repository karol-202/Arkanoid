package dsl

import component.Component
import gameobject.GameObject

class GameObjectBuilder
{
	private val components = mutableListOf<Component>()

	val gameObject get() = GameObject(components)

	operator fun Component.unaryPlus()
	{
		components += this
	}
}
