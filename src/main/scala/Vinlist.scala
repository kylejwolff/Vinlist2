import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

class Item(name: String, check: Boolean){}
object Vinlist{
    def main(args: Array[String]){
        // print("Enter your first name: ")
        // val firstName = readLine()

        // print("Enter your last name: ")
        // val lastName = readLine()

        // println(s"Your name is $firstName $lastName")
        // var arr = Array("One", "Two", "Three")
        // arr.foreach(a => println(a))

        // val s = Source.fromFile("../csv/Shopping List.csv").mkString.substring(3)
        // var arr = s.split("\n")
        // var arr2 = Array.ofDim[String](arr.length, 2)
        // for(a <- 0 to arr.length-1){
        //     val temp = arr(a).split(",")
        //     arr2(a)(0) = temp(0).mkString
        //     arr2(a)(1) = temp(1).mkString
        // }
        // for(i <- 0 to arr2.length-1){
        //     for(j <- 0 to arr2(0).length-1){
        //         print(" " + arr2(i)(j))
        //     }
        //     println()
        // }
        print("Enter a path: ")
        val x = readLine()
        readCSV(x)

        var run = true
        while(run){
            //Main menu
            println("Main menu")
            println("x - exit the program")
            print("Enter a menu option from the list: ")
            val userEntry = readLine()
            userEntry match {
                case "x" => run = false
            }
        }
    }

    def readCSV(fileName: String){
        val s = Source.fromFile("../csv/Shopping List.csv").mkString.substring(3)
        var arr = s.split("\n")
        var arr2 = Array.ofDim[String](arr.length, 2)
        for(a <- 0 to arr.length-1){
            val temp = arr(a).split(",")
            arr2(a)(0) = temp(0).mkString
            arr2(a)(1) = temp(1).mkString
        }
        for(j <- 0 to arr2(0).length-1){
            if(arr2(0)(j).length < 6){
                print("| " + arr2(0)(j) + "\t\t")
            }
            else print("| " + arr2(0)(j) + "\t")            
        }
        println()
        for(j <- 0 to arr2(0).length-1){
            print("________________")            
        }
        println()        
        for(i <- 1 to arr2.length-1){
            for(j <- 0 to arr2(0).length-1){
                if(arr2(i)(j).length < 6){
                    print("| " + arr2(i)(j) + "\t\t")
                }
                else print("| " + arr2(i)(j) + "\t")
            }
            println()
        }
    }
}