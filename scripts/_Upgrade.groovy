def updateGem(String gem) {
    Process p = "jruby -S gem update $gem".execute()
    p.consumeProcessOutput(System.out, System.err)
    p.waitFor()
}

println "Updating Compass plugins"
def listOfGemsToUpgrade = ['compass', 'rb-fsevent']
listOfGemsToUpgrade.each {
    updateGem(it)
}