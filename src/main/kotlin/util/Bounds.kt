package util

data class Bounds(val start: Vector,
                  val size: Vector)
{
	private val end = start + size

	operator fun contains(vector: Vector) =
			vector.x >= start.x && vector.y >= start.y && vector.x <= end.x && vector.y <= end.y
}
