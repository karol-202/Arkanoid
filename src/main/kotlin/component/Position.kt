package component

import gameobject.ParentNode
import util.Vector

data class Position(val local: Vector) : Component
{
	constructor(x: Double = 0.0, y: Double = 0.0) : this(Vector(x, y))

	fun withLocal(local: Vector) = copy(local = local)
}

fun getGlobalPosition(node: ParentNode): Vector = getLocalPosition(node) + getParentPosition(node)

fun getLocalPosition(node: ParentNode) = node.getComponent<Position>()?.local ?: Vector.ZERO

private fun getParentPosition(node: ParentNode) = when(node)
{
	is ParentNode.GameObjectNode -> getGlobalPosition(node.parentNode)
	is ParentNode.SceneNode -> Vector.ZERO
}
