import component.ColorBackground
import gameobject.GameObject
import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import manager.GameManager
import org.w3c.dom.CanvasRenderingContext2D
import scene.Scene
import kotlin.browser.document

fun main()
{
    val canvas = document.body!!.append.canvas { }
    val context = canvas.getContext("2d") as CanvasRenderingContext2D

    val manager = GameManager(context)

    val scene = Scene(listOf(GameObject(listOf(ColorBackground("blue")))))
    manager.start(scene)
}
