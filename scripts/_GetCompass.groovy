GroovyClassLoader loader = new GroovyClassLoader(getClass().getClassLoader())
Class compassInvokerClass = loader.parseClass(
        new File("$compassScssIntegrationPluginDir/src/groovy/com/stefankendall/CompassInvoker.groovy"))
Class javaProcessKillerClass = loader.parseClass(
        new File("$compassScssIntegrationPluginDir/src/groovy/com/stefankendall/JavaProcessKiller.groovy"))

compass = compassInvokerClass.newInstance(new File("$basedir/grails-app/conf/GrassConfig.groovy"), javaProcessKillerClass.newInstance())