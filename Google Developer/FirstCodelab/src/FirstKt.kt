fun main() {
    val age = 24
    val layers = 5
    val name = "Hani"

    val border = "`-._,-'"
    val repeatTime = 5

    printBorder(repeatTime, border)
    birthdayCard(age, name, layers)
    printBorder(repeatTime, border)

}

fun birthdayCard(age: Int, name: String, layers: Int) {
    println("Happy Birthday, ${name}!")

    // top
    repeat(age + 2) {
        print("=")
    }
    println()

    // candle
    print(" ")
    repeat(age) {
        print(",")
    }
    println()

    print(" ")
    repeat(age) {
        print("|")
    }
    println()

    // bottom
    repeat(layers) {
        repeat(age + 2) {
            print("@")
        }
        println()
    }

    println("You are already $age!")
    println("$age is the very best age to celebrate!")

}


fun printBorder(repeatTime: Int, border: String) {
    repeat(repeatTime) {
        print(border)
    }
    println()
}