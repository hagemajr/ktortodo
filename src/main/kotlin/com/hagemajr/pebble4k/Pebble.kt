package com.hagemajr.pebble4k

import com.mitchellbosecke.pebble.template.PebbleTemplate
import com.mitchellbosecke.pebble.PebbleEngine
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.util.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.cio.*
import kotlinx.coroutines.io.*
import java.util.*

class PebbleContent(
    val template: String,
    val model: Map<String, Any>,
    val etag: String? = null,
    val contentType: ContentType = ContentType.Text.Html.withCharset(Charsets.UTF_8)
)

/**
 * Adds Pebble engine.
 */
class Pebble(configuration: Configuration) {
    // get an immutable snapshot of a configuration values
    private val templatePath = configuration.templatePath
    private val engine = configuration.engine

    // Feature configuration class
    class Configuration {
        // mutable properties with default values so user can modify it
        var templatePath = "templates/"
        var strictVariables = false
        var engine = PebbleEngine.Builder()
        .strictVariables(strictVariables)
        .defaultLocale(Locale.getDefault())
        .build()
    }

    private fun process(content: PebbleContent): PebbleOutgoingContent {
        return PebbleOutgoingContent(
            engine.getTemplate(templatePath + content.template),
            content.model,
            content.etag,
            content.contentType
        )
    }

    /**
     * Installable feature for [Pebble].
     */
    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Pebble.Configuration, Pebble> {
        override val key = AttributeKey<Pebble>("Pebble")
        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): Pebble {
            // Call user code to configure a feature
            val configuration = Configuration().apply(configure)

            // Create a feature instance
            val feature = Pebble(configuration)

            // Install an interceptor that will be run on each call and call feature instance
            pipeline.sendPipeline.intercept(ApplicationSendPipeline.Transform) {
                if (it is PebbleContent) {
                    val response = feature.process(it)
                    proceedWith(response)
                }
            }

            // Return a feature instance so that client code can use it
            return feature
        }
    }

    private class PebbleOutgoingContent(
        val template: PebbleTemplate,
        val context: Map<String, Any>,
        etag: String?,
        override val contentType: ContentType
    ) : OutgoingContent.WriteChannelContent() {
        override suspend fun writeTo(channel: ByteWriteChannel) {
            channel.bufferedWriter(contentType.charset() ?: Charsets.UTF_8).use {
                template.evaluate(it, context)
            }
        }

        init {
            if (etag != null)
                versions += EntityTagVersion(etag)
        }
    }
}