import assets.loadImage
import component.*
import dsl.gameObject
import dsl.scene
import input.InputEvent
import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import manager.GameManager
import org.w3c.dom.events.MouseEvent
import render.context2d
import render.fixBounds
import state.State
import util.Vector
import kotlin.browser.document

private val canvas = document.body!!.append.canvas { }
private val context = canvas.context2d
private val gameManager = GameManager(context)

object MyState : State

val scene1 = scene {
    root {
        + StateManager(MyState)
        + gameObject {
            + ColorBackground("black")
        }
        + gameObject {
            + Position(y = 200.0)
            + gameObject {
                + Position(100.0, 100.0)
                + Sprite(loadImage("assets/ball.png"))
                + StatePublisher<MyState>()
                + ClickHandler<MyState>(size = Vector(100.0, 100.0)) { println("click 1"); this }
            }
            + gameObject {
                + Position(300.0, 100.0)
                + Sprite(loadImage("assets/ball.png"))
                + StatePublisher<MyState>()
                + ClickHandler<MyState>(size = Vector(100.0, 100.0)) { println("click 2"); this }
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
