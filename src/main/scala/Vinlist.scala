import scala.io.StdIn.readLine

object Vinlist{
    def main(args: Array[String]){
        print("Enter your first name: ")
        val firstName = readLine()

        print("Enter your last name: ")
        val lastName = readLine()

        println(s"Your name is $firstName $lastName")
    }
}