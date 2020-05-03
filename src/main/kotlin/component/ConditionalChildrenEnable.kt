package component

import gameobject.ParentNode
import gameobject.withChildrenMapped
import state.State
import update.UpdateContext

data class ConditionalChildrenEnable<S : State>(private val condition: S.() -> Boolean) : Component
{
	override fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) {
		val enable = requireState<S>(ownerNode).condition()
		owner.withChildrenMapped { it.enabled(enable) }
	}
}
