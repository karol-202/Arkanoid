import assets.loadImage
import component.ColorBackground
import component.Position
import component.Sprite
import dsl.gameObject
import dsl.scene
import input.InputEvent
import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import manager.GameManager
import org.w3c.dom.events.MouseEvent
import render.context2d
import render.fixBounds
import kotlin.browser.document

private val canvas = document.body!!.append.canvas { }
private val context = canvas.context2d
private val gameManager = GameManager(context)

val scene1 = scene {
    root {
        + gameObject {
            + ColorBackground("blue")
        }
        + gameObject {
            + Position(y = 200.0)
            + gameObject {
                + Position(100.0, 100.0)
                + Sprite(loadImage("assets/ball.png"))
            }
            + gameObject {
                + Position(300.0, 100.0)
                + Sprite(loadImage("assets/ball.png"))
            }
        }
    }
}

fun main()
{
    initCanvas()
    gameManager.start(scene1)
}

private fun initCanvas()
{
    canvas.fixBounds()
    canvas.onmousedown = ::handleMouseEvent
    canvas.onmousemove = ::handleMouseEvent
    canvas.onmouseup = ::handleMouseEvent
}

private fun handleMouseEvent(mouseEvent: MouseEvent) = gameManager.handleInput(InputEvent.from(mouseEvent))
