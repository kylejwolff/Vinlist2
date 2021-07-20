import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Vinlist{
    def main(args: Array[String]){
        var table = ArrayBuffer(ArrayBuffer[String]())
        var run = true
        while(run){
            //Main menu
            println("+++++++++++++++++++++++++++")
            println("+ Main menu               +")
            println("+ 1 - read csv            +")
            println("+ 2 - print current table +")
            println("+ x - exit the program    +")
            println("+++++++++++++++++++++++++++")
            print("Enter a menu option from the list: ")
            val userEntry = readLine()
            userEntry match {
                case "1" => {
                    print("Enter a path: ")
                    val fileName = readLine()
                    table = readCSV(fileName)                    
                }
                case "2" => printTable(table)
                case "x" => run = false
                case _ =>
            }
        }
    }

    def readCSV(fileName: String): ArrayBuffer[ArrayBuffer[String]] = {
        val s = Source.fromFile(fileName).mkString
        var arr = s.split("\n")
        var arr2 = ArrayBuffer(ArrayBuffer[String]())
        val temp = arr(0).split(",")
        for(i <- 0 to arr.length-2){
            arr2 += ArrayBuffer[String]()
        }
        for(i <- 0 to arr2.length-1){
            val temp = arr(i).split(",")
            for(j <- 0 to temp.length-1){
                arr2(i) += temp(j).mkString
            }
        }
        return arr2
    }

    def printTable(arr2: ArrayBuffer[ArrayBuffer[String]]){
        for(j <- 0 to arr2(0).length-1){
            if(arr2(0)(j).length < 6){
                print("| " + arr2(0)(j) + "\t\t")
            }
            else print("| " + arr2(0)(j) + "\t")            
        }
        print("\n")
        for(j <- 0 to arr2(0).length-1){
            print("________________")
        }
        print("\n")
        for(i <- 1 to arr2.length-1){
            for(j <- 0 to arr2(0).length-1){
                if(arr2(i)(j).length < 6){
                    print("| " + arr2(i)(j) + "\t\t")
                    Console.out.flush()
                }
                else print("| " + arr2(i)(j) + "\t")
            }
            print("\n")
        }        
    }
}