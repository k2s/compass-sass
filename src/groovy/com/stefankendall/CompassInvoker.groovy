package com.stefankendall

class CompassInvoker {
    def config
    def javaProcessKiller

    public CompassInvoker(File grassConfigLocation, def javaProcessKiller) {
        config = new ConfigSlurper().parse(grassConfigLocation.toURL())
        this.javaProcessKiller = javaProcessKiller
    }

    public CompassInvoker(Map config, def javaProcessKiller ) {
        this.config = config
        this.javaProcessKiller = javaProcessKiller
    }

    public void compile(callback) {
        def sass_dir = config.grass?.sass_dir
        def css_dir = config.grass?.css_dir
        def images_dir = config.grass?.images_dir
        def relative_assets = config.grass?.relative_assets == null ? true : config.compass?.relative_assets
        def output_style = config.grass?.output_style ?: 'compact'

        ensureParameterSet sass_dir, "sass_dir is not set (GrassConfig.groovy)", callback
        ensureParameterSet css_dir, "css_dir is not set (GrassConfig.groovy)", callback
        ensureParameterSet images_dir, "images_dir is not set (GrassConfig.groovy)", callback
        ensureParameterSet output_style, "output_style is not set (GrassConfig.groovy)", callback

        println """
            sass_dir = '${sass_dir}'
            css_dir = '${css_dir}'
            images_dir = '${images_dir}'
            relative_assets = ${relative_assets}
            output_style = ${output_style}
        """

        println "Compiling sass stylesheets..."

        def sassCompileCommandLineArgs = ['compile',
                '--sass-dir', "${sass_dir}",
                '--css-dir', "${css_dir}",
                '--images-dir', "${images_dir}",
                relative_assets ? "--relative-assets" : "",
                '--output-style', "${output_style}"]

        def p = runCompassCommand(sassCompileCommandLineArgs)
        p?.waitFor()
    }

    public void watch() {
        runCompassCommandInThread(['watch', '--sass-dir', config.grass.sass_dir,
                '--css-dir', config.grass.css_dir, '--images-dir', config.grass.images_dir,
                '--output-style', config.grass.output_style])
    }

    protected Process runCompassCommand(def compassArgs, PrintStream output = System.out) {
        String[] command = ['jruby', '-S', 'compass', compassArgs].flatten()
        println "Executing: ${command.join(' ')}"

        Process p = null
        try {
            p = command.execute()
            p.consumeProcessOutput(output, System.err)
        }
        catch (IOException e) {
            System.err.println("JRuby could not be started. Make sure 'jruby' exists on the PATH and try again.")
            System.err.println("No SCSS/SASS compilation will be performed.")
        }

        return p
    }

    private static boolean shutdownHookAdded = false
    protected def runCompassCommandInThread(def compassArgs) {
        if (!shutdownHookAdded) {
            addShutdownHookToKillCompass()
        }

        Thread.start {
            def process = runCompassCommand(compassArgs)
            process?.waitFor()
        }
    }

    protected void ensureParameterSet(parameter, message, callback) {
        if (!parameter) {
            callback(message)
        }
    }

    protected addShutdownHookToKillCompass = {->
        Runtime.runtime.addShutdownHook {
            killCompass()
        }
    }

    protected def killCompass() {
        println "Attempting to kill compass threaded processes"
        javaProcessKiller.killAll('org/jruby/Main -S compass')
    }
}