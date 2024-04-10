package com.hrofeh.kronos.extensions.resources.common

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.extensions.json.json
import com.hrofeh.kronos.extensions.resources.kotlinx.KotlinxSerializer
import com.hrofeh.kronos.source.typedSource
import org.spekframework.spek2.dsl.Root

fun kronosTest(block: Root.() -> Unit): Root.() -> Unit {
	return {
		beforeGroup {
			Kronos.init {
				logging {
					logger = ConsoleLogger()
				}
				extensions {
					json {
						serializer = KotlinxSerializer()
					}
				}
			}

			withRemoteMap()
		}

		block()
	}
}

class MapConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<MapSource>()
}

fun mapConfig() = MapConfig()

fun withRemoteMap(vararg pairs: Pair<String, Any?>) {
	Kronos.configSourceRepository.addSource(MapSource(map = mutableMapOf(*pairs)))
}

fun withRemoteMap2(vararg pairs: Pair<String, Any?>) {
	Kronos.configSourceRepository.addSource(MapSource2(mutableMapOf(*pairs)))
}