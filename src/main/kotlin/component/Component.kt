package component

import render.RenderOperation
import scene.Scene

interface Component
{
	fun update(deltaTime: Double, scene: Scene) = scene

	fun render(): RenderOperation
}
