package com.derek.backuptool.exception.parser

import java.lang.Exception

class IllFormedConfigException : Exception {
    constructor(rawConfig: String) : super(rawConfig)
    constructor(rawConfig: String, exception: Exception) : super("Ill formed config: $rawConfig", exception)
}
