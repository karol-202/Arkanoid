package component

import gameobject.GameObject
import gameobject.ParentNode
import gameobject.getComponent
import state.State
import update.UpdateContext

data class StateManager<S : State>(val state: S) : Component
{
	override fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) {
		owner.withComponentReplaced(withUpdatedState(updatedState(owner)))
	}

	private fun updatedState(owner: GameObject) = findStateChanges(owner).fold(state) { currentState, change ->
		currentState.change()
	}

	private fun findStateChanges(owner: GameObject) = findPublishers(owner).flatMap { it.stateChanges }

	private fun findPublishers(gameObject: GameObject): List<StatePublisher<S>> =
			listOfNotNull(gameObject.getComponent<StatePublisher<S>>()) + findPublishersInChildren(gameObject)

	private fun findPublishersInChildren(gameObject: GameObject) =
			gameObject.children.flatMap { findPublishers(gameObject) }

	private fun withUpdatedState(state: S) = copy(state = state)
}

fun <S : State> getState(ownerNode: ParentNode): S? = when(ownerNode)
{
	is ParentNode.GameObjectNode ->
		ownerNode.gameObject.getComponent<StateManager<S>>()?.state ?: getState(ownerNode.parentNode)
	is ParentNode.SceneNode -> null
}
