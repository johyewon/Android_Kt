import kotlin.math.PI
import kotlin.math.sqrt

open class RoundHut(residents: Int, val radius: Double) : Dwelling(residents) {

    override val buildingMaterial: String = "Straw"
    override val capacity: Int = 4

    override fun floorArea(): Double {
        return PI * radius * radius
    }

    fun calculateMaxCarpetSize() : Double {
        val diameter = 2 * radius
        return sqrt(diameter * diameter / 2)
    }
}