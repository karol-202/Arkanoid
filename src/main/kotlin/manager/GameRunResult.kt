package manager

import render.RenderOperation

data class GameRunResult(val state: State,
                         val renderOperations: List<RenderOperation> = emptyList())
