package component

import gameobject.GameObject
import gameobject.ParentNode
import gameobject.getComponentInParents
import state.State
import state.StateChange
import update.UpdateContext

data class StateManager<S : State>(val state: S) : Component
{
	private data class TraversalResult<S : State>(val gameObject: GameObject,
	                                              val stateChanges: List<StateChange<S>> = emptyList())
	{
		fun traverse(): TraversalResult<S> = traverseSelf().traverseChildren()

		private fun traverseSelf(): TraversalResult<S>
		{
			val result = gameObject.getComponent<StatePublisher<S>>()?.getChangesWithClearedPublisher() ?: return this
			val newObject = gameObject.withComponentReplaced(result.publisher)
			return withGameObject(newObject).withAddedChanges(result.stateChanges)
		}

		private fun traverseChildren() =
				gameObject.children.fold(this) { currentState, child ->
					val childResult = TraversalResult<S>(child).traverse()
					val newChild = currentState.gameObject.withChildReplaced(child, childResult.gameObject)
					currentState.withGameObject(newChild).withAddedChanges(childResult.stateChanges)
				}

		fun withGameObject(gameObject: GameObject) = copy(gameObject = gameObject)

		fun withAddedChanges(newChanges: List<StateChange<S>>) = copy(stateChanges = stateChanges + newChanges)
	}

	override fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) {
		val traversalResult = TraversalResult<S>(owner).traverse()
		val newState = traversalResult.stateChanges.applyTo(state)
		traversalResult.gameObject.withComponentReplaced(withUpdatedState(newState))
	}

	private fun List<StateChange<S>>.applyTo(state: S) = fold(state) { currentState, change -> currentState.change() }

	private fun withUpdatedState(state: S) = copy(state = state)
}

fun <S : State> getState(node: ParentNode) = node.getComponentInParents<StateManager<S>>()?.state

fun <S : State> requireState(node: ParentNode) = getState<S>(node) ?: throw IllegalStateException("No state found")
