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

private class UpdateInterruptException : Exception()

class ComponentContext(private val ownerNode: ParentNode.GameObjectNode) : ComponentProvider
{
	val scene get() = ownerNode.scene
	val owner get() = ownerNode.gameObject
	val parentNode get() = ownerNode.parentNode

	override fun getComponent(type: KClass<out Component>) = owner.getComponent(type)

	fun dontUpdate(): Nothing = throw UpdateInterruptException()
}

private val ParentNode.GameObjectNode.componentContext get() = ComponentContext(this)

fun update(ownerNode: ParentNode.GameObjectNode, update: ComponentContext.() -> GameObject) =
		try { ownerNode.componentContext.update() }
		catch(e: UpdateInterruptException) { ownerNode.gameObject }

fun render(ownerNode: ParentNode.GameObjectNode, render: ComponentContext.(RenderContext) -> Unit) =
		{ context: RenderContext ->
			try { ownerNode.componentContext.render(context) }
			catch(e: UpdateInterruptException) { }
		}
