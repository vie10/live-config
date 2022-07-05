package online.viestudio.config

import kotlinx.serialization.Serializable
import online.viestudio.paperkit.message.message
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Serializable
data class MessagesConfig(
    val enabled: List<String> = message("{{pluginPrefix}} <{{primaryHex}}>Live reloading enabled."),
    val disabled: List<String> = message("{{pluginPrefix}} <{{primaryHex}}>Live reloading disabled."),
    val alreadyDisabled: List<String> = message("{{pluginPrefix}} <{{warnHex}}>Already disabled."),
    val alreadyEnabled: List<String> = message("{{pluginPrefix}} <{{warnHex}}>Already enabled."),
) {

    companion object {

        val KoinComponent.messages get() = get<MessagesConfig>()
    }
}