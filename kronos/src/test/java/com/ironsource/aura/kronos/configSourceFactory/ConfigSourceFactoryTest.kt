package com.ironsource.aura.kronos.configSourceFactory

import com.ironsource.aura.kronos.Kronos
import com.ironsource.aura.kronos.common.ConsoleLogger
import com.ironsource.aura.kronos.common.MapSource
import com.ironsource.aura.kronos.common.mockContext
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.type.intConfig
import com.ironsource.aura.kronos.source.identifiableTypedSource
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigSourceFactoryTest : Spek({

    beforeGroup {
        Kronos.init {
            context = mockContext()
            logging {
                logger = ConsoleLogger()
            }
            configSourceFactory<MapSource, String>(MapSource::class) {
                MapSource(it, mutableMapOf("prefixSomeInt" to 1))
            }
        }
    }

    describe("Using config source with prefix") {

        class Config : FeatureRemoteConfig {

            override val sourceDefinition = identifiableTypedSource<MapSource, String>("prefix")

            val someInt by intConfig {
                default = 0
            }
        }

        val config = Config()

        it("Should return configured value with prefix") {
            assertEquals(1, config.someInt)
        }
    }
})
