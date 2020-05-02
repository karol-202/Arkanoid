package manager

import input.InputEvent
import render.RenderContext
import scene.Scene
import kotlin.browser.window
import kotlin.js.Date

private const val UPDATE_INTERVAL = 30

class GameManager(private val context: RenderContext)
{
	private var state: GameState = GameState.Idle

	init
	{
		window.setInterval(this::run, UPDATE_INTERVAL)
	}

	fun start(scene: Scene)
	{
		state = GameState.Running(scene, Date.now())
	}

	fun stop()
	{
		state = GameState.Idle
	}

	private fun run()
	{
		val result = state.run(Date.now())
		state = result.state
		result.renderOperations.forEach { it(context) }
	}

	fun handleInput(event: InputEvent) = state.handleInput(event)
}
