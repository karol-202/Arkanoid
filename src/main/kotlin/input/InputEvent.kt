package input

import org.w3c.dom.events.MouseEvent

sealed class InputEvent
{
	abstract class Mouse(val x: Double,
	                     val y: Double) : InputEvent()
	{
		class Down(x: Double,
		           y: Double) : Mouse(x, y)

		class Move(x: Double,
		           y: Double) : Mouse(x, y)

		class Up(x: Double,
		         y: Double) : Mouse(x, y)
	}

	companion object
	{
		private const val TYPE_MOUSE_DOWN = "mousedown"
		private const val TYPE_MOUSE_MOVE = "mousemove"
		private const val TYPE_MOUSE_UP = "mouseup"

		fun from(mouseEvent: MouseEvent) = when(mouseEvent.type)
		{
			TYPE_MOUSE_DOWN -> Mouse.Down(mouseEvent.x, mouseEvent.y)
			TYPE_MOUSE_MOVE -> Mouse.Move(mouseEvent.x, mouseEvent.y)
			TYPE_MOUSE_UP -> Mouse.Up(mouseEvent.x, mouseEvent.y)
			else -> throw IllegalArgumentException("Unknown event type: ${mouseEvent.type}")
		}
	}
}
