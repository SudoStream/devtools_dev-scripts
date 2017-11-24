#!/bin/sh
exec scala "$0" "$@"
!#
import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

import scala.collection.mutable.ListBuffer

var schoolName = ""
var address = ""
var postCode = ""
var telephone = ""
var localAuthority = ""


def blankTheCurrents(): Unit = {
  schoolName = ""
  address = ""
  postCode = ""
  telephone = ""
  localAuthority = ""
}

case class School(
                   name: String,
                   address: String,
                   postCode: String,
                   telephone: String,
                   localAuthority: String
                 )

if (args.length != 1) {
  Console.err.println("Usage: extractSchoolsFromCsv <emailFile>")
  System.exit(1)
}

val schoolsFile: String = args(0)
val schoolsFilePath = Paths.get(schoolsFile)
val schoolsFileArray = Files.readAllLines(schoolsFilePath)
val schoolsFileArrayScala = schoolsFileArray.toArray.toList

var processingASchool = false
val people: ListBuffer[School] = new ListBuffer[School]()


for (line <- schoolsFileArrayScala) {
  val lineAsString = line.asInstanceOf[String]
  if (processingASchool) {
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

      processingASchool = false
      blankTheCurrents()
    }
  } else {
    if (lineAsString.contains("Firstname")) {
      processingASchool = true
      val startPositionFirstName = lineAsString.indexOf("Firstname: ") + 11
      val endPositionFirstName = lineAsString.indexOf("</p>")
      val firstname = lineAsString.substring(startPositionFirstName, endPositionFirstName)
      currentFirstname = firstname
    }
  }
}

println(s"How many people: ${people.size}")

val charset = Charset.forName("US-ASCII")
val outCsvFile = Paths.get(schoolsFile + ".csv")
val writer = Files.newBufferedWriter(outCsvFile, charset)

for ( person <- people) {
  val lineToWrite =
    s"${java.util.UUID.randomUUID().toString}, ${person.firstname}, ${person.surname}, ${person.positionType}, " +
  s"${person.email}, ${person.localAuthority}\n"
  writer.write(lineToWrite, 0, lineToWrite.length)
}

