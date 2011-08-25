includeTargets << new File("${compassScssIntegrationPluginDir}/scripts/_GetCompass.groovy")

eventConfigureTomcat = {
    compass.watch()
}

