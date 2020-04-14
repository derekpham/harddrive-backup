package com.derek.backuptool.service

import com.derek.backuptool.dto.BackupToolConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PrimaryBackupServiceIntegrationTest {

    @BeforeEach
    fun init() {
        teardown()
        createTestDirectory("/tmp/original")
        createFileWithContent("/tmp/original/foo.txt", "foo")
        createFileWithContent("/tmp/original/bar.txt", "bar")
        createTestDirectory("/tmp/original/.hidden")
        createFileWithContent("/tmp/original/.hidden/foo.txt", "foo")
        createTestDirectory("/tmp/original/.hidden1")
        createTestDirectory("/tmp/original/yo")
        createFileWithContent("/tmp/original/yo/hey.jpg", "does it matter?")
        createFileWithContent("/tmp/original/.hello.foo", "foo")

        createTestDirectory("/tmp/original1/.hidden2")
        createFileWithContent("/tmp/original1/foo.txt", "yo")
        createFileWithContent("/tmp/original1/.hidden2/foo.txt", "foo")

        createTestDirectory("/tmp/backup")
    }

    @AfterEach
    fun teardown() {
        forceRemoveDirectory("/tmp/original")
        forceRemoveDirectory("/tmp/original1")
        forceRemoveDirectory("/tmp/backup")
    }

    private val primaryBackupService = PrimaryBackupService()

    @Test
    fun test() {
        val backupToolConfig = BackupToolConfig(
            originalPaths = listOf("/tmp/original", "/tmp/original1"),
            primaryBackupDest = "/tmp/backup",
            exclude = listOf(".*"),
            dirsToTarInSecondaryBackup = emptyList()
        )
        primaryBackupService.performService(backupToolConfig)

        assertThatDirectoryExists("/tmp/backup/original")
        assertThatFileHasContent("/tmp/backup/original/foo.txt", "foo")
        assertThatFileHasContent("/tmp/backup/original/bar.txt", "bar")
        assertThatPathDoesNotExist("/tmp/backup/original/.hidden")
        assertThatPathDoesNotExist("/tmp/backup/original/.hidden1")
        assertThatDirectoryExists("/tmp/backup/original/yo")
        assertThatFileHasContent("/tmp/backup/original/yo/hey.jpg", "does it matter?")
        assertThatPathDoesNotExist("/tmp/backup/original/.hello.foo")
        assertThatPathDoesNotExist("/tmp/backup/original1/.hidden2")
        assertThatFileHasContent("/tmp/backup/original1/foo.txt", "yo")
    }

    private fun assertThatPathDoesNotExist(path: String) {
        val file = File(path)
        assertThat(file.exists()).describedAs("Path $path does not exist.").isFalse()
    }

    private fun assertThatDirectoryExists(path: String) {
        val file = File(path)
        assertThat(file.exists()).describedAs("Path $path exists.").isTrue()
        assertThat(file.isDirectory).describedAs("$path is a directory.").isTrue()
    }

    private fun assertThatFileHasContent(path: String, content: String) {
        val file = File(path)
        assertThat(file.exists()).describedAs("Path $path exists.").isTrue()
        assertThat(file.isFile).describedAs("$path is a directory.").isTrue()
        assertThat(file.readText(Charsets.UTF_8)).describedAs("Content of file matches string.")
            .isEqualTo(content)
    }

    private fun createTestDirectory(dir: String) {
        File(dir).mkdirs()
    }

    private fun createFileWithContent(dir: String, content: String) {
        File(dir).writeText(content)
    }

    private fun forceRemoveDirectory(dir: String) {
        File(dir).deleteRecursively()
    }
}
