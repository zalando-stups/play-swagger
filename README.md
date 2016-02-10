# Play-Swagger

[![Build Status](https://travis-ci.org/zalando/play-swagger.svg)](https://travis-ci.org/zalando/play-swagger)
[![Codecov](https://img.shields.io/codecov/c/github/zalando/play-swagger.svg)](http://codecov.io/github/zalando/play-swagger)
[![Gitter Chat](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/zalando/play-swagger?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Status

The status of this software is beta, an end-to-end functional release intented to demonstrate the possibility to generate from a swagger specification:

- Play route files
- Generators of random test data
- Wrappers for play route files to convert semantics from http-related to domain-related
- Skeletons for the domain-driven controller implementation
- Unit tests for invalid and valid parameter sets

We benefit from community feedback, all comments are welcome!

# Play-Swagger Tutorial

This tutorial is using the [play-swagger-service](http://www.typesafe.com/activator/template/play-swagger-service) activator template.

```bash
$ activator new playground play-swagger-service
```

## Welcome to Play-Swagger

Congratulations, you just created a new Play-Swagger application!

The [Play Framework](http://www.playframework.com/) with the [Play-Swagger](https://github.com/zalando/play-Swagger/) plugin make it easy to build restful web services from a Swagger API specification as the single source of truth. Play is based on a lightweight, stateless, web-friendly architecture. Built on [Akka](http://akka.io), Play provides predictable and minimal resource consumption for highly-scalable applications.  The Play-Swagger plugin takes Swagger API definitions and treats them as the single source of truth of your rest services.

Play-Swagger supports round-trip regeneration and compilation of:

- Play routes definitions (managed).
- Swagger domain model definitions and parameters onto Scala case classes (managed).
- Swagger domain model constraints onto Play validations (managed).
- Generators for random test data generation of parameter values (managed).
- Unit tests for validating your service at the API boundary (managed).
- Swagger path definitions onto skeletons for Play controller implementations (unmanaged).

Managed in the list above means that the code is managed by activator or sbt. The code is not under controlled and altered by you, the programmer of the rest service. The plugin takes your Swagger API definition as the single source of truth and will regenerate these code parts in a consistent manner.  You'll instead be focusing on implementing the service business logic in an (unmanaged) Play controller class which is generated once, subsequent regenerations keep the code that you added, either by commenting out the parts that are not valid anymore, or by adding parts that are needed because you made a change to the API.

## Run Your Application

Before we go any further, let's run the application.

- Open a shell and `cd` into your service project directory.
- Start `sbt` and `run` the service.
- View the running application at [http://localhost:9000](http://localhost:9000).

The service template comes with the Swagger UI frontend included, run statically from the within Play, which provides a sandbox for your service. The template is configured with a template Swagger API definition called `echo.yaml` and located in the `conf` directory of the Play application. The `echo.yaml` definition provides a minimal API definition shown below. This definition contains two end points, the `/` path which accept the `GET` and `POST` methods and the `/test-path/{id}` that accepts the `GET` method. Requesting `GET /` from the UI will echo the request with an empty body, while requesting `POST /` which takes two query parameters `name` and `year` will echo given parameter values back in the body of a `application/json` response. As an additional example, requesting `GET /test-path/{id}` with a given `id` will echo that `id` back as a json string value in the body of the response. Try it out for yourself, click the [default](http://localhost:9000/) button to expand the API definition in Swagger UI.

```yaml
basePath: /echo
paths:
  /:
    get:
      responses:
        200:
          schema:
            type: string
    post:
      responses:
        200:
          schema:
            type: object
            properties:
              name:
                type: string
              year:
                type: string
      parameters:
        - name: name
          in: query
          type: string
        - name: year
          in: query
          type: string
  /test-path/{id}:
    get:
      parameters:
        - name: id
          in: path
          type: string
          required: true
      responses:
        200:
          schema:
            type: string
```

# Play Routes Integration

As a Play application developer you are used to define your end-points in the `conf/routes` file. Not so with the Play-Swagger plugin! Swagger API specifications already define endpoints as `path` definitions as seen in the example above, so why do the work twice, right? Instead the Play-Swagger plugin requires you to link your API definition in the routes file ones, bringing all Swagger API defined end-points available as children of one single path context location, generating Play route definitions from them as shown below.

```
->          /echo/          echo.yaml.Routes
```

Note that the `conf/routes` file provided by this activator template also contains a couple of aditional `GET` mappings in order to be able to run the Swagger UI sandbox against the project contained API definition and the service that is generated from it.

## Swagger Domain Definitions

Scala domain model definitions are generated for all data types defined as Swagger parameters in an api specification.  Swagger parameters can be of path, query, header, form or body types and consist of either primitive data types or more complex types composed from objects and arrays with primitives as leaves.  Both primitive types and complex types are mapped to scala.  As an example, lets look at the Swagger api specification file `simple.petstore.api.yaml` that defines the api of a simple pet store.  It contains a model definition for a pet.

```yaml
definitions:
  pet:
    required:
      - id
      - name
    properties:
      id:
        type: integer
        format: int64
      name:
        type: string
      tag:
        type: string
```

This definition consists of an object `pet` containing the required properties `id` and `name` and the optional property `tag` the Swagger primitive types of these properties are a 64 bit `integar` and twice a `string` successively.  The play Swagger plugin will map this definition on to a generated scala model.

```scala
package simple.petstore.api.yaml
object definitions {
    type PetTag = Option[String]
    case class Pet(id: Long, name: String, tag: PetTag)
}
```

This generated model contains a type definition `PetTag` that declares a type alias for the optional `tag` property and a `Pet` case class with the properties as named in the Swagger api definition and mapped on the subsequent scala primitive or declared types.  The case class and type alias are generated in an object `definitions` taken from the Swagger API specification root property with the same name.  This object itself is contained in the package `simple.petstore.api.yaml`, which is taken from the API filename.

Note that models are generated within a play application as _unmanaged_em> code in the target folder.  Generated model code is not intended to be altered, we should instead look upon the Swagger definition as the single source of truth, and indeed, as the source code that defines our model.  The Swagger specification file of our API is in that sense part of the codebase.  Even though the generated `Pet` case class is not managed by us but by the plugin instead, it can of course, after being imported, be used in our application codebase.

```scala
import simple.petstore.api.yaml.definitions._

val pet = Pet(0L, "Tucker", Some("Greyhound"))
```

## Primitive Types

Swagger version 2.0 allows for primitive data types based on the types defined by [JSON-Schema](http://json-schema.org/latest/json-schema-core.html#anchor8).  When generated as scala the following mapping applies.

| Common Name | Swagger Type | Swagger Format | Scala Type     |
| -----------:| ------------:| --------------:| --------------:|
| integer     | integer      | int32          | scala.Int      |
| long        | integer      | int64          | scala.Long     |
| float       | number       | float          | scala.Float    |
| double      | number       | double         | scala.Double   |
| boolean     | boolean      |                | scala.Boolean  |
| string      | string       |                | scala.String   |
| byte        | string       | byte           | scala.Byte     |
| binary      | string       | binary         | unsupported    |
| date        | string       | date           | java.util.Date |
| datetime    | string       | date-time      | java.util.Date |
| password    | string       | password       | scala.String   |
| file        | file         |                | java.io.File   |

## Complex Types

Complex types are made up of primitive object, or nested objects.

### Objects

Complex object types are defined in Swagger model definitions as either objects or arrays.

Objects are, again, based on the [JSON-Schema](http://json-schema.org/latest/json-schema-core.html#anchor8) specification and defined as Swagger [Schema Objects](https://github.com/Swagger-api/Swagger-spec/blob/master/versions/2.0.md#schema-object) for parameter definitions of `type: "object"`.  For example, given a Swagger api definition file `api.yaml` containing a model that defines a `person` as an object with the properties `name` and `age` of the primitive types `string` and `integer` subsequently, this object will be mapped on a scala case class, generated in a scala object (namespace) with the same name as the root Swagger property in which it occurs.  I.e. `definitions` and in a package with the same name as the Swagger definition file in which the model is defined, that is, `api`

```yaml
definitions:
  person:
    type: object
    required:
      - name
      - age
    properties:
      name:
        type: string
      age:
        type: integer
        format: int32
```

Is generated into:

```scala
package api.yaml
object definitions {
    case class Person(name: String, age: Int)
}
```

### Nested Objects

Nested objects are generated adjourned but referenced hierarchically. E.g.

```yaml
definitions:
  parent:
    type: object
    required:
      - child
    properties:
      child:
        type: object
        required:
          - name
        properties:
          name:
            type: string
```

Is generated into:

```scala
package api.yaml
object definitions {
  case class Parent(child: Child)
  case class Child(name: String)
}
```

### Optionality

Swagger, by default, defines object properties to be optional, which can be overridden by providing a list of `required` object properties as already used in the examples above.  Optional properties are mapped upon scala's `Option` type for which a type alias is generated for each property that is optional. E.g.

```yaml
definitions:
  product:
    required:
      - name
    properties:
      name:
        type: string
      tag:
        type: string
```

Which is generated as:

```scala
package api.yaml
object definitions {
    type ProductTag = Option[String]
    case class Product(name: String, tag: ProductTag)
}
```

As objects can be nested, so can be object property optionality.  To facilitate for nested optionality, we generate a nested scala `Option` type alias. E.g.

```yaml
definitions:
  Basic:
    properties:
      optional:
        type: object
        properties:
          nested:
            type: string
```

Which is generated as:

```scala
object definitions {
  type BasicOptional = Option[BasicOptionalOpt]
  type BasicOptionalNested = Option[String]
  case class Basic(optional: BasicOptional)
  case class BasicOptionalOpt(nested: BasicOptionalNested)
}
```

### Extension

Objects can extend other objects via employment of Swaggers' `allOff` property.  In the example below the `ExtendedErrorModel` inherits _all of_ the properties of the `ErrorModel` which it refers to, that is, the properties `message` and `code` and _extends_ this model with the property `rootCause`. Swagger object extension is mapped by duplicating inherited properties in the object that extends. E.g.

```yaml
definitions:
  ErrorModel:
    type: object
    required:
    - message
    - code
    properties:
      message:
        type: string
      code:
        type: integer
  ExtendedErrorModel:
    allOf:
    - $ref: '#/definitions/ErrorModel'
    - type: object
      required:
      - rootCause
      properties:
        rootCause:
          type: string
```

Which is generated as:

```scala
package api.yaml
object definitions {
  case class ErrorModel(message: String, code: Int)
  case class ExtendedErrorModel(
        message: String,
        code: Int,
        rootCause: String
  )
}
```

### Polymorphism

Polymorphic object definitions are possible through employment of the Swagger `discriminator` property.  In the example definition below an abstract `Pet` defines that what concrete `Cat` and `Dog`s have in common.  As Swagger object models are defining data, a discriminator property is required to distinguish concrete cat and dog instances as they are serialised to and from the api.  The discriminator property works in that sense the same way as a discriminator column works in ORM frameworks when mapping a class hierarchy onto a single table.  It simply contains a value that maps onto one of the concrete types, for example `petType: "Cat"` or `petType: "Dog"`.

```
definitions:
  Pet:
    discriminator: petType
    properties:
      name:
        type: string
      petType:
        type: string
    required:
    - name
    - petType
  Cat:
    allOf:
    - $ref: '#/definitions/Pet'
    - properties:
        huntingSkill:
          type: string
          default: lazy
          enum:
          - clueless
          - lazy
          - adventurous
          - aggressive
      required:
      - huntingSkill
  Dog:
    allOf:
    - $ref: '#/definitions/Pet'
    - properties:
        packSize:
          type: integer
          format: int32
      required:
      - packSize
```

Which is generated as:

```scala
package api.yaml
object definitions {
  trait IPet {
    def name: String
    def petType: String
  }
  case class Cat(
        name: String,
        petType: String,
        huntingSkill: String
  ) extends IPet
  case class Dog(name: String,
        petType: String,
        packSize: Int
  ) extends IPet
}
```

### Additional Properties

Swagger's model language allows objects additional properties to be loosely defined employing the `additionalProperties`code> annotation in order to model dictionaries.  These dictionaries are mapped to scala's `Map`code> type for which a type alias is generated following the same (by now) well known pattern as for optional properties, with the map's key parameter type being a scala `String`code>.

A Swagger additional property definition takes as its type property the element type of the dictionary which can be of primitive or complex type and which is mapped on scala as the map's value parameter type.  Swagger allows for one `additionalProperties`code> annotation per object definition so we can generate this scala parameter with the static name `additionalProperties`code>.

In the following example we define a Swagger model object definition `KeyedArray`code> that uses the `additionalProperties` annotation to provide the object with a set of key value mappings from string to array. E.g.

```yaml
definitions:
  KeyedArrays:
    type: object
    additionalProperties:
      type: array
      items:
        type: integer
```

Which is generated as:

```scala
package api.yaml
object definitions {
    import scala.collection.immutable.Map
    import scala.collection.Seq

    type KeyedArraysAdditionalProperties =
        Map[String, KeyedArraysAdditionalPropertiesArr]
    type KeyedArraysAdditionalPropertiesArr =
        Seq[Int]
    case class KeyedArrays(
        additionalProperties: KeyedArraysAdditionalProperties
    )
}
```

## Arrays

Swagger's `array` is used to define properties that hold sets or lists of model values, possibly of a primitive type, but complex element types are also allowed.  We map Swagger array types on scala's `Seq` type, parameterised for the element type that it contains.

For example, in the snippet below, an `Activity`> object definition is referred as item element in the `messages` property of `type: array` of the containing object definition `Example`  A scala type alias will be generated for the array type, just as we've seen before with for optional properties, after which the array containing property can be generated within the case class as being of this alias type. E.g. in the Swagger definition and code

```yaml
definitions:
  Activity:
    type: object
    required:
    - actions
    properties:
      actions:
        type: string
  Example:
    type: object
    required:
    - messages
    properties:
      messages:
        type: array
        items:
          $ref: '#/definitions/Activity'
```

Which is generated as:

```scala
package api.yaml
object definitions {
  type ExampleMessagesArr =
        scala.collection.Seq[Activity]
  case class Activity(actions: ActivityActions)
  case class Example(messages: ExampleMessagesArr)
}
```

### Nested Arrays

Array definition types can be nested and possibly optional. The following (contrived) snippet depicts the generated scala code when both definition types are employed in a somewhat non-useful manner.  The intend of this example is to show that the case class definitions are rather concisely generated, even though a stack of type aliases is needed to make sure that we still refer in scala code to an aptly named Swagger definition, especially in conjunction with the object properties being optional.  Next to its benefits, type safety against `null` pointers does have an associated cost as well.

```yaml
definitions:
  Activity:
    type: object
    properties:
      actions:
        type: string
  Example:
    type: object
    properties:
      messages:
        type: array
        items:
          type: array
          items:
            $ref: '#/definitions/Activity'
      nested:
        type: array
        items:
          type: array
          items:
            type: array
            items:
              type: array
              items:
                type: string
```

Which is generated as:

```scala
package api.yaml
object definitions {
  type ActivityActions =
        Option[String]
  type ExampleNested =
        Option[ExampleNestedOpt]
  type ExampleNestedOpt =
        scala.collection.Seq[ExampleNestedOptArr]
  type ExampleNestedOptArr =
        scala.collection.Seq[ExampleNestedOptArrArr]
  type ExampleNestedOptArrArr =
        scala.collection.Seq[ActivityActions]
  type ExampleMessages =
        Option[ExampleMessagesOpt]
  type ExampleMessagesOpt =
        scala.collection.Seq[ExampleMessagesOptArr]
  type ExampleMessagesOptArr =
        scala.collection.Seq[Activity]

  case class Activity(
        actions: ActivityActions
  )
  case class Example(
        messages: ExampleMessages,
        nested: ExampleNested
  )
}
```

## Swagger Validations

Swagger API definitions allow for constraints to be put on parameter types. We have seen already the `required` constraint that is used to mark a parameter or specific field within a domain definition to be required upon input. Additional constraints, as defined by the <a href="https://github.com/swagger-api/swagger-spec/blob/master/versions/2.0.md#parameterObject">Parameter Object</a>, can be added to your API definition.  The Play-Swagger plugin wil generate validations for these parameter constraints and make sure that your controller methods are only called if the input of your service complies to those constraints.  In the example below we'll take our original `echo.yaml` API definition which contains a `year` parameter of type `string`, both in the query parameter and in the response.  Let's change that type to integer and add a `minimum` and `maximum` constraint that allows only for years to be in between 2000 and 2100, also lets make the parameter required.

```yaml
...
post:
  responses:
    200:
      description: Echo POST
      schema:
        type: object
        properties:
          name:
            type: string
          year:
            type: integer
  parameters:
    - name: name
      in: query
      type: string
    - name: year
      in: query
      type: integer
      required: true
      minimum: 2000
      maximum: 2100
...
```

As we just changed the parameter type, refreshing Swagger UI will, next to generating validations for that parameter type, force a regeneration of the model, consistent with the validation. That's nice, but note that it still breaks the current implementation of the controller class, as the implementation of the `postAction` expects `year` to be of type `String`.

![Validation screenshot](/docs/validations-01.png)

Let's change the implementation. The second parameter `year` is now not longer (normalised as a type from the `name` parameter) type `PostName` but of type `PostYear` which is not optional any longer. We change the type in the input, and wrap it in a `Some` when responding as the parameter still is optional in its response.

```scala
val post = postAction {
    input: (PostName, PostYear) =>
    val (name, year) = input
    Success(
        Some(PostResponses200Opt(name, Some(year)))
    )
}
```

Refreshing Swagger UI and trying out a couple of integer values for `year` shows that the service now excepts value within the range `[2000..2100]`, but returns an descriptive error when outside. I.e.

```json
[
  {
    "messages": [
      "error.max"
    ],
    "args": [
      2100
    ]
  }
]
```

## Test Generators

Having an api definition as the single source of truth in your codebase, with formal type specification of the in- and output values, including their constraints, provides for a powerful feature when it comes to testing.  The play swagger plugin automates the creation of test data generators which can drive property checks directly from the API specification. Play-Swagger generates data generators and unit tests directly from your Swagger API specification.

Property based testing using generator-driven property checks is an cool way to test the validity of your application according to the rules, or properties, that apply to your application.  Properties in this sense are high-level specifications that should always hold for a range of data values.  The idea is to generate a range of data values for your data types and let (also generated) tests assert that the properties of these data types hold.  As a swagger api definition contains formal type definitions _and_ constraints for all data values, and as the play swagger plugin maps these types on _unmanaged_ Scala source code that represent the data types, it is also possible to map these api definitions on test data generators that provide a range of data values for these types.  The plugin does exactly that, it creates unmanaged test data generators and unit tests that assert whether your application still complies to your specification, and it does so in a single source of truth manner, taking the swagger api definition as the source.

We employ the ScalaTest [property-based testing](http://www.scalatest.org/user_guide/property_based_testing) functionality as the framework to generate the data values and map the data types of our api definition on the test data generators that are created by the plugin.  ScalaTest provides `org.scalacheck.Gen` and `org.scalacheck.Arbitrary` objects with utility methods that help generating a range of (possibly arbitrary) data values for common scala types and primitives.  The Play-Swagger plugin uses this methods to create test data generators specific for the data types of our api definition, and when necessary composing generators from primitive types into generators for complex types, such that we end up with a set of generators that provide test data for your complete api.

As an example, lets take the api definition for the simple pet store, trimmed down to the parts defining parameter types, so omitting any non-data definitions and error definitions for brevity.

```yaml
paths:
  /pets:
    get:
      parameters:
        - name: limit
          in: query
          required: false
          type: integer
          format: int32
    post:
      parameters:
        - name: pet
          in: body
          required: true
          schema:
            $ref: '#/definitions/newPet'
  /pets/{id}:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: integer
          format: int64
    delete:
      parameters:
        - name: id
          in: path
          required: true
          type: integer
          format: int64
definitions:
  pet:
    required:
      - id
      - name
    properties:
      id:
        type: integer
        format: int64
      name:
        type: string
      tag:
        type: string
  newPet:
      required:
        - name
      properties:
        id:
          type: integer
          format: int64
        name:
          type: string
        tag:
          type: string
```

The `get` method on path `/pets` takes an optional `limit` parameter of common type `integer` while the `post` method takes a `newPet` body parameter comprising of the primitive attributes `id`, `name` and `tag` subsequently of common types `long` and `string` twice.  Of these only the `name` attribute is mandatory.  The `get` method on the path `/pets/{id}` takes the path parameter `id` of common type `long` and returns an array of `pet`s consisting of the same attributes and primitive types as a `newPet` but this time with both `name` and `id` being mandatory.  This specification maps to the following unmanaged scala domain model code.

This generates the following model and path definitions:

```scala
package simple.petstore.api.yaml
object definitions {

  type NewPetTag = Option[String]
  type NewPetId = Option[Long]

  case class Pet(
        id: Long,
        name: String,
        tag: NewPetTag
  )
  case class NewPet(
        name: String,
        id: NewPetId,
        tag: NewPetTag
  )
}
object paths {
  import definitions._
  type PetsGetLimit = Option[Int]
}
```

We want to have test data generators that generate an arbitrary range of values for the unmanaged model code shown above, composed from primitive, and sometimes optional, data definitions.  The play swagger plugin does so by generating two scala objects, one for the swagger api definition and one for the api path parts.  Each object contains generator factory methods for the defined data types prefixed by `create` that returns a generator function.  Generator functions take a given integer count and return a generated amount of test data for the data type it was created for.

Data types are composed from primitive types, scala optional types, and possibly more complex types.  Test data values for the primitive types are generated arbitrarily employing the ScalaCheck `org.scalacheck.Arbitrary.arbitrary[T]` method, the type parameter replaced with scala's primitive type on which swagger common type is mapped.

Starting with primitive leaf data values, the `pet` parameter's attribute `id` of common type `long` is arbitrarily generated from a `scala.Long` in the code shown below.  Note that the `id` attribute is optional though for the `newPet` definition, and as with the generated model we created a `NewPetIdGenerator` value that takes an arbitrarily generated `scala.Long` id value and generates an option value from it, employing the ScalaCheck `org.scalacheck.Gen.option[T]`.  This generator will generate test data values comprising of `None` and `Some` arbitrarily id value.  It's probably best to let the unmanaged scala generator code speak for itself, note how it composes according to the same structure as the unmanaged scala model code.

```scala
package simple.petstore.api.yaml
import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

object definitionsGenerator {
    import definitions._

    def createNewPetTagGenerator =
        _generate(NewPetTagGenerator)
    def createNewPetIdGenerator =
        _generate(NewPetIdGenerator)
    def createPetGenerator =
        _generate(PetGenerator)
    def createNewPetGenerator =
        _generate(NewPetGenerator)

    val NewPetTagGenerator =
        Gen.option(arbitrary[String])
    val NewPetIdGenerator =
        Gen.option(arbitrary[Long])

    val PetGenerator =
        for {
            id <- arbitrary[Long]
            name <- arbitrary[String]
            tag <- NewPetTagGenerator
        } yield Pet(id, name, tag)

    val NewPetGenerator =
        for {
            name <- arbitrary[String]
            id <- NewPetIdGenerator
            tag <- NewPetTagGenerator
        } yield NewPet(name, id, tag)

    def _generate[T](gen: Gen[T]) =
        (count: Int) => for {
            i <- 1 to count
        } yield gen.sample
}

object pathsGenerator {
    import definitions._

    def createPetsGetLimitGenerator =
        _generate(PetsGetLimitGenerator)

    val PetsGetLimitGenerator =
        Gen.option(arbitrary[Int])

    def _generate[T](gen: Gen[T]) =
        (count: Int) => for {
            i <- 1 to count
        } yield gen.sample
}
```

A `PetGenerator` and `NewPetGenerator` is created and implemented by the plugin as a for comprehension that generates data values for each attribute, yielding an instance of a test pet.  Other generators follow the same pattern but delegate to different child generators if necessary.  From this we acquire a set of test data generators to implement our property based testing.

Running the test is as simple as running a test set from sbt. Just type `test` from your `sbt` prompt.

# Building Play-Swagger Plugin

To build a plugin, do the following:

- Clone the repository to your local filesystem
- Run ```sbt +publishLocal``` in the play-swagger directory, this will publish the plugin into your local ivy repository

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

- Link the newly added specification to the central Play routes by adding a new line to the ```routes``` file, for example:
  ```-> 	      /api                  petstore.Routes```. The prefix should correspond to the basePath
  set in the specification

## Plugin Project Structure

There are 3 sub projects:

* `api` - This is the project that's automatically added to the runtime classpath of any projects that use this plugin.
* `compiler` - This is a stand alone compiler that the plugin uses, it doesn't depend on sbt, and could theoretically be used to build support for other build systems.
* `plugin` - This is the sbt plugin, it depends on `compiler`, and automatically adds `api` to the `libraryDependencies` of any project that uses it.

## Plugin Developing

sbt doesn't allow sub projects to depend on each other as sbt plugins, so to test an sbt plugin, you need a separate project.  This project is `swagger-tester`.  To test your changes as you're developing the plugin, cd into this directory, and run sbt.  This project uses an sbt `ProjectRef` to the sbt plugin, which means you don't need to `publishLocal` the plugin after each change, just run `reload` in the sbt console, and it will pick your changes up.

## Plugin Testing

We're using the sbt scripted framework for testing, the tests can be found in `plugin/src/sbt-test`, and can be run by running `scripted` in the sbt console.
