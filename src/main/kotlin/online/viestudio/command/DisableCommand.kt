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

class DisableCommand : ChildCommand() {

    override val config: CommandConfig get() = commands.disable
    private val liveService by inject<LiveService>()

    override suspend fun onExecute(sender: CommandSender, args: Arguments): Boolean {
        if (!liveService.isEnabled) {
            sender.message(messages.alreadyDisabled)
            return true
        }
        liveService.disable()
        sender.message(messages.disabled)
        return true
    }
}