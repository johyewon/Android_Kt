class RoundTower
    (residents: Int, radius: Double, val floors: Int = 2)
    : RoundHut(residents, radius) {

    override val buildingMaterial: String = "Stone"
    override val capacity: Int = 4 * floors

    override fun floorArea(): Double {
        return super.floorArea() * floors
    }
}