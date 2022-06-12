package online.viestudio.config

import kotlinx.serialization.Serializable
import online.viestudio.paperkit.message.Message
import online.viestudio.paperkit.message.message

@Serializable
data class MessagesConfig(
    val enabled: Message = message("{{pluginPrefix}} <{{primaryHex}}>Live reloading enabled."),
    val disabled: Message = message("{{pluginPrefix}} <{{primaryHex}}>Live reloading disabled."),
    val alreadyDisabled: Message = message("{{pluginPrefix}} <{{warnHex}}>Already disabled."),
    val alreadyEnabled: Message = message("{{pluginPrefix}} <{{warnHex}}>Already enabled."),
    val noPermission: Message = message("{{pluginPrefix}} <{{errorHex}}>You haven't permission to do that!")
)