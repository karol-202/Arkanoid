package gameobject

import component.Component
import scene.Scene
import kotlin.reflect.KClass

data class GameObject(private val components: Map<KClass<out Component>, Component>)
{
	constructor(components: List<Component>) : this(components.associateBy { it::class })

	fun update(deltaTime: Double, scene: Scene) = components.values.fold(scene) { currentScene, component ->
		component.update(deltaTime, currentScene, this)
	}

	fun render() = components.values.map { it.render(this) }

	fun getComponent(type: KClass<out Component>) = components[type]
}

inline fun <reified C : Component> GameObject.getComponent() = getComponent(C::class) as C?

inline fun <reified C : Component> GameObject.requireComponent() = getComponent<C>() ?: throw IllegalStateException()
