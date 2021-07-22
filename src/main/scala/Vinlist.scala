import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import org.mongodb.scala.Observable._

import org.mongodb.scala._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model._

import scala.collection.JavaConverters._
import org.mongodb.scala.bson.collection.mutable
import org.apache.log4j.BasicConfigurator
import com.mongodb.client.result.InsertOneResult
import javax.ws.rs.core.Link.Builder

object Vinlist{
    def main(args: Array[String]){
        val mongoClient: MongoClient = MongoClient()
        val database: MongoDatabase = mongoClient.getDatabase("test")
        var table = ArrayBuffer(ArrayBuffer[String]())
        var run = true
        var title = ""
        while(run){
            //Main menu
            println("+++++++++++++++++++++++++++++")
            println("+ Main menu                 +")
            println("+ 1 - read csv              +")
            println("+ 2 - print current table   +")
            println("+ 3 - push table to MongoDB +")
            println("+ x - exit the program      +")
            println("+++++++++++++++++++++++++++++")
            print("Enter a menu option from the list: ")
            val userEntry = readLine()
            userEntry match {
                case "1" => {
                    print("Enter a path: ")
                    val fileName = readLine()
                    if(fileName.lastIndexOf('/') >= 0){
                        title = fileName.substring(fileName.lastIndexOf('/')+1)
                    }
                    else if(fileName.lastIndexOf('\\') >= 0){
                        title = fileName.substring(fileName.lastIndexOf('\\')+1)
                    }
                    title = title.substring(0,title.length-4)
                    //println(s"TITLE:  $title")
                    table = readCSV(fileName)                    
                }
                case "2" => printTable(table, title)
                case "3" => pushToDB(table, title, database)
                case "x" => run = false
                case _ =>
            }
        }
        mongoClient.close()
    }

    def pushToDB(table: ArrayBuffer[ArrayBuffer[String]], title: String, database: MongoDatabase){
        val collection: MongoCollection[Document] = database.getCollection(title)
        val headers = ArrayBuffer[String]()
        for(i <- 0 to table(0).length-1){
            headers += table(0)(i)
        }
        for(i <- 1 to table.length-1){
            val docArray = ArrayBuffer[String]()
            var doc = Document.apply()
            for(j <- 0 to table(0).length-1){
                docArray += table(i)(j)
                doc += (headers(j) -> docArray(j))
            }
            val observable: Observable[InsertOneResult] = collection.insertOne(doc)          
            observable.subscribe(new Observer[InsertOneResult]{
                override def onSubscribe(subscription: Subscription): Unit = (subscription.request(1))
                override def onNext(result: InsertOneResult): Unit = println(s"onNext $result")
                override def onError(e: Throwable): Unit = println("Failed")
                override def onComplete(): Unit = println("Completed")
            })
        }
    }

    def readCSV(fileName: String): ArrayBuffer[ArrayBuffer[String]] = {
        try{            
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
        catch{
            case x: java.io.FileNotFoundException => {
                println("File Not Found")
                return ArrayBuffer(ArrayBuffer[String]())
            }
        }
    }

    def printTable(arr2: ArrayBuffer[ArrayBuffer[String]], title: String){
        println(s"Title: $title")
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