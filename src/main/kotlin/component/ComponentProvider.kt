package component

import kotlin.reflect.KClass

interface ComponentProvider
{
	fun getComponent(type: KClass<out Component>): Component?
}
