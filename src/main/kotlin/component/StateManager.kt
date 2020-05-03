package component

import gameobject.GameObject
import gameobject.ParentNode
import gameobject.getComponent
import state.State
import state.StateChange
import update.UpdateContext

data class StateManager<S : State>(val state: S) : Component
{
	override fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) {
		owner.withComponentReplaced(withUpdatedState(updatedState(owner)))
	}

	private fun updatedState(owner: GameObject) = findStateChanges(owner).fold(state) { currentState, change ->
		currentState.change()
	}

	private fun findStateChanges(owner: GameObject) =
			findPublishers(owner).flatMap { it.getChangesWithClearedPublisher().stateChanges /* TODO Clear publisher */ }

	private fun findPublishers(gameObject: GameObject): List<StatePublisher<S>> =
			listOfNotNull(gameObject.getComponent<StatePublisher<S>>()) + findPublishersInChildren(gameObject)

	private fun findPublishersInChildren(gameObject: GameObject) =
			gameObject.children.flatMap { findPublishers(it) }

	private fun withUpdatedState(state: S) = copy(state = state)
}

fun <S : State> getState(node: ParentNode): S? = when(node)
{
	is ParentNode.GameObjectNode ->
		node.gameObject.getComponent<StateManager<S>>()?.state ?: getState(node.parentNode)
	is ParentNode.SceneNode -> null
}
