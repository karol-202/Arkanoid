package component

import gameobject.GameObject
import render.render
import scene.Scene

interface Component
{
	fun update(deltaTime: Double, scene: Scene, gameObject: GameObject) = scene

	fun render(gameObject: GameObject) = render {  }
}
