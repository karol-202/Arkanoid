package input

import org.w3c.dom.events.MouseEvent
import util.Vector

sealed class InputEvent
{
	abstract class Mouse(val location: Vector) : InputEvent()
	{
		class Down(location: Vector) : Mouse(location)

		class Move(location: Vector) : Mouse(location)

		class Up(location: Vector) : Mouse(location)
	}

	companion object
	{
		private const val TYPE_MOUSE_DOWN = "mousedown"
		private const val TYPE_MOUSE_MOVE = "mousemove"
		private const val TYPE_MOUSE_UP = "mouseup"

		fun from(mouseEvent: MouseEvent) = when(mouseEvent.type)
		{
			TYPE_MOUSE_DOWN -> Mouse.Down(mouseEvent.location)
			TYPE_MOUSE_MOVE -> Mouse.Move(mouseEvent.location)
			TYPE_MOUSE_UP -> Mouse.Up(mouseEvent.location)
			else -> throw IllegalArgumentException("Unknown event type: ${mouseEvent.type}")
		}

		private val MouseEvent.location get() = Vector(x, y)
	}
}
