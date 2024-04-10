package com.hrofeh.myapplication

import android.app.Application
import com.hrofeh.myapplication.config.source.MapConfigSource
import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.extensions.json.json
import com.hrofeh.kronos.extensions.resources.kotlinx.KotlinxSerializer
import com.hrofeh.kronos.extensions.resources.resources
import com.hrofeh.kronos.logging.AndroidLogger

internal class App : Application() {

	override fun onCreate() {
		super.onCreate()

		Kronos.init {
			logging {
				enabled = true
				logger = AndroidLogger()
			}

			configSource {
				MapConfigSource(
					"texts" to "{\"greeting\": [\"Hello\", \"World!\"]}"
				)
			}

			extensions {
				json {
					serializer = KotlinxSerializer()
				}

				resources {
					resources = applicationContext.resources
				}
			}
		}
	}
}