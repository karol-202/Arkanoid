package render

import org.w3c.dom.CanvasRenderingContext2D

val CanvasRenderingContext2D.width get() = canvas.width.toDouble()
val CanvasRenderingContext2D.height get() = canvas.height.toDouble()
