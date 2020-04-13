package com.derek.backuptool

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupToolRunner @Inject constructor() {

    fun run(args: Array<String>) {
        println("hello, world")
    }
}