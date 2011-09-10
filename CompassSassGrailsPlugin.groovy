class CompassSassGrailsPlugin {
    def version = "0.3.1"
    def grailsVersion = "1.3.7 > *"
    def dependsOn = [:]
    def pluginExcludes = [
    ]

    def author = "Stefan Kendall"
    def authorEmail = "stefankendall@gmail.com"
    def title = "Compass Framework - SASS/SCSS support"
    def description = '''
<a href='http://compass-style.org/'>Compass</a>, SASS and SCSS support for Grails. Automatically compiles .scss/.sass during run-app, and adds other framework functionality.
'''

    def documentation = "http://grails.org/plugin/compass-sass"

    def license = "APACHE"
    def developers = [
    ]
    def scm = [url: "https://github.com/stefankendall/compass-scss-integration"]

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