package component

import kotlin.reflect.KClass

interface ComponentProvider
{
	fun getComponent(type: KClass<out Component>): Component?
}

inline fun <reified C : Component> ComponentProvider.getComponent() = getComponent(C::class) as C?

inline fun <reified C : Component> ComponentProvider.requireComponent() =
		getComponent<C>() ?: throw IllegalStateException("No component: ${C::class.simpleName}")
