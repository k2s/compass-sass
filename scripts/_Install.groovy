def getOutputFromCommand(String command) {
    def process = command.execute()
    def out = new StringBuffer()
    def err = new StringBuffer()
    process.consumeProcessOutput(out, err)
    process.waitFor()

    return out.toString()
}

def isJRubyInstalled() {
    try {
        println "JRuby version: ${getOutputFromCommand('jruby --version')}"
    }
    catch (Exception e) {
        return false
    }

    return true
}

def isGemInstalled(String gem) {
    String gems = getOutputFromCommand("jruby -S gem list")
    return gems.contains("compass")
}

def installGem(String gem) {
    Process p = "jruby -S gem install $gem".execute()
    p.consumeProcessOutput(System.out, System.err)
    p.waitFor()
}

def createGrassConfigFile() {
    println "Creating GrassConfig.groovy if not already present"
    Ant.copy(
            tofile: "${basedir}/grails-app/conf/GrassConfig.groovy", overwrite: false,
            file: "${compassScssIntegrationPluginDir}/grails-app/conf/DefaultGrassConfig.groovy")
}

createGrassConfigFile()

println "Testing to see if JRuby is installed..."
if (!isJRubyInstalled()) {
    println '*' * 20
    println "JRuby could not be found on your system. Make sure it is on your path, or this plugin will not function properly"
    println '*' * 20
    return
}

def listOfGemsToInstall = ['compass', 'rb-fsevent']
listOfGemsToInstall.each { gem ->
    println "Testing to see if ${gem} gem is installed..."
    if (!isGemInstalled(gem)) {
        println "${gem} gem not found; attempting to install automatically..."
        installGem(gem)
    }
}