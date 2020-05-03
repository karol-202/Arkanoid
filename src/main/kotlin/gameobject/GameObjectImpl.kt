package gameobject

import component.Component
import render.RenderOperation
import scene.Scene
import update.UpdateContext
import util.replaced
import kotlin.reflect.KClass

data class GameObjectImpl(override val children: List<GameObject>,
                          private val components: Map<KClass<out Component>, Component>,
                          private val enabled: Boolean) : GameObject
{
	override fun update(parentNode: ParentNode, updateContext: UpdateContext) =
			if(enabled) updateSelf(parentNode, updateContext).updateChildren(parentNode, updateContext)
			else this

	private fun GameObject.updateSelf(parentNode: ParentNode, updateContext: UpdateContext) =
			components.values.fold(this) { currentObject, component ->
				component.update(currentObject.asParentNode(parentNode), updateContext)
			}

	private fun GameObject.updateChildren(parentNode: ParentNode, updateContext: UpdateContext) =
			children.fold(this) { currentObject, child ->
				val newChild = child.update(currentObject.asParentNode(parentNode), updateContext)
				currentObject.withChildReplaced(child, newChild)
			}

	override fun render(parentNode: ParentNode): List<RenderOperation> =
			if(enabled) renderSelf(parentNode) + renderChildren(parentNode)
			else emptyList()

	private fun renderSelf(parentNode: ParentNode) = components.values.map { it.render(this.asParentNode(parentNode)) }

	private fun renderChildren(parentNode: ParentNode) = children.flatMap { it.render(this.asParentNode(parentNode)) }

	override fun getComponent(type: KClass<out Component>) = components[type]

	override fun withChildReplaced(oldChild: GameObject, newChild: GameObject) =
			copy(children = children.replaced(oldChild, newChild))

	override fun withComponentReplaced(newComponent: Component) =
			copy(components = components.mapValues { (type, oldComponent) ->
				if(type == newComponent::class) newComponent else oldComponent
			})

	override fun enabled(enabled: Boolean) = copy(enabled = enabled)
}

private fun GameObject.asParentNode(parent: ParentNode) = ParentNode.GameObjectNode(this, parent)
