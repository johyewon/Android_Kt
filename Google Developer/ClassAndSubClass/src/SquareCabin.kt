class SquareCabin(residents: Int, val length: Double) : Dwelling(residents) {

    override val buildingMaterial: String = "Wood"
    override val capacity: Int = 6

    override fun floorArea(): Double {
        return length * length
    }

}