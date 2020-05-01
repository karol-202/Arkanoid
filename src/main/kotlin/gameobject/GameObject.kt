package gameobject

import component.Component
import render.RenderOperation
import scene.Scene
import kotlin.reflect.KClass

data class GameObject(private val children: List<GameObject>,
                      private val components: Map<KClass<out Component>, Component>,
                      private val enabled: Boolean)
{
	constructor(children: List<GameObject> = emptyList(),
	            components: List<Component> = emptyList(),
	            enabled: Boolean = true) :
			this(children, components.associateBy { it::class }, enabled)

	fun update(deltaTime: Double, scene: Scene): Scene =
			if(enabled) listOf(this::updateSelf, this::updateChildren).fold(scene) { currentScene, update ->
				update(deltaTime, currentScene)
			}
			else scene

	private fun updateSelf(deltaTime: Double, scene: Scene) = components.values.fold(scene) { currentScene, component ->
		component.update(deltaTime, currentScene, this)
	}

	private fun updateChildren(deltaTime: Double, scene: Scene) = children.fold(scene) { currentScene, child ->
		child.update(deltaTime, currentScene)
	}

	fun render(): List<RenderOperation> =
			if(enabled) components.values.map { it.render(this) } + children.flatMap { it.render() }
			else emptyList()

	fun getComponent(type: KClass<out Component>) = components[type]
}

inline fun <reified C : Component> GameObject.getComponent() = getComponent(C::class) as C?

inline fun <reified C : Component> GameObject.requireComponent() = getComponent<C>() ?: throw IllegalStateException()
