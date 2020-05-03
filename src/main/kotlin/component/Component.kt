package component

import gameobject.GameObject
import gameobject.ParentNode
import render.RenderContext
import update.UpdateContext
import kotlin.reflect.KClass

interface Component
{
	fun update(ownerNode: ParentNode.GameObjectNode, updateContext: UpdateContext) = update(ownerNode) { owner }

	fun render(ownerNode: ParentNode.GameObjectNode) = render(ownerNode) { }
}

class ComponentContext(private val ownerNode: ParentNode.GameObjectNode) : ComponentProvider
{
	val scene get() = ownerNode.scene
	val owner get() = ownerNode.gameObject
	val parentNode get() = ownerNode.parentNode

	override fun getComponent(type: KClass<out Component>) = owner.getComponent(type)
}

private val ParentNode.GameObjectNode.componentContext get() = ComponentContext(this)

fun update(ownerNode: ParentNode.GameObjectNode, update: ComponentContext.() -> GameObject) =
		ownerNode.componentContext.update()

fun render(ownerNode: ParentNode.GameObjectNode, render: ComponentContext.(RenderContext) -> Unit) =
		{ context: RenderContext -> ownerNode.componentContext.render(context) }
