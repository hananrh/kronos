@file:Suppress("UnusedReceiverParameter")

package com.hrofeh.kronos.extensions.json

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.config.Config
import com.hrofeh.kronos.config.ConfigPropertyFactory
import com.hrofeh.kronos.config.ConfigSourceResolver
import com.hrofeh.kronos.config.FeatureRemoteConfig
import kotlin.reflect.typeOf

val kronosJsonSerializer
	get() = JsonExtension.serializer

inline fun <reified T> FeatureRemoteConfig.jsonConfig(noinline block: Config<String, T>.() -> Unit) = ConfigPropertyFactory.from(
	ConfigSourceResolver.String,
	validator = { it.isNotEmpty() },
	getterAdapter = {
		try {
			kronosJsonSerializer.fromJson<T>(it, typeOf<T>())
		} catch (e: Exception) {
			Kronos.logger?.e("Failed to parse json: $it", e)
			null
		}
	},
	setterAdapter = { value ->
		value?.let {
			try {
				kronosJsonSerializer.toJson(it, typeOf<T>())
			} catch (e: Exception) {
				Kronos.logger?.e("Failed to convert to json", e)
				null
			}
		}
	},
	block = block
)
