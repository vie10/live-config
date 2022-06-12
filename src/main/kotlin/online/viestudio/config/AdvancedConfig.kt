package online.viestudio.config

import kotlinx.serialization.Serializable
import online.viestudio.paperkit.config.Comment

@Serializable
data class AdvancedConfig(
    @Comment("Delay between checks for changes. In millis.")
    val delay: Long = 100,
    @Comment("Extension of files that will be considered as config")
    val extensions: Set<String> = setOf("yaml", "yml", "json", "properties")
)