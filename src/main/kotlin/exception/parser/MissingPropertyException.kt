package exception.parser

class MissingPropertyException(property: String) : Exception("Config file missing property: $property")
