includeTargets << grailsScript("Init")
includeTargets << new File("${compassScssIntegrationPluginDir}/scripts/_CompassFrameworks.groovy")
includeTargets << new File("${compassScssIntegrationPluginDir}/scripts/_GetCompassInvoker.groovy")

void displayCompassFrameworks() {
    println "\nAvailable Compass frameworks:"
    availableCompassFrameworks.each { name, frameworkInfo ->
        println "    $name : ${frameworkInfo.description}"
    }
}

target(initCompass: 'Initialize compass framework') {
    displayCompassFrameworks()

    if (args) {
        frameworkName = args.trim()
    } else {
        Ant.input(addProperty: "compass.init.framework.name", message: "Enter the framework name:")
        frameworkName = Ant.antProject.properties."compass.init.framework.name"
    }

    initCompassFramework()
}

target(initCompassFramework: 'Initialize compass framework') {
    def framework = frameworkName ? availableCompassFrameworks."$frameworkName" : null

    if (framework) {
        def config = new ConfigSlurper().parse(new File(basedir, "grails-app/conf/GrassConfig.groovy").toURL())
        def sassOutputDir = config.grass.sass_dir

        if (framework == 'blueprint') {
            compass.installBlueprint()
        }
        else {
            println "\nCopying sass stylesheets to ${sassOutputDir}"
            Ant.copy(todir: "${basedir}/${sassOutputDir}", overwrite: true) {
                fileset(dir: "${compassScssIntegrationPluginDir}/src/stylesheets/${framework.dir}")
            }
        }

        println """
Congratulations! Compass sass files have been installed.
Sass stylesheets are recompiled automatically when running 'grails run-app'.
To compile sass stylesheets manually use 'grails compile-css'.

To import your new stylesheets add the following lines of HTML (or equivalent) to your gsp:
${framework.import}
"""
    } else {
        event("StatusError", ["Cannot find specified compass framework\nUse 'grails list-compass-frameworks' to get the list of available frameworks"])
    }
}

setDefaultTarget(initCompass)
