package persistentmapexample

import scala.pickling._
import scala.pickling.binary._
import scala.slick.session.Database
import st.sparse.persistentmap._

case class Foo(int: Int)
case class Bar(foos: List[Foo], string: String)

object PersistentMapExample extends App {
  val database: scala.slick.session.Database = {
    val tempFile = java.io.File.createTempFile("PersistentMapExample", "sqlite")
    tempFile.deleteOnExit
    Database.forURL(s"jdbc:sqlite:$tempFile", driver = "org.sqlite.JDBC")
  }

  // Create a `PersistentMap`.
  // Of course, you can also connect to an existing one.
  val map = PersistentMap.create[Int, String]("myMap", database)
  
  println(s"0: $map")

  // Add key-value pairs.
  map += 1 -> "no"
  map += 2 -> "boilerplate"

  println(s"1: $map")
  
  // Retrieve values.
  assert(map(1) == "no")

  // Delete key-value pairs.
  map -= 2

  println(s"2: $map")
  
  // And do anything else supported by `collection.mutable.Map`.
  // ...
  
  // You can use arbitrary types in the store, so long as scala-pickling
  // can handle them.

  val otherMap = PersistentMap.create[Foo, Bar]("myOtherMap", database)
  
  println(s"3: $otherMap")
  
  otherMap += Foo(10) -> Bar(List(Foo(1), Foo(2)), "hello")
  
  println(s"4: $otherMap")
}
