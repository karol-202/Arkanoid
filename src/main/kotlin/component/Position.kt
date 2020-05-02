package component

import gameobject.ParentNode
import gameobject.getComponent
import util.Vector

data class Position(val local: Vector) : Component
{
	constructor(x: Double = 0.0, y: Double = 0.0) : this(Vector(x, y))

	fun calculateGlobal(parentNode: ParentNode): Vector = local + parentNode.calculateGlobal()

	private fun ParentNode.calculateGlobal(): Vector = when(this)
	{
		is ParentNode.GameObjectNode ->
			gameObject.getComponent<Position>()?.calculateGlobal(parentNode) ?: parentNode.calculateGlobal()
		is ParentNode.SceneNode -> Vector.ZERO
	}
}
