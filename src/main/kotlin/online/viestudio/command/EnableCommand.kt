package online.viestudio.command

import online.viestudio.config.CommandsConfig
import online.viestudio.config.CommandsConfig.CommandConfig
import online.viestudio.config.MessagesConfig
import online.viestudio.paperkit.command.Arguments
import online.viestudio.paperkit.command.ChildCommand
import online.viestudio.paperkit.koin.config
import online.viestudio.paperkit.message.message
import online.viestudio.service.LiveService
import org.bukkit.command.CommandSender
import org.koin.core.component.inject

class EnableCommand : ChildCommand("enable") {

    override val name: String get() = config.name
    override val description: String get() = config.description
    override val permission: String get() = config.permission
    private val config: CommandConfig get() = commands.enable
    private val commands by config<CommandsConfig>()
    private val messages by config<MessagesConfig>()
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

    override suspend fun onHasNotPermission(sender: CommandSender, args: Arguments): Boolean {
        sender.message(messages.noPermission)
        return false
    }
}