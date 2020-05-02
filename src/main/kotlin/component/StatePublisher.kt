package component

import state.StateChange

data class StatePublisher<S>(val stateChanges: List<StateChange<S>>) : Component
{
	fun withStateChange(stateChange: StateChange<S>) = copy(stateChanges = stateChanges + stateChange)

	private fun withNoChanges() = copy(stateChanges = emptyList())
}
