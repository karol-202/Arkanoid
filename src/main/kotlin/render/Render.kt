package render

import org.w3c.dom.CanvasRenderingContext2D

typealias RenderContext = CanvasRenderingContext2D

typealias RenderOperation = (RenderContext) -> Unit

fun render(operation: RenderContext.() -> Unit) = { context: RenderContext -> context.operation() }
