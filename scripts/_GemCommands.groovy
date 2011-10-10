getOutputFromCommand = { String command ->
    def process = command.execute()
    def out = new StringBuffer()
    def err = new StringBuffer()
    process.consumeProcessOutput(out, err)
    process.waitFor()

    return out.toString()
}

isGemInstalled = { String gem ->
    String gems = getOutputFromCommand("jruby -S gem list")
    return gems.contains(gem)
}

installGem = { String gem ->
    Process p = "jruby -S gem install $gem".execute()
    p.consumeProcessOutput(System.out, System.err)
    p.waitFor()
}

updateGem = { String gem ->
    println "Updating gem: $gem"
    Process p = "jruby -S gem update $gem".execute()
    p.consumeProcessOutput(System.out, System.err)
    p.waitFor()
}