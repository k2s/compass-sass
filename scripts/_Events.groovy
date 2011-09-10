includeTargets << new File("${compassSassPluginDir}/scripts/_GetCompassInvoker.groovy")

eventConfigureTomcat = {
    compass.watch()
}

