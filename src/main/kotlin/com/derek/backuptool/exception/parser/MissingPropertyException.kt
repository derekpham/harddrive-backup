package com.derek.backuptool.exception.parser

class MissingPropertyException(property: String) : Exception("Config file missing property: $property")
