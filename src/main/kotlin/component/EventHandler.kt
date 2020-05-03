package component

import gameobject.ParentNode
import input.InputEvent
import state.State
import state.StateChange
import update.UpdateContext
import util.Bounds
import util.Vector

data class EventHandler<S : State>(private val listener: S.(InputEvent) -> S) : Component
{
	override fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) {
		val publisher = requireComponent<StatePublisher<S>>()
		val stateChanges = updateContext.inputEvents.map<InputEvent, StateChange<S>> { { this.listener(it) } }
		owner.withComponentReplaced(publisher.withStateChanges(stateChanges))
	}
}
