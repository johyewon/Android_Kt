class Vegetables(
    val topping1: String,
    val topping2: String,
    val topping3: String
) : Item("Vegetables", 5) {
    override fun toString(): String {
        return name
    }
}