package online.viestudio.command

import online.viestudio.config.CommandsConfig
import online.viestudio.config.CommandsConfig.CommandConfig
import online.viestudio.config.MessagesConfig
import online.viestudio.paperkit.command.Arguments
import online.viestudio.paperkit.command.ParentCommand
import online.viestudio.paperkit.koin.config
import online.viestudio.paperkit.message.message
import org.bukkit.command.CommandSender

class LiveConfigCommand : ParentCommand(
    name = "live-config",
    subCommands = listOf(EnableCommand(), DisableCommand())
) {

    override val name: String get() = config.name
    override val description: String get() = config.description
    override val aliases: List<String> get() = config.aliases.orEmpty()
    override val permission: String get() = config.permission
    private val config: CommandConfig get() = commands.liveConfig
    private val commands by config<CommandsConfig>()
    private val messages by config<MessagesConfig>()

    override suspend fun onHasNotPermission(sender: CommandSender, args: Arguments): Boolean {
        sender.message(messages.noPermission)
        return false
    }
}