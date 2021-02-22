/**
 * @author Paul Scott
 * @version 25 January 2019
 *
 * class representing a point
 * contains x value, y value, angle,
 * and boolean value stating whether the point
 * is in the convex hull (for graphic purposes only)
 */

class PointAngle(pointX: Int, pointY: Int, theta: Double, inHull: Boolean) {

    // instance data
    private var x = 0
    private var y = 0
    private var angle = 0.0
    private var inHull = true

    init {
        x = pointX
        y = pointY
        angle = theta
    }

    /**
     * returns x value
     * @return x
     */
    fun getX(): Int { return x }

    /**
     * returns y value
     * @return y
     */
    fun getY(): Int { return y }

    /**
     * returns angle of point
     * @return angle
     */
    fun getAngle(): Double { return angle }

    /**
     * returns inHull boolean value
     * @return inHull
     */
    fun getStatus(): Boolean { return inHull }

    /**
     * sets inHull boolean value
     * @param status - new inHull value
     */
    fun setStatus(status: Boolean) { inHull = status }

    /**
     * sets angle of point
     * @param theta - new angle value
     */
    fun setAngle(theta: Double) { angle = theta }
}