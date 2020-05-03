package gameobject

import component.Component
import component.ComponentProvider
import scene.Scene
import kotlin.reflect.KClass

sealed class ParentNode : ComponentProvider
{
	data class SceneNode(override val scene: Scene) : ParentNode()
	{
		override fun getComponent(type: KClass<out Component>): Component? = null

		override fun getComponentInParents(type: KClass<out Component>): Component? = null
	}

	data class GameObjectNode(val gameObject: GameObject,
	                          val parentNode: ParentNode) : ParentNode()
	{
		override val scene get() = parentNode.scene

		override fun getComponent(type: KClass<out Component>) = gameObject.getComponent(type)

		override fun getComponentInParents(type: KClass<out Component>) =
				getComponent(type) ?: parentNode.getComponentInParents(type)
	}

	abstract val scene: Scene

	abstract fun getComponentInParents(type: KClass<out Component>): Component?
}

inline fun <reified C : Component> ParentNode.getComponentInParents() = getComponentInParents(C::class) as C?

inline fun <reified C : Component> ParentNode.requireComponentInParents() =
		getComponentInParents<C>() ?: throw IllegalStateException("No component: ${C::class.simpleName}")
