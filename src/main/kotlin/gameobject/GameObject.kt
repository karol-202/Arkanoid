package gameobject

import component.Component
import render.RenderOperation
import update.UpdateContext
import kotlin.reflect.KClass

interface GameObject
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

	fun getComponent(type: KClass<out Component>): Component?

	fun withChildReplaced(oldChild: GameObject, newChild: GameObject): GameObject

	fun withComponentReplaced(newComponent: Component): GameObject
}

inline fun <reified C : Component> GameObject.getComponent() = getComponent(C::class) as C?

inline fun <reified C : Component> GameObject.requireComponent() = getComponent<C>() ?: throw IllegalStateException()
