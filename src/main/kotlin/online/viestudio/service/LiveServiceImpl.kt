package online.viestudio.service

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toSet
import online.viestudio.config.AdvancedConfig.Companion.advanced
import online.viestudio.paperkit.koin.plugin
import online.viestudio.paperkit.plugin.KitPlugin
import online.viestudio.paperkit.util.safeRunWithMeasuring
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.FileFilter
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap

class LiveServiceImpl : LiveService {

    override var isEnabled: Boolean = false
    private val log get() = plugin.log
    private val plugin by plugin<KitPlugin>()
    private var job: Job? = null
    private val modificationsMap: MutableMap<String, String> = ConcurrentHashMap()
    private val plugins get() = plugin.server.pluginManager.plugins
    private val configFilter = FileFilter { it.extension in advanced.extensions }

    override suspend fun enable() {
        if (isEnabled) disable()
        isEnabled = true
        job = plugin.scope.launch {
            while (isActive) {
                reloadChanged()
                delay(advanced.delay)
            }
        }
    }

    override suspend fun disable() {
        job?.let {
            it.cancel()
            job = null
        }
        isEnabled = false
    }

    private suspend fun reloadChanged() = coroutineScope {
        plugins.forEach {
            async { it.reloadIfChanged() }.start()
        }
    }

    private suspend fun Plugin.reloadIfChanged() = supervisorScope {
        val config = dataFolder.listFiles(configFilter) ?: return@supervisorScope
        val channel = Channel<Pair<File, Boolean>>(Channel.UNLIMITED)
        config.forEach {
            async {
                val isChanged = runCatching { it.isChanged() }.getOrElse { false }
                channel.send(it to isChanged)
            }.start()
        }
        val checkedConfig = channel.receiveAsFlow().take(config.size).toSet()
        val isChanged = checkedConfig.any { it.second }
        if (!isChanged) return@supervisorScope
        log.i { "Reloading config of plugin $name.." }
        safeRunWithMeasuring {
            reloadConfig()
        }.onFailure {
            log.w { "This error relates to $name plugin, please, contact its developer." }
        }.onSuccess {
            log.i { "Config of plugin $name reloaded in $it millis." }
        }
    }

    private fun File.isChanged(): Boolean {
        val md5 = calculateMd5()
        val previousLastModified = modificationsMap[absolutePath] ?: run {
            modificationsMap[absolutePath] = md5
            return false
        }
        if (md5 == previousLastModified) return false
        modificationsMap[absolutePath] = md5
        return true
    }

    private fun File.calculateMd5(): String = with(MessageDigest.getInstance("MD5")) {
        return BigInteger(1, digest(readBytes())).toString(16).padStart(32, '0')
    }
}