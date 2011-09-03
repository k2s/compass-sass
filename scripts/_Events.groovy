includeTargets << new File("${compassScssIntegrationPluginDir}/scripts/_GetCompassInvoker.groovy")

eventConfigureTomcat = {
    compass.watch()
}

