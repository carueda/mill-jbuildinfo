## JBuildInfo

This is a [mill](https://www.lihaoyi.com/mill/) module similar to 
[BuildInfo](https://com-lihaoyi.github.io/mill/mill/Plugin_BuildInfo.html)
but for Java. 
It will generate a Java class containing information from your build.

Project home: https://github.com/carueda/mill-jbuildinfo

To declare a module that uses this plugin, extend the
`com.github.carueda.mill.JBuildInfo` trait and provide
the desired information via the `buildInfoMembers` method:

```scala
// build.sc
import $ivy.`com.github.carueda::jbuildinfo:0.1.2`
import com.github.carueda.mill.JBuildInfo
import mill.T

object project extends JBuildInfo {
  def buildInfoMembers: T[Map[String, String]] = T {
    Map(
      "name" -> "some name",
      "version" -> "x.y.z"
    )
  }
}
```

This will generate:

```java
// BuildInfo.java
public class BuildInfo {
  public static final String getName() { return "some name"; }
  public static final String getVersion() { return "x.y.z"; }
}
```

### Configuration options

* `def buildInfoMembers: T[Map[String, String]]`

    The map containing all member names and values for the generated class.

* `def buildInfoClassName: String`, default: `BuildInfo`

    The name of the class that will contain all the members from
    `buildInfoMembers`.

* `def buildInfoPackageName: Option[String]`, default: `None`
  
    The package name for the generated class.
