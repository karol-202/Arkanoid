package component

import gameobject.ParentNode
import input.InputEvent
import state.State
import state.StateChange
import update.UpdateContext
import util.Bounds
import util.Vector

data class ClickHandler<S : State>(private val size: Vector,
                                   private val listener: StateChange<S>) : Component
{
	override fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) {
		val publisher = requireComponent<StatePublisher<S>>()
		val stateChanges = findClickEvents(ownerNode, updateContext.inputEvents).map { listener }
		owner.withComponentReplaced(publisher.withStateChanges(stateChanges))
	}

	private fun findClickEvents(ownerNode: ParentNode.GameObjectNode, events: List<InputEvent>) =
			events.mapNotNull { it as? InputEvent.Mouse.Up }.filter { isInBounds(ownerNode, it) }

	private fun isInBounds(ownerNode: ParentNode.GameObjectNode, event: InputEvent.Mouse) =
			event.location in getBounds(ownerNode)

	private fun getBounds(ownerNode: ParentNode.GameObjectNode) = Bounds(start = getGlobalPosition(ownerNode), size = size)
}
