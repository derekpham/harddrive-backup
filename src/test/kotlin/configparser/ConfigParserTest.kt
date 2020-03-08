package configparser

import exception.parser.IllFormedConfigException
import exception.parser.IllFormedPropertyValueException
import exception.parser.MissingPropertyException
import model.BackupConfig
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.junit.rules.ExpectedException
import java.util.stream.Stream
import kotlin.reflect.KClass


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
            "backup-base-path: 1",
            """
                backup-base-path: "foo/bar"
                backup: 1
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
                backup:
                  "foo": "bar"
                  "bar": "foo"
            """.trimIndent(),
            """
                backup-base-path: "foo"
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
            backup-base-path: "foo"
            backup:
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