package com.hananrh.kronos.constraint

import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.constraint.FallbackPolicy
import com.hananrh.kronos.config.constraint.maxValue
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object MaxValueConstraintTest : Spek(kronosTest {

	class DefaultFallbackConfig : FeatureRemoteConfig by mapConfig() {

		val someInt by intConfig {
			default = 1
			maxValue = 2
			cached = false
		}
		val someLong by longConfig {
			default = 1
			maxValue = 2
			cached = false
		}
		val someFloat by floatConfig {
			default = 1f
			maxValue = 2f
			cached = false
		}
	}

	val defaultFallbackConfig = DefaultFallbackConfig()

	describe("Remote value greater than maxValue should fallback to default") {
		beforeGroup {
			withRemoteMap(
				"someInt" to 3,
				"someLong" to 3L,
				"someFloat" to 3f
			)
		}

		it("Should return default - intConfig") {
			assertEquals(1, defaultFallbackConfig.someInt)
		}
		it("Should return default - longConfig") {
			assertEquals(1L, defaultFallbackConfig.someLong)
		}
		it("Should return default - floatConfig") {
			assertEquals(1f, defaultFallbackConfig.someFloat)
		}
	}

	describe("Remote value equal or smaller than maxValue should be used") {
		beforeGroup {
			withRemoteMap(
				"someInt" to 0,
				"someLong" to 0L,
				"someFloat" to 0f
			)
		}

		it("Should return remote value - intConfig") {
			assertEquals(0, defaultFallbackConfig.someInt)
		}
		it("Should return remote value - longConfig") {
			assertEquals(0L, defaultFallbackConfig.someLong)
		}
		it("Should return remote value - floatConfig") {
			assertEquals(0f, defaultFallbackConfig.someFloat)
		}
	}

	describe("Remote value greater than maxValue with range fallback should fall to it") {
		class RangeFallbackConfig : FeatureRemoteConfig by mapConfig() {
			val someInt by intConfig {
				default = 1
				maxValue {
					value = 2
					fallbackPolicy = FallbackPolicy.RANGE
				}
			}
			val someLong by longConfig {
				default = 1
				maxValue {
					value = 2L
					fallbackPolicy = FallbackPolicy.RANGE
				}
			}
			val someFloat by floatConfig {
				default = 1f
				maxValue {
					value = 2f
					fallbackPolicy = FallbackPolicy.RANGE
				}
			}
		}

		val rangeFallbackConfig = RangeFallbackConfig()

		beforeGroup {
			withRemoteMap(
				"someInt" to 3,
				"someLong" to 3L,
				"someFloat" to 3f
			)
		}

		it("Should return maxValue - intConfig") {
			assertEquals(2, rangeFallbackConfig.someInt)
		}
		it("Should return maxValue - longConfig") {
			assertEquals(2L, rangeFallbackConfig.someLong)
		}
		it("Should return maxValue - floatConfig") {
			assertEquals(2f, rangeFallbackConfig.someFloat)
		}
	}
})