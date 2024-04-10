package com.hrofeh.kronos.customConfigs

import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.Config
import com.hrofeh.kronos.config.ConfigPropertyFactory
import com.hrofeh.kronos.config.ConfigSourceResolver
import com.hrofeh.kronos.config.FeatureRemoteConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

data class Label(val value: String)

@Suppress("UnusedReceiverParameter")
fun FeatureRemoteConfig.labelConfig(block: Config<String, Label>.() -> Unit) =
	ConfigPropertyFactory.from(
		ConfigSourceResolver.String,
		validator = { it.isNotEmpty() },
		getterAdapter = { Label(it) },
		setterAdapter = { it.value },
		block = block
	)

object UserCustomConfigTest : Spek(kronosTest {

	class Config : FeatureRemoteConfig by mapConfig() {

		var someLabel by labelConfig {
			default = Label("default")
			cached = false
		}
	}

	val config = Config()

	describe("Enum config field get") {

		it("Should return remote value when valid") {
			withRemoteMap(
				"someLabel" to "remote"
			)

			assertEquals(Label("remote"), config.someLabel)
		}

		it("Should return default value when invalid") {
			withRemoteMap(
				"someLabel" to ""
			)

			assertEquals(Label("default"), config.someLabel)
		}
	}

	describe("Enum config field set") {

		it("Should return set value") {
			config.someLabel = Label("override")
			assertEquals(Label("override"), config.someLabel)
		}
	}
})

