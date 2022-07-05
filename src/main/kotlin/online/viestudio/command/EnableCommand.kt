package online.viestudio.command

import online.viestudio.config.CommandsConfig.Companion.commands
import online.viestudio.config.MessagesConfig.Companion.messages
import online.viestudio.paperkit.command.Arguments
import online.viestudio.paperkit.command.ChildCommand
import online.viestudio.paperkit.command.CommandConfig
import online.viestudio.paperkit.message.message
import online.viestudio.service.LiveService
import org.bukkit.command.CommandSender
import org.koin.core.component.inject

class EnableCommand : ChildCommand() {

    override val config: CommandConfig get() = commands.enable
    private val liveService by inject<LiveService>()

    override suspend fun onExecute(sender: CommandSender, args: Arguments): Boolean {
        if (liveService.isEnabled) {
            sender.message(messages.alreadyEnabled)
            return true
        }
        liveService.enable()
        sender.message(messages.enabled)
        return true
    }
}