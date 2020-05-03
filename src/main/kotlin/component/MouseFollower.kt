package component

import gameobject.ParentNode
import input.InputEvent
import update.UpdateContext
import util.Vector

data class MouseFollower(private val lockX: Boolean = false,
                         private val lockY: Boolean = false) : Component
{
	override fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) {
		val position = requireComponent<Position>()

		val parentGlobal = getGlobalPosition(parentNode)
		val targetGlobal = findMoveLocation(updateContext.inputEvents)
		val targetLocal = targetGlobal - parentGlobal
		val resultLocal = lockPosition(position.local, targetLocal)

		owner.withComponentReplaced(position.withLocal(resultLocal))
	}

	private fun findMoveLocation(events: List<InputEvent>) =
			events.mapNotNull { it as? InputEvent.Mouse.Move }.last().location

	private fun lockPosition(current: Vector, target: Vector) = Vector(x = if(lockX) current.x else target.x,
	                                                                   y = if(lockY) current.y else target.y)
}
