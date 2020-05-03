package manager

import input.InputEvent
import scene.Scene
import update.UpdateContext

sealed class GameState
{
	data class Running(private val scene: Scene,
	                   private val lastTime: Double,
	                   private val deltaTime: Double = 0.0,
	                   private val inputQueue: List<InputEvent> = emptyList()) : GameState()
	{
		override fun run(currentTime: Double): GameRunResult
		{
			val state = deltaTimeCalculated(currentTime).updated()
			val renderOperations = state.render()
			return GameRunResult(state, renderOperations)
		}

		private fun deltaTimeCalculated(currentTime: Double) =
				copy(lastTime = currentTime, deltaTime = currentTime - lastTime)

		private fun updated() = copy(scene = sceneUpdated(), inputQueue = emptyList())

		private fun sceneUpdated() = sceneUpdated(scene.update(createFirstUpdateContext()))

		private fun sceneUpdated(previous: Scene): Scene =
				previous.update(createNextUpdateContext()).let { if(it == previous) it else sceneUpdated(it) }

		private fun createFirstUpdateContext() = UpdateContext(deltaTime, inputQueue)

		private fun createNextUpdateContext() = UpdateContext(0.0, emptyList())

		private fun render() = scene.render()

		override fun handleInput(event: InputEvent) = copy(inputQueue = inputQueue + event)
	}

	object Idle : GameState()
	{
		override fun run(currentTime: Double) = GameRunResult(this)

		override fun handleInput(event: InputEvent) = this
	}

	abstract fun run(currentTime: Double): GameRunResult

	abstract fun handleInput(event: InputEvent): GameState
}
