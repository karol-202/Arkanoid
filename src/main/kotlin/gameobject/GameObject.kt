package gameobject

import component.Component
import component.ComponentProvider
import render.RenderOperation
import update.UpdateContext
import kotlin.reflect.KClass

interface GameObject : ComponentProvider
{
	companion object
	{
		fun create(children: List<GameObject> = emptyList(),
		           components: List<Component> = emptyList(),
		           enabled: Boolean = true) =
				GameObjectImpl(children, components.associateBy { it::class }, enabled)
	}

	val children: List<GameObject>

	fun update(parentNode: ParentNode, updateContext: UpdateContext): GameObject

	fun render(parentNode: ParentNode): List<RenderOperation>

	fun withChildReplaced(oldChild: GameObject, newChild: GameObject): GameObject

	fun withComponentReplaced(newComponent: Component): GameObject
}

inline fun <reified C : Component> ComponentProvider.getComponent() = getComponent(C::class) as C?

inline fun <reified C : Component> ComponentProvider.requireComponent() =
		getComponent<C>() ?: throw IllegalStateException("No component: ${C::class.simpleName}")
