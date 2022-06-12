package online.viestudio

import online.viestudio.command.LiveConfigCommand
import online.viestudio.config.AdvancedConfig
import online.viestudio.config.CommandsConfig
import online.viestudio.config.MessagesConfig
import online.viestudio.paperkit.command.CommandsDeclaration
import online.viestudio.paperkit.config.ConfigurationDeclaration
import online.viestudio.paperkit.koin.KoinModulesContainer
import online.viestudio.paperkit.logger.KitLogger
import online.viestudio.paperkit.plugin.BaseKitPlugin
import online.viestudio.service.LiveService
import online.viestudio.service.LiveServiceImpl
import org.koin.dsl.bind

class LiveConfigPlugin : BaseKitPlugin() {

    override suspend fun onStart() {
        log.banner()
    }

    private fun KitLogger.banner() = i {
        """
            
            $name v. $version.
            Thanks for supporting our team.
            With love, (c) VieStudio.
        """
    }

    override fun KoinModulesContainer.export() {
        module {
            single { LiveServiceImpl() } bind LiveService::class
        }
    }

    override fun CommandsDeclaration.declareCommands() {
        register { LiveConfigCommand() }
    }

    override fun ConfigurationDeclaration.declareConfiguration() {
        MessagesConfig::class loadFrom (file("messages.yml") or defaults(MessagesConfig::class))
        AdvancedConfig::class loadFrom (file("advanced.yml") or defaults(AdvancedConfig::class))
        CommandsConfig::class loadFrom (file("commands.yml") or defaults(CommandsConfig::class))
    }
}