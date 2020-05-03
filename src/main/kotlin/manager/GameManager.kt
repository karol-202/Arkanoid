package manager

import input.InputEvent
import render.RenderContext
import scene.Scene
import kotlin.browser.window
import kotlin.js.Date

private const val UPDATE_INTERVAL = 20

class GameManager(private val context: RenderContext)
{
	private var state: GameState = GameState.Idle

	init
	{
		window.setInterval(this::run, UPDATE_INTERVAL)
	}

	fun start(scene: Scene) = setState(GameState.Running(scene, Date.now()))

	fun stop() = setState(GameState.Idle)

	private fun run()
	{
		val result = state.run(Date.now())
		setState(result.state)
		result.renderOperations.forEach { it(context) }
	}

	fun handleInput(event: InputEvent) = setState(state.handleInput(event))

	private fun setState(state: GameState)
	{
		this.state = state
	}
}
