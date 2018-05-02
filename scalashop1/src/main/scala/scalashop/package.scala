
import common._

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {

    var r = 0
    var g = 0
    var b = 0
    var a = 0

    val x1 = clamp(x - radius, 0, src.width - 1)
    val x2 = clamp(x + radius, 0, src.width - 1)
    val y1 = clamp(y - radius, 0, src.height - 1)
    val y2 = clamp(y + radius, 0, src.height - 1)

    val cnt = (x2 - x1 + 1) * (y2 - y1 + 1)

    var x0 = x1
    while (x0 <= x2) {
      var y0 = y1
      while (y0 <= y2) {
        r = r + red(src(x0, y0))
        g = g + green(src(x0, y0))
        b = b + blue(src(x0, y0))
        a = a + alpha(src(x0, y0))
        y0 = y0 + 1
      }
      x0 = x0 + 1
    }

    rgba(r/cnt, g/cnt, b/cnt, a/cnt)
  }

}
