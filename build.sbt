name := "scala-blackscholes"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.2"
)

scalacOptions ++= Seq("-language:implicitConversions,higherKinds")
scalacOptions ++= Seq("-unchecked", "-deprecation","-feature")
//scalacOptions ++= Seq("-print")