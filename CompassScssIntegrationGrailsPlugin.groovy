class CompassScssIntegrationGrailsPlugin {
    def version = "0.2.5"
    def grailsVersion = "1.3.7 > *"
    def dependsOn = [:]
    def pluginExcludes = [
    ]

    def author = "Stefan Kendall"
    def authorEmail = "stefankendall@gmail.com"
    def title = "Compass SCSS/SASS compilation plugin, based on the original grass compass plugin."
    def description = '''
CompassScssIntegration is a stylesheet authoring tool that uses compass (http://compass-style.org/) to bring scss and sass support to grails.
JRuby must be installed locally to work correctly, as most functionality thin-wraps the compass gem. During run-app,
sass/scss files are watched for changes and compiled on the fly, and compilation can be invoked manually outside of run-app.
'''

    def documentation = "https://github.com/stefankendall/compass-scss-integration"

    def license = "MIT"
    def developers = [
    ]
    def scm = "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/"

    def doWithSpring = {
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def doWithWebDescriptor = { xml ->
    }

    def doWithDynamicMethods = { ctx ->
    }

    def onChange = { event ->
    }
}