package update

import input.InputEvent

data class UpdateContext(val deltaTIme: Double,
                         val inputEvents: List<InputEvent>)
