class ClassTest {

    companion object {
        @JvmStatic
        fun main(arg: Array<String>) {
            val squareCabin = SquareCabin(6, 50.0)

            with(squareCabin) {
                println("\nSquare Cabin\n============")
                println("Capacity: $capacity")
                println("Material: $buildingMaterial")
                println("Floor area: ${floorArea()}")
                println("Has room? ${hasRoom()}")
                getRoom()
                println("Has room? ${hasRoom()}")
                getRoom()
            }

            val roundHut = RoundHut(3, 10.0)
            with(roundHut) {
                println("\nRound Hut\n=========")
                println("Material: $buildingMaterial")
                println("Capacity: $capacity")
                println("Floor area: ${floorArea()}")
                println("Has room? ${hasRoom()}")
                println("Carpet size: ${calculateMaxCarpetSize()}")
            }

            val roundTower = RoundTower(4, 15.5)
            with(roundTower) {
                println("\nRound Tower\n=========")
                println("Material: $buildingMaterial")
                println("Capacity: $capacity")
                println("Floor area: ${floorArea()}")
                println("Has room? ${hasRoom()}")
                println("Carpet size: ${calculateMaxCarpetSize()}")
            }
        }
    }
}
