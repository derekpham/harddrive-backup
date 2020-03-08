package exception.parser

class IllFormedPropertyValueException(property: String): Exception("Property $property is ill-formed")