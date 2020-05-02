import assets.loadImage
import component.ColorBackground
import component.Position
import component.Sprite
import dsl.gameObject
import dsl.scene
import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import manager.GameManager
import render.context2d
import render.fixBounds
import kotlin.browser.document

fun main()
{
    val canvas = document.body!!.append.canvas { }
    val context = canvas.context2d
    canvas.fixBounds()

    val manager = GameManager(context)

    val scene = scene {
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
    manager.start(scene)
}
