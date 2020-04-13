package com.derek.backuptool

import com.google.inject.Guice

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val injector = Guice.createInjector(BackupToolModule())
        val backupToolRunner = injector.getInstance(BackupToolRunner::class.java)
        backupToolRunner.run(args)
    }
}
