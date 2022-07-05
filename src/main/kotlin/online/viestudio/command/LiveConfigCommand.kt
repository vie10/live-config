package online.viestudio.command

import online.viestudio.config.CommandsConfig.Companion.commands
import online.viestudio.paperkit.command.CommandConfig
import online.viestudio.paperkit.command.ParentCommand

class LiveConfigCommand : ParentCommand(
    subCommands = listOf(EnableCommand(), DisableCommand())
) {

    override val config: CommandConfig get() = commands.liveConfig
}