package manager

import scene.Scene

sealed class State
{
	data class Running(private val scene: Scene,
	                   private val lastTime: Double,
	                   private val deltaTime: Double = 0.0) : State()
	{
		override fun run(currentTime: Double): GameRunResult
		{
			val state = deltaTimeCalculated(currentTime).updated()
			val renderOperations = state.render()
			return GameRunResult(state, renderOperations)
		}

		private fun deltaTimeCalculated(currentTime: Double) =
				copy(lastTime = currentTime, deltaTime = currentTime - lastTime)

		private fun updated() = copy(scene = scene.update(deltaTime))

		private fun render() = scene.render()
	}

	object Idle : State()
	{
		override fun run(currentTime: Double) = GameRunResult(this)
	}

	abstract fun run(currentTime: Double): GameRunResult
}
