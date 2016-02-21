package string_formats

package object yaml {

    import de.zalando.play.controllers.Base64String
    import Base64String._
    import de.zalando.play.controllers.BinaryString
    import BinaryString._
    import org.joda.time.DateTime
    import org.joda.time.DateMidnight

    import de.zalando.play.controllers.PlayPathBindables

    import PlayPathBindables.queryBindableBase64String

    import PlayPathBindables.queryBindableDateTime

    import PlayPathBindables.queryBindableDateMidnight



    type GetBase64 = Option[Base64String]
    type GetPetId = BinaryString
    type GetDate_time = Option[DateTime]
    type GetDate = Option[DateMidnight]
    type GetResponses200 = Null



    implicit val bindable_OptionBase64StringQuery = PlayPathBindables.createOptionQueryBindable[Base64String]
    
    implicit val bindable_OptionDateTimeQuery = PlayPathBindables.createOptionQueryBindable[DateTime]
    
    implicit val bindable_OptionDateMidnightQuery = PlayPathBindables.createOptionQueryBindable[DateMidnight]
    

}
