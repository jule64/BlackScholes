name := "scala-blackscholes"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.2"
  //  "org.scalaz" % "scalaz-core_2.10" % "7.1.1",
//  "io.argonaut" %% "argonaut" % "6.1",
//  "org.scalatest" %% "scalatest" % "2.2.6" % Test
)

scalacOptions ++= Seq("-language:implicitConversions,higherKinds")
scalacOptions ++= Seq("-unchecked", "-deprecation","-feature")
//scalacOptions ++= Seq("-print")