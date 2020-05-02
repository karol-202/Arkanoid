package manager

import render.RenderOperation

data class GameRunResult(val state: GameState,
                         val renderOperations: List<RenderOperation> = emptyList())
