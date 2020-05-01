package gameobject

import component.Component
import scene.Scene

data class GameObject(private val components: List<Component> = emptyList())
{
	fun update(deltaTime: Double, scene: Scene) = components.fold(scene) { currentScene, component ->
		component.update(deltaTime, currentScene)
	}

	fun render() = components.map { it.render() }
}
