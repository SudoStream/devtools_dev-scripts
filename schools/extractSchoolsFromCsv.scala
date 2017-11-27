#!/bin/sh
exec scala "$0" "$@"
!#
import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

import scala.collection.mutable.ListBuffer

var currentSchoolName = ""
var currentAddress = ""
var currentPostCode = ""
var currentTelephone = ""
var currentLocalAuthority = ""


def blankTheCurrents(): Unit = {
  currentSchoolName = ""
  currentAddress = ""
  currentPostCode = ""
  currentTelephone = ""
  currentLocalAuthority = ""
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
val schools: ListBuffer[School] = new ListBuffer[School]()

for (line <- schoolsFileArrayScala) {
  val lineAsString = line.asInstanceOf[String]
  if (!lineAsString.contains("Address 1")) {
    val csvElements = lineAsString.split(",")
    // LA Name,School Name,Address 1,Address 2,Post code,Phone
    currentLocalAuthority = csvElements(0).trim
    currentSchoolName = csvElements(1).trim
    currentAddress = csvElements(2).trim + ", " + csvElements(3).trim
    currentPostCode = csvElements(4).trim
    currentTelephone = csvElements(5).trim

    val newSchool = School(
      name = currentSchoolName,
      address = currentAddress,
      postCode = currentPostCode,
      telephone = currentTelephone,
      localAuthority = currentLocalAuthority
    )

    schools += newSchool

    blankTheCurrents()
  }
}

println(s"How many schools: ${schools.size}")

val charset = Charset.forName("UTF-8")
val outCsvFile = Paths.get(schoolsFile + ".json")
val writer = Files.newBufferedWriter(outCsvFile, charset)

writer.write("[", 0, 1)
var count = 0
for (school <- schools) {
  count = count + 1
  val comma = if (count < schools.size) "," else ""

  writeLine("  {")
  writeLine(s"""    "_id" : "",""")
  writeLine(s"""    "name" : "${school.name}",""")
  writeLine(s"""    "address" : "${school.address}",""")
  writeLine(s"""    "postCode" : "${school.postCode}",""")
  writeLine(s"""    "telephone" : "${school.telephone}",""")
  writeLine(s"""    "localAuthority" : "${school.telephone}",""")
  writeLine(s"""    "country" : "SCOTLAND"""")
  writeLine(s"""  }$comma""")
}
writer.write("]", 0, 1)

def writeLine(stringToWrite: String ): Unit = {
//  println("Writing line :" + stringToWrite)
  val lineToWrite = stringToWrite + "\n"
  writer.write(lineToWrite, 0, lineToWrite.length)
}

writer.flush()
writer.close()
