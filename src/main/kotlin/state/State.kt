package state

interface State

typealias StateChange<S> = S.() -> S
