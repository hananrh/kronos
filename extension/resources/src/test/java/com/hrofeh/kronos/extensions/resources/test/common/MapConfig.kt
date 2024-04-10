package com.hrofeh.kronos.extensions.resources.test.common

import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.source.typedSource

class MapConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<MapSource>()
}

fun mapConfig() = MapConfig()