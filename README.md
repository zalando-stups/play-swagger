# Play-Swagger

[![Build Status](https://travis-ci.org/zalando/play-swagger.svg)](https://travis-ci.org/zalando/play-swagger)
[![Gitter Chat](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/zalando/play-swagger?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This is an end-to-end prototype with the intention to demonstrate the possibility to generate 
following components from the swagger specification:
* Play route files
* Generators of random test data
* Wrappers for play route files to convert semantics from http-related to domain-related
* Skeletons for the domain-driven controller implementation
* Unit tests for invalid and valid parameter sets

Please pay attention that this is really just a prototype with a lot of bugs and code duplication, 
missing corner- and special cases and should not be treated as a production code. The source code
of this branch will be refactored or rewritten almost completely in the near future.
The intent of putting this version of the codebase into the public domain is to benefit 
from community feedback during the mentioned refactoring.

There are 3 sub projects:

* `api` - This is the project that's automatically added to the runtime classpath of any projects that use this plugin.
* `compiler` - This is a stand alone compiler that the plugin uses, it doesn't depend on sbt, and could theoretically be used to build support for other build systems.
* `plugin` - This is the sbt plugin, it depends on `compiler`, and automatically adds `api` to the `libraryDependencies` of any project that uses it.

## Developing

sbt doesn't allow sub projects to depend on each other as sbt plugins, so to test an sbt plugin, you need a separate project.  This project is `swagger-tester`.  To test your changes as you're developing the plugin, cd into this directory, and run sbt.  This project uses an sbt `ProjectRef` to the sbt plugin, which means you don't need to `publishLocal` the plugin after each change, just run `reload` in the sbt console, and it will pick your changes up.

## Testing

We're using the sbt scripted framework for testing, the tests can be found in `plugin/src/sbt-test`, and can be run by running `scripted` in the sbt console.
