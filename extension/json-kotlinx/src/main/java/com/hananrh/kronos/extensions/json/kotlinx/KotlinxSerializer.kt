package com.hananrh.kronos.extensions.json.kotlinx

import com.hananrh.kronos.extensions.json.util.JsonSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

class KotlinxSerializer(private val json: Json = Json.Default) : JsonSerializer {

	override fun toJson(
		obj: Any,
		type: KType
	) = json.encodeToString(serializer(type), obj)

	@Suppress("UNCHECKED_CAST")
	override fun <T> fromJson(
		jsonStr: String,
		type: KType
	) = json.decodeFromString(serializer(type) as KSerializer<T>, jsonStr)
}