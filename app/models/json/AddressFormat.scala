package models.json

import play.api.libs.json._
import models._
import play.api.libs.functional.syntax._
import defines.EntityType
import defines.EnumUtils._


object AddressFormat {
  import AddressF._
  import Entity._
  import Isdiah._

  implicit val addressWrites = new Writes[AddressF] {
    def writes(d: AddressF): JsValue = {
      Json.obj(
        ID -> d.id,
        TYPE -> d.isA,
        DATA -> Json.obj(
          ADDRESS_NAME -> d.name,
          CONTACT_PERSON -> d.contactPerson,
          STREET_ADDRESS -> d.streetAddress,
          CITY -> d.city,
          REGION -> d.region,
          POSTAL_CODE -> d.postalCode,
          COUNTRY_CODE -> d.countryCode,
          EMAIL -> d.email,
          TELEPHONE -> d.telephone,
          FAX -> d.fax,
          URL -> d.url
        )
      )
    }
  }

  implicit val addressReads: Reads[AddressF] = (
    (__ \ TYPE).read[EntityType.Value](equalsReads(EntityType.Address)) andKeep
    (__ \ ID).readNullable[String] and
    (__ \ DATA \ ADDRESS_NAME).readNullable[String] and
    (__ \ DATA \ CONTACT_PERSON).readNullable[String] and
    (__ \ DATA \ STREET_ADDRESS).readNullable[String] and
    (__ \ DATA \ CITY).readNullable[String] and
    (__ \ DATA \ REGION).readNullable[String] and
    (__ \ DATA \ POSTAL_CODE).readNullable[String] and
    (__ \ DATA \ COUNTRY_CODE).readNullable[String] and
    (__ \ DATA \ EMAIL).readNullable[String] and
    (
      (__ \ DATA \ TELEPHONE).readNullable[List[String]] |
        Reads.list((__ \ DATA \ TELEPHONE).read[String]).map(Option(_))) and
    (__ \ DATA \ FAX).readNullable[String] and
    (__ \ DATA \ URL).readNullable[String]
  )(AddressF.apply _)

  implicit val addressFormat: Format[AddressF] = Format(addressReads,addressWrites)

}
