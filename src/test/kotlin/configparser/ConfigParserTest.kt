package configparser

import exception.parser.IllFormedConfigException
import exception.parser.IllFormedPropertyValueException
import exception.parser.MissingPropertyException
import model.BackupConfig
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail

class ConfigParserTest {

    private val configParser = ConfigParser()

    @Test
    fun testParseGarbage() {
        val data = arrayOf(
            "foo",
            ""
        )

        data.forEach {
            try {
                configParser.parse(it)
                fail()
            } catch (e: IllFormedConfigException) {
                // succeeds
            }
        }
    }

    @Test
    fun testParseIllFormedPropertyValue() {
        val data = arrayOf(
            "backup-destination: 1",
            """
                backup-destination: "foo/bar"
                backup-map: 1
            """.trimIndent()
        )

        data.forEach {
            try {
                configParser.parse(it)
                fail()
            } catch (e: IllFormedPropertyValueException) {
                // succeeds
            }
        }
    }

    @Test
    fun testParseMissingPropertyValueException() {
        val data = arrayOf(
            """
                backup-map:
                  "foo": "bar"
                  "bar": "foo"
            """.trimIndent(),
            """
                backup-destination: "foo"
            """.trimIndent()
        )

        data.forEach {
            try {
                configParser.parse(it)
                fail()
            } catch (e: MissingPropertyException) {
                // succeeds
            }
        }
    }

    @Test
    fun testParseGoodConfig() {
        val data = """
            backup-destination: "foo"
            backup-map:
              "foo": "bar"
              "bar": "foo"
        """.trimIndent()

        val actualBackupConfig = configParser.parse(data)
        val expectedBackupConfig = BackupConfig(
            backupBasePath = "foo",
            originalToBackupDir = mapOf("foo" to "bar", "bar" to "foo")
        )
        assertEquals(expectedBackupConfig, actualBackupConfig)
    }
}
