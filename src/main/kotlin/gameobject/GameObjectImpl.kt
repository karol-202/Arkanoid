package gameobject

import component.Component
import render.RenderOperation
import scene.Scene
import util.replaced
import kotlin.reflect.KClass

data class GameObjectImpl(private val children: List<GameObject>,
                          private val components: Map<KClass<out Component>, Component>,
                          private val enabled: Boolean) : GameObject
{
	override fun update(deltaTime: Double, parentNode: ParentNode) =
			if(enabled) updateSelf(deltaTime, parentNode).updateChildren(deltaTime, parentNode)
			else this

	private fun GameObject.updateSelf(deltaTime: Double, parentNode: ParentNode) =
			components.values.fold(this) { currentObject, component ->
				component.update(deltaTime, currentObject.asParentNode(parentNode))
			}

	private fun GameObject.updateChildren(deltaTime: Double, parentNode: ParentNode) =
			children.fold(this) { currentObject, child ->
				val newChild = child.update(deltaTime, currentObject.asParentNode(parentNode))
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
}

private fun GameObject.asParentNode(parent: ParentNode) = ParentNode.GameObjectNode(this, parent)
