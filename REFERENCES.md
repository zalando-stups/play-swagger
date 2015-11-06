## Swagger Reference `$ref`

Within Swagger, the Reference Object is a JSON Reference that uses a JSON Pointer as its string value.  The values that a Reference Object points to are canonically dereferenced.

### JSON Reference
A JSON Object containing a `$ref` attribute with a string value.  The "$ref" string value contains a URI, which identifies the location of the JSON value being referenced.  The JSON Reference [specification](https://tools.ietf.org/html/draft-pbryan-zyp-json-ref-03) in itself does not require the string value to be a JSON Pointer, but Swagger definitions limit valid JSON References to those that are.  Informally spoken this means that the Swagger Reference Object's string value contains a URI with a URI fragment part containing a JSON Pointer or a URI without a fragment part, that identifies some rooted JSON document value.  Note that the URI may be relative.

### JSON Pointer
A JSON Pointer is a string syntax for identifying a specific value within a JSON document.  A JSON Pointer is a Unicode string containing a sequence of zero or more reference tokens, each prefixed by a `/` character.  Token values can be strings or integers, with strings identifying (possibly nested) JSON Object fields and integers identifying JSON Array indices.  The characters `/` and `~` are escaped, see the JSON Pointer [specification](http://tools.ietf.org/html/rfc6901) for more information.

### Canonical dereferencing
A Swagger implemenation should canonically dereference all resolved JSON Reference URI values.  Informally spoken this means that relative URI's should be made absolute before values are dereferenced.  See the JSON Schema [specification](http://json-schema.org/latest/json-schema-core.html#anchor27) paragraph 7.2.3 for more information on the difference about canonical dereferencing and inline dereferencing.

### Examples

```
$ref: 'pet.yaml'                            // relative rooted reference
$ref: '#/definitions/Pet'                   // relative fragment reference
$ref: 'definitions.yaml#/Pet'               // relative file fragment reference

$ref: 'file:///pet.yaml'                    // absolute rooted file reference
$ref: 'file:///definitions.yaml#/Pet'       // absolute file fragment reference
$ref: 'http://some.host/definitions#/Pet'   // absolute remote fragment reference
```


## Methods on Typename or JSON Reference

- `implicit def uriToReference(uri: URI):  Reference`  creates a JSON Reference from given uri.
- `implicit def stringToReference(string: String):  Reference`  creates a JSON Reference from given URI string.
- `def simple:  String`  the last JSON Pointer token
- `def parent: Reference` a new JSON Reference with the last JSON Pointer token dropped (if it exists)
- `def +:(token: String): Reference` a new JSON Reference with the token added to the JSON Pointer in postfix position
- `def :+(token: String): Reference` a new JSON Reference with the token added to the JSON Pointer in prefix posistion






