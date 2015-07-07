package com.example.play.swagger.compiler

import java.io.File
import java.nio.file.Files

/**
 * The compiler
 */
object SwaggerCompiler {

  def compile(task: SwaggerCompilationTask, outputDir: File): SwaggerCompilationResult = {

    // As an example, generate a routes file
    outputDir.mkdirs()
    val routesFile = new File(outputDir, task.packageName + ".router.routes")
    Files.write(routesFile.toPath, """GET /example com.example.play.swagger.api.ExampleController.index""".getBytes("utf-8"))
    SwaggerCompilationResult(routesFile, Nil)
  }

}

/**
 * This should capture everything about the compilation of a single file.
 *
 * Incremental compilation will hash the toString of this file and use it as part of its algorithm to determine if
 * anything has changed - this is why, for example, the version is included, so that when you upgrade play swagger, it
 * will trigger a recompile.
 */
case class SwaggerCompilationTask(definitionFile: File, packageName: String, someOtherOption: String, playSwaggerVersion: String)

/**
 * The result of a compilation
 */
case class SwaggerCompilationResult(routesFile: File, modelFiles: Seq[File])