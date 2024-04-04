package com.hananrh.kronos.config

import com.hananrh.kronos.source.ConfigSource


class ConfigSourceResolver<T> private constructor(
	val sourceGetter: ConfigSource.(String) -> T?,
	val sourceSetter: ConfigSource.(String, T?) -> Unit
) {

	companion object {
		val Int = ConfigSourceResolver(
			ConfigSource::getInteger,
			ConfigSource::putInteger
		)

		val Long = ConfigSourceResolver(
			ConfigSource::getLong,
			ConfigSource::putLong
		)

		val Float = ConfigSourceResolver(
			ConfigSource::getFloat,
			ConfigSource::putFloat
		)

		val Boolean = ConfigSourceResolver(
			ConfigSource::getBoolean,
			ConfigSource::putBoolean
		)

		val String = ConfigSourceResolver(
			ConfigSource::getString,
			ConfigSource::putString
		)

		val StringSet = ConfigSourceResolver(
			ConfigSource::getStringSet,
			ConfigSource::putStringSet
		)

		val Any = ConfigSourceResolver(
			ConfigSource::getAny,
			ConfigSource::putAny
		)
	}
}