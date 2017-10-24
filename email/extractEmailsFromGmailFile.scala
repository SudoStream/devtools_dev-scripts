#!/bin/sh
exec scala "$0" "$@"
!#
import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

import scala.collection.mutable.ListBuffer

var currentFirstname = ""
var currentSurname = ""
var currentEmail = ""
var currentPositionType = ""
var currentLocalAuthority = ""

def blankTheCurrents(): Unit = {
  currentFirstname = ""
  currentSurname = ""
  currentEmail = ""
  currentPositionType = ""
  currentLocalAuthority = ""
}

case class Person(
                   firstname: String,
                   surname: String,
                   email: String,
                   positionType: String,
                   localAuthority: String
                 )

if (args.length != 1) {
  Console.err.println("Usage: extractEmailsFromGmailFile <emailFile>")
  System.exit(1)
}

val emailFile: String = args(0)
val emailFilePath = Paths.get(emailFile)
val emailFileArray = Files.readAllLines(emailFilePath)
val emailFileArrayScala = emailFileArray.toArray.toList

var processingAPerson = false
val people: ListBuffer[Person] = new ListBuffer[Person]()



for (line <- emailFileArrayScala) {
  val lineAsString = line.asInstanceOf[String]
  if (processingAPerson) {
    if (lineAsString.contains("Surname")) {
      val startPosition = lineAsString.indexOf("Surname: ") + 9
      val endPosition = lineAsString.indexOf("</p>")
      currentSurname = lineAsString.substring(startPosition, endPosition)
    } else if (lineAsString.contains("Email")) {
      val startPosition = lineAsString.indexOf("Email: ") + 7
      val endPosition = lineAsString.indexOf("</p>")
      currentEmail = lineAsString.substring(startPosition, endPosition)
    } else if (lineAsString.contains("Local Authority")) {
      val startPosition = lineAsString.indexOf("Local Authority: ") + 17
      val endPosition = lineAsString.indexOf("</p>")
      currentLocalAuthority = lineAsString.substring(startPosition, endPosition)
    } else if (lineAsString.contains("Position Type")) {
      val startPosition = lineAsString.indexOf("Position Type: ") + 15
      val endPosition = lineAsString.indexOf("</p>")
      currentPositionType = lineAsString.substring(startPosition, endPosition)

      val newPerson = Person(
        firstname = currentFirstname,
        surname = currentSurname,
        email = currentEmail,
        positionType = currentPositionType,
        localAuthority = currentLocalAuthority
      )

      people += newPerson

      processingAPerson = false
      blankTheCurrents()
    }
  } else {
    if (lineAsString.contains("Firstname")) {
      processingAPerson = true
      val startPositionFirstName = lineAsString.indexOf("Firstname: ") + 11
      val endPositionFirstName = lineAsString.indexOf("</p>")
      val firstname = lineAsString.substring(startPositionFirstName, endPositionFirstName)
      currentFirstname = firstname
    }
  }
}

println(s"How many people: ${people.size}")

val charset = Charset.forName("US-ASCII")
val outCsvFile = Paths.get(emailFile + ".csv")
val writer = Files.newBufferedWriter(outCsvFile, charset)

for ( person <- people) {
  val lineToWrite =
    s"${java.util.UUID.randomUUID().toString}, ${person.firstname}, ${person.surname}, ${person.positionType}, " +
  s"${person.email}, ${person.localAuthority}\n"
  writer.write(lineToWrite, 0, lineToWrite.length)
}

