
package form_data.yaml

import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc.{AnyContent, Request}
import de.zalando.play.controllers.PlayBodyParsing

import java.io.File

import de.zalando.play.controllers.PlayPathBindables

import PlayPathBindables.queryBindableFile


object FormDataParser {

    def postmultipartParseForm(request: Request[AnyContent]):Either[Seq[String],(String, BothPostYear, MultipartPostAvatar)] = {
        val contentType = request.contentType.getOrElse("application/x-www-form-urlencoded")
        def fromDataParts(data: Map[String, Seq[String]]):Either[Seq[String],(String, BothPostYear, MultipartPostAvatar)] = {
            val name: Either[String,String] = PlayBodyParsing.fromParameters[String]("form")("name", data)
            val year: Either[String,BothPostYear] = PlayBodyParsing.fromParameters[BothPostYear]("form")("year", data)
            val avatar: Either[String,MultipartPostAvatar] = PlayBodyParsing.fromParameters[MultipartPostAvatar]("form")("avatar", data)
            val all = Seq(name, year, avatar)
            val errors = all.filter(_.isLeft).flatMap(_.left.toSeq)
            if (errors.nonEmpty) Left(errors) else {
                Right((name.right.toOption.get, year.right.toOption.get, avatar.right.toOption.get))
            }
        }
        contentType.toLowerCase match {
            
            case "multipart/form-data" => request.body.asMultipartFormData.map { form =>
                fromDataParts(form.dataParts)
                // val file: Option[FilePart[TemporaryFile]] = form.file(fileName)
            }.getOrElse(Left(Seq("Could not find 'application/x-www-form-urlencoded' body")))
            
            case other =>
                Left(Seq("Content type " + other + " is not supported"))
        }
    }


    def postbothParseForm(request: Request[AnyContent]):Either[Seq[String],(String, BothPostYear, MultipartPostAvatar, MultipartPostAvatar)] = {
        val contentType = request.contentType.getOrElse("application/x-www-form-urlencoded")
        def fromDataParts(data: Map[String, Seq[String]]):Either[Seq[String],(String, BothPostYear, MultipartPostAvatar, MultipartPostAvatar)] = {
            val name: Either[String,String] = PlayBodyParsing.fromParameters[String]("form")("name", data)
            val year: Either[String,BothPostYear] = PlayBodyParsing.fromParameters[BothPostYear]("form")("year", data)
            val avatar: Either[String,MultipartPostAvatar] = PlayBodyParsing.fromParameters[MultipartPostAvatar]("form")("avatar", data)
            val ringtone: Either[String,MultipartPostAvatar] = PlayBodyParsing.fromParameters[MultipartPostAvatar]("form")("ringtone", data)
            val all = Seq(name, year, avatar, ringtone)
            val errors = all.filter(_.isLeft).flatMap(_.left.toSeq)
            if (errors.nonEmpty) Left(errors) else {
                Right((name.right.toOption.get, year.right.toOption.get, avatar.right.toOption.get, ringtone.right.toOption.get))
            }
        }
        contentType.toLowerCase match {
            case "application/x-www-form-urlencoded" => request.body.asFormUrlEncoded.map { form =>
                fromDataParts(form)
            }.getOrElse(Left(Seq("Could not find 'application/x-www-form-urlencoded' body")))
            
            case "multipart/form-data" => request.body.asMultipartFormData.map { form =>
                fromDataParts(form.dataParts)
                // val file: Option[FilePart[TemporaryFile]] = form.file(fileName)
            }.getOrElse(Left(Seq("Could not find 'application/x-www-form-urlencoded' body")))
            
            case other =>
                Left(Seq("Content type " + other + " is not supported"))
        }
    }


    def posturl_encodedParseForm(request: Request[AnyContent]):Either[Seq[String],(String, BothPostYear, File)] = {
        val contentType = request.contentType.getOrElse("application/x-www-form-urlencoded")
        def fromDataParts(data: Map[String, Seq[String]]):Either[Seq[String],(String, BothPostYear, File)] = {
            val name: Either[String,String] = PlayBodyParsing.fromParameters[String]("form")("name", data)
            val year: Either[String,BothPostYear] = PlayBodyParsing.fromParameters[BothPostYear]("form")("year", data)
            val avatar: Either[String,File] = PlayBodyParsing.fromParameters[File]("form")("avatar", data)
            val all = Seq(name, year, avatar)
            val errors = all.filter(_.isLeft).flatMap(_.left.toSeq)
            if (errors.nonEmpty) Left(errors) else {
                Right((name.right.toOption.get, year.right.toOption.get, avatar.right.toOption.get))
            }
        }
        contentType.toLowerCase match {
            case "application/x-www-form-urlencoded" => request.body.asFormUrlEncoded.map { form =>
                fromDataParts(form)
            }.getOrElse(Left(Seq("Could not find 'application/x-www-form-urlencoded' body")))
            
            case other =>
                Left(Seq("Content type " + other + " is not supported"))
        }
    }

}
