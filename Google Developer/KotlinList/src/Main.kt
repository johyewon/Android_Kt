class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            classTest()
        }

        fun dataAddRemoveTest() {
            val entrees = mutableListOf<String>()

            // 데이터 추가
            println("Add noodles: ${entrees.add("noodles")}")
            println("Entrees: $entrees")

            println("Add noodles: ${entrees.add("spaghetti")}")
            println("Entrees: $entrees")

            val moreItems = listOf("ravioli", "lasagna", "fettuccine")

            println("Add noodles: ${entrees.addAll(moreItems)}")
            println("Entrees: $entrees")

            // 데이터 삭제
            println("Remove item that doesn't exist: ${entrees.remove("rice")}")
            println("Entrees: $entrees")

            println("Remove first element: ${entrees.removeAt(0)}")
            println("Entrees: $entrees")

            entrees.clear()
            println("Entrees: $entrees")

            println("Empty? ${entrees.isEmpty()}\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        }

        fun whileTest() {
            // while loop
            val guestsPerFamily = listOf(2, 4, 1, 3)

            var totalGuests = 0

            var index = 0

            while(index < guestsPerFamily.size) {
                totalGuests +=guestsPerFamily[index]
                index++
            }
            println("Total Guest Count: $totalGuests")

        }

        fun forTest() {
            // for
            val names = listOf("Jessica", "Henry", "Alicia", "Jose")

            for(name in names) {
                println(name)
            }
        }

        private fun classTest() {
            val noodles = Noodles()
            val vegetables = Vegetables("Cabbage", "Sprouts", "Onion")

            println(noodles.toString())
            println(vegetables.toString())
        }
    }
}