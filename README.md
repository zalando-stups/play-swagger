# Play-Swagger

[![Build Status](https://travis-ci.org/zalando/play-swagger.svg)](https://travis-ci.org/zalando/play-swagger)

There are 3 sub projects:

* `api` - This is the project that's automatically added to the runtime classpath of any projects that use this plugin.
* `compiler` - This is a stand alone compiler that the plugin uses, it doesn't depend on sbt, and could theoretically be used to build support for other build systems.
* `plugin` - This is the sbt plugin, it depends on `compiler`, and automatically adds `api` to the `libraryDependencies` of any project that uses it.

## Developing

sbt doesn't allow sub projects to depend on each other as sbt plugins, so to test an sbt plugin, you need a separate project.  This project is `swagger-tester`.  To test your changes as you're developing the plugin, cd into this directory, and run sbt.  This project uses an sbt `ProjectRef` to the sbt plugin, which means you don't need to `publishLocal` the plugin after each change, just run `reload` in the sbt console, and it will pick your changes up.

## Testing

We're using the sbt scripted framework for testing, the tests can be found in `plugin/src/sbt-test`, and can be run by running `scripted` in the sbt console.
