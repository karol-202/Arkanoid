package component

import state.StateChange

data class StatePublisher<S>(private val stateChanges: List<StateChange<S>> = emptyList()) : Component
{
	data class ChangesAndClearedPublisher<S>(val stateChanges: List<StateChange<S>>,
	                                         val publisher: StatePublisher<S>)

	fun withStateChanges(stateChanges: List<StateChange<S>>) = copy(stateChanges = this.stateChanges + stateChanges)

	fun getChangesWithClearedPublisher() = ChangesAndClearedPublisher(stateChanges, withNoChanges())

	private fun withNoChanges() = copy(stateChanges = emptyList())
}
