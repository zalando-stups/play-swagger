# Play-Swagger

[![Build Status](https://travis-ci.org/zalando/play-swagger.svg)](https://travis-ci.org/zalando/play-swagger)
[![Gitter Chat](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/zalando/play-swagger?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Warning

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

## Building and using

To build a plugin, do the following:

- Clone the repository to your local filesystem
- run ```sbt +publishLocal``` in the play-swagger directory, this will publish the plugin into your local ivy repository

To use the plugin in a play project:

- Create a new play project for example using activator, for example: ```activator new hello-world play-scala``` 
- Edit the project/plugins.sbt by adding following: 
  ```addSbtPlugin("de.zalando" % "sbt-play-swagger" % "0.1-SNAPSHOT")```
- Edit the build.sbt in the new project directory by adding following:
  - ```, PlaySwagger``` in the list of plugins
  - change the scope of the ```spec2``` dependency by removing ```% Test```
  - ```"org.scalacheck" %% "scalacheck" % "1.12.4",
       "org.specs2" %% "specs2-scalacheck" % "3.6"
    ``` to the list of the libraryDependencies

- Start ```sbt```
- Run play ```run```
- Navigate to the "http://localhost:9000 in the browser
- Put a swagger specification with a ```.yaml``` or ```.json``` extension into the ```conf``` directory
- Put the handler specification for the HTTP method in the swagger specification, for example:
  ```"x-api-first-handler": "controllers.PetstoreYaml.findPets"```
 
- Link newly added specification to central play routes by adding new line to the ```rotes``` file, for example:
  ```-> 	      /api                  petstore.Routes```. The prefix should correspond to the basePath 
  set in the specification
  
## Structure

There are 3 sub projects:

* `api` - This is the project that's automatically added to the runtime classpath of any projects that use this plugin.
* `compiler` - This is a stand alone compiler that the plugin uses, it doesn't depend on sbt, and could theoretically be used to build support for other build systems.
* `plugin` - This is the sbt plugin, it depends on `compiler`, and automatically adds `api` to the `libraryDependencies` of any project that uses it.

## Developing

sbt doesn't allow sub projects to depend on each other as sbt plugins, so to test an sbt plugin, you need a separate project.  This project is `swagger-tester`.  To test your changes as you're developing the plugin, cd into this directory, and run sbt.  This project uses an sbt `ProjectRef` to the sbt plugin, which means you don't need to `publishLocal` the plugin after each change, just run `reload` in the sbt console, and it will pick your changes up.

## Testing

We're using the sbt scripted framework for testing, the tests can be found in `plugin/src/sbt-test`, and can be run by running `scripted` in the sbt console.
