package configparser

import exception.parser.ValidatorFailureException
import model.BackupConfig
import org.junit.Test

class ConfigValidatorTest {

    private val configValidator = ConfigValidator()

    @Test(expected = ValidatorFailureException::class)
    fun testBasePathIsNotAbsolute() {
        val backupConfig = BackupConfig(
            backupBasePath = "home/boom",
            originalToBackupDir = emptyMap()
        )

        configValidator.validate(backupConfig)
    }

    @Test(expected = ValidatorFailureException::class)
    fun testBackupPathsAreNotRelative() {
        val backupConfig = BackupConfig(
            backupBasePath = "/home/boom",
            originalToBackupDir = mapOf(
                "/foo/bar1" to "/foo",
                "/foo/bar" to "foo",
                "/foo/bar2" to "/foo2"
            )
        )

        configValidator.validate(backupConfig)
    }

    @Test(expected = ValidatorFailureException::class)
    fun testOriginalPathsAreNotAbsolute() {
        val backupConfig = BackupConfig(
            backupBasePath = "/home/boom",
            originalToBackupDir = mapOf(
                "foo/bar1" to "foo",
                "/foo/bar" to "foo",
                "/foo/bar2" to "foo2"
            )
        )

        configValidator.validate(backupConfig)
    }

    @Test
    fun testValidBackupConfig() {
        val backupConfig = BackupConfig(
            backupBasePath = "/foo/bar",
            originalToBackupDir = mapOf(
                "/foo/bar1" to "bar",
                "/foo/bar2" to "bar2"
            )
        )

        configValidator.validate(backupConfig)
    }
}