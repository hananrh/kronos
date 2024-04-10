package com.hrofeh.myapplication.config

import com.aura.myapplication.R
import com.hrofeh.myapplication.config.source.MapConfigSource
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.stringConfig
import com.hrofeh.kronos.extensions.json.jsonConfig
import com.hrofeh.kronos.extensions.resources.defaultRes
import com.hrofeh.kronos.source.typedSource

class MainScreenConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<MapConfigSource>()

	val defaultedConfig by stringConfig {
		defaultRes(R.string.config_default)
	}

	val texts by jsonConfig<Map<String, List<String>>> {
		default = emptyMap()
	}
}
