package component

import gameobject.GameObject
import gameobject.ParentNode
import render.RenderContext

interface Component
{
	fun update(deltaTime: Double, ownerNode: ParentNode.GameObjectNode) = update(ownerNode) { owner }

	fun render(ownerNode: ParentNode.GameObjectNode) = render(ownerNode) { }
}

class ComponentContext(private val ownerNode: ParentNode.GameObjectNode)
{
	val scene get() = ownerNode.scene
	val owner get() = ownerNode.gameObject
	val parentNode get() = ownerNode.parentNode
}

private val ParentNode.GameObjectNode.componentContext get() = ComponentContext(this)

fun update(ownerNode: ParentNode.GameObjectNode, update: ComponentContext.() -> GameObject) =
		ownerNode.componentContext.update()

fun render(ownerNode: ParentNode.GameObjectNode, render: ComponentContext.(RenderContext) -> Unit) =
		{ context: RenderContext -> ownerNode.componentContext.render(context) }
