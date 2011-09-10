includeTargets << grailsScript("Init")
includeTargets << new File("${compassSassPluginDir}/scripts/_GetCompassInvoker.groovy")

def updateGem(String gem) {
    println "Updating gem: $gem"
    Process p = "jruby -S gem update $gem".execute()
    p.consumeProcessOutput(System.out, System.err)
    p.waitFor()
}

target(updateCompass: 'Update compass and its required gems') {
    println "Updating Compass plugins"
    def listOfGemsToUpgrade = ['compass', 'rb-fsevent']
    listOfGemsToUpgrade.each {
        updateGem(it)
    }
}

setDefaultTarget(updateCompass)
