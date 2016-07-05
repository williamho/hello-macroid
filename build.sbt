scalaVersion := "2.11.8"

androidBuild
platformTarget in Android := "android-23"

val androidVersion = "23.2.1"

libraryDependencies ++= Seq(
  aar("org.macroid" %% "macroid" % "2.0.0-M5"),
  aar("com.fortysevendeg" %% "macroid-extras" % "0.3"),
  aar("com.android.support" % "appcompat-v7" % androidVersion),
  aar("com.android.support" % "recyclerview-v7" % androidVersion)
)

proguardScala in Android := true
proguardOptions in Android ++= Seq(
  "-keep class scala.Dynamic",
  "-keep class scala.math.Numeric$IntIsIntegral$",
  "-keep class scala.math.Numeric$Implicits$",
  "-keep class scala.util.Try",
  "-keep class macroid.IdGenerator",
  "-ignorewarnings"
)

