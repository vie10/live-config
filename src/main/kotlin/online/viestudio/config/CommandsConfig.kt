package online.viestudio.config

import kotlinx.serialization.Serializable
import online.viestudio.paperkit.config.Comment

@Serializable
data class CommandsConfig(
    @Comment("Main command of the plugin")
    val liveConfig: CommandConfig = CommandConfig(
        name = "live-config",
        aliases = listOf("lc"),
        description = "Command to manipulate LiveConfig plugin",
        permission = "liveconfig.execute"
    ),
    @Comment("Subcommand to enable live config reloading")
    val enable: CommandConfig = CommandConfig(
        name = "enable",
        description = "Enable live config reloading",
        permission = "liveconfig.enable"
    ),
    @Comment("Subcommand to disable live config reloading")
    val disable: CommandConfig = CommandConfig(
        name = "disable",
        description = "Disable live config reloading",
        permission = "liveconfig.disable"
    ),
) {

    @Serializable
    data class CommandConfig(
        val name: String,
        val aliases: List<String>? = null,
        val description: String,
        val permission: String,
    )
}