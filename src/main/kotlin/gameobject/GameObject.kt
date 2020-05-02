package gameobject

import component.Component
import render.RenderOperation
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

	fun update(deltaTime: Double, parentNode: ParentNode): GameObject

	fun render(parentNode: ParentNode): List<RenderOperation>

	fun getComponent(type: KClass<out Component>): Component?

	fun withChildReplaced(oldChild: GameObject, newChild: GameObject): GameObject
}

inline fun <reified C : Component> GameObject.getComponent() = getComponent(C::class) as C?

inline fun <reified C : Component> GameObject.requireComponent() = getComponent<C>() ?: throw IllegalStateException()
