
abstract class Dwelling(private var residents: Int) {

    abstract val buildingMaterial: String
    abstract val capacity: Int

    abstract fun floorArea() : Double

    fun hasRoom(): Boolean {
        return residents < capacity
    }

    fun getRoom() {
        if (capacity > residents) {
            residents++
            println("You got a room")
        } else
            println("Sorry, at capacity and no rooms left.")
    }
}